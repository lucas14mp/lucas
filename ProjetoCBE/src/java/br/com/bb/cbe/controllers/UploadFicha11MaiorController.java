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
        Part filePart = req.getPart("arquivoExcel");
        
        // Correção Java 8: Substituindo Map.of por HashMap
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
                
                // 1. Empresa (Coluna A - Index 0)
                String nomeEmpresa = formatter.formatCellValue(row.getCell(0)).trim();
                
                // Ignora cabeçalho ou linhas vazias
                if (nomeEmpresa.isEmpty() || nomeEmpresa.equalsIgnoreCase("Empresa")) continue;

                int idEmpresa = buscarIdEmpresa(conn, nomeEmpresa);
                if (idEmpresa == -1) {
                    throw new Exception("Empresa não encontrada no sistema: " + nomeEmpresa);
                }

                // 2. Possui Cotação (Coluna B - Index 1)
                boolean possuiCotacao = "SIM".equalsIgnoreCase(formatter.formatCellValue(row.getCell(1)).trim());

                // 3. Moeda (Coluna C - Index 2)
                String nomeMoeda = formatter.formatCellValue(row.getCell(2)).trim();
                int idMoeda = buscarIdMoeda(conn, nomeMoeda);
                if (idMoeda == -1) {
                    throw new Exception("Moeda não encontrada: " + nomeMoeda);
                }

                // 4. Método Valoração (Coluna D - Index 3)
                String metodo = formatter.formatCellValue(row.getCell(3)).trim();

                // 5. Controla Outras (Coluna E - Index 4)
                // ATENÇÃO: Ajustado conforme sua ordem pedida
                boolean controlaOutras = "SIM".equalsIgnoreCase(formatter.formatCellValue(row.getCell(4)).trim());

                // Leitura dos Valores Numéricos (Colunas F até P -> Index 5 a 15)
                double valorEmpresa = lerNumero(formatter, row.getCell(5));
                double patrimonioTotal = lerNumero(formatter, row.getCell(6));
                double percCapital = lerNumero(formatter, row.getCell(7));
                double percVoto = lerNumero(formatter, row.getCell(8));
                double ativo = lerNumero(formatter, row.getCell(9));
                double passivo = lerNumero(formatter, row.getCell(10));
                double valorLucroPrej = lerNumero(formatter, row.getCell(11));
                double resNaoRecorrentes = lerNumero(formatter, row.getCell(12));
                double resReavaliacoes = lerNumero(formatter, row.getCell(13));
                double resVarCambial = lerNumero(formatter, row.getCell(14));
                double lucroDistribuido = lerNumero(formatter, row.getCell(15));

                // Monta o JSON com os nomes exatos das colunas do banco
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
                item.put("participacao_capital_social", percCapital); // percentual_capital
                item.put("porcento_poder_voto", percVoto); // percentual_voto
                item.put("ativo_database", ativo); // ativo
                item.put("passivo_exigivel", passivo);
                item.put("valor_total_lucro_preju_liquido", valorLucroPrej);
                item.put("result_liq_itens_nao_recorrentes", resNaoRecorrentes);
                item.put("result_liq_reavaliacoes", resReavaliacoes);
                item.put("result_liq_variacao_cambial", resVarCambial);
                item.put("lucro_distribuido", lucroDistribuido);

                lista.add(item);
            }
            out.print(gson.toJson(lista));

        } catch (Exception e) {
            e.printStackTrace();
            // Correção Java 8: HashMap em vez de Map.of
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            out.print(gson.toJson(erro));
        } finally {
            if(conn != null) Conexao.fecharConexao(conn, null, null);
        }
    }

    // --- Métodos Auxiliares ---
    
    private int buscarIdEmpresa(Connection conn, String nome) throws SQLException {
        // CORREÇÃO: Mudado de 'nome' para 'nome_empresa'
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

    private double lerNumero(DataFormatter f, Cell c) {
        if (c == null) return 0.0;
        String v = f.formatCellValue(c).trim().replace("R$", "").replace("%", "").trim();
        if (v.isEmpty() || v.equals("-")) return 0.0;
        
        // Remove pontos de milhar e troca virgula decimal por ponto
        v = v.replace(".", "").replace(",", ".");
        try { return Double.parseDouble(v); } catch (Exception e) { return 0.0; }
    }
}