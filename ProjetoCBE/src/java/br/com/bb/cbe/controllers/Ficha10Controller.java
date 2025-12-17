package br.com.bb.cbe.controllers;

import java.util.Date;
import br.com.bb.cbe.DAO.Ficha10DAO;
import br.com.bb.cbe.Bean.Ficha10;
import br.com.bb.cbe.Bean.Justificativa;
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

@WebServlet("/ficha10")
public class Ficha10Controller extends HttpServlet {

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
        try {
            final class Teste {
                String valor;
            }
            Teste justificativaTeste = new Teste();
            List<Map<String, Object>> list = null;
            if (tipoRequisicao == null) { 
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
                // System.out.println("LISTA: " + list);              
                
                for (Map<String, Object> map : list) {
                    if (map.containsKey("tipo-requisicao")) {
                        tipoRequisicao = (String) map.get("tipo-requisicao");
                            break;
                    }
                }
                
                for (Map<String, Object> map : list) {
                    if (map.containsKey("justificativa")) {
                        justificativaTeste.valor = (String) map.get("justificativa");
                        // System.out.println(justificativaTeste.valor);
                            break;
                    }
                }
            }
            Ficha10 ficha = new Ficha10();
            if (tipoRequisicao.equals("post") || tipoRequisicao.equals("edit")) {
                int moedaId = Integer.parseInt(req.getParameter("moeda"));
                int paisId = Integer.parseInt(req.getParameter("pais"));
                ficha.setMoeda(moedaController.getMoedaById(moedaId));
                ficha.setPais(paisController.getPaisById(paisId));
                ficha.setMetodoValoracao(String.format(req.getParameter("metodo")));
                ficha.setValorDatabase(NumeroUtils.stringToDouble(req.getParameter("valor")));
                ficha.setDataCriacao(new Date());
                ficha.setTrimestre(DataUtils.validaTrimestre());
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
                ficha.setStatus(statusContoller.getStatusById(1)); 
            }
            switch (tipoRequisicao) {
                case "delete":
                    int id = Integer.parseInt(req.getParameter("id"));
                    Ficha10DAO.delete(id);
                    break;
                case "post":
                    ficha.setDataCriacao(new Date());
                    Ficha10DAO.create(ficha);
                    break;
                case "edit":
                    ficha.setDataCriacao(new Date());
                    ficha.setId(Integer.parseInt(req.getParameter("id")));
                    Ficha10DAO.update(ficha);
                    break;
                case "validacao":
                    String[] idsValidadosArray = req.getParameterValues("idsValidados[]");
                    List<String> idsValidadosList = new ArrayList<>();
                    if (idsValidadosArray != null) {
                        idsValidadosList = Arrays.asList(idsValidadosArray);
                    }
                    Ficha10DAO.validarFormularios(idsValidadosList, chaveFuncionario);
                    break;
                case "validacaoBatch":
                    List<String> arrayIdsValidados = processarValidacao(list);
                    Ficha10DAO.validarFormularios(arrayIdsValidados, chaveFuncionario);
                    if (justificativaTeste.valor != null && !"NTD".equals(justificativaTeste.valor)){
                        Justificativa justificativa = processarJustificativa(list, chaveFuncionario);
                        JustificativaController.createBatchJustController(justificativa);
                    }
                    break;    
                default:
                    System.out.println("Tipo de requisição desconhecido");
            }
            if (tipoRequisicao.equals("createbatch") || tipoRequisicao.equals("validacaoBatch")) {
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.setHeader("Content-Type", "application/json");
                    resp.getWriter().write("{\"redirectUrl\": \"/ProjetoCBE/views/ficha10.jsp\"}");
                    return;
            }
            resp.sendRedirect("views/ficha10.jsp");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("mensagemErro", "O valor foi inserido em um formato inválido.");
            req.setAttribute("linkPaginaAnterior", "/ProjetoCBE/forms/ficha10.jsp");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/errors/customError.jsp");
            dispatcher.forward(req, resp);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public List<Ficha10> getAllFichas() {
        return Ficha10DAO.getAllFichas();
    }

    public List<Ficha10> getAllFichasByTrimestreAno(int trimestre, int ano) {
        int trimestreParaDAO = trimestre;
        int anoParaDAO = ano;

        switch (trimestre) {
            case 1: trimestreParaDAO = 4; anoParaDAO = ano - 1; break;
            case 2: trimestreParaDAO = 1; break;
            case 3: trimestreParaDAO = 2; break;
            case 4: trimestreParaDAO = 3; break;
            default: trimestreParaDAO = trimestre; break;
        }
        return Ficha10DAO.getAllFichasByTrimestreAno(trimestreParaDAO, anoParaDAO);
    }

    public List<Ficha10> getAllFichasByTrimestre(int trimestre) {
        int trimestreParaDAO = trimestre;
        switch (trimestre) {
            case 1: trimestreParaDAO = 4; break;
            case 2: trimestreParaDAO = 1; break;
            case 3: trimestreParaDAO = 2; break;
            case 4: trimestreParaDAO = 3; break;
            default: trimestreParaDAO = trimestre; break;
        }
        return Ficha10DAO.getAllFichasByTrimestre(trimestreParaDAO);
    }

    public List<Ficha10> getAllFichasByAno(int ano) {
        return Ficha10DAO.getAllFichasByAno(ano);
    }

    public List<Integer> getAnosExistentes() {
        return Ficha10DAO.getAnosExistentes();
    }

    public List<Integer> getTrimestresExistentes() {
        return Ficha10DAO.getTrimestresExistentes();
    }

    public Ficha10 getFichaById(int id) {
        Optional<Ficha10> optFicha10 = Ficha10DAO.getFichaById(id);
        if (optFicha10.isPresent()) {
            return optFicha10.get();
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
        List<Ficha10> fichas = Ficha10DAO.getAllFichas();
        String ficha10Json = gson.toJson(fichas);
        return ficha10Json;
}
        
        public List<Ficha10> getFichasPorPeriodo(int ano, int trimestre) {
        return Ficha10DAO.getAllFichasByTrimestreAno(trimestre, ano);
      }
        
}
