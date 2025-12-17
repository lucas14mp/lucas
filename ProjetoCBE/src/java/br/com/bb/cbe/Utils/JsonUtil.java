/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.cbe.Utils;

/**
 *
 * @author T1092407
 */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import br.com.bb.cbe.Bean.Ficha11Controle;
import br.com.bb.cbe.Bean.Ficha11Maior;
import br.com.bb.cbe.Bean.Ficha11Menor;
import  br.com.bb.cbe.Services.teste;
//import  br.com.bb.cbe.services;

public class JsonUtil {

    //conferir se compensa utilizar esse metodo no(s) controller(s)
    public static String buildJsonFromReq(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        String json = jsonBuilder.toString();
        return json;
    }

    public static List<Ficha11Maior> parseFicha11Maior(String json) throws IOException, SQLException, ParseException{
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("JSON NO UTIL:" + json);
        JsonNode rootNode = objectMapper.readTree(json);
        List<Ficha11Maior> fichas = new ArrayList<>();

        for (JsonNode itemNode : rootNode) {
//            Integer numeroItem = itemNode.get("numeroItem").asInt();
            String empresa = itemNode.get("empresa").asText();
//            String chaveFuncionario = itemNode.get("chaveFuncionario").asText();
            JsonNode valoresNode = itemNode.get("valores");

            for (JsonNode valorNode : valoresNode) {
                Ficha11Maior ficha = new Ficha11Maior();
                ficha.setMetodoValoracao(empresa);
//                projecao.setJustificativa(justificativa);
//                projecao.setFuncionario(FuncionarioService.obterFuncionarioPorId(chaveFuncionario));
//                projecao.setDataProjecao(DataHoraUtils.formatStringToDate(valorNode.get("dataProjecao").asText()));
//                String valorProgecao = valorNode.get("valorProjecao").asText();
//                projecao.setValorProjecao(NumeroUtils.stringToDouble(valorProgecao));
                fichas.add(ficha);
            }
        }

        return fichas;
    }
}