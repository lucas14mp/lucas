package br.com.bb.cbe.controllers;

import br.com.bb.cbe.DAO.Ficha17DAO;
import br.com.bb.cbe.Bean.Ficha17;
import br.com.bb.cbe.Bean.Justificativa;
import br.com.bb.cbe.Utils.DataUtils;
import br.com.bb.cbe.Utils.NumeroUtils;
import static br.com.bb.cbe.controllers.Ficha02Controller.processarJustificativa;
import static br.com.bb.cbe.controllers.Ficha02Controller.processarValidacao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
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

@WebServlet("/ficha17")

public class Ficha17Controller extends HttpServlet {

    private MoedaController moedaController;
    private EmpresaController empresaController;
    private FuncionarioController funcionarioController;
    private StatusController statusController;

    @Override
    public void init() {
        this.moedaController = new MoedaController();
        this.funcionarioController = new FuncionarioController();
        this.empresaController = new EmpresaController();
        this.statusController = new StatusController();
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
                // System.out.println("LISTA: " + list);              
                // Primeiro loop para encontrar "tipoRequisicao"
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

            Ficha17 ficha = new Ficha17();
            if (tipoRequisicao.equals("post") || tipoRequisicao.equals("edit")) {
                int empresaId = Integer.parseInt(req.getParameter("empresa"));
                int moedaId = Integer.parseInt(req.getParameter("moeda"));
                ficha.setEmpresa(empresaController.getEmpresaById(empresaId));
                ficha.setMoeda(moedaController.getMoedaById(moedaId));
                ficha.setPrazoDivida(req.getParameter("prazo-divida"));
                ficha.setValorMercado(NumeroUtils.stringToDouble(req.getParameter("valor")));
                ficha.setJurosRecebidos(NumeroUtils.stringToDouble(req.getParameter("juros")));
                ficha.setDataCriacao(new Date());
                ficha.setTrimestre(DataUtils.validaTrimestre());
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
                ficha.setStatus(statusController.getStatusById(1)); // 1 - não certificado / 2 - certificado
            }
            switch (tipoRequisicao) {
                case "delete":
                    int id = Integer.parseInt(req.getParameter("id"));
                    Ficha17DAO.delete(id);
                    break;
                case "post":
                    ficha.setDataCriacao(new Date());
                    Ficha17DAO.create(ficha);
                    break;
                case "edit":
                    ficha.setDataCriacao(new Date());
                    ficha.setId(Integer.parseInt(req.getParameter("id")));
                    Ficha17DAO.update(ficha);
                    break;
                case "validacao":
                    String[] idsValidadosArray = req.getParameterValues("idsValidados[]");
                    List<String> idsValidadosList = new ArrayList<>();
                    if (idsValidadosArray != null) {
                        idsValidadosList = Arrays.asList(idsValidadosArray); // Convertendo array para ArrayList
                    }
                    Ficha17DAO.validarFormularios(idsValidadosList, chaveFuncionario);
                    break;
                case "validacaoBatch":
                    System.out.println("TESTE");
                    List<String> arrayIdsValidados = processarValidacao(list);
                    for (String ids : arrayIdsValidados) {
                        System.out.println("ID: " + ids);
                    }
                    Ficha17DAO.validarFormularios(arrayIdsValidados, chaveFuncionario);
                    //Verificando se a justificativa não está nula e se é diferente de NTD (NÃO TEM DIFERENÇA)
                    if (justificativaTeste.valor != null && !"NTD".equals(justificativaTeste.valor)){
                        Justificativa justificativa = processarJustificativa(list, chaveFuncionario);
                        JustificativaController.createBatchJustController(justificativa);
                    }
                    break;
                default:
                    System.out.println("Tipo de requisição desconhecido");
            }
            if (tipoRequisicao.equals("createbatch") || tipoRequisicao.equals("validacaoBatch")) {
//                  Redirecionando caso a requisição seja feita pelo ajax  
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.setHeader("Content-Type", "application/json");
                    resp.getWriter().write("{\"redirectUrl\": \"/ProjetoCBE/views/ficha17.jsp\"}");
                    return;
            }
            resp.sendRedirect("views/ficha17.jsp");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("mensagemErro", "O valor foi inserido em um formato inválido.");
            req.setAttribute("linkPaginaAnterior", "/ProjetoCBE/forms/ficha17.jsp");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/errors/customError.jsp");
            dispatcher.forward(req, resp);
        }
    }

    public List<Ficha17> getAllFichas() {
        return Ficha17DAO.getAllFichas();
    }

    // =========================================================================
    // LÓGICA INVERSA E MÉTODOS DE FILTRO
    // =========================================================================

    public List<Ficha17> getAllFichasByTrimestreAno(int trimestre, int ano) {
        int trimestreParaDAO = trimestre;
        int anoParaDAO = ano;

        switch (trimestre) {
            case 1: trimestreParaDAO = 4; anoParaDAO = ano - 1; break;
            case 2: trimestreParaDAO = 1; break;
            case 3: trimestreParaDAO = 2; break;
            case 4: trimestreParaDAO = 3; break;
            default: trimestreParaDAO = trimestre; break;
        }
        return Ficha17DAO.getAllFichasByTrimestreAno(trimestreParaDAO, anoParaDAO);
    }

    public List<Ficha17> getAllFichasByTrimestre(int trimestre) {
        int trimestreParaDAO = trimestre;
        switch (trimestre) {
            case 1: trimestreParaDAO = 4; break;
            case 2: trimestreParaDAO = 1; break;
            case 3: trimestreParaDAO = 2; break;
            case 4: trimestreParaDAO = 3; break;
            default: trimestreParaDAO = trimestre; break;
        }
        return Ficha17DAO.getAllFichasByTrimestre(trimestreParaDAO);
    }

    public List<Ficha17> getAllFichasByAno(int ano) {
        return Ficha17DAO.getAllFichasByAno(ano);
    }

    public List<Integer> getAnosExistentes() {
        return Ficha17DAO.getAnosExistentes();
    }

    public List<Integer> getTrimestresExistentes() {
        return Ficha17DAO.getTrimestresExistentes();
    }

    public Ficha17 getFichaById(int id) {
        Optional<Ficha17> optFicha17 = Ficha17DAO.getFichaById(id);
        if (optFicha17.isPresent()) {
            return optFicha17.get();
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
                        Logger.getLogger(Ficha17Controller.class.getName()).log(Level.SEVERE, null, ex);
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
        List<Ficha17> fichas = Ficha17DAO.getAllFichas();
        return gson.toJson(fichas);
    }
    
    public List<Ficha17> getFichasPorPeriodo(int ano, int trimestre) {
        return Ficha17DAO.getAllFichasByTrimestreAno(trimestre, ano);
      }
    
}