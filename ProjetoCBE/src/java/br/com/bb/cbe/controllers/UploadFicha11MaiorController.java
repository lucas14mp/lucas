package br.com.bb.cbe.controllers;

import br.com.bb.cbe.conexao.Conexao;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.poi.ss.usermodel.*;

@WebServlet("/UploadFicha11MaiorController")
@MultipartConfig
public class UploadFicha11MaiorController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json"); 
        resp.setCharacterEncoding("UTF-8");
        
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        
        try {
            Part filePart = req.getPart("arquivoExcel");
            
            if (filePart == null || filePart.getSize() == 0) {
                Map<String, String> erro = new HashMap<>();
                erro.put("erro", "Nenhum arquivo enviado.");
                out.print(gson.toJson(erro));
                return;
            }

            List<Map<String, Object>> lista = new ArrayList<>();
            Connection conn = null;

            try (InputStream fileContent = filePart.getInputStream()) {
                conn = Conexao.conectar();
                DataFormatter formatter = new DataFormatter();
                Workbook workbook = WorkbookFactory.create(fileContent);
                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> iter = sheet.iterator();

                while (iter.hasNext()) {
                    Row row = iter.next();
                    
                    // 1. Empresa
                    String nomeEmpresa = formatter.formatCellValue(row.getCell(0)).trim();
                    nomeEmpresa = nomeEmpresa.replace(",", "");
                    if (nomeEmpresa.isEmpty() || nomeEmpresa.equalsIgnoreCase("Empresa")) continue;

                    int idEmpresa = buscarIdEmpresa(conn, nomeEmpresa);
                    if (idEmpresa == -1) {
                        throw new Exception("Empresa não encontrada no sistema: " + nomeEmpresa);
                    }

                    // 2. Possui Cotação (Usa o parseBooleanSeguro para permitir NULL)
                    Boolean possuiCotacao = parseBooleanSeguro(formatter.formatCellValue(row.getCell(1)));

                    // 3. Moeda
                    String nomeMoeda = formatter.formatCellValue(row.getCell(2)).trim();
                    int idMoeda = buscarIdMoeda(conn, nomeMoeda);
                    if (idMoeda == -1) {
                        throw new Exception("Moeda não encontrada: " + nomeMoeda);
                    }

                    // 4. Método Valoração
                    String metodo = formatter.formatCellValue(row.getCell(3)).trim();

                    // 5. Controla Outras (Usa o parseBooleanSeguro para permitir NULL)
                    Boolean controlaOutras = parseBooleanSeguro(formatter.formatCellValue(row.getCell(4)));
                    
                    // Usa os métodos seguros retornando Double (com D maiúsculo) para permitir NULL
                    Double valorEmpresa = parseDoubleSeguro(formatter.formatCellValue(row.getCell(5)));
                    Double patrimonioTotal = parseDoubleSeguro(formatter.formatCellValue(row.getCell(6)));
                    Double percCapital = parseDoubleSeguro(formatter.formatCellValue(row.getCell(7)));
                    Double percVoto = parseDoubleSeguro(formatter.formatCellValue(row.getCell(8)));
                    Double ativo = parseDoubleSeguro(formatter.formatCellValue(row.getCell(9)));
                    Double passivo = parseDoubleSeguro(formatter.formatCellValue(row.getCell(10)));
                    Double valorLucroPrej = parseDoubleSeguro(formatter.formatCellValue(row.getCell(11)));
                    Double resNaoRecorrentes = parseDoubleSeguro(formatter.formatCellValue(row.getCell(12)));
                    Double resReavaliacoes = parseDoubleSeguro(formatter.formatCellValue(row.getCell(13)));
                    Double resVarCambial = parseDoubleSeguro(formatter.formatCellValue(row.getCell(14)));
                    Double lucroDistribuido = parseDoubleSeguro(formatter.formatCellValue(row.getCell(15)));

                    Map<String, Object> item = new HashMap<>();
                    item.put("id_empresa", idEmpresa);
                    item.put("nome_empresa", nomeEmpresa);
                    item.put("possui_cotacao_em_bolsa", possuiCotacao);
                    item.put("id_moeda", idMoeda);
                    item.put("nome_moeda", nomeMoeda);
                    item.put("metodo_valoracao", metodo);
                    item.put("controla_empresas", controlaOutras);
                    
                    item.put("valor_empresa", valorEmpresa);
                    item.put("patrimonio_total", patrimonioTotal);
                    item.put("participacao_capital_social", percCapital); 
                    item.put("porcento_poder_voto", percVoto); 
                    item.put("ativo_database", ativo); 
                    item.put("passivo_exigivel", passivo);
                    item.put("valor_total_lucro_preju_liquido", valorLucroPrej);
                    item.put("result_liq_itens_nao_recorrentes", resNaoRecorrentes);
                    item.put("result_liq_reavaliacoes", resReavaliacoes);
                    item.put("result_liq_variacao_cambial", resVarCambial);
                    item.put("lucro_distribuido", lucroDistribuido);

                    lista.add(item);
                }
                out.print(gson.toJson(lista));

            } finally {
                if(conn != null) Conexao.fecharConexao(conn, null, null);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", e.getMessage() != null ? e.getMessage() : "Erro interno crítico: " + e.toString());
            out.print(gson.toJson(erro));
        }
    }

    private int buscarIdEmpresa(Connection conn, String nome) throws SQLException {
        String sql = "SELECT id_empresa FROM empresa WHERE nome_empresa = ?"; 
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nome);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getInt("id_empresa");
            }
        }
        return -1;
    }

    private int buscarIdMoeda(Connection conn, String nome) throws SQLException {
        String sql = "SELECT id_moeda FROM moeda WHERE nome = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nome);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getInt("id_moeda");
            }
        }
        return -1;
    }

    private Double parseDoubleSeguro(String valorStr) {
        if (valorStr == null || valorStr.trim().isEmpty() || valorStr.equals("-") || valorStr.toUpperCase().contains("VALOR PL")) {
            return null;
        }
        try {
            String val = valorStr.replace("R$", "").replace("%", "").trim();
            if (val.contains(",") && val.contains(".")) {
                val = val.replace(".", "").replace(",", ".");
            } else if (val.contains(",")) {
                val = val.replace(",", ".");
            }
            return Double.parseDouble(val);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Boolean parseBooleanSeguro(String valorStr) {
        if (valorStr == null || valorStr.trim().isEmpty() || valorStr.equals("-") || valorStr.toUpperCase().contains("VALOR PL")) {
            return null;
        }
        return valorStr.trim().equalsIgnoreCase("SIM");
    }
}