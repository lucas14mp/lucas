package br.com.bb.cbe.controllers;

import br.com.bb.cbe.DAO.ConsolidadoDAO;
import br.com.bb.cbe.DAO.Ficha08DAO;
import br.com.bb.cbe.DAO.Ficha11MaiorDAO;
import br.com.bb.cbe.DAO.Ficha11MenorDAO; // <--- IMPORTANTE: Adicione este import
import br.com.bb.cbe.DAO.Ficha18DAO;
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

        // 1. Configura o Trimestre de REFERÊNCIA (Contábil / PTAX)
        int trimestreRef = mesReferencia / 3;
        int anoRef = ano;

        // 2. Configura o Trimestre de BUSCA (Para a Ficha 18 usar)
        // Regra: O dado está gravado no trimestre seguinte
        int triBusca = trimestreRef + 1;
        int anoBusca = ano;
        
        if (triBusca > 4) {
            triBusca = 1;
            anoBusca = ano + 1;
        }

        for (String ficha : fichas) {
            double valorFicha = 0.0;

            // --- LÓGICA DE CADA FICHA ---

            if ("11.1".equals(ficha)) {
                // Mantém como estava (usa trimestreRef)
                valorFicha = Ficha11MenorDAO.getSomaPorDependencia(trimestreRef, ano, 9958);
            
            } else if ("11.2".equals(ficha)) {
                // Mantém como estava (usa trimestreRef)
                valorFicha = Ficha11MaiorDAO.getSomaPatrimonioPonderado(trimestreRef, ano, 9568);
                
            } else if ("11.4".equals(ficha)) {
                // Mantém como estava (usa trimestreRef)
                valorFicha = Ficha11MaiorDAO.getSomaPatrimonioPonderadoUPE(trimestreRef, ano, 9568);
            
            } else if ("8.1".equals(ficha)) { 
                // COSIF 1.1.5.20.00.00 -> Dependência 9958
                valorFicha = Ficha08DAO.getSomaPorDependencia(trimestreRef, ano, 9958);

            } else if ("8.2".equals(ficha)) { 
                // COSIF 1.2.6.10.20.00 -> Dependência a definir
                // Deixando o espaço conforme solicitado. 
                // Quando tiver o ID, troque o null pelo número (ex: 9999)
                Integer idDepFutura = null; 
                valorFicha = Ficha08DAO.getSomaPorDependencia(trimestreRef, ano, 8555);    
                
            } else if ("18".equals(ficha)) {
                // --- CORREÇÃO FICHA 18 ---
                // Agora as variáveis 'triBusca' e 'anoBusca' existem e funcionam!
                valorFicha = Ficha18DAO.getSomaTotalComJuros(triBusca, anoBusca, trimestreRef, anoRef);
                
            } else {
                // Outras fichas (usa trimestreRef, o DAO faz o shift internamente)
                Map<String, Object> fichaData = ConsolidadoDAO.getSomaFichaByTrimestreAno(ficha, trimestreRef, ano);
                valorFicha = ((Number) fichaData.getOrDefault("valorFicha", 0.0)).doubleValue();
            }

            // --- FIM DA LÓGICA DE VALOR ---

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