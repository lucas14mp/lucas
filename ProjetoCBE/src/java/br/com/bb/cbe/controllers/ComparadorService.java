package br.com.bb.cbe.controllers;

import br.com.bb.cbe.DAO.ConsolidadoDAO;
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

public static String gerarJsonComparacao(List<String> fichas, int mesReferencia, int ano) {
        List<Map<String, Object>> resultados = compararConsolidadoComFichas(fichas, mesReferencia, ano);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(resultados);
    }

    public static List<Map<String, Object>> compararConsolidadoComFichas(List<String> fichas, int mesReferencia, int ano) {
        List<Map<String, Object>> resultadosAgrupados = new ArrayList<>();

        for (String ficha : fichas) {
            // AQUI: Atenção! O getSomaFichaByTrimestreAno usa trimestre ou mês? 
            // Se as fichas gestor (8, 11) usam trimestre (1,2,3,4) no banco delas, você precisa converter de volta ou ajustar o DAO delas.
            // Assumindo que aqui vamos focar no Consolidado (Contábil):
            
            // 1) Valor Gestor (Cuidado: Se as tabelas 'ficha_11' usam trimestre 1,2,3,4, você precisa converter o mesReferencia de volta para trimestre aqui)
            int trimestreParaFicha = mesReferencia / 3; 
            Map<String, Object> fichaData = ConsolidadoDAO.getSomaFichaByTrimestreAno(ficha, trimestreParaFicha, ano);
            
            double valorFicha = ((Number) fichaData.getOrDefault("valorFicha", 0.0)).doubleValue();

            // 2) Valor Contábil (Este usa o mês 3, 6, 9, 12 na tabela 4010)
            // Passamos o Mês e Ano para filtrar a data correta no SQL
            List<Map<String, Object>> consolidadoDataList = ConsolidadoDAO.getConsolidadoByFicha(ficha, mesReferencia, ano);
            if (consolidadoDataList == null || consolidadoDataList.isEmpty()) {
                continue;
            }

            Map<String, Object> grupoResultado = new HashMap<>();
            String nomeFicha = String.valueOf(consolidadoDataList.get(0).getOrDefault("nomeFicha", ""));
            grupoResultado.put("ficha", ficha);                 // "11.1" / "11.2" / "11.3" / "8" / etc.
            grupoResultado.put("nome_ficha", nomeFicha);        // nome descritivo
            grupoResultado.put("cosifs", consolidadoDataList);  // linhas COSIF
            grupoResultado.put("rowCount", consolidadoDataList.size());

            // >>> Regra de agrupamento (rowspan) para ficha 8 E para qualquer "11.x"
            boolean isFicha8 = "8".equals(ficha);
            boolean isSubFicha11 = ficha != null && ficha.startsWith("11.");

            if (isFicha8 || isSubFicha11) {
                double somaValorConsolidado = 0.0;
                for (Map<String, Object> consolidadoData : consolidadoDataList) {
                    somaValorConsolidado += ((Number) consolidadoData.getOrDefault("consolidado", 0.0)).doubleValue();
                }
                double diferencaTotal = somaValorConsolidado - valorFicha;
                double porcentagemTotal = somaValorConsolidado != 0
                        ? (diferencaTotal / somaValorConsolidado) * 100
                        : 0.0;

                grupoResultado.put("valorGestorAgregado", valorFicha);
                grupoResultado.put("diferencaAgregada", diferencaTotal);
                grupoResultado.put("porcentagemAgregada", porcentagemTotal);
            } else {
                // Demais fichas -> cálculo por linha
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