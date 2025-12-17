package br.com.bb.cbe.controllers;

import br.com.bb.cbe.Bean.Consolidado;
import br.com.bb.cbe.DAO.ConsolidadoDAO;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ConsolidadoController {

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

        List<String> fichas = Arrays.asList("1", "3", "8", "11.1", "11.2", "11.3", "11.4", "16", "18", "0");

        // Chama o serviço passando o mês calculado (para o contábil)
        // E o ano selecionado.
        // O serviço passará o 'trimestreSelecionado' para o DAO do Gestor, que fará o shift (3->4).
        String resultado = ComparadorService.gerarJsonComparacao(
                fichas,
                mesReferencia, // Passa 9 se selecionou Tri 3
                anoSelecionado
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