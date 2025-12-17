/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.cbe.controllers;

import br.com.bb.cbe.Bean.Contabil;
import br.com.bb.cbe.DAO.ContabilDAO;
import br.com.bb.cbe.Bean.Dependencia;
import br.com.bb.cbe.controllers.MoedaController;
import br.com.bb.cbe.controllers.MoedaController;
import br.com.bb.cbe.Bean.Ficha11Maior;
import br.com.bb.cbe.DAO.Ficha11MaiorDAO;
import br.com.bb.cbe.Bean.Empresa;
import br.com.bb.cbe.Bean.Funcionario;
import br.com.bb.cbe.Bean.Moeda;
import br.com.bb.cbe.Utils.DataUtils;
import br.com.bb.cbe.Utils.JsonUtil;
import br.com.bb.cbe.Utils.NumeroUtils;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import com.google.gson.Gson;
import java.util.List;
import java.util.Map;

/**
 *
 * @author T1092407
 */
public class ContabilController {
    
    public static List<Contabil> getAllCosifs(){
        List<Contabil> cosifs = ContabilDAO.getAllCosifs();
        return cosifs;
    }
    
    public static String getAllCosifsJson(){
        Gson gson = new Gson(); 
        List<Contabil> cosifs = ContabilDAO.getAllCosifs();
        String cosifsJson = gson.toJson(cosifs);
        return cosifsJson;
    }
    
    public static Contabil getCosifByCosif(String Cosif){
        Contabil cosifs = ContabilDAO.getCosifByCosif ();
        return cosifs;
    }
}
