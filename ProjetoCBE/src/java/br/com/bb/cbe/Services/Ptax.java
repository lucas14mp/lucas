package br.com.bb.cbe.Services;

import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author T1092407
 */


public class Ptax {
    
        // Método para puxar e imprimir a cotação do dólar no dia específico
    public static Map<String, Double> obterCotacaoDolar(String sigla) {
        try {
            System.out.println("SIGLA " + sigla);
            Date data = new Date();
            // Formatar a data para o padrão MM-DD-AAAA exigido pela API
            Map<String, Double> cotacoesFinal = new HashMap<>();
            
            SimpleDateFormat apiFormato = new SimpleDateFormat("MM-dd-yyyy");
            String dataFormatada = apiFormato.format(data);

            System.out.println("DATA: " + dataFormatada);
            // URL da API com a data formatada
            String url = "https://www.okanebox.com.br/api/cambioptax/lista/";

            System.out.println("URL" + url);

            // Configuração da conexão HTTP
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Leitura da resposta
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                response.append(linha);
            }
            reader.close();

            // Parse do JSON retornado
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray cotacoes = jsonResponse.getJSONArray("value");

            if (cotacoes.length() > 0) {
                JSONObject cotacao = cotacoes.getJSONObject(0);
                double compra = cotacao.getDouble("cotacaoCompra");
                double venda = cotacao.getDouble("cotacaoVenda");
                System.out.println("Cotação de Compra: " + compra);
                System.out.println("Cotação de Venda: " + venda);
                cotacoesFinal.put("venda", venda);
                cotacoesFinal.put("compra", compra);
                return cotacoesFinal;
            } else {
                cotacoesFinal.put("venda", 0.1);
                cotacoesFinal.put("compra", 0.1);
                System.out.println("Nenhuma cotação encontrada para a data especificada.");
                return cotacoesFinal;
            }

        } catch (Exception e) {
            System.err.println("Erro ao obter a cotação: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
}
