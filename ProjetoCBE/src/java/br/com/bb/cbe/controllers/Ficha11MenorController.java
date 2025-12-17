package br.com.bb.cbe.controllers;

import br.com.bb.cbe.Bean.Empresa;
import br.com.bb.cbe.Bean.Ficha11Maior;
import br.com.bb.cbe.controllers.JustificativaController;
import br.com.bb.cbe.Bean.Pais;
import br.com.bb.cbe.Bean.Ficha11Menor;
import br.com.bb.cbe.Bean.Justificativa;
import br.com.bb.cbe.Bean.Moeda;
import br.com.bb.cbe.DAO.Ficha11MenorDAO;
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

@WebServlet("/ficha11/menor")
public class Ficha11MenorController extends HttpServlet {

    private PaisController paisController;
    private MoedaController moedaController;
    private FuncionarioController funcionarioController;
    private StatusController statusController;

    @Override
    public void init() {
        this.paisController = new PaisController();
        this.moedaController = new MoedaController();
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
            Teste testeNomeDiretoria = new Teste();
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
                for (Map<String, Object> map : list) {
                    if (map.containsKey("empresa")) {
                        String empresa = (String) map.get ("empresa");
                        System.out.println("EMPRESA: " + empresa);
                            break;
                    }
                }              
            }
            System.out.println("DIRETORIA: " + testeNomeDiretoria.valor);
            Ficha11Menor ficha11 = new Ficha11Menor(); // Crie uma nova instância de Ficha1
            Ficha11Menor ficha = new Ficha11Menor();
            if (tipoRequisicao.equals("post") || tipoRequisicao.equals("edit")) {
                int paisId = Integer.parseInt(req.getParameter("pais"));
                int moedaId = Integer.parseInt(req.getParameter("moeda"));
                ficha.setPais(paisController.getPaisById(paisId));
                ficha.setMoeda(moedaController.getMoedaById(moedaId));
                ficha.setMetodoValoracao(req.getParameter("metodoValoracao"));
                ficha.setValorParticipacao(NumeroUtils.stringToDouble(req.getParameter("valorParticipacao")));
                ficha.setLucroDistribuido(NumeroUtils.stringToDouble(req.getParameter("lucroDistribuido")));
                ficha.setDataCriacao(new Date());
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
                ficha.setTrimestre(DataUtils.validaTrimestre());
                ficha.setStatus(statusController.getStatusById(1)); // 1 - não certificado / 2 - certificado
            }
            switch (tipoRequisicao) {
                case "delete":
                    int id = Integer.parseInt(req.getParameter("id"));
                    System.out.println("delete" + id);
                    Ficha11MenorDAO.delete(id);
                    break;
                case "post":
                    ficha.setDataCriacao(new Date());
                    Ficha11MenorDAO.create(ficha);
                    break;
                case "edit":
                    ficha.setDataCriacao(new Date());
                    ficha.setId(Integer.parseInt(req.getParameter("id")));
                    Ficha11MenorDAO.update(ficha);
                    break;
                case "validacao":
                    String[] idsValidadosArray = req.getParameterValues("idsValidados[]");
                    List<String> idsValidadosList = new ArrayList<>();
                    if (idsValidadosArray != null) {
                        idsValidadosList = Arrays.asList(idsValidadosArray); // Convertendo array para ArrayList
                    }
                    System.out.println("teste" + idsValidadosList);
                    Ficha11MenorDAO.validarFormularios(idsValidadosList, chaveFuncionario);
                    break;
                case "validacaoBatch":
                    System.out.println("TESTE");
                    List<String> arrayIdsValidados = processarValidacao(list);
                    for (String ids : arrayIdsValidados) {
                        System.out.println("ID: " + ids);
                    }
                    Ficha11MenorDAO.validarFormularios(arrayIdsValidados, chaveFuncionario);
                    //Verificando se a justificativa não está nula e se é diferente de NTD (NÃO TEM DIFERENÇA)
                    if (justificativaTeste.valor != null && !"NTD".equals(justificativaTeste.valor)){
                        System.out.println("DIFERENTE DE NULO");
                        Justificativa justificativa = processarJustificativa(list, chaveFuncionario);
                        JustificativaController.createBatchJustController(justificativa);
                    }
                    break;
                case "createbatch":
                    System.out.println("ENTROU");
//                    String diretoria = "";
//                    //CRIANDO UMA CLASSE FINAL PARA PEGAR O VALOR DA DIRETORIA E DEPOIS ATRIBUIR A STRING DIRETORIA
//                    final class Nome {
//                        String valor;
//                    }
//                    Nome nomeDiretoria = new Nome();
                    List<Ficha11Menor> fichas = new ArrayList<>();;
//                     // Iterar sobre a lista e inserir cada linha na tabela do banco de dados
                    for (Map<String, Object> map : list) {
                        Ficha11Menor tempFicha = new Ficha11Menor(); // Crie uma nova instância de Ficha1;
                        map.forEach((key, value) -> {
                            if (value != null) {                                                              
                                switch (key) {
                                    case "pais":
                                        Object obPais = map.get("pais");
                                        String  paisNome = obPais.toString();
                                        System.out.println("NOME PAIS: " + paisNome);
                                        Pais pais = paisController.getPaisByNome((String) (paisNome));
                                        System.out.println("Nome pais " + pais.getNome());
                                        tempFicha.setPais((Pais) pais);                                                                               
                                        break;
                                    case "moeda":
                                        Object obMoedaId = map.get("moeda");
                                        String nomeMoeda = obMoedaId.toString();
                                        nomeMoeda = NumeroUtils.verificarString(nomeMoeda);
                                        System.out.println("NOME MOEDA: " + nomeMoeda);
                                        Moeda moeda = moedaController.getMoedaByNome((String) (nomeMoeda));
                                        System.out.println("MOEDA Nome: " + moeda.getNome());
                                        tempFicha.setMoeda((Moeda) moeda);
                                        System.out.println("MOEDA FICHA: " + tempFicha.getMoeda().getId());
                                        break;
                                    case "valoracao":
                                        Object obValoracao = map.get("valoracao");
                                        System.out.println("OB VALORACAO: " + obValoracao.toString());
                                        String valoracao = "";
                                        boolean resultado =  Ficha11MenorController.testarValoracao(obValoracao.toString());
                                        if (resultado == false){
                                            System.out.println("ENTROU IF");
                                            valoracao = "Não informado";
                                        }
                                        else{
                                        valoracao = obValoracao.toString();
                                        System.out.println("VALORÇÃO ELSE: " + valoracao);   
                                        }
                                        System.out.println("VALORACAO FORA IF ELSE: " + valoracao);
                                        tempFicha.setMetodoValoracao(valoracao);
                                        break;
                                    case "valorParticipacao":
                                       Object obValorParticipacao = map.get("valorParticipacao");
                                       Double valorParticipacao = NumeroUtils.stringToDouble(obValorParticipacao.toString());
                                       System.out.println("PORCENTAGEM SOCIAL: " + valorParticipacao);
                                       tempFicha.setValorParticipacao(valorParticipacao);
                                        break;
                                    case "lucroDistribuido":
                                       Object obLucroDistribuido = map.get("lucroDistribuido");
                                       Double lucroDistribuido = NumeroUtils.stringToDouble(obLucroDistribuido.toString());
                                       System.out.println("PORCENTAGEM VOTO: " + lucroDistribuido);
                                       tempFicha.setLucroDistribuido(lucroDistribuido);
                                        break;
                                }
                            }
                        });
                        tempFicha.setTrimestre(DataUtils.validaTrimestre());
                        tempFicha.setDataCriacao(new Date());
                        tempFicha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
                        tempFicha.setStatus(statusController.getStatusById(1)); // 1 - não certificado / 2 - certificado
                        fichas.add(tempFicha);
                        
                        for (Ficha11Menor registro : fichas){
                            List<Ficha11Menor> temp = new ArrayList();
                            temp.add(registro);
                            if(registro.getPais() != null){
//                                boolean existe = Ficha11MenorDAO.paisExiste(registro.getPais().getId());
                                System.out.println("CRIANDO");
                                Ficha11MenorDAO.createBatch(temp);                               
                            }
                            else{
                                System.out.println("IGNORANDO ITENS INDESEJADOS MENORES 2");
                            }
                        }
                        
                    }
                    
                    break;
                default:
                    System.out.println("Tipo de requisição desconhecido");
            }
            
            if (tipoRequisicao.equals("createbatch") || tipoRequisicao.equals("validacaoBatch") ) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setHeader("Content-Type", "application/json");
                resp.getWriter().write("{\"redirectUrl\": \"/ProjetoCBE/views/ficha11.jsp\"}");
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

    public List<Ficha11Menor> getAllFichas() {
        return Ficha11MenorDAO.getAllFichas();
    }
    
     public String getAllFichasJson() {
        Gson gson = new Gson(); 
        List<Ficha11Menor> fichas = Ficha11MenorDAO.getAllFichas();
        String fichasMenorJson = gson.toJson(fichas);
        return fichasMenorJson;
    }

    public List<Ficha11Menor> getAllFichasByTrimestreAno(int trimestre, int ano) {
        return Ficha11MenorDAO.getAllFichasByTrimestreAno(trimestre, ano);
    }
    
    public Ficha11Menor getFichaById(int id) {
        Optional<Ficha11Menor> optFicha11Menor = Ficha11MenorDAO.getFichaById(id);
        if (optFicha11Menor.isPresent()) {
            return optFicha11Menor.get();
        }
        return null;
    }
    
    public static boolean testarValoracao(String valoracao){
        System.out.println("TESTANDO");
        boolean resultado;
        if ("Avaliação por especialista".equals(valoracao)){
            resultado = true;
        }
        else if ("Fluxo de caixa descontado".equals(valoracao)){
            resultado = true;
        }
        else if ("Negociação recente de parcela do capital".equals(valoracao)){
            resultado = true;
        }
        else if ("Valor patrimonial".equals(valoracao)){
            resultado = true;
        }
        else{
            resultado = false;
            System.out.println("ENTROU ELSE");
        }       
        System.out.println("RESULTADO: " + resultado);
        return resultado;
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
    }
