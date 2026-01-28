package br.com.bb.cbe.controllers;

import br.com.bb.cbe.Bean.Consolidado;
import br.com.bb.cbe.DAO.ConsolidadoDAO;
import java.util.Arrays;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ConsolidadoController", urlPatterns = {"/ConsolidadoController"})
public class ConsolidadoController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Define codificação para não quebrar caracteres
        req.setCharacterEncoding("UTF-8");

        try {
            // 1. Lê o JSON enviado pelo JavaScript
            BufferedReader reader = req.getReader();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Map<String, String>>>() {
            }.getType();
            List<Map<String, String>> dados = gson.fromJson(reader, listType);

            if (dados != null && !dados.isEmpty()) {

                for (Map<String, String> item : dados) {
                    try {
                        int tri = Integer.parseInt(item.get("trimestre"));
                        int ano = Integer.parseInt(item.get("ano"));
                        String ficha = item.get("ficha");
                        String valorStr = item.get("valor");

                        double valor = 0.0;

                        if (valorStr != null && !valorStr.trim().isEmpty()) {
                            // --- LÓGICA DE PROTEÇÃO DE FORMATO ---

                            // Caso 1: Formato Brasileiro (com vírgula decimal) -> "46.466.935,70"
                            if (valorStr.contains(",")) {
                                valorStr = valorStr.replace(".", ""); // Remove ponto de milhar (46466935,70)
                                valorStr = valorStr.replace(",", "."); // Troca vírgula por ponto (46466935.70)
                            }

                            // Caso 2: Formato Americano/Limpo (sem vírgula, com ponto) -> "46466935.70"
                            // Nesse caso, NÃO fazemos replace de ponto, senão o valor multiplica por 100!
                            // O Java Double.parseDouble já entende o ponto como decimal nativamente.
                            valor = Double.parseDouble(valorStr);
                        }

                        // Chama o DAO
                        ConsolidadoDAO.salvarValorBacen(tri, ano, ficha, valor);

                    } catch (NumberFormatException e) {
                        // Se um número falhar, loga o erro mas tenta salvar os outros
                        System.out.println("Erro de conversão no item: " + item + " - Erro: " + e.getMessage());
                    }
                }

                resp.setStatus(200); // Retorna sucesso para o navegador

            } else {
                resp.sendError(400, "Dados inválidos ou lista vazia.");
            }

        } catch (Exception e) {
            e.printStackTrace(); // Loga no console do servidor (Glassfish/Tomcat)
            resp.sendError(500, "Erro interno ao processar salvamento: " + e.getMessage());
        }
    }

    public static String getAllConsolidadoJson() {
        Gson gson = new Gson();
        List<Consolidado> cosifs = ConsolidadoDAO.getAllConsolidado();
        return gson.toJson(cosifs);
    }

    /**
     * Recebe o trimestre e ano selecionados e busca os dados.
     * NÃO subtrai 1. Se o usuário quer tri 3, buscamos tri 3 (Mês 9).
     * O DAO cuidará de buscar os dados do gestor no tri 4 automaticamente.
     */
    public static String obterJsonComparacao(int trimestreSelecionado, int anoSelecionado) {
        
        // Converte Trimestre em Mês para o SQL (Contábil)
        // 1 -> 3 (Março), 2 -> 6 (Junho), 3 -> 9 (Setembro), 4 -> 12 (Dezembro)
        int mesReferencia = trimestreSelecionado * 3;

        List<String> fichas = Arrays.asList("1", "3", "8.1", "8.2", "11.1", "11.2", "11.3", "11.4", "16", "18");

        // Chama o serviço passando o mês calculado (para o contábil)
        // E o ano selecionado.
        // O serviço passará o 'trimestreSelecionado' para o DAO do Gestor, que fará o shift (3->4).
        String resultado = ComparadorService.gerarJsonComparacao(
                fichas,
                mesReferencia, // Passa 9 se selecionou Tri 3
                anoSelecionado,
                null
        );
        
        System.out.println("Buscando Conciliação: " + trimestreSelecionado + "º Tri/" + anoSelecionado + " (Mês Contábil: " + mesReferencia + ")");
        return resultado;
    }

    public static double getConsolidadoByCosif(int cosif) {
        return ConsolidadoDAO.getConsoliadoByCosif(cosif);
    }

    /**
     * Retorna EXATAMENTE o que tem no banco para o filtro.
     * Sem somar +1. Se tem dado do Tri 3, mostra Tri 3.
     */
    public static String getPeriodosDisponiveisJson() {
        List<Map<String, Integer>> listaReal = ConsolidadoDAO.getPeriodosDisponiveis();
        Gson gson = new Gson();
        return gson.toJson(listaReal);
    }
}