package br.com.bb.cbe.controllers;

import br.com.bb.cbe.Bean.Ficha18;
import br.com.bb.cbe.Bean.Justificativa;
import java.util.Date;
import br.com.bb.cbe.DAO.Ficha18DAO;
import br.com.bb.cbe.DAO.PtaxDAO;
import br.com.bb.cbe.Utils.DataUtils;
import br.com.bb.cbe.Utils.NumeroUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
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

@WebServlet("/ficha18")

public class Ficha18Controller extends HttpServlet {

    private MoedaController moedaController;
    private PaisController paisController;
    private FuncionarioController funcionarioController;
    private StatusController statusController;

    @Override
    public void init() {
        this.moedaController = new MoedaController();
        this.paisController = new PaisController();
        this.funcionarioController = new FuncionarioController();
        this.statusController = new StatusController();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");
        String tipoRequisicao = req.getParameter("tipo-requisicao");
        HttpSession session = req.getSession();
        String chaveFuncionario = (String) session.getAttribute("chave");
        JsonObject jsonBodyObject = null;
        List<Map<String, Object>> jsonBodyList = null;

        final class Teste {

            String valor;
        }
        Teste justificativaTeste = new Teste();

        try {
            if (tipoRequisicao == null) {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = req.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String json = sb.toString().trim();

                if (!json.isEmpty()) {
                    if (json.startsWith("{")) {
                        jsonBodyObject = JsonParser.parseString(json).getAsJsonObject();
                        if (jsonBodyObject.has("tipo-requisicao")) {
                            tipoRequisicao = jsonBodyObject.get("tipo-requisicao").getAsString();
                        }
                    } else if (json.startsWith("[")) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Map<String, Object>>>() {
                        }.getType();
                        jsonBodyList = gson.fromJson(json, type); // Preenche a lista aqui

                        // Extrai o tipo de requisição da lista antiga
                        if (jsonBodyList != null) {
                            for (Map<String, Object> map : jsonBodyList) {
                                if (map.containsKey("tipo-requisicao")) {
                                    tipoRequisicao = (String) map.get("tipo-requisicao");
                                    break;
                                }
                            }
                            for (Map<String, Object> map : jsonBodyList) {
                                if (map.containsKey("justificativa")) {
                                    justificativaTeste.valor = (String) map.get("justificativa");
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            if (tipoRequisicao == null) {
                tipoRequisicao = "";
            }

            Ficha18 ficha = new Ficha18();
            // Lógica de Formulário Único (Post/Edit tradicional)
            if ("post".equals(tipoRequisicao) || "edit".equals(tipoRequisicao)) {
                int moedaId = Integer.parseInt(req.getParameter("moeda"));
                int paisId = Integer.parseInt(req.getParameter("pais"));
                ficha.setMoeda(moedaController.getMoedaById(moedaId));
                ficha.setPais(paisController.getPaisById(paisId));
                ficha.setPrazoDivida(req.getParameter("resposta-prazo"));
                ficha.setValorMercado(NumeroUtils.stringToDouble(req.getParameter("valor")));
                ficha.setJurosRecebidos(NumeroUtils.stringToDouble(req.getParameter("juros")));
                ficha.setDataCriacao(new Date());
                ficha.setTrimestre(DataUtils.validaTrimestre());
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
                ficha.setStatus(statusController.getStatusById(1));
            }

            switch (tipoRequisicao) {
                case "recusar":
                    int idRecusar = Integer.parseInt(req.getParameter("id"));
                    // Chama o DAO para mudar o status para 1
                    Ficha18DAO.alterarStatus(idRecusar, 1);
                    resp.setStatus(200);
                    return;
                case "validar-lote":
                    validarLote(jsonBodyObject, resp);
                    return;
                case "salvar-lote":
                    salvarLote(jsonBodyObject, chaveFuncionario);
                    resp.setStatus(200);
                    return;
                case "delete":
                    int id = Integer.parseInt(req.getParameter("id"));
                    Ficha18DAO.delete(id);
                    break;
                case "post":
                    ficha.setDataCriacao(new Date());
                    Ficha18DAO.create(ficha);
                    break;
                case "edit":
                    ficha.setDataCriacao(new Date());
                    ficha.setId(Integer.parseInt(req.getParameter("id")));
                    Ficha18DAO.update(ficha);
                    break;
                case "validacao":
                    String[] idsValidadosArray = req.getParameterValues("idsValidados[]");
                    List<String> idsValidadosList = new ArrayList<>();
                    if (idsValidadosArray != null) {
                        idsValidadosList = Arrays.asList(idsValidadosArray);
                    }
                    Ficha18DAO.validarFormularios(idsValidadosList, chaveFuncionario);
                    break;
                case "validacaoBatch":
                    if (jsonBodyList != null) {
                        List<String> arrayIdsValidados = processarValidacao(jsonBodyList);
                        Ficha18DAO.validarFormularios(arrayIdsValidados, chaveFuncionario);

                        if (justificativaTeste.valor != null && !"NTD".equals(justificativaTeste.valor)) {
                            Justificativa justificativa = processarJustificativa(jsonBodyList, chaveFuncionario);
                            JustificativaController.createBatchJustController(justificativa);
                        }
                    }
                    break;
                default:
                    System.out.println("Tipo de requisição desconhecido: " + tipoRequisicao);
            }

            if ("createbatch".equals(tipoRequisicao) || "validacaoBatch".equals(tipoRequisicao)) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setHeader("Content-Type", "application/json");
                resp.getWriter().write("{\"redirectUrl\": \"/ProjetoCBE/views/ficha18.jsp\"}");
                return;
            }
            resp.sendRedirect("views/ficha18.jsp");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("mensagemErro", "Valor inválido.");
            req.setAttribute("linkPaginaAnterior", "/ProjetoCBE/forms/ficha18.jsp");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/errors/customError.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Ficha18> getAllFichas() {
        return Ficha18DAO.getAllFichas();
    }
    

    public List<Ficha18> getAllFichasByTrimestreAno(int trimestre, int ano) {
        int trimestreParaDAO = trimestre;
        int anoParaDAO = ano;

        switch (trimestre) {
            case 1: trimestreParaDAO = 4; anoParaDAO = ano - 1; break;
            case 2: trimestreParaDAO = 1; break;
            case 3: trimestreParaDAO = 2; break;
            case 4: trimestreParaDAO = 3; break;
            default: trimestreParaDAO = trimestre; break;
        }
        return Ficha18DAO.getAllFichasByTrimestreAno(trimestreParaDAO, anoParaDAO);
    }

    public List<Ficha18> getAllFichasByTrimestre(int trimestre) {
        int trimestreParaDAO = trimestre;
        switch (trimestre) {
            case 1: trimestreParaDAO = 4; break;
            case 2: trimestreParaDAO = 1; break;
            case 3: trimestreParaDAO = 2; break;
            case 4: trimestreParaDAO = 3; break;
            default: trimestreParaDAO = trimestre; break;
        }
        return Ficha18DAO.getAllFichasByTrimestre(trimestreParaDAO);
    }

    public List<Ficha18> getAllFichasByAno(int ano) {
        return Ficha18DAO.getAllFichasByAno(ano);
    }

    public List<Integer> getAnosExistentes() {
        return Ficha18DAO.getAnosExistentes();
    }

    public List<Integer> getTrimestresExistentes() {
        return Ficha18DAO.getTrimestresExistentes();
    }

    public Ficha18 getFichaById(int id) {
        Optional<Ficha18> optFicha18 = Ficha18DAO.getFichaById(id);
        if (optFicha18.isPresent()) {
            return optFicha18.get();
        }
        return null;
    }
    
    public static  List<String> processarValidacao(List<Map<String, Object>> list){
        //CRIANDO UMA CLASSE FINAL PARA PEGAR O VALOR DA DIRETORIA E DEPOIS ATRIBUIR A STRING DIRETORIA
        final class Ids {
            List<String> ids;
        }
        Ids idsFinal = new Ids();
        for (Map<String, Object> map : list) {
//          Loop for para cada chave presente em cada item da lista  
            map.forEach((key, value) -> {
                if (value != null) {
                    switch (key) {
                        case "fichas":
                            Object obFichasIds = map.get("fichas");
                            //criando lista para poder receber o obFichasIds
                            List<?> lista = (List<?>) obFichasIds;
                            //Criando uma lista String para receber a lista de ids que veio de obFichasIds
                            List<String> ListaString = (List<String>) lista;
                            //Usando o 
                            idsFinal.ids = ListaString;
                            break;
                    }
                }
            });
        }
        return idsFinal.ids;
    }
    
    public static Justificativa processarJustificativa(List<Map<String, Object>> list, String chaveFuncionario){
        Justificativa tempJust = new Justificativa();
        FuncionarioController funcionario = new FuncionarioController();
        for (Map<String, Object> map : list) {
            // Crie uma ficha temporária para ir adicionando as informações ficha a ficha;
//          Loop for para cada chave presente em cada item da lista  
            map.forEach((key, value) -> {
                if (value != null) {
                    try {
                        switch (key) {
                            case "justificativa": tempJust.setJust(value.toString()); break;
                            case "numeroFicha": tempJust.setNumeroFicha(value.toString()); break;
                            case "somatorio": tempJust.setSomatorio(NumeroUtils.formatAndConvertToFloat(value.toString())); break;
                            case "contabil": tempJust.setContabil(NumeroUtils.formatAndConvertToFloat(value.toString())); break;
                            case "diferenca": tempJust.setDiferenca(NumeroUtils.formatAndConvertToFloat(value.toString())); break;    
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(Ficha18Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        tempJust.setDataCriacao(new Date());
        tempJust.setFuncionario(funcionario.getFuncionarioByChave(chaveFuncionario));
        return tempJust;
    }
    
              public String getAllFichasJson() {
        Gson gson = new Gson(); 
        List<Ficha18> fichas = Ficha18DAO.getAllFichas();
        String ficha18Json = gson.toJson(fichas);
        return ficha18Json;
}
              
              public List<Ficha18> getFichasPorPeriodo(int ano, int trimestre) {
        return Ficha18DAO.getAllFichasByTrimestreAno(trimestre, ano);
      }
              
              private void validarLote(JsonObject json, HttpServletResponse resp) throws IOException {
        JsonArray itens = json.getAsJsonArray("itens");
        double somaTotalConvertidaBrl = 0.0;

        int trimestreAtual = DataUtils.validaTrimestre();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int anoAtual = cal.get(java.util.Calendar.YEAR);
        
        int triRef = trimestreAtual - 1;
        int anoRef = anoAtual;
        if (triRef == 0) { triRef = 4; anoRef = anoAtual - 1; }

        for (JsonElement el : itens) {
            JsonObject item = el.getAsJsonObject();
            // Valida usando o VALOR DE MERCADO
            double valorMercado = NumeroUtils.stringToDouble(item.get("valorMercado").getAsString());
            int idMoeda = Integer.parseInt(item.get("id_moeda").getAsString());
            
            double taxa = PtaxDAO.getTaxaCompra(idMoeda, triRef, anoRef);
            somaTotalConvertidaBrl += (valorMercado * taxa);
        }

        boolean precisa = Ficha18DAO.verificarNecessidadeJustificativa(somaTotalConvertidaBrl, trimestreAtual, anoAtual);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"precisaJustificar\": " + precisa + "}");
    }

    private void salvarLote(JsonObject json, String chaveFuncionario) {
        JsonArray itens = json.getAsJsonArray("itens");
        String justificativa = "";
        if (json.has("justificativa") && !json.get("justificativa").isJsonNull()) {
            justificativa = json.get("justificativa").getAsString();
        }

        for (JsonElement el : itens) {
            JsonObject item = el.getAsJsonObject();
            Ficha18 ficha = new Ficha18();
            
            // Campos específicos da Ficha 18
            ficha.setPrazoDivida(item.get("prazo").getAsString());
            ficha.setValorMercado(NumeroUtils.stringToDouble(item.get("valorMercado").getAsString()));
            ficha.setJurosRecebidos(NumeroUtils.stringToDouble(item.get("juros").getAsString()));
            
            int moedaId = Integer.parseInt(item.get("id_moeda").getAsString());
            int paisId = Integer.parseInt(item.get("id_pais").getAsString());
            
            ficha.setMoeda(moedaController.getMoedaById(moedaId));
            ficha.setPais(paisController.getPaisById(paisId));
            ficha.setTrimestre(DataUtils.validaTrimestre());
            ficha.setDataCriacao(new java.util.Date());
            ficha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
            ficha.setStatus(statusController.getStatusById(1));
            ficha.setJustificativaGestor(justificativa);

            Ficha18DAO.create(ficha);
        }
    }
    
}