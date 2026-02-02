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
        
        if (filePart == null || filePart.getSize() == 0) {
            out.print(gson.toJson(Map.of("erro", "Nenhum arquivo enviado.")));
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
                String nomeEmpresa = formatter.formatCellValue(row.getCell(0)).trim();
                
                // Ignora cabeçalho ou linhas vazias
                if (nomeEmpresa.isEmpty() || nomeEmpresa.equalsIgnoreCase("Empresa")) continue;

                int idEmpresa = buscarIdEmpresa(conn, nomeEmpresa);
                if (idEmpresa == -1) throw new Exception("Empresa não encontrada no banco: " + nomeEmpresa);

                String nomeMoeda = formatter.formatCellValue(row.getCell(1)).trim();
                int idMoeda = buscarIdMoeda(conn, nomeMoeda);
                if (idMoeda == -1) throw new Exception("Moeda não encontrada: " + nomeMoeda);

                Map<String, Object> item = new HashMap<>();
                item.put("id_empresa", idEmpresa);
                item.put("nome_empresa", nomeEmpresa);
                item.put("id_moeda", idMoeda);
                item.put("nome_moeda", nomeMoeda);
                item.put("patrimonio_liquido", lerNumero(formatter, row.getCell(2)));
                item.put("percentual_capital", lerNumero(formatter, row.getCell(3)));
                item.put("percentual_voto", lerNumero(formatter, row.getCell(4)));
                item.put("ativo", lerNumero(formatter, row.getCell(5)));
                item.put("passivo", lerNumero(formatter, row.getCell(6)));
                item.put("resultado_recorrente", lerNumero(formatter, row.getCell(7)));
                item.put("resultado_reavaliacao", lerNumero(formatter, row.getCell(8)));
                item.put("resultado_distribuido", lerNumero(formatter, row.getCell(9)));
                
                String controla = formatter.formatCellValue(row.getCell(10)).trim();
                item.put("controla_outras", "SIM".equalsIgnoreCase(controla));

                lista.add(item);
            }
            out.print(gson.toJson(lista));

        } catch (Exception e) {
            e.printStackTrace();
            out.print(gson.toJson(Map.of("erro", e.getMessage())));
        } finally {
            if(conn != null) Conexao.fecharConexao(conn, null, null);
        }
    }

    private int buscarIdEmpresa(Connection conn, String nome) throws SQLException {
        // Tabela correta de empresas
        String sql = "SELECT id_empresa FROM empresa WHERE nome = ?"; 
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
        String v = f.formatCellValue(c).trim().replace(".", "").replace(",", ".");
        try { return v.isEmpty() ? 0.0 : Double.parseDouble(v); } catch (Exception e) { return 0.0; }
    }
}