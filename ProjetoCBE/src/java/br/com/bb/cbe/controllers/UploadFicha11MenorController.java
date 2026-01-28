package br.com.bb.cbe.controllers;

import br.com.bb.cbe.conexao.Conexao;
import com.google.gson.Gson; // Certifique-se de ter o GSON importado
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

@WebServlet("/UploadFicha11MenorController")
@MultipartConfig
public class UploadFicha11MenorController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json"); 
        resp.setCharacterEncoding("UTF-8");

        Part filePart = req.getPart("arquivoExcel");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();

        if (filePart == null || filePart.getSize() == 0) {
            out.print(gson.toJson(Map.of("erro", "Nenhum arquivo enviado.")));
            return;
        }

        List<Map<String, Object>> listaParaFrontend = new ArrayList<>();
        InputStream fileContent = filePart.getInputStream();
        Connection conn = null;

        try {
            conn = Conexao.conectar();
            DataFormatter formatter = new DataFormatter();
            Workbook workbook = WorkbookFactory.create(fileContent);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iter = sheet.iterator();

            while (iter.hasNext()) {
                Row row = iter.next();
                
                // Pula cabeçalho se necessário (se a linha 0 não tiver dados válidos de país)
                // Ajuste conforme sua planilha real. Se a planilha não tem cabeçalho, remova o if.
                // if (row.getRowNum() == 0) continue; 

                // --- 1. País (Coluna A) ---
                String nomePais = formatter.formatCellValue(row.getCell(0)).trim();
                if (nomePais.isEmpty()) continue;
                
                int idPais = buscarIdPais(conn, nomePais);
                if (idPais == -1) {
                    throw new Exception("País não encontrado: " + nomePais);
                }

                // --- 2. Moeda (Coluna B) ---
                String nomeMoeda = formatter.formatCellValue(row.getCell(1)).trim();
                int idMoeda = buscarIdMoeda(conn, nomeMoeda);
                if (idMoeda == -1) {
                    throw new Exception("Moeda não encontrada: " + nomeMoeda);
                }

                // --- 3. Método Valoração (Coluna C) ---
                String metodo = formatter.formatCellValue(row.getCell(2)).trim();

                // --- 4. Valor Participação (Coluna D) ---
                String valorPartStr = limparNumero(formatter.formatCellValue(row.getCell(3)));
                Double valorPart = valorPartStr.isEmpty() ? 0.0 : Double.parseDouble(valorPartStr);

                // --- 5. Lucro Distribuído (Coluna E) ---
                String lucroDistStr = limparNumero(formatter.formatCellValue(row.getCell(4)));
                Double lucroDist = lucroDistStr.isEmpty() ? 0.0 : Double.parseDouble(lucroDistStr);

                // --- Cria Objeto MAP para o JSON ---
                Map<String, Object> item = new HashMap<>();
                item.put("id_pais", idPais);
                item.put("nome_pais", nomePais);
                item.put("id_moeda", idMoeda);
                item.put("nome_moeda", nomeMoeda);
                item.put("metodo", metodo);
                item.put("valor_participacao", valorPart);
                item.put("lucro_distribuido", lucroDist);

                listaParaFrontend.add(item);
            }

            // Retorna a lista como JSON para o JavaScript
            out.print(gson.toJson(listaParaFrontend));

        } catch (Exception e) {
            e.printStackTrace();
            // Retorna erro em JSON
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            out.print(gson.toJson(erro));
        } finally {
            if(conn != null) Conexao.fecharConexao(conn, null, null);
            if (fileContent != null) fileContent.close();
        }
    }

    private int buscarIdPais(Connection conn, String nome) throws SQLException {
        String sql = "SELECT id_pais FROM pais WHERE nome = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nome);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getInt("id_pais");
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

    private String limparNumero(String val) {
        if (val == null || val.trim().isEmpty()) return "";
        String str = val.trim();
        if (str.contains(",")) {
            str = str.replace(".", "").replace(",", ".");
        }
        return str;
    }
}