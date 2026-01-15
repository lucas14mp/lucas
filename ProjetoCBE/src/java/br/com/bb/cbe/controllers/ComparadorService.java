package br.com.bb.cbe.controllers;

import br.com.bb.cbe.DAO.ConsolidadoDAO;
import br.com.bb.cbe.DAO.Ficha11MaiorDAO;
import br.com.bb.cbe.DAO.Ficha11MenorDAO; // <--- IMPORTANTE: Adicione este import
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

/**
 * Service de comparação:
 * - Recebe fichas como String (ex.: "11.1");
 * - Ficha 8 e TODAS as 11.x (ex.: "11.1","11.2","11.3") são AGRUPADAS (rowspan);
 * - Demais fichas ficam linha a linha.
 */
public class ComparadorService {

    // Adicionado parâmetro idDependencia para repassar filtros
    public static String gerarJsonComparacao(List<String> fichas, int mesReferencia, int ano, Integer idDependencia) {
        List<Map<String, Object>> resultados = compararConsolidadoComFichas(fichas, mesReferencia, ano, idDependencia);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(resultados);
    }

public static List<Map<String, Object>> compararConsolidadoComFichas(List<String> fichas, int mesReferencia, int ano, Integer idDependencia) {
        List<Map<String, Object>> resultadosAgrupados = new ArrayList<>();

        // 1. Apenas calcula o Trimestre de REFERÊNCIA (Contábil)
        // Ex: Mês 9 -> Tri 3
        int trimestreRef = mesReferencia / 3;
        
        // Passamos 'trimestreRef' e 'ano' puros. O DAO que decida se busca no tri seguinte.

        for (String ficha : fichas) {
            double valorFicha = 0.0;

            if ("11.1".equals(ficha)) {
                // Ficha 11.1 -> Dep 9958
                // DAO deve tratar a busca no trimestre seguinte internamente
                valorFicha = Ficha11MenorDAO.getSomaPorDependencia(trimestreRef, ano, 9958);
            
            } else if ("11.2".equals(ficha)) {
                // Ficha 11.2 -> Dep 9568
                // DAO deve tratar a busca no trimestre seguinte internamente
                valorFicha = Ficha11MaiorDAO.getSomaPatrimonioPonderado(trimestreRef, ano, 9568);
                
                } else if ("11.4".equals(ficha)) {
                // Ficha 11.4 -> Dep 9568 (Ficha Maior - Somente UPE)
                valorFicha = Ficha11MaiorDAO.getSomaPatrimonioPonderadoUPE(trimestreRef, ano, 9568);
                
            } else {
                // Outras Fichas
                // Se as outras fichas NÃO têm a regra de "preencher no trimestre seguinte", elas vão funcionar normal com o triRef.
                // Se elas TÊM a regra, os DAOs delas também devem ser ajustados.
                Map<String, Object> fichaData = ConsolidadoDAO.getSomaFichaByTrimestreAno(ficha, trimestreRef, ano);
                valorFicha = ((Number) fichaData.getOrDefault("valorFicha", 0.0)).doubleValue();
            }

            // ... (Restante do código de buscar o Contábil e montar o JSON permanece inalterado) ...
            List<Map<String, Object>> consolidadoDataList = ConsolidadoDAO.getConsolidadoByFicha(ficha, mesReferencia, ano);
            if (consolidadoDataList == null || consolidadoDataList.isEmpty()) {
                continue;
            }
            
            Map<String, Object> grupoResultado = new HashMap<>();
            String nomeFicha = String.valueOf(consolidadoDataList.get(0).getOrDefault("nomeFicha", ""));
            grupoResultado.put("ficha", ficha);
            grupoResultado.put("nome_ficha", nomeFicha);
            grupoResultado.put("cosifs", consolidadoDataList);
            grupoResultado.put("rowCount", consolidadoDataList.size());

            boolean isFicha8 = "8".equals(ficha);
            boolean isSubFicha11 = ficha != null && ficha.startsWith("11.");

            if (isFicha8 || isSubFicha11) {
                double somaValorConsolidado = 0.0;
                for (Map<String, Object> consolidadoData : consolidadoDataList) {
                    somaValorConsolidado += ((Number) consolidadoData.getOrDefault("consolidado", 0.0)).doubleValue();
                }
                double diferencaTotal = somaValorConsolidado - valorFicha;
                double porcentagemTotal = somaValorConsolidado != 0 ? (diferencaTotal / somaValorConsolidado) * 100 : 0.0;
                grupoResultado.put("valorGestorAgregado", valorFicha);
                grupoResultado.put("diferencaAgregada", diferencaTotal);
                grupoResultado.put("porcentagemAgregada", porcentagemTotal);
            } else {
                for (Map<String, Object> consolidadoData : consolidadoDataList) {
                    double valorConsolidado = ((Number) consolidadoData.getOrDefault("consolidado", 0.0)).doubleValue();
                    double diferenca = valorConsolidado - valorFicha;
                    double porcentagem = valorConsolidado != 0 ? (diferenca / valorConsolidado) * 100 : 0.0;
                    consolidadoData.put("valorFicha", valorFicha);
                    consolidadoData.put("diferenca", diferenca);
                    consolidadoData.put("porcentagem", porcentagem);
                }
            }
            resultadosAgrupados.add(grupoResultado);
        }
        return resultadosAgrupados;
    }
}