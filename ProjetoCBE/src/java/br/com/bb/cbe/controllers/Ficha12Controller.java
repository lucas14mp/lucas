package br.com.bb.cbe.controllers;

import java.util.Date;
import br.com.bb.cbe.DAO.Ficha12DAO;
import br.com.bb.cbe.Bean.Ficha12;
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

@WebServlet("/ficha12")

public class Ficha12Controller extends HttpServlet {

    private MoedaController moedaController;
    private EmpresaController empresaController;
    private FuncionarioController funcionarioController;
    private StatusController statusController;

    @Override
    public void init() {
        this.moedaController = new MoedaController();
        this.empresaController = new EmpresaController();
        this.funcionarioController = new FuncionarioController();
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
                System.out.println("LISTA: " + list);              
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
                        System.out.println(justificativaTeste.valor);
                            break;
                    }
                }
            }
            Ficha12 ficha = new Ficha12();

            if (tipoRequisicao.equals("post") || tipoRequisicao.equals("edit")) {
                int moedaId = Integer.parseInt(req.getParameter("moeda"));
                int empresaId = Integer.parseInt(req.getParameter("empresa"));
                ficha.setMoeda(moedaController.getMoedaById(moedaId));
                ficha.setEmpresa(empresaController.getEmpresaById(empresaId));
                ficha.setJurosPeriodoBase(NumeroUtils.stringToDouble(req.getParameter("juros")));
                ficha.setSaldoDatabase(NumeroUtils.stringToDouble(req.getParameter("saldo")));
                ficha.setPrazoEmprestimo(req.getParameter("prazo"));
                ficha.setDataCriacao(new Date());
                ficha.setTrimestre(DataUtils.validaTrimestre());
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
                ficha.setStatus(statusController.getStatusById(1)); // 1 - não certificado / 2 - certificado
            }
            switch (tipoRequisicao) {
                case "delete":
                    int id = Integer.parseInt(req.getParameter("id"));
                    Ficha12DAO.delete(id);
                    break;
                case "post":
                    ficha.setDataCriacao(new Date());
                    Ficha12DAO.create(ficha);
                    break;
                case "edit":
                    ficha.setDataCriacao(new Date());
                    ficha.setId(Integer.parseInt(req.getParameter("id")));
                    Ficha12DAO.update(ficha);
                    break;
                case "validacao":
                    String[] idsValidadosArray = req.getParameterValues("idsValidados[]");
                    List<String> idsValidadosList = new ArrayList<>();
                    if (idsValidadosArray != null) {
                        idsValidadosList = Arrays.asList(idsValidadosArray); // Convertendo array para ArrayList
                    }
                    Ficha12DAO.validarFormularios(idsValidadosList, chaveFuncionario);
                    break; 
                case "validacaoBatch":
                    System.out.println("TESTE");
                    List<String> arrayIdsValidados = processarValidacao(list);
                    for (String ids : arrayIdsValidados) {
                        System.out.println("ID: " + ids);
                    }
                    Ficha12DAO.validarFormularios(arrayIdsValidados, chaveFuncionario);
                    //Verificando se a justificativa não está nula e se é diferente de NTD (NÃO TEM DIFERENÇA)
                    if (justificativaTeste.valor != null && !"NTD".equals(justificativaTeste.valor)){
                        System.out.println("DIFERENTE DE NULO");
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
                    resp.getWriter().write("{\"redirectUrl\": \"/ProjetoCBE/views/ficha12.jsp\"}");
                    return;
            }
            resp.sendRedirect("views/ficha12.jsp");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("mensagemErro", "O valor foi inserido em um formato inválido.\nPor favor, utilize o padrão: \"0.000.000,00\"");
            req.setAttribute("linkPaginaAnterior", "/ProjetoCBE/forms/ficha12.jsp");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/errors/customError.jsp");
            dispatcher.forward(req, resp);
        }

    }

    public List<Ficha12> getAllFichas() {
        return Ficha12DAO.getAllFichas();
    }

    
    public List<Ficha12> getAllFichasByTrimestreAno(int trimestre, int ano) {
        int trimestreParaDAO = trimestre;
        int anoParaDAO = ano;

        // "Engana" o DAO enviando o trimestre anterior
        switch (trimestre) {
            case 1: trimestreParaDAO = 4; anoParaDAO = ano - 1; break;
            case 2: trimestreParaDAO = 1; break;
            case 3: trimestreParaDAO = 2; break;
            case 4: trimestreParaDAO = 3; break;
            default: trimestreParaDAO = trimestre; break;
        }
        return Ficha12DAO.getAllFichasByTrimestreAno(trimestreParaDAO, anoParaDAO);
    }
    
        public List<Ficha12> getAllFichasByTrimestre(int trimestre) {
        int trimestreParaDAO = trimestre;
        switch (trimestre) {
            case 1: trimestreParaDAO = 4; break;
            case 2: trimestreParaDAO = 1; break;
            case 3: trimestreParaDAO = 2; break;
            case 4: trimestreParaDAO = 3; break;
            default: trimestreParaDAO = trimestre; break;
        }
        return Ficha12DAO.getAllFichasByTrimestre(trimestreParaDAO);
    }
        
    public List<Ficha12> getAllFichasByAno(int ano) {
        return Ficha12DAO.getAllFichasByAno(ano);
    }

    public List<Integer> getAnosExistentes() {
        return Ficha12DAO.getAnosExistentes();
    }

    public List<Integer> getTrimestresExistentes() {
        return Ficha12DAO.getTrimestresExistentes();
    }


    public Ficha12 getFichaById(int id) {
        Optional<Ficha12> optFicha12 = Ficha12DAO.getFichaById(id);
        if (optFicha12.isPresent()) {
            return optFicha12.get();
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
                    switch (key) {
                        case "justificativa":
                            Object obJustificativa = map.get("justificativa");
                            String  justificativaNome = obJustificativa.toString();
                            System.out.println("Justificativa: " + justificativaNome);
                            tempJust.setJust(justificativaNome);
                            break;
                        case "numeroFicha":
                            Object obNumeroFicha = map.get("numeroFicha");
                            String  numeroFicha = obNumeroFicha.toString();
                            System.out.println("numeroFicha: " + numeroFicha);
                            tempJust.setNumeroFicha(numeroFicha);
                            break;
                        case "somatorio":   
                           System.out.println("somatorio");
                           Object obSomatorio = map.get("somatorio");
                           String numeroSM = obSomatorio.toString(); 
                           double somatorio = 0;
                           try {
                                somatorio = NumeroUtils.formatAndConvertToFloat(numeroSM);
                           } catch (ParseException ex) {
                               Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                           }
                           System.out.println("somatorio: " + somatorio);
                           tempJust.setSomatorio(somatorio);
                            break;
                        case "contabil":   
                           System.out.println("contabil");
                           Object obContabil = map.get("contabil");
                           String numeroCB = obContabil.toString(); 
                           double contabil = 0;
                           try {
                                contabil = NumeroUtils.formatAndConvertToFloat(numeroCB);
                           } catch (ParseException ex) {
                               Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                           }
                           System.out.println("contabil: " + contabil);
                           tempJust.setContabil(contabil);
                            break;
                        case "diferenca":   
                           System.out.println("diferenca");
                           Object obDiferenca = map.get("diferenca");
                           String numeroDF = obDiferenca.toString(); 
                           double diferenca = 0;
                           try {
                                diferenca = NumeroUtils.formatAndConvertToFloat(numeroDF);
                           } catch (ParseException ex) {
                               Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                           }
                           System.out.println("diferenca: " + diferenca);
                           tempJust.setDiferenca(diferenca);
                            break;    
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
        List<Ficha12> fichas = Ficha12DAO.getAllFichas();
        String ficha12Json = gson.toJson(fichas);
        return ficha12Json;
    }
        
        public List<Ficha12> getFichasPorPeriodo(int ano, int trimestre) {
        return Ficha12DAO.getAllFichasByTrimestreAno(trimestre, ano);
      }
        
}