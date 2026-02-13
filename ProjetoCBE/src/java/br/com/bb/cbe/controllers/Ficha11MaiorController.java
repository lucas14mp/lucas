package br.com.bb.cbe.controllers;

import br.com.bb.cbe.Bean.Dependencia;
import br.com.bb.cbe.Bean.Ficha11Maior;
import br.com.bb.cbe.Services.Ptax;
import br.com.bb.cbe.DAO.Ficha11MaiorDAO;
import br.com.bb.cbe.Bean.Empresa;
import br.com.bb.cbe.Bean.Ficha11Controle;
import br.com.bb.cbe.Bean.Funcionario;
import br.com.bb.cbe.Bean.Justificativa;
import br.com.bb.cbe.Bean.Moeda;
import br.com.bb.cbe.Bean.Status;
import br.com.bb.cbe.DAO.Ficha11ControleDAO;
import br.com.bb.cbe.Utils.DataUtils;
import br.com.bb.cbe.Utils.JsonUtil;
import br.com.bb.cbe.Utils.NumeroUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/ficha11/maior")

public class Ficha11MaiorController extends HttpServlet {

    private MoedaController moedaController;
    private FuncionarioController funcionarioController;
    private EmpresaController empresaController;
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
            Teste justificativaTeste = new Teste();
            JsonObject jsonBodyObject = null;
            List<Map<String, Object>> list = null;
            if (tipoRequisicao == null) { 
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = req.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String json = sb.toString().trim(); // Use trim() para garantir
                Gson gson = new Gson();

                if (!json.isEmpty()) {
                    // SE FOR O NOVO UPLOAD (Objeto começa com {)
                    if (json.startsWith("{")) {
                        jsonBodyObject = gson.fromJson(json, JsonObject.class);
                        if (jsonBodyObject.has("tipo-requisicao")) {
                            tipoRequisicao = jsonBodyObject.get("tipo-requisicao").getAsString();
                        }
                    } 
                    // SE FOR O ANTIGO (Lista começa com [) - MANTÉM SUA LÓGICA ANTIGA AQUI
                    else {
                        Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();
                        list = gson.fromJson(json, type); // Sua linha antiga
                        
                        // ... MANTENHA SEUS LOOPS ANTIGOS AQUI (createbatch, diretoria, justificativa) ...
                        // (Não apague nada do que você já tinha aqui dentro)
                    }
                }
            }    
            
//            Ficha11MaiorController.obterCotacaoDolar(datinha);
            System.out.println("DIRETORIA: " + testeNomeDiretoria.valor);
            Ficha11Maior ficha = new Ficha11Maior();
            int idFicha = Ficha11MaiorDAO.getIdIncrementado();
            if (tipoRequisicao.equals("post") || tipoRequisicao.equals("edit")) {

                int idMoeda;
                if (req.getParameter("moeda").isEmpty()) {
                    ficha.setMoeda(moedaController.getMoedaById(15));
                } else {
                    idMoeda = Integer.parseInt(req.getParameter("moeda"));
                    ficha.setMoeda(moedaController.getMoedaById(idMoeda));
                }

                int idEmpresa = Integer.parseInt(req.getParameter("empresa"));
                ficha.setEmpresa(empresaController.getEmpresaById(idEmpresa));

                ficha.setPossuiCotacaoEmBolsa(Boolean.parseBoolean(req.getParameter("cotacao")));
                if (req.getParameter("valoracao").isEmpty()) {
                    ficha.setMetodoValoracao("Não informado");
                } else {
                    ficha.setMetodoValoracao(req.getParameter("valoracao"));
                }
                ficha.setValorEmpresa(NumeroUtils.stringToDouble(req.getParameter("valorDataBase")));
                ficha.setPatrimonioTotal(NumeroUtils.stringToDouble(req.getParameter("patrimonioLiquido")));
                ficha.setPorcentoParticipacaoCapital(NumeroUtils.stringToDouble(req.getParameter("porcentagemSocial")));
                ficha.setPorcentoPoderVoto(NumeroUtils.stringToDouble(req.getParameter("porcentagemVoto")));
                ficha.setAtivoDatabase(NumeroUtils.stringToDouble(req.getParameter("ativoDataBase")));
                ficha.setPassivoExigivel(NumeroUtils.stringToDouble(req.getParameter("passivoExigivel")));
                ficha.setValorTotalLucroPrejuizo(NumeroUtils.stringToDouble(req.getParameter("valorTotal")));
                ficha.setResultadoLiquidoItensNaoRecorrentes(NumeroUtils.stringToDouble(req.getParameter("resultadoLiquidoItens")));
                ficha.setResultadoLiquidoReavaliacoes(NumeroUtils.stringToDouble(req.getParameter("resultadoLiquidoReavaliacoes")));
                ficha.setResultadoLiquidoVariacaoCambial(NumeroUtils.stringToDouble(req.getParameter("resultadoLiquidoCambial")));
                ficha.setLucroDistribuido(NumeroUtils.stringToDouble(req.getParameter("lucroDistribuido")));
                ficha.setControlaEmpresa(Boolean.parseBoolean(req.getParameter("controla")));
                ficha.setTrimestre(DataUtils.validaTrimestre());
                ficha.setDataCriacao(new Date());
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
                ficha.setStatus(statusController.getStatusById(1)); // 1 - não certificado / 2 - certificado
            }
            switch (tipoRequisicao) {
                case "delete":
                    int id = Integer.parseInt(req.getParameter("id"));
                    Ficha11MaiorDAO.delete(id);
                    break;
                case "post":
                    ficha.setDataCriacao(new Date());
                    Ficha11MaiorDAO.create(ficha);
                    break;
                case "edit":
                    ficha.setDataCriacao(new Date());
                    ficha.setId(Integer.parseInt(req.getParameter("id")));
                    Ficha11MaiorDAO.update(ficha);
                    break;
                case "validar-lote":
                    validarLote(jsonBodyObject, resp);
                    return;

                case "salvar-lote":
                    try {
                        salvarLote(jsonBodyObject, chaveFuncionario);
                        resp.setStatus(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                        resp.setStatus(500);
                        resp.setContentType("text/plain; charset=UTF-8");
                        resp.getWriter().write("Erro ao gravar: " + e.getMessage());
                    }
                    return;
                case "validacao":
                    System.out.println("TESTE");
                    String[] idsValidadosArray = req.getParameterValues("idsValidados[]");
                    List<String> idsValidadosList = new ArrayList<>();
                    if (idsValidadosArray != null) {
                        idsValidadosList = Arrays.asList(idsValidadosArray); // Convertendo array para ArrayList
                    }
//                    System.out.println("teste" + idsValidadosList);
                    Ficha11MaiorDAO.validarFormularios(idsValidadosList, chaveFuncionario);
                    break;
                case "validacaoBatch":
                    System.out.println("TESTE");
                    List<String> arrayIdsValidados = processarValidacao(list);
                    for (String ids : arrayIdsValidados) {
                        System.out.println("ID: " + ids);
                    }
                    Ficha11MaiorDAO.validarFormularios(arrayIdsValidados, chaveFuncionario);
                    //Verificando se a justificativa não está nula e se é diferente de NTD (NÃO TEM DIFERENÇA)
                    if (justificativaTeste.valor != null && !"NTD".equals(justificativaTeste.valor)){
                        System.out.println("DIFERENTE DE NULO");
                        Justificativa justificativa = processarJustificativa(list, chaveFuncionario);
                        JustificativaController.createBatchJustController(justificativa);
                    }
                    break;
                case "createbatch":            
                    System.out.println("ENTROU");
                    List<Ficha11Maior> fichas = new ArrayList<>();
//                  Verficiando qual é a diretoria para poder processar as informações
//                  SEÇÃO COGER
                    if ("COGER".equals(testeNomeDiretoria.valor)){                                                
//                      Processando informações e atribuindo a variável fichas  
                        fichas = Ficha11MaiorController.processarFichasCoger(list, fichas, funcionarioController, statusController, 
                                empresaController, moedaController, chaveFuncionario);
//                      Loop para adicionar uma ficha de cada vez para as n fichas que tiverem  
                        for (Ficha11Maior registro : fichas){
                            List<Ficha11Maior> temp = new ArrayList<>();
                            temp.add(registro);
//                          Verfica se na ficha tem empresa caso não tenha, essa ficha deve ser ignorada
                            if (registro.getEmpresa() != null){
//                              Verificando se já existe registro na tabela Ficha11Maior com o mesmo id de empresa presente na ficha
                                SimpleDateFormat anoFormat = new SimpleDateFormat("yyyy");
                                int anoFicha = Integer.parseInt(anoFormat.format(registro.getDataCriacao()));
                                System.out.println("DATA EMPRESA COGER: " + anoFicha);
                                boolean existe = Ficha11MaiorController.empresaExiste(registro.getEmpresa().getId(), registro.getTrimestre(), anoFicha);
                                System.out.println("EMPRESA QUE VAI SER CRIADA OU ATUALIZADA: " + registro.getEmpresa().getNome());
//                              Caso tenha, irá atualizar o registro que contem essa empresa  
                                if (existe){
                                    System.out.println("COGER: EMPRESA JA EXISTE " + registro.getTrimestre());
                                    Ficha11MaiorDAO.updateBatchCoger(temp);
                                }
//                              Caso não tenha, irá criar um novo registro  
                                else{
                                    System.out.println("COGER: EMPRESA NÃO EXISTE " + registro.getTrimestre());
                                    Ficha11MaiorDAO.createBatchCoger(temp);
                                }
                            }
                            else{
                                System.out.println("PARTE INDESEJADA IGNORADA");
                            }
                        }
                    }
//                  SEÇÃO UPE  
                    else{
//                      Processando informações e atribuindo a variável fichas  
                        fichas = Ficha11MaiorController.processarFichasUpe(list, fichas, funcionarioController, statusController, 
                                empresaController, moedaController, chaveFuncionario);
//                      Loop para adicionar uma ficha de cada vez para as n fichas que tiverem
                        for (Ficha11Maior registro : fichas){
                            List<Ficha11Maior> temp = new ArrayList<>();
                            temp.add(registro);
//                          Verfica se na ficha tem empresa caso não tenha, essa ficha deve ser ignorada
                            if (registro.getEmpresa() != null){
//                              Verificando se já existe registro na tabela Ficha11Maior com o mesmo id de empresa presente na ficha 
                                SimpleDateFormat anoFormat = new SimpleDateFormat("yyyy");
                                int anoFicha = Integer.parseInt(anoFormat.format(registro.getDataCriacao()));
                                System.out.println("DATA EMPRESA UPE: " + anoFicha);
                                boolean existe = Ficha11MaiorController.empresaExiste(registro.getEmpresa().getId(), registro.getTrimestre(), anoFicha);
                                System.out.println("EMPRESA QUE VAI SER CRIADA OU ATUALIZADA: " + registro.getEmpresa().getNome());
//                              Caso tenha, irá atualizar o registro que contem essa empresa                                  
                                if (existe){
                                    System.out.println("UPE: EMPRESA JA EXISTE");
                                    Ficha11MaiorDAO.updateBatchUpe(temp);
                                }
//                              Caso não tenha, irá criar um novo registro  
                                else{
                                    System.out.println("UPE: EMPRESA NÃO EXISTE");
                                    Ficha11MaiorDAO.createBatchUpe(temp);;
                                }    
                            }
                            else{
                                System.out.println("PARTE INDESEJADA IGNOARADA");
                            }                                    
                        }
                    }                                    
                break;                   
                default:
                    System.out.println("Tipo de requisição desconhecido");
            }
            
            if (tipoRequisicao.equals("createbatch") || tipoRequisicao.equals("validacaoBatch")) {
//                  Redirecionando caso a requisição seja feita pelo ajax  
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.setHeader("Content-Type", "application/json");
                    resp.getWriter().write("{\"redirectUrl\": \"/ProjetoCBE/views/ficha11.jsp\"}");
                    return;
            }
            if (tipoRequisicao.equals("post") || tipoRequisicao.equals("edit")) {
                if (ficha.isControlaEmpresa()) {
                    if (tipoRequisicao.equals("post")) {
                        resp.sendRedirect("/ProjetoCBE/forms/ficha11Empresa.jsp?id=" + idFicha);
                        return;
                    }
                    resp.sendRedirect("/ProjetoCBE/views/ficha11.jsp");
                    return;
                }
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

    public List<Ficha11Maior> getAllFichas() {
        return Ficha11MaiorDAO.getAllFichas();
    }

    public List<Ficha11Maior> getAllFichasByTrimestreAno(int trimestre, int ano) {
        return Ficha11MaiorDAO.getAllFichasByTrimestreAno(trimestre, ano);
    }

    public Ficha11Maior getFichaById(int id) {
        Optional<Ficha11Maior> optFicha11 = Ficha11MaiorDAO.getFichaById(id);
        if (optFicha11.isPresent()) {
            return optFicha11.get();
        }
        return null;
    }
    
    public static String testarValoracao(String valoracao){
        System.out.println("TESTANDO");
        String resultado;
        if (valoracao != null){
            resultado = valoracao;
        }
        else{
            resultado = "Não Informado";
        }
        return resultado;
    }
    
    
   public static double testarNum(String num) {
        if (num == null || num =="") { // Verifica se o valor é zero
            num = "-0.01"; // Atribui -0.01 se estiver vazio
        }
        double novo_num = NumeroUtils.stringToDouble(num);
        return novo_num; // Retorna o valor (original ou modificado)
    }
   
    public static boolean empresaExiste(int id, int tri, int anoFicha){
        boolean existe = Ficha11MaiorDAO.empresaExiste(id, tri, anoFicha);
        return existe;
    }
    
//    public static  Map<String, Double> pegarPTAX(){
//        String datinha = ("2024-08-01");
//        Map<String, Double> valores = Ptax.obterCotacaoDolar(datinha);
//        System.out.println("COMPRA: " + valores.get("comopra") + "; VENDA: " + valores.get("venda"));
//        return valores;
//    }
    
    public static List<Ficha11Maior> processarFichasCoger( List<Map<String, Object>> list, 
                            List<Ficha11Maior> fichas,
                            FuncionarioController funcionarioController, 
                            StatusController statusController, 
                            EmpresaController empresaController,
                            MoedaController moedaController, 
                            String chaveFuncionario) {
        String diretoria = "";
        //CRIANDO UMA CLASSE FINAL PARA PEGAR O VALOR DA DIRETORIA E DEPOIS ATRIBUIR A STRING DIRETORIA
        final class Nome {
            String valor;
        }
        Nome nomeDiretoria = new Nome(); 
//      Loop para pegar as informações da lista de dicionários  
        for (Map<String, Object> map : list) {
            Ficha11Maior tempFicha = new Ficha11Maior(); // Crie uma ficha temporária para ir adicionando as informações ficha a ficha;
//          Loop for para cada chave presente em cada item da lista  
            map.forEach((key, value) -> {
                if (value != null) {
                    switch (key) {
//                      Lógica geral de cada case:
//                      1. Pegar o valor e passar para um objeto  
//                      2. Converter para uma String e fazer os tratamentos necessários 
//                      3. Adicionar a ficha temporária que a cada loop é adicionado a variável(TIPO LISTA []) fichas
//                      Lógica geral para os valores numéricos (Patrimonio Liquido, ValorTotal, ResultadoLiquido, etc)  
//                      Exemplo Partimonio Liquido:
//                      1. Pegar o valor em um objeto e passa para uma String numero (nesse caso numeroPL)   
//                      2. Pega o numero e converte para double dependendo do formato que vier (Americano virgula e ponto 
//                              ou Europeu ponto e vitgula)
//                      3.Atribuir esse numero convertido a uma variavel double e adicionar a ficha temporária    
                        case "empresa":
                            Object obEmpresa = map.get("empresa");
                            String  empresaNome = obEmpresa.toString();
//                          Removendo parênteses e o que tiver dentro dele  
                            empresaNome = NumeroUtils.removerParenteses(empresaNome);
                            System.out.println("NOME EMPRESA: " + empresaNome);
//                          Pega o id empresa a partir nome em um Switch Case para pegar a empresa   
                            Empresa empresa = empresaController.getEmpresaByNome((String) (empresaNome));
                            System.out.println("Nome emopresa " + empresa.getNome());
                            tempFicha.setEmpresa((Empresa) empresa);                                                                               
                            break;
                        case "moeda":
                            Object obMoedaId = map.get("moeda");
                            String nomeMoeda = obMoedaId.toString();
//                          Aqui só verifica se a moeda vem no formato "Dóla -...", caso o nomeMoeda será Dolár Americano  
                            nomeMoeda = NumeroUtils.verificarString(nomeMoeda);
                            System.out.println("NOME MOEDA: " + nomeMoeda);
//                          Pega a moeda a partir do nome em uma query pro banco de dados  
                            Moeda moeda = moedaController.getMoedaByNome((String) (nomeMoeda));
                            System.out.println("MOEDA Nome: " + moeda.getNome());
                            tempFicha.setMoeda((Moeda) moeda);
                            System.out.println("MOEDA FICHA: " + tempFicha.getMoeda().getId());
                            break;
                        case "valoracao":
                            Object obValoracao = map.get("valoracao");
                            System.out.println("OB VALORACAO: " + obValoracao.toString());
                            String valoracao = "";
                            valoracao = Ficha11MaiorController.testarValoracao(obValoracao.toString());
                            System.out.println("VALORACAO FORA IF ELSE: " + valoracao);
                            tempFicha.setMetodoValoracao(valoracao);
                            break;
                        case "patrimonioLiquido":   
                           System.out.println("PATRIMONIO LIQUIDO");
                           Object obPatrimonioLiquido = map.get("patrimonioLiquido");
                           String numeroPL = obPatrimonioLiquido.toString(); 
                           numeroPL = NumeroUtils.removerNumParenteses(numeroPL);
                           double patrimonioLiquido = 0;
                           try {
                                patrimonioLiquido = NumeroUtils.formatAndConvertToFloat(numeroPL);
                           } catch (ParseException ex) {
                               Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                           }
                           System.out.println("PATRIMÔNIO LIQUIDO: " + patrimonioLiquido);
                           tempFicha.setPatrimonioTotal(patrimonioLiquido);
                           tempFicha.setValorEmpresa(patrimonioLiquido);
                            break;
                        case "porcentagemSocial":
                           System.out.println("PORCENTAGEM SOCIAL");
                           Object obPorcentagemSocial = map.get("porcentagemSocial");
//                         Convertendo o valor para double  
                           double porcentagemSocial = NumeroUtils.stringToDouble(obPorcentagemSocial.toString());
                           System.out.println("PORCENTAGEM SOCIAL: " + porcentagemSocial);
                           tempFicha.setPorcentoParticipacaoCapital(porcentagemSocial);
                            break;
                        case "porcentagemVoto": 
                           System.out.println("PORCENTAGEM VOTO");
                           Object obPorcentagemVoto = map.get("porcentagemVoto");
//                         Convertendo o valor para double
                           Double porcentagemVoto = NumeroUtils.stringToDouble(obPorcentagemVoto.toString());
                           System.out.println("PORCENTAGEM VOTO: " + porcentagemVoto);
                           tempFicha.setPorcentoPoderVoto(porcentagemVoto);
                            break;
                        case "ativoDataBase":
                            System.out.println("ATIVO DATA BASE");
                            Object obAtivoDataBase = map.get("ativoDataBase");
                            String numeroAB = obAtivoDataBase.toString();
                            numeroAB = NumeroUtils.removerNumParenteses(numeroAB);
                            System.out.println("NUMNERO AB: " + numeroAB);
                            double ativoDataBase = 0;
                            try {                          
                                ativoDataBase = NumeroUtils.formatAndConvertToFloat(numeroAB);
                            } catch (ParseException ex) {
                                Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("ATIVO DATA BASE: " + ativoDataBase);
                            tempFicha.setAtivoDatabase(ativoDataBase);
                            break;
                        case "passivoExigivel":
                            Object obPassivoExigivel = map.get("passivoExigivel");
                            String numeroPE = obPassivoExigivel.toString();                             
                            numeroPE = NumeroUtils.removerNumParenteses(numeroPE);
                            double passivoExigivel = 0;
                            try {
                                passivoExigivel = NumeroUtils.formatAndConvertToFloat(numeroPE);
                            } catch (ParseException ex) {
                                Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("PASSIVO EXIGÍVEL: " + passivoExigivel);
                            tempFicha.setPassivoExigivel(passivoExigivel);
                            break;
                        case "valorTotal":
                            Object obValorTotal = map.get("valorTotal");
                            String numeroVT = obValorTotal.toString();
                            numeroVT = NumeroUtils.removerNumParenteses(numeroVT);
                            double valorTotal = 0;
                            try {
                                valorTotal = NumeroUtils.formatAndConvertToFloat(numeroVT);
                            } catch (ParseException ex) {
                                Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("VALOR TOTAL: " + valorTotal);
                            tempFicha.setValorTotalLucroPrejuizo(valorTotal);
                            break;
                        case "resultadoLiquidoReavaliacoes":
                            Object obResultadoLiquidoReavalicoes = map.get("resultadoLiquidoReavaliacoes");
                            String numeroRLR = obResultadoLiquidoReavalicoes.toString();
                            numeroRLR = NumeroUtils.removerNumParenteses(numeroRLR);
                            double resultadoLiquidoReavalicoes = -0;
                            try {
                                resultadoLiquidoReavalicoes = NumeroUtils.formatAndConvertToFloat(numeroRLR);
                            } catch (ParseException ex) {
                                Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("RESULTADO REAVALIAÇÃO: " + resultadoLiquidoReavalicoes);
                            tempFicha.setResultadoLiquidoReavaliacoes(resultadoLiquidoReavalicoes); 
                            break;
                        case "resultadoLiquidoCambial":
                            Object obResultadoCambial = map.get("resultadoLiquidoCambial");
                            String numeroRC = obResultadoCambial.toString();
                            System.out.println("NUMERO COM PARENTESES" + numeroRC);
                            numeroRC = NumeroUtils.removerNumParenteses(numeroRC);
                            System.out.println("NUMERO SEM PARENTESES" + numeroRC);
                            double ResultadoCambial = 0;
                            try {
                                ResultadoCambial = NumeroUtils.formatAndConvertToFloat(numeroRC);
                            } catch (ParseException ex) {
                                Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("RESULTADO CAMBIAL: " + ResultadoCambial);
                            tempFicha.setResultadoLiquidoVariacaoCambial(ResultadoCambial); 
                            break;
                        case "lucroDistribuido":
                            Object obLucroDisttribuido = map.get("lucroDistribuido");
                            String numeroLD = obLucroDisttribuido.toString();
                            System.out.println("NUMERO COM PARENTESES" + numeroLD);
                            numeroLD = NumeroUtils.removerNumParenteses(numeroLD);
//                          Adiocionei mais uma etapa de vertificação para garantir que o valor padrão seja 0  
                            if ("-".equals(numeroLD)){
                                numeroLD = "0";
                            }
                            System.out.println("NUMERO SEM PARENTESES" + numeroLD);
                            numeroLD.trim();
                            double LucroDistribuido = 0;
//                          Adiocionei mais uma etapa de vertificação para garantir que o valor padrão seja 0                             
                            if ("0".equals(numeroLD)){
                                LucroDistribuido = 0;
                            }
                            else{
                                try {
                                LucroDistribuido = NumeroUtils.formatAndConvertToFloat(numeroLD);
                                } catch (ParseException ex) {
                                    Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            System.out.println("RESULTADO CAMBIAL: " + LucroDistribuido);
                            tempFicha.setLucroDistribuido(LucroDistribuido); 
                            break;

                        case "controla":
                            boolean controla = false;
                            Object obControla = map.get("controla");
                            String stControla = obControla.toString().trim().toUpperCase();
                            System.out.println("CONTROLA OU NÃO: " + stControla);
//                          Caso o valor de "controla" seja sim atrbui true, caso não atribui false
                            if ("SIM".equals(stControla)){
                                controla = true;                                           
                                System.out.println("CONTROLA FINAL: " + controla);
                                tempFicha.setControlaEmpresa(controla);
                                break;
                            }
                            System.out.println("NÃO CONTROLA");
                            tempFicha.setControlaEmpresa(controla);                                          
                            break;
//                      ESSE CASO É SÓ PARA SABER SE OS VALORES ESTÃO SENDO CAPTURADOS E PASSADOS CORRETAMENTE (TESTE LINHA 480)      
                        case "diretoria":
                            Object obDiretoria = map.get("diretoria");
                            nomeDiretoria.valor = obDiretoria.toString();
                            break;
                    }
                }
            });
//          TESTANDO AQUI  
            diretoria = nomeDiretoria.valor;
            System.out.println("DIRETORIA: " + diretoria);
            tempFicha.setTrimestre(DataUtils.validaTrimestre());
            tempFicha.setDataCriacao(new Date());
            tempFicha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
            tempFicha.setStatus(statusController.getStatusById(1)); // 1 - não certificado / 2 - certificado
            System.out.println("FICHAS " + tempFicha.isControlaEmpresa());
            fichas.add(tempFicha);          
        }
        return fichas;
    }
    
    public static List<Ficha11Maior> processarFichasUpe( List<Map<String, Object>> list, 
                            List<Ficha11Maior> fichas,
                            FuncionarioController funcionarioController, 
                            StatusController statusController, 
                            EmpresaController empresaController,
                            MoedaController moedaController, 
                            String chaveFuncionario) {
        
        String diretoria = "";
        //CRIANDO UMA CLASSE FINAL PARA PEGAR O VALOR DA DIRETORIA E DEPOIS ATRIBUIR A STRING DIRETORIA
        final class Nome {
            String valor;
        }
        Nome nomeDiretoria = new Nome();
        
        for (Map<String, Object> map : list) {
        Ficha11Maior tempFicha = new Ficha11Maior(); // Crie uma nova instância de Ficha1;
            map.forEach((key, value) -> {
                if (value != null) {            
                    switch (key) {
                        case "empresa":
//                          Removendo parênteses e o que tiver dentro dele
                            Object obEmpresa = map.get("empresa");
                            String  empresaNome = obEmpresa.toString();
                            empresaNome = NumeroUtils.removerParenteses(empresaNome);
                            System.out.println("NOME EMPRESA: " + empresaNome);
                            Empresa empresa = empresaController.getEmpresaByNome((String) (empresaNome));
                            System.out.println("Nome emopresa " + empresa.getNome());
                            tempFicha.setEmpresa((Empresa) empresa);                                                                               
                            break;
                        case "cotacao":
                            boolean blCotacao;
                            Object obCotacao = map.get("cotacao");
                            String cotacao = obCotacao.toString();
                            System.out.println("STRING: " + cotacao);
                            if (cotacao.equals("Sim")){
                                blCotacao = true;
                            }
                            else{
                                blCotacao = false;
                            }
                            System.out.println("COTACAO TESTE: " + blCotacao);
                            tempFicha.setPossuiCotacaoEmBolsa((boolean) blCotacao); 
                            break;
                        case "moeda":
                            Object obMoedaId = map.get("moeda");
                            String nomeMoeda = obMoedaId.toString();
                            nomeMoeda = NumeroUtils.verificarString(nomeMoeda);
                            System.out.println("NOME MOEDA: " + nomeMoeda);
                            Moeda moeda = moedaController.getMoedaBySigla((String) (nomeMoeda));
                            System.out.println("MOEDA Nome: " + moeda.getNome());
                            tempFicha.setMoeda((Moeda) moeda);
                            System.out.println("MOEDA FICHA: " + tempFicha.getMoeda().getId());
                            break;
                        case "valoracao":
                            Object obValoracao = map.get("valoracao");
                            System.out.println("OB VALORACAO: " + obValoracao.toString());
                            String valoracao = "";
                            valoracao = Ficha11MaiorController.testarValoracao(obValoracao.toString());
                            System.out.println("VALORACAO FORA IF ELSE: " + valoracao);
                            tempFicha.setMetodoValoracao(valoracao);
                            break;
                        case "porcentagemSocial":
                           System.out.println("PORCENTAGEM SOCIAL");
                           Object obPorcentagemSocial = map.get("porcentagemSocial");
                           Double porcentagemSocial = NumeroUtils.stringToDouble(obPorcentagemSocial.toString());
                           System.out.println("PORCENTAGEM SOCIAL: " + porcentagemSocial);
                           tempFicha.setPorcentoParticipacaoCapital(porcentagemSocial);
                            break;
                        case "porcentagemVoto": 
                           System.out.println("PORCENTAGEM VOTO");
                           Object obPorcentagemVoto = map.get("porcentagemVoto");
                           Double porcentagemVoto = NumeroUtils.stringToDouble(obPorcentagemVoto.toString());
                           System.out.println("PORCENTAGEM VOTO: " + porcentagemVoto);
                           tempFicha.setPorcentoPoderVoto(porcentagemVoto);
                            break;
                        case "resultadoLiquidoItens":
                            Object obResultadoLiquidoItens = map.get("resultadoLiquidoItens");
                            String numeroRLI = obResultadoLiquidoItens.toString();
                            numeroRLI = NumeroUtils.removerNumParenteses(numeroRLI);
                            double resultadoLiquidoItens = 0;
                            try {
                                resultadoLiquidoItens = NumeroUtils.formatAndConvertToFloat(numeroRLI);
                            } catch (ParseException ex) {
                                Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("RESULTADO LIQUIDO: " + resultadoLiquidoItens);
                            tempFicha.setResultadoLiquidoItensNaoRecorrentes(resultadoLiquidoItens);                                       
                            break;
                        case "lucroDistribuido":
                            Object obLucroDistribuido = map.get("lucroDistribuido");
                            String numeroLD = obLucroDistribuido.toString();
                            numeroLD = NumeroUtils.removerNumParenteses(numeroLD);
                            double lucroDistribuido = 0;
                            try {
                                lucroDistribuido = NumeroUtils.formatAndConvertToFloat(numeroLD);
                            } catch (ParseException ex) {
                                Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("LUCRO DISTRIBUIDO: " + lucroDistribuido);
                            tempFicha.setLucroDistribuido(lucroDistribuido); 
                            break;
                        case "controla":
                            boolean controla = false;
                            Object obControla = map.get("controla");
                            String stControla = obControla.toString().trim().toUpperCase();
                            System.out.println("CONTROLA OU NÃO: " + stControla);
                            if ("SIM".equals(stControla)){
                                controla = true;                                           
                                System.out.println("CONTROLA FINAL: " + controla);
                                tempFicha.setControlaEmpresa(controla);
                                break;
                            }
                            System.out.println("NÃO CONTROLA");
                            tempFicha.setControlaEmpresa(controla);                                           
                            break;
                        case "diretoria":
                            Object obDiretoria = map.get("diretoria");
                            nomeDiretoria.valor = obDiretoria.toString();
                            break;
                    }
                }                                
            });
        diretoria = nomeDiretoria.valor;
        System.out.println("DIRETORIA: " + diretoria);
        tempFicha.setTrimestre(DataUtils.validaTrimestre());
        tempFicha.setDataCriacao(new Date());
        tempFicha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
        tempFicha.setStatus(statusController.getStatusById(1)); // 1 - não certificado / 2 - certificado
        System.out.println("FICHAS " + tempFicha.isControlaEmpresa());
        fichas.add(tempFicha);
        }
    return fichas;
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
    
private void validarLote(JsonObject json, HttpServletResponse resp) throws IOException {
        boolean precisa = false; // Lógica de validação pode ser expandida aqui
        resp.setContentType("application/json");
        resp.getWriter().write("{\"precisaJustificar\": " + precisa + "}");
    }

private void salvarLote(JsonObject json, String chaveFuncionario) throws Exception {
        JsonArray itens = json.getAsJsonArray("itens");
        String justificativa = "";
        if (json.has("justificativa") && !json.get("justificativa").isJsonNull()) {
            justificativa = json.get("justificativa").getAsString();
        }

        boolean isUpe = false;
        if (json.has("flagUpe")) {
            isUpe = json.get("flagUpe").getAsBoolean();
        }

        List<Ficha11Maior> lista = new ArrayList<>();

        for (JsonElement el : itens) {
            JsonObject item = el.getAsJsonObject();
            Ficha11Maior f = new Ficha11Maior();

            // IDs e FKs
            Empresa emp = new Empresa();
            emp.setId(item.get("id_empresa").getAsInt());
            f.setEmpresa(emp);

            Moeda m = new Moeda();
            m.setId(item.get("id_moeda").getAsInt());
            f.setMoeda(m);

            // Mapeamento com as chaves exatas do banco/JSON
            f.setPossuiCotacaoEmBolsa(item.get("possui_cotacao_em_bolsa").getAsBoolean());
            f.setMetodoValoracao(item.get("metodo_valoracao").getAsString());
            f.setControlaEmpresa(item.get("controla_empresas").getAsBoolean());
            
            f.setValorEmpresa(item.get("valor_empresa").getAsDouble());
            f.setPatrimonioTotal(item.get("patrimonio_total").getAsDouble());
            f.setPorcentoParticipacaoCapital(item.get("participacao_capital_social").getAsDouble());
            f.setPorcentoPoderVoto(item.get("porcento_poder_voto").getAsDouble());
            f.setAtivoDatabase(item.get("ativo_database").getAsDouble());
            f.setPassivoExigivel(item.get("passivo_exigivel").getAsDouble());
            f.setValorTotalLucroPrejuizo(item.get("valor_total_lucro_preju_liquido").getAsDouble());
            f.setResultadoLiquidoItensNaoRecorrentes(item.get("result_liq_itens_nao_recorrentes").getAsDouble());
            f.setResultadoLiquidoReavaliacoes(item.get("result_liq_reavaliacoes").getAsDouble());
            f.setResultadoLiquidoVariacaoCambial(item.get("result_liq_variacao_cambial").getAsDouble());
            f.setLucroDistribuido(item.get("lucro_distribuido").getAsDouble());

            // Lógica UPE -> Diretoria
            if (isUpe) { f.setDiretoria("UPE"); } else { f.setDiretoria(null); }

            // Campos de Controle
            f.setTrimestre(DataUtils.validaTrimestre());
            f.setDataCriacao(new java.util.Date());
            Funcionario func = new Funcionario();
            func.setChave(chaveFuncionario);
            f.setFuncionario(func);
            Status s = new Status(); s.setId(1);
            f.setStatus(s);
            f.setJustificativaGestor(justificativa);

            lista.add(f);
        }

        if (!lista.isEmpty()) {
            Ficha11MaiorDAO.createBatch(lista);
        }
    }
}
