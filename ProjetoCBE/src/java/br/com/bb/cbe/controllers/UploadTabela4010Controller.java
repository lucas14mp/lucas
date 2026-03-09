package br.com.bb.cbe.controllers;

import br.com.bb.cbe.conexao.Conexao; 
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.poi.ss.usermodel.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/UploadTabela4010Controller")
@MultipartConfig
public class UploadTabela4010Controller extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        Part filePart = req.getPart("arquivoExcel");
        
        if (filePart == null || filePart.getSize() == 0) {
            resp.getWriter().println("Erro: Nenhum arquivo enviado.");
            return;
        }

        InputStream fileContent = filePart.getInputStream();
        Connection conn = null;
        PreparedStatement stmtInsert = null;
        PreparedStatement stmtLimpeza = null;
        PreparedStatement stmtLimpaMes = null;

        try {
            conn = Conexao.conectar();
            if (conn == null) throw new SQLException("Erro de conexão (null).");
            
            conn.setAutoCommit(false);
            DataFormatter formatter = new DataFormatter(); // Lê EXATAMENTE como está no Excel
            Workbook workbook = WorkbookFactory.create(fileContent);
            Sheet sheet = workbook.getSheetAt(0);
            DateTimeFormatter formatadorBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // --- 1. PEGAR DATA PARA LIMPEZA ---
            java.sql.Date dataRef = null;
            Row row1 = sheet.getRow(1);
            if (row1 != null) {
                Cell cellD = row1.getCell(0); // Coluna A (Data)
                if (cellD != null) {
                    if (DateUtil.isCellDateFormatted(cellD)) {
                        dataRef = new java.sql.Date(cellD.getDateCellValue().getTime());
                    } else {
                        try {
                            String t = formatter.formatCellValue(cellD).trim();
                            if (t.length() >= 8) {
                                LocalDate ld = LocalDate.parse(t, formatadorBr);
                                dataRef = java.sql.Date.valueOf(ld);
                            }
                        } catch (Exception e) {}
                    }
                }
            }

            // --- 2. LIMPEZAS ---
            stmtLimpeza = conn.prepareStatement("DELETE FROM `planilha4010` WHERE DT_EVD < DATE_SUB(NOW(), INTERVAL 3 YEAR)");
            stmtLimpeza.executeUpdate();

            if (dataRef != null) {
                stmtLimpaMes = conn.prepareStatement("DELETE FROM `planilha4010` WHERE DT_EVD = ?");
                stmtLimpaMes.setDate(1, dataRef);
                stmtLimpaMes.executeUpdate();
            }

            // --- 3. INSERT (Sem forçar tipos no Java) ---
            String sqlInsert = "INSERT INTO `planilha4010` "
                    + "(DT_EVD, CD_GR, CD_IOR, CD_CT_PLN, CD_RBC, NM, CD_TIP_MVT, AGLUTINADO, ELIMINADO, CONSOLIDADO) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            stmtInsert = conn.prepareStatement(sqlInsert);

            Iterator<Row> iter = sheet.iterator();
            while (iter.hasNext()) {
                Row row = iter.next();
                if (row.getRowNum() == 0) continue;

                // --- LER TUDO COMO TEXTO ORIGINAL DO EXCEL ---
                
                // Col 0: DATA (DT_EVD)
                java.sql.Date dt = null;
                Cell c0 = row.getCell(0);
                if (c0 != null) {
                    if (DateUtil.isCellDateFormatted(c0)) {
                        dt = new java.sql.Date(c0.getDateCellValue().getTime());
                    } else {
                        try {
                            String s = formatter.formatCellValue(c0).trim();
                            if(!s.isEmpty()) dt = java.sql.Date.valueOf(LocalDate.parse(s, formatadorBr));
                        } catch(Exception e){}
                    }
                }

                // Colunas Inteiras (Limpamos para garantir que "1.0" ou "1,0" não quebre, mas enviamos como String)
                String cdGr    = limparNumero(formatter.formatCellValue(row.getCell(1))); 
                String cdIor   = limparNumero(formatter.formatCellValue(row.getCell(2))); 
                String cdCtPln = limparNumero(formatter.formatCellValue(row.getCell(3))); 
                String cdRbc   = limparNumero(formatter.formatCellValue(row.getCell(4))); 
                
                // Coluna Texto
                String nm      = formatter.formatCellValue(row.getCell(5)); 
                
                // Colunas Numéricas (INT e DOUBLE)
                String cdTip   = limparNumero(formatter.formatCellValue(row.getCell(6))); 
                String aglut   = limparNumero(formatter.formatCellValue(row.getCell(7))); // AGLUTINADO
                String elim    = limparNumero(formatter.formatCellValue(row.getCell(8))); // ELIMINADO
                String consol  = limparNumero(formatter.formatCellValue(row.getCell(9))); // CONSOLIDADO

                // --- ENVIAR PARA O BANCO ---
                // Usamos setString. O Driver do MySQL converte automaticamente para INT ou DOUBLE.
                
                // 1. DT_EVD
                if (dt != null) stmtInsert.setDate(1, dt);
                else stmtInsert.setNull(1, Types.DATE);

                // 2. CD_GR
                if (!cdGr.isEmpty()) stmtInsert.setString(2, cdGr); 
                else stmtInsert.setNull(2, Types.INTEGER);

                // 3. CD_IOR
                if (!cdIor.isEmpty()) stmtInsert.setString(3, cdIor);
                else stmtInsert.setNull(3, Types.INTEGER);

                // 4. CD_CT_PLN
                if (!cdCtPln.isEmpty()) stmtInsert.setString(4, cdCtPln);
                else stmtInsert.setNull(4, Types.INTEGER);

                // 5. CD_RBC
                if (!cdRbc.isEmpty()) stmtInsert.setString(5, cdRbc);
                else stmtInsert.setNull(5, Types.INTEGER);

                // 6. NM
                if (nm != null && !nm.isEmpty()) stmtInsert.setString(6, nm);
                else stmtInsert.setNull(6, Types.VARCHAR);

                // 7. CD_TIP_MVT
                if (!cdTip.isEmpty()) stmtInsert.setString(7, cdTip);
                else stmtInsert.setNull(7, Types.INTEGER);

                // 8. AGLUTINADO
                if (!aglut.isEmpty()) stmtInsert.setString(8, aglut);
                else stmtInsert.setNull(8, Types.DOUBLE);

                // 9. ELIMINADO
                if (!elim.isEmpty()) stmtInsert.setString(9, elim);
                else stmtInsert.setNull(9, Types.DOUBLE);

                // 10. CONSOLIDADO
                if (!consol.isEmpty()) stmtInsert.setString(10, consol);
                else stmtInsert.setNull(10, Types.DOUBLE);

                stmtInsert.addBatch();
            }

            stmtInsert.executeBatch();
            conn.commit();
            
            String msg = URLEncoder.encode("Sucesso", StandardCharsets.UTF_8.toString());
            resp.sendRedirect(req.getContextPath() + "/filtro4010Conciliacao.jsp?msg=" + msg);
            return;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            resp.getWriter().println("Erro: " + e.getMessage());
        } finally {
            if (stmtLimpeza != null) try{ stmtLimpeza.close(); }catch(Exception e){}
            if (stmtLimpaMes != null) try{ stmtLimpaMes.close(); }catch(Exception e){}
            Conexao.fecharConexao(conn, stmtInsert, null);
            if (fileContent != null) fileContent.close();
        }
    }

    // --- CORREÇÃO IMPORTANTE AQUI ---
    // Transforma "1.234,56" em "1234.56" (Remove ponto de milhar, troca vírgula por ponto)
    // Transforma "1,0" em "1.0"
    // Transforma "1" em "1"
    private String limparNumero(String val) {
        if (val == null || val.trim().isEmpty()) return "";
        String str = val.trim();
        
        // Se tiver vírgula, assumimos formato Brasileiro (1.000,00)
        if (str.contains(",")) {
            str = str.replace(".", ""); // Remove ponto de milhar (Ex: 1.200 -> 1200)
            str = str.replace(",", "."); // Troca vírgula por ponto decimal (Ex: ,50 -> .50)
        }
        
        return str;
    }
}