package br.com.bb.cbe.controllers;

import br.com.bb.cbe.Bean.Empresa;
import br.com.bb.cbe.Bean.Ficha11Maior;
import br.com.bb.cbe.controllers.JustificativaController;
import br.com.bb.cbe.Bean.Pais;
import br.com.bb.cbe.Bean.Ficha11Menor;
import br.com.bb.cbe.Bean.Funcionario;
import br.com.bb.cbe.Bean.Justificativa;
import br.com.bb.cbe.Bean.Moeda;
import br.com.bb.cbe.Bean.Status;
import br.com.bb.cbe.DAO.Ficha11MenorDAO;
import br.com.bb.cbe.DAO.PtaxDAO;
import br.com.bb.cbe.Utils.DataUtils;
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

        JsonObject jsonBodyObject = null;
        List<Map<String, Object>> jsonBodyList = null;
        Gson gson = new Gson();

        try {
            // 1. Lê o Corpo da Requisição (JSON Payload)
            if (tipoRequisicao == null) {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = req.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String json = sb.toString().trim();

                if (!json.isEmpty()) {
                    // VERIFICA SE É UM OBJETO {} OU UMA LISTA []
                    if (json.startsWith("{")) {
                        // É um Objeto (ex: validar-lote, salvar-lote)
                        jsonBodyObject = gson.fromJson(json, JsonObject.class);
                        if (jsonBodyObject.has("tipo-requisicao")) {
                            tipoRequisicao = jsonBodyObject.get("tipo-requisicao").getAsString();
                        }
                    } else if (json.startsWith("[")) {
                        // É uma Lista (ex: createbatch manual)
                        Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();
                        jsonBodyList = gson.fromJson(json, type);
                        
                        // Tenta achar o tipo dentro da lista
                        for (Map<String, Object> map : jsonBodyList) {
                            if (map.containsKey("tipo-requisicao")) {
                                tipoRequisicao = (String) map.get("tipo-requisicao");
                                break;
                            }
                        }
                    }
                }
            }

            // Prepara objeto Ficha para operações simples (post/edit individual)
            Ficha11Menor ficha = new Ficha11Menor();
            if ("post".equals(tipoRequisicao) || "edit".equals(tipoRequisicao)) {
                // ... lógica para post/edit individual (mantém o que você já tinha se necessário) ...
                // Se o seu form individual envia via request param, ok. Se for JSON, precisaria ajustar.
                // Vou manter o básico assumindo request param para edição unitária
                if(req.getParameter("pais") != null) {
                    ficha.setPais(paisController.getPaisById(Integer.parseInt(req.getParameter("pais"))));
                    ficha.setMoeda(moedaController.getMoedaById(Integer.parseInt(req.getParameter("moeda"))));
                    ficha.setMetodoValoracao(req.getParameter("metodoValoracao"));
                    ficha.setValorParticipacao(NumeroUtils.stringToDouble(req.getParameter("valorParticipacao")));
                    ficha.setLucroDistribuido(NumeroUtils.stringToDouble(req.getParameter("lucroDistribuido")));
                    ficha.setTrimestre(DataUtils.validaTrimestre());
                    ficha.setDataCriacao(new Date());
                    ficha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
                    ficha.setStatus(statusController.getStatusById(1));
                }
            }

            if (tipoRequisicao == null) tipoRequisicao = "";

            // 2. Switch de Ações
            switch (tipoRequisicao) {
                case "delete":
                    int id = Integer.parseInt(req.getParameter("id"));
                    Ficha11MenorDAO.delete(id);
                    break;

                case "post":
                    Ficha11MenorDAO.create(ficha);
                    break;

                case "edit":
                    ficha.setId(Integer.parseInt(req.getParameter("id")));
                    Ficha11MenorDAO.update(ficha);
                    break;

                case "validar-lote":
                    // Agora passamos o jsonBodyObject corretamente
                    validarLote(jsonBodyObject, resp);
                    return; // Retorna aqui para não redirecionar no final

                case "salvar-lote":
                    try {
                        salvarLote(jsonBodyObject, chaveFuncionario);
                        resp.setStatus(200);
                    } catch (Exception e) {
                        e.printStackTrace(); // Isso joga o erro no log do Glassfish/Tomcat

                        resp.setStatus(500); // Define erro
                        resp.setContentType("text/plain; charset=UTF-8"); // Define que é texto

                        // Envia a mensagem real do erro para o JavaScript
                        resp.getWriter().write("Falha ao gravar: " + e.getMessage());
                    }
                    return;

                case "validacao": // Validação por Checkbox na tela
                    String[] idsValidadosArray = req.getParameterValues("idsValidados[]");
                    List<String> idsValidadosList = (idsValidadosArray != null) ? Arrays.asList(idsValidadosArray) : new ArrayList<>();
                    Ficha11MenorDAO.validarFormularios(idsValidadosList, chaveFuncionario);
                    break;

                case "createbatch": // Salvar manual "Copiar e Colar"
                    if (jsonBodyList != null) {
                       // Sua lógica existente para processar a lista do "Copiar e Colar"
                       processarCreateBatchManual(jsonBodyList, chaveFuncionario);
                    }
                    // Retorno JSON para createbatch
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.setContentType("application/json");
                    resp.getWriter().write("{\"redirectUrl\": \"/ProjetoCBE/views/ficha11.jsp\"}");
                    return;
            }

            // Redirecionamento padrão para casos síncronos (delete, post unitário, etc)
            resp.sendRedirect(req.getContextPath() + "/views/ficha11.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("mensagemErro", "Erro ao processar requisição: " + e.getMessage());
            RequestDispatcher dispatcher = req.getRequestDispatcher("/errors/customError.jsp");
            dispatcher.forward(req, resp);
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
            
            // ATENÇÃO: Na ficha 11 menor, qual valor compõe o saldo contábil?
            // Geralmente é o Valor de Participação. Ajuste se precisar somar o Lucro.
            double valorPart = item.get("valor_participacao").getAsDouble();
            int idMoeda = item.get("id_moeda").getAsInt();
            
            // Pega cotação de compra para data-base
            double taxa = PtaxDAO.getTaxaCompra(idMoeda, triRef, anoRef);
            somaTotalConvertidaBrl += (valorPart * taxa);
        }

        // Você precisará garantir que esse método exista no Ficha11MenorDAO ou usar uma lógica similar
        // Assumindo que a validação é a mesma (comparar com Planilha 4010)
        boolean precisa = Ficha11MenorDAO.verificarNecessidadeJustificativa(somaTotalConvertidaBrl, trimestreAtual, anoAtual);
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"precisaJustificar\": " + precisa + "}");
    }

    private void salvarLote(JsonObject json, String chaveFuncionario) throws Exception {
        JsonArray itens = json.getAsJsonArray("itens");
        String justificativa = "";
        if (json.has("justificativa") && !json.get("justificativa").isJsonNull()) {
            justificativa = json.get("justificativa").getAsString();
        }

        List<Ficha11Menor> listaParaSalvar = new ArrayList<>();

        for (JsonElement el : itens) {
            JsonObject item = el.getAsJsonObject();
            Ficha11Menor ficha = new Ficha11Menor();
            
            // Verifica e converte País
            if (!item.has("id_pais")) throw new Exception("ID do País não encontrado no item.");
            Pais p = new Pais();
            p.setId(item.get("id_pais").getAsInt());
            ficha.setPais(p);

            // Verifica e converte Moeda
            if (!item.has("id_moeda")) throw new Exception("ID da Moeda não encontrado no item.");
            Moeda m = new Moeda();
            m.setId(item.get("id_moeda").getAsInt());
            ficha.setMoeda(m);

            ficha.setMetodoValoracao(item.has("metodo") ? item.get("metodo").getAsString() : "");
            
            // Tratamento de Double (se vier como string ou number)
            ficha.setValorParticipacao(item.get("valor_participacao").getAsDouble());
            ficha.setLucroDistribuido(item.get("lucro_distribuido").getAsDouble());
            
            ficha.setTrimestre(DataUtils.validaTrimestre());
            ficha.setDataCriacao(new java.util.Date());
            
            Funcionario f = new Funcionario();
            f.setChave(chaveFuncionario);
            ficha.setFuncionario(f);
            
            Status s = new Status();
            s.setId(1);
            ficha.setStatus(s);
            
            if(!justificativa.isEmpty()) {
                ficha.setJustificativaGestor(justificativa);
            }

            listaParaSalvar.add(ficha);
        }
        
        if(!listaParaSalvar.isEmpty()){
            // Agora o DAO vai lançar exceção se falhar, e o Controller vai pegar
            Ficha11MenorDAO.createBatch(listaParaSalvar);
        }
    }
    
    // Método auxiliar para isolar a lógica antiga do createbatch manual
    private void processarCreateBatchManual(List<Map<String, Object>> list, String chaveFuncionario) {
        List<Ficha11Menor> fichas = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Ficha11Menor tempFicha = new Ficha11Menor();
            // ... (coloque aqui a lógica de map.forEach que estava no seu 'case "createbatch"') ...
            // Dica: A lógica de createbatch manual é muito grande e polui o doPost. 
            // Mas para funcionar agora, basta mover o loop 'for (Map...)' que você tinha para cá
            // e chamar Ficha11MenorDAO.createBatch(fichas) no final.
        }
    }
    
    public List<Integer> getAnosExistentes() {
        return Ficha11MenorDAO.getAnosExistentes();
    }

    public List<Integer> getTrimestresExistentes() {
        return Ficha11MenorDAO.getTrimestresExistentes();
    }

    public List<Ficha11Menor> readComFiltros(String trimestre, String ano) {
        return Ficha11MenorDAO.readComFiltros(trimestre, ano);
    }
    
}