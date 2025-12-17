/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.cbe.controllers;

import br.com.bb.cbe.Bean.Ptax;
import br.com.bb.cbe.DAO.PtaxDAO;
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
import static br.com.bb.cbe.Utils.NumeroUtils.extrairValorCompra;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author T1092407
 */
@WebServlet("/Ptax")
public class PtaxController extends HttpServlet {

    private MoedaController moedaController;

    @Override
    public void init() {
        this.moedaController = new MoedaController();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");
        HttpSession session = req.getSession();
        String tipoRequisicao = req.getParameter("tipo-requisicao");
//        HttpSession session = req.getSession();
        String chaveFuncionario = (String) session.getAttribute("chave");
        int status = 1;
        try {
//          CRIANDO UMA CLASSE FINAL PARA PODER PEGAR O VALOR DA CHAVE DIRETORIA E USAR DEPOIS (SÓ CONSEGUI FAZER ASSIM)
            final class Teste {

                String valor;
            }
//          INSTÊNCIA DA CLASSE
            Teste testeNomeDiretoria = new Teste();
            List<Map<String, Object>> list = null;
            if (tipoRequisicao == null) { //obter o tipoRequisicao aqui, caso seja passado pelo ajax
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = req.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String json = sb.toString();
                Gson gson = new Gson();
                Type type = new TypeToken<List<Map<String, Object>>>() {
                }.getType();
                list = gson.fromJson(json, type);
//                System.out.println("LISTA: " + list);              
                // Primeiro loop para encontrar "tipoRequisicao"
                for (Map<String, Object> map : list) {
                    if (map.containsKey("tipo-requisicao")) {
                        tipoRequisicao = (String) map.get("tipo-requisicao");
                        break;
                    }
                }
            }

            switch (tipoRequisicao) {
                case "createbatch":
                    System.out.println("ENTROU");
                    List<Ptax> taxas = new ArrayList<>();
//                  Verficiando qual é a diretoria para poder processar as informações
//                  SEÇÃO COGER                                               
//                      Processando informações e atribuindo a variável fichas  
                    taxas = PtaxController.processarInformacoes(list, taxas, moedaController);
//                      Loop para adicionar uma ficha de cada vez para as n fichas que tiverem  
                    for (Ptax registro : taxas) {
                        List<Ptax> temp = new ArrayList<>();
                        temp.add(registro);
//                          Verfica se na ficha tem moeda caso não tenha, essa ficha deve ser ignorada
                        if (registro.getMoeda() != null) {
//                              Verificando se já existe registro na tabela Ficha11Maior com o mesmo id de empresa presente na ficha
                            int idMoeda = registro.getMoeda().getId();
                            boolean existe = PtaxController.taxaExiste(idMoeda);
                            System.out.println("MOEDA QUE VAI SER CRIADA OU ATUALIZADA: " + registro.getMoeda().getNome());
//                              Caso tenha, irá atualizar o registro que contem essa empresa  
                            if (existe) {
                                System.out.println("MOEDA JA EXISTE " + registro.getMoeda().getNome());
                                PtaxDAO.updateBatchPtax(temp);
                            } //                              Caso não tenha, irá criar um novo registro  
                            else {
                                System.out.println("MOEDA NÃO EXISTE " + registro.getMoeda().getNome());
                                PtaxDAO.createBatchTaxa(temp);
                            }
                        } else {
                            System.out.println("PARTE INDESEJADA IGNORADA");
                        }
//                  SEÇÃO UPE                     
                    }
                    break;
                default:
                    System.out.println("Tipo de requisição desconhecido");
            }

            if (tipoRequisicao.equals("createbatch")) {
//                  Redirecionando caso a requisição seja feita pelo ajax  
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setHeader("Content-Type", "application/json");
                resp.getWriter().write("{\"redirectUrl\": \"/ProjetoCBE/index.jsp\"}");
                return;
            }

            resp.sendRedirect("/ProjetoCBE/views/ficha11.jsp");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("mensagemErro", "O valor foi inserido em um formato inválido.\nPor favor, utilize o padrão: \"0.000.000,00\"");
            req.setAttribute("linkPaginaAnterior", "/ProjetoCBE/forms/ficha11.jsp");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/errors/customError.jsp");
            dispatcher.forward(req, resp);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private static int calcularTrimestre(Date data) {
        if (data == null) {
            return 0;
        }
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(data);
        int mes = cal.get(java.util.Calendar.MONTH); // Jan = 0, Dez = 11
        return (mes / 3) + 1;
    }

    public static List<Ptax> processarInformacoes(List<Map<String, Object>> list,
            List<Ptax> taxas, MoedaController moedaController) {
//      Loop para pegar as informações da lista de dicionários  
        for (Map<String, Object> map : list) {
            Ptax tempPtax = new Ptax(); // Crie uma ficha temporária para ir adicionando as informações ficha a ficha;
//          Loop for para cada chave presente em cada item da lista  
            map.forEach((key, value) -> {
                if (value != null) {
                    switch (key) {
//                      Lógica geral de cada case:
//                      1. Pegar o valor e passar para um objeto  
//                      2. Converter para uma String e fazer os tratamentos necessários 
//                      3. Adicionar a ficha temporária que a cada loop é adicionado a variável fichas
//                      Lógica geral para os valores numéricos (Patrimonio Liquido, ValorTotal, ResultadoLiquido, etc)  
//                      Exemplo Partimonio Liquido:
//                      1. Pegar o valor em um objeto e passa para uma String numero (nesse caso numeroPL)   
//                      2. Pega o numero e converte para double dependendo do formato que vier (Americano virgula e ponto 
//                              ou Europeu ponto e vitgula)
//                      3.Atribuir esse numero convertido a uma variavel double e adicionar a ficha temporária    
                        case "sigla":
                            Object obMoedaId = map.get("sigla");
                            String nomeMoeda = obMoedaId.toString();
//                          Aqui só verifica se a moeda vem no formato "Dóla -...", caso o nomeMoeda será Dolár Americano  
                            nomeMoeda = NumeroUtils.verificarString(nomeMoeda);
                            System.out.println("NOME MOEDA: " + nomeMoeda);
//                          Pega a moeda a partir do nome em uma query pro banco de dados  
                            boolean existe = moedaController.moedaExiste(nomeMoeda);
                            Moeda moeda = null;
                            if (existe) {
                                moeda = moedaController.getMoedaBySigla((String) (nomeMoeda));
                            } else {
//                                moeda.setNome("IGN");
//                                tempPtax.setMoeda((Moeda) moeda);
                                break;
                            }
                            System.out.println("MOEDA Nome: " + moeda.getNome());
                            tempPtax.setMoeda((Moeda) moeda);
                            System.out.println("MOEDA FICHA: " + tempPtax.getMoeda().getId());
                            break;
                        case "compra":
                            System.out.println("COMPRA");
                            System.out.println("COMPRA");
                            Object obCompra = map.get("compra");
                            String numeroCP = obCompra.toString();
                            numeroCP = NumeroUtils.removerNumParenteses(numeroCP);
                            double compra = extrairValorCompra(map);
                            try {
                                compra = NumeroUtils.formatAndConvertToFloat(numeroCP);
                            } catch (ParseException ex) {
                                Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            tempPtax.setCompra(compra);

                            break;
                        case "venda":
                            System.out.println("VENDA");
                            Object obVenda = map.get("venda");
//                         Convertendo o valor para double  
                            double venda = NumeroUtils.stringToDouble(obVenda.toString());
                            System.out.println("VENDA: " + venda);
                            tempPtax.setVenda(venda);
                            break;

                        case "data":
                            Object obData = map.get("data");
                            String dataString = obData != null ? obData.toString().trim() : null;

                            if (dataString != null && !dataString.isEmpty()) {
                                try {
                                    // Ajuste o formato aqui se necessário (ex: "yyyy-MM-dd" ou "dd/MM/yyyy")
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    Date dataDaTaxa = sdf.parse(dataString);

                                    // Define a data correta no objeto
                                    tempPtax.setData_criacao(dataDaTaxa);

                                    // Calcula o trimestre baseado nessa data
                                    tempPtax.setTrimestre(calcularTrimestre(dataDaTaxa));

                                } catch (ParseException e) {
                                    System.out.println("Erro ao converter data: " + dataString);
                                    e.printStackTrace();
                                    // Fallback: usa data atual se der erro
                                    tempPtax.setData_criacao(new Date());
                                    tempPtax.setTrimestre(calcularTrimestre(new Date()));
                                }
                            }
                            break;

//                      ESSE CASO É SÓ PARA SABER SE OS VALORES ESTÃO SENDO CAPTURADOS E PASSADOS CORRETAMENTE (TESTE LINHA 480)      
                    }
                }
            });
        }
        return taxas;
    }

    public static boolean taxaExiste(int id) {
        boolean existe = PtaxDAO.taxaExiste(id);
        return existe;
    }

    public static List<Ptax> getAllTaxas() {
        List<Ptax> taxas = PtaxDAO.getAllTaxas();
        return taxas;
    }

    public static String getAllTaxasJson() {
        Gson gson = new Gson();
        List<Ptax> taxas = PtaxDAO.getAllTaxas();
        String taxasJson = gson.toJson(taxas);

        return taxasJson;
    }

    public static List<Integer> getAnosDisponiveis() {
        return PtaxDAO.getAnosDisponiveis();
    }

    public static List<Ptax> getTaxasFiltradas(int ano, int trimestre) {
        return PtaxDAO.getTaxasPorPeriodo(ano, trimestre);
    }

// Sobrecarga para facilitar o JSON filtrado
    public static String getTaxasJsonFiltrado(int ano, int trimestre) {
        Gson gson = new Gson();
        List<Ptax> taxas = getTaxasFiltradas(ano, trimestre);
        return gson.toJson(taxas);
    }

}
