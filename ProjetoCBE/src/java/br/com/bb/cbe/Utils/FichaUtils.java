/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.cbe.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author T1092408
 */
public class FichaUtils {
    
    public static Map<String, String> getParametros(int numFicha){
        Map<String, String> map = new HashMap<>();
          
        //Como esse possuem o mesmo nome de coluna estou colocando antes
        if (numFicha == 1 || numFicha == 3 || numFicha == 16){
            map.put("coluna", "valor_database");
        }
        
        switch (numFicha) {
            case 1:
                map.put("tabela", "ficha01");              
                break;
            case 3:
                map.put("tabela", "ficha03");
                break;
            case 8:
                map.put("tabela", "ficha08");
                map.put("coluna", "saldo_database");
                break;
            case 11:
                map.put("tabela", "ficha11");
                map.put("coluna", "valor_database");
                break;
            case 16:
                map.put("tabela", "ficha16");
                break;
            case 18:
                map.put("tabela", "ficha18");
                map.put("coluna", "valor_mercado");
                break;    
            default:
                break;
        }
        
        return map;
        
        
    }
    
}
