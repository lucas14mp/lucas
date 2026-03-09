package br.com.bb.cbe.controllers;

import br.com.bb.cbe.conexao.Conexao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/UploadPtaxCsvController")
@MultipartConfig
public class UploadPtaxCsvController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        Part filePart = req.getPart("arquivoCsv");
        
        if (filePart == null || filePart.getSize() == 0) {
            resp.getWriter().println("Erro: Nenhum arquivo enviado.");
            return;
        }

        InputStream fileContent = filePart.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent, "UTF-8"));
        
        Connection conn = null;
        PreparedStatement stmtInsert = null;
        PreparedStatement stmtDelete = null;
        PreparedStatement stmtLimpezaAntiga = null;
        PreparedStatement stmtBuscaId = null;

        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false); 

            // --- 1. REGRA DOS 3 ANOS (LIMPEZA GERAL) ---
            // Apaga tudo que for mais velho que 3 anos a partir de hoje
            String sqlLimpeza = "DELETE FROM ptax WHERE data_criacao < DATE_SUB(NOW(), INTERVAL 3 YEAR)";
            stmtLimpezaAntiga = conn.prepareStatement(sqlLimpeza);
            stmtLimpezaAntiga.executeUpdate();

            // --- PREPARAÇÃO DAS QUERYS ---
            String sqlBusca = "SELECT id_moeda FROM moeda WHERE id_csvptax = ?";
            stmtBuscaId = conn.prepareStatement(sqlBusca);

            String sqlDelete = "DELETE FROM ptax WHERE data_criacao = ? AND id_moeda = ?";
            stmtDelete = conn.prepareStatement(sqlDelete);

            String sqlInsert = "INSERT INTO ptax (data_criacao, id_moeda, compra, venda, trimestre) VALUES (?, ?, ?, ?, ?)";
            stmtInsert = conn.prepareStatement(sqlInsert);
            
            String line;
            // Formatadores para os dois casos possíveis
            DateTimeFormatter dtfComBarra = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter dtfSemBarra = DateTimeFormatter.ofPattern("ddMMyyyy");

            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";"); 
                
                if (dados.length < 5) continue; 

                // --- 1. LER O CÓDIGO DA MOEDA (Coluna B / Índice 1) ---
                String codigoCsvStr = dados[1].trim(); 
                if (codigoCsvStr.isEmpty()) continue;

                int codigoCsv = 0;
                try {
                    codigoCsv = Integer.parseInt(codigoCsvStr);
                } catch (NumberFormatException e) {
                    continue; // Pula cabeçalho ou linha inválida
                }

                int idMoedaInterno = buscarIdMoedaPeloCodigoCsv(stmtBuscaId, codigoCsv);
                if (idMoedaInterno == 0) continue; // Ignora moeda não mapeada

                // --- 2. TRATAMENTO DE DATA ROBUSTO (CORREÇÃO DO ERRO) ---
                String dataStr = dados[0].trim();
                LocalDate data = null;
                
                try {
                    // Tenta identificar o formato
                    if (dataStr.contains("/")) {
                        data = LocalDate.parse(dataStr, dtfComBarra);
                    } else {
                        // Se vier "30092025", usa o formatador sem barras
                        data = LocalDate.parse(dataStr, dtfSemBarra);
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Erro ao ler data na linha: " + line + " -> " + e.getMessage());
                    continue; // Pula essa linha se a data for ilegível
                }
                
                java.sql.Date sqlDate = java.sql.Date.valueOf(data);

                // --- 3. RESTANTE DOS DADOS ---
                int trimestre = (data.getMonthValue() - 1) / 3 + 1;
                double compra = parseValor(dados[4]); 
                double venda = parseValor(dados[5]);

                // --- 4. GRAVAR NO BANCO ---
                
                // Remove duplicidade (se já existir esse dia/moeda, atualiza inserindo o novo)
                stmtDelete.setDate(1, sqlDate);
                stmtDelete.setInt(2, idMoedaInterno);
                stmtDelete.executeUpdate();

                // Insere
                stmtInsert.setDate(1, sqlDate);
                stmtInsert.setInt(2, idMoedaInterno);
                stmtInsert.setDouble(3, compra);
                stmtInsert.setDouble(4, venda);
                stmtInsert.setInt(5, trimestre);
                
                stmtInsert.addBatch();
            }

            stmtInsert.executeBatch();
            conn.commit();
            
            
            String msg = URLEncoder.encode("Sucesso", StandardCharsets.UTF_8.toString());
            resp.sendRedirect(req.getContextPath() + "/filtroPtax.jsp?msg=" + msg);
            return;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            resp.getWriter().println("Erro: " + e.getMessage());
        } finally {
            if (reader != null) reader.close();
            try { if (stmtLimpezaAntiga != null) stmtLimpezaAntiga.close(); } catch (Exception e) {}
            try { if (stmtBuscaId != null) stmtBuscaId.close(); } catch (Exception e) {}
            Conexao.fecharConexao(conn, stmtInsert, null);
        }
    }

    private int buscarIdMoedaPeloCodigoCsv(PreparedStatement stmt, int codigoCsv) throws SQLException {
        stmt.setInt(1, codigoCsv);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id_moeda");
            }
        }
        return 0;
    }

    private double parseValor(String val) {
        if (val == null) return 0.0;
        // Remove ponto de milhar e troca vírgula por ponto
        return Double.parseDouble(val.trim().replace(".", "").replace(",", "."));
    }
}