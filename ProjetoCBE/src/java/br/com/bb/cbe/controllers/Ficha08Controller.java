package br.com.bb.cbe.controllers;

import java.util.Date;
import br.com.bb.cbe.DAO.Ficha08DAO;
import br.com.bb.cbe.Bean.Ficha08;
import br.com.bb.cbe.Bean.Justificativa;
import br.com.bb.cbe.DAO.PtaxDAO;
import br.com.bb.cbe.Utils.DataUtils;
import br.com.bb.cbe.Utils.NumeroUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
import br.com.bb.cbe.conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.PrintWriter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/ficha08")
public class Ficha08Controller extends HttpServlet {

    private MoedaController moedaController;
    private PaisController paisController;
    private FuncionarioController funcionarioController;
    private StatusController statusContoller;

    @Override
    public void init() {
        this.moedaController = new MoedaController();
        this.paisController = new PaisController();
        this.funcionarioController = new FuncionarioController();
        this.statusContoller = new StatusController();
    }

    @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");
        String tipoRequisicao = req.getParameter("tipo-requisicao");
        HttpSession session = req.getSession();
        String chaveFuncionario = (String) session.getAttribute("chave");

        JsonObject jsonBodyObject = null;
        List<Map<String, Object>> jsonBodyList = null;
        final class Teste { String valor; }
        Teste justificativaTeste = new Teste();

        try {
            // Leitura do JSON (Lote ou Lista Antiga)
            if (tipoRequisicao == null) {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = req.getReader();
                String line;
                while ((line = reader.readLine()) != null) sb.append(line);
                String json = sb.toString().trim();

                if (!json.isEmpty()) {
                    if (json.startsWith("{")) {
                        jsonBodyObject = JsonParser.parseString(json).getAsJsonObject();
                        if (jsonBodyObject.has("tipo-requisicao")) {
                            tipoRequisicao = jsonBodyObject.get("tipo-requisicao").getAsString();
                        }
                    } else if (json.startsWith("[")) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();
                        jsonBodyList = gson.fromJson(json, type);
                        for (Map<String, Object> map : jsonBodyList) {
                            if (map.containsKey("tipo-requisicao")) {
                                tipoRequisicao = (String) map.get("tipo-requisicao"); break;
                            }
                        }
                        for (Map<String, Object> map : jsonBodyList) {
                            if (map.containsKey("justificativa")) {
                                justificativaTeste.valor = (String) map.get("justificativa"); break;
                            }
                        }
                    }
                }
            }

            Ficha08 ficha = new Ficha08();
            // Preenchimento para requests via formulário padrão (não JSON)
            if ("post".equals(tipoRequisicao) || "edit".equals(tipoRequisicao)) {
                int moedaId = Integer.parseInt(req.getParameter("moeda"));
                int paisId = Integer.parseInt(req.getParameter("pais"));
                
                ficha.setMoeda(moedaController.getMoedaById(moedaId));
                ficha.setPais(paisController.getPaisById(paisId));
                ficha.setSaldoDatabase(NumeroUtils.stringToDouble(req.getParameter("valor")));
                
                // CORREÇÃO: Usando "rendimentos" conforme o formulário da Ficha 08
                ficha.setRendimentos(NumeroUtils.stringToDouble(req.getParameter("rendimentos")));
                
                ficha.setTrimestre(DataUtils.validaTrimestre());
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
                ficha.setStatus(statusContoller.getStatusById(1)); 
                
                String just = req.getParameter("justificativa_gestor");
                if(just != null && !just.isEmpty()) ficha.setJustificativaGestor(just);
                else ficha.setJustificativaGestor("");
            }

            if (tipoRequisicao == null) tipoRequisicao = "";

            switch (tipoRequisicao) {
                case "recusar":
                    int idRecusar = Integer.parseInt(req.getParameter("id"));
                    // Chama o DAO para mudar o status para 1
                    Ficha08DAO.alterarStatus(idRecusar, 1);
                    resp.setStatus(200);
                    return;
                case "delete":
                    int id = Integer.parseInt(req.getParameter("id"));
                    Ficha08DAO.delete(id);
                    break;
                case "post":
                    ficha.setDataCriacao(new Date());
                    Ficha08DAO.create(ficha);
                    break;
                case "edit":
                    ficha.setDataCriacao(new Date());
                    ficha.setId(Integer.parseInt(req.getParameter("id")));
                    Ficha08DAO.update(ficha);
                    break;
                case "validacao":
                    String[] idsValidadosArray = req.getParameterValues("idsValidados[]");
                    List<String> idsValidadosList = new ArrayList<>();
                    if (idsValidadosArray != null) idsValidadosList = Arrays.asList(idsValidadosArray);
                    Ficha08DAO.validarFormularios(idsValidadosList, chaveFuncionario);
                    break;
                case "validacaoBatch":
                    if (jsonBodyList != null) {
                        List<String> arrayIdsValidados = processarValidacao(jsonBodyList);
                        Ficha08DAO.validarFormularios(arrayIdsValidados, chaveFuncionario);
                        if (justificativaTeste.valor != null && !"NTD".equals(justificativaTeste.valor)) {
                            Justificativa justificativa = processarJustificativa(jsonBodyList, chaveFuncionario);
                            JustificativaController.createBatchJustController(justificativa);
                        }
                    }
                    break;

                // --- FLUXOS DE LOTE (FICHA 08) ---
                case "validar-lote":
                    validarLote(jsonBodyObject, resp);
                    return; 

                case "salvar-lote":
                    salvarLote(jsonBodyObject, chaveFuncionario);
                    resp.setStatus(200); 
                    return;
            }

            if (tipoRequisicao.equals("createbatch") || tipoRequisicao.equals("validacaoBatch")) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setHeader("Content-Type", "application/json");
                resp.getWriter().write("{\"redirectUrl\": \"/ProjetoCBE/views/ficha08.jsp\"}");
                return;
            }
            resp.sendRedirect("views/ficha08.jsp");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("mensagemErro", "Valor inválido.");
            req.setAttribute("linkPaginaAnterior", "/ProjetoCBE/forms/ficha08.jsp");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/errors/customError.jsp");
            dispatcher.forward(req, resp);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    // --- MÉTODOS AUXILIARES ---

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
            String valorStr = item.get("valor").getAsString();
            double valorOriginal = NumeroUtils.stringToDouble(valorStr);
            int idMoeda = Integer.parseInt(item.get("id_moeda").getAsString());

            double taxa = PtaxDAO.getTaxaCompra(idMoeda, triRef, anoRef);
            somaTotalConvertidaBrl += (valorOriginal * taxa);
        }

        boolean precisa = Ficha08DAO.verificarNecessidadeJustificativa(somaTotalConvertidaBrl, trimestreAtual, anoAtual);

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
            Ficha08 ficha = new Ficha08();
            
            ficha.setSaldoDatabase(NumeroUtils.stringToDouble(item.get("valor").getAsString()));
            
            // CORREÇÃO: Lê 'rendimentos' do JSON (deve bater com o JS)
            // Se o JS mandar 'dividendos', altere aqui para 'dividendos'.
            // Sugiro alterar o JS para enviar 'rendimentos' para ficar tudo igual.
            // Aqui estou assumindo que você ajustou o JS para enviar 'rendimentos' ou 'dividendos'.
            // Pelo seu JSP, o name é 'rendimentos'.
            if(item.has("rendimentos")) {
                ficha.setRendimentos(NumeroUtils.stringToDouble(item.get("rendimentos").getAsString()));
            } else if(item.has("dividendos")) {
                // Fallback caso o JS ainda mande dividendos
                ficha.setRendimentos(NumeroUtils.stringToDouble(item.get("dividendos").getAsString()));
            }
            
            int moedaId = Integer.parseInt(item.get("id_moeda").getAsString());
            int paisId = Integer.parseInt(item.get("id_pais").getAsString());
            
            ficha.setMoeda(moedaController.getMoedaById(moedaId));
            ficha.setPais(paisController.getPaisById(paisId));
            
            ficha.setTrimestre(DataUtils.validaTrimestre());
            ficha.setDataCriacao(new java.util.Date());
            ficha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
            ficha.setStatus(statusContoller.getStatusById(1));
            
            ficha.setJustificativaGestor(justificativa);

            Ficha08DAO.create(ficha);
        }
    }

    public List<Ficha08> getAllFichas() {
        return Ficha08DAO.getAllFichas();
    }

    public List<Ficha08> getAllFichasByTrimestreAno(int trimestre, int ano) {
        int trimestreParaDAO = trimestre;
        int anoParaDAO = ano;

        switch (trimestre) {
            case 1: trimestreParaDAO = 4; anoParaDAO = ano - 1; break;
            case 2: trimestreParaDAO = 1; break;
            case 3: trimestreParaDAO = 2; break;
            case 4: trimestreParaDAO = 3; break;
            default: trimestreParaDAO = trimestre; break;
        }
        return Ficha08DAO.getAllFichasByTrimestreAno(trimestreParaDAO, anoParaDAO);
    }

    public List<Ficha08> getAllFichasByTrimestre(int trimestre) {
        int trimestreParaDAO = trimestre;
        switch (trimestre) {
            case 1: trimestreParaDAO = 4; break;
            case 2: trimestreParaDAO = 1; break;
            case 3: trimestreParaDAO = 2; break;
            case 4: trimestreParaDAO = 3; break;
            default: trimestreParaDAO = trimestre; break;
        }
        return Ficha08DAO.getAllFichasByTrimestre(trimestreParaDAO);
    }

    public List<Ficha08> getAllFichasByAno(int ano) {
        return Ficha08DAO.getAllFichasByAno(ano);
    }

    public List<Integer> getAnosExistentes() {
        return Ficha08DAO.getAnosExistentes();
    }

    public List<Integer> getTrimestresExistentes() {
        return Ficha08DAO.getTrimestresExistentes();
    }

    public Ficha08 getFichaById(int id) {
        Optional<Ficha08> optFicha08 = Ficha08DAO.getFichaById(id);
        if (optFicha08.isPresent()) {
            return optFicha08.get();
        }
        return null;
    }
    
    public static List<String> processarValidacao(List<Map<String, Object>> list){
        final class Ids { List<String> ids; }
        Ids idsFinal = new Ids();
        for (Map<String, Object> map : list) {
            map.forEach((key, value) -> {
                if (value != null && key.equals("fichas")) {
                    List<?> lista = (List<?>) map.get("fichas");
                    idsFinal.ids = (List<String>) lista;
                }
            });
        }
        return idsFinal.ids;
    }
    
    public static Justificativa processarJustificativa(List<Map<String, Object>> list, String chaveFuncionario){
        Justificativa tempJust = new Justificativa();
        FuncionarioController funcionario = new FuncionarioController();
        for (Map<String, Object> map : list) {
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
                        Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
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
        List<Ficha08> fichas = Ficha08DAO.getAllFichas();
        String ficha08Json = gson.toJson(fichas);
        return ficha08Json;
}
     
     public List<Ficha08> getFichasPorPeriodo(int ano, int trimestre) {
        return Ficha08DAO.getAllFichasByTrimestreAno(trimestre, ano);
      }
     
}