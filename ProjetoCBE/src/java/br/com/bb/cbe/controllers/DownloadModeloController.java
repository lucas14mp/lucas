package br.com.bb.cbe.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DownloadModeloFicha11")
public class DownloadModeloController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Caminho do arquivo físico dentro do projeto (pasta web)
        String filePath = "/resources/arquivos/Modelo_Ficha11_Maior.xlsx"; // Ajuste a pasta se necessário
        
        InputStream inStream = getServletContext().getResourceAsStream(filePath);

        if (inStream == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Arquivo modelo não encontrado no servidor.");
            return;
        }

        // Informa ao navegador que é um download de Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // Força o navegador a baixar em vez de tentar abrir
        response.setHeader("Content-Disposition", "attachment; filename=\"Modelo_Ficha11_Maior.xlsx\"");

        // Lê o arquivo do servidor e envia para o usuário
        try (OutputStream outStream = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        } finally {
            if (inStream != null) {
                inStream.close();
            }
        }
    }
}