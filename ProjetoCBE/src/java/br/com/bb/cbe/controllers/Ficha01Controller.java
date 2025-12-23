package br.com.bb.cbe.controllers;

import java.util.Date;
import br.com.bb.cbe.DAO.Ficha01DAO;
import br.com.bb.cbe.Bean.Ficha01;
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

@WebServlet("/ficha01")
public class Ficha01Controller extends HttpServlet {

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

        // Variáveis auxiliares para processamento de JSON
        JsonObject jsonBodyObject = null;
        List<Map<String, Object>> jsonBodyList = null;
        
        // Classe auxiliar para capturar justificativa do fluxo antigo
        final class Teste { String valor; }
        Teste justificativaTeste = new Teste();

        try {
            // Lógica Unificada de Leitura do Body (Suporta Objeto Novo ou Lista Antiga)
            if (tipoRequisicao == null) {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = req.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String json = sb.toString().trim();

                if (!json.isEmpty()) {
                    // Verifica se é um Objeto JSON (Novo fluxo de Lote)
                    if (json.startsWith("{")) {
                        jsonBodyObject = JsonParser.parseString(json).getAsJsonObject();
                        if (jsonBodyObject.has("tipo-requisicao")) {
                            tipoRequisicao = jsonBodyObject.get("tipo-requisicao").getAsString();
                        }
                    } 
                    // Verifica se é uma Lista JSON (Fluxo antigo de validação)
                    else if (json.startsWith("[")) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();
                        jsonBodyList = gson.fromJson(json, type);

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

            // Objeto ficha auxiliar para operações unitárias (post/edit legados)
            Ficha01 ficha = new Ficha01();
            if ("post".equals(tipoRequisicao) || "edit".equals(tipoRequisicao)) {
                int moedaId = Integer.parseInt(req.getParameter("moeda"));
                int paisId = Integer.parseInt(req.getParameter("pais"));
                ficha.setMoeda(moedaController.getMoedaById(moedaId));
                ficha.setPais(paisController.getPaisById(paisId));
                ficha.setValorDatabase(NumeroUtils.stringToDouble(req.getParameter("valor")));
                ficha.setDividendos(NumeroUtils.stringToDouble(req.getParameter("dividendos")));
                ficha.setTrimestre(DataUtils.validaTrimestre());
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
                ficha.setStatus(statusController.getStatusById(1));

                String just = req.getParameter("justificativa_gestor");
                if (just != null && !just.isEmpty()) {
                    ficha.setJustificativaGestor(just);
                } else {
                    ficha.setJustificativaGestor("");
                }
            }

            // Switch Principal
            if (tipoRequisicao == null) tipoRequisicao = ""; // Evita NullPointer no switch

            switch (tipoRequisicao) {
                case "recusar":
                    int idRecusar = Integer.parseInt(req.getParameter("id"));
                    // Chama o DAO para mudar o status para 1
                    Ficha01DAO.alterarStatus(idRecusar, 1);
                    resp.setStatus(200);
                    return;
                case "delete":
                    int id = Integer.parseInt(req.getParameter("id"));
                    Ficha01DAO.delete(id);
                    break;
                case "post":
                    ficha.setDataCriacao(new Date());
                    Ficha01DAO.create(ficha);
                    break;
                case "edit":
                    ficha.setDataCriacao(new Date());
                    ficha.setId(Integer.parseInt(req.getParameter("id")));
                    Ficha01DAO.update(ficha);
                    break;
                case "validacao":
                    String[] idsValidadosArray = req.getParameterValues("idsValidados[]");
                    List<String> idsValidadosList = new ArrayList<>();
                    if (idsValidadosArray != null) {
                        idsValidadosList = Arrays.asList(idsValidadosArray);
                    }
                    Ficha01DAO.validarFormularios(idsValidadosList, chaveFuncionario);
                    break;
                case "validacaoBatch":
                    if (jsonBodyList != null) {
                        List<String> arrayIdsValidados = processarValidacao(jsonBodyList);
                        Ficha01DAO.validarFormularios(arrayIdsValidados, chaveFuncionario);
                        if (justificativaTeste.valor != null && !"NTD".equals(justificativaTeste.valor)) {
                            Justificativa justificativa = processarJustificativa(jsonBodyList, chaveFuncionario);
                            JustificativaController.createBatchJustController(justificativa);
                        }
                    }
                    break;

                //  VALIDAÇÃO DE LOTE (SOMA + CONVERSÃO)
                case "validar-lote":
                    validarLote(jsonBodyObject, resp);
                    return; // Retorna aqui pois é a resposta JSON

                // SALVAR O LOTE NO BANCO
                case "salvar-lote":
                    salvarLote(jsonBodyObject, chaveFuncionario);
                    resp.setStatus(200);
                    return;
            }

            // Redirecionamentos padrão
            if (tipoRequisicao.equals("createbatch") || tipoRequisicao.equals("validacaoBatch")) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setHeader("Content-Type", "application/json");
                resp.getWriter().write("{\"redirectUrl\": \"/ProjetoCBE/views/ficha01.jsp\"}");
                return;
            }

            resp.sendRedirect("views/ficha01.jsp");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("mensagemErro", "O valor foi inserido em um formato inválido.");
            req.setAttribute("linkPaginaAnterior", "/ProjetoCBE/forms/ficha01.jsp");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/errors/customError.jsp");
            dispatcher.forward(req, resp);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void validarLote(JsonObject json, HttpServletResponse resp) throws IOException {
        JsonArray itens = json.getAsJsonArray("itens");
        double somaTotalConvertidaBrl = 0.0;

        // 1. Obtém dados de Período
        int trimestreAtual = DataUtils.validaTrimestre();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int anoAtual = cal.get(java.util.Calendar.YEAR);

        // 2. Calcula Referência (Trimestre Anterior)
        int triRef = trimestreAtual - 1;
        int anoRef = anoAtual;
        if (triRef == 0) {
            triRef = 4;
            anoRef = anoAtual - 1;
        }

        System.out.println(">>> VALIDANDO LOTE (" + itens.size() + " itens) <<<");
        
        // 3. Itera sobre os itens para converter e somar
        for (JsonElement el : itens) {
            JsonObject item = el.getAsJsonObject();
            
            // Pega o valor original (ex: 1000.00 USD)
            String valorStr = item.get("valor").getAsString();
            double valorOriginal = NumeroUtils.stringToDouble(valorStr);
            int idMoeda = Integer.parseInt(item.get("id_moeda").getAsString());

            // Busca a Taxa PTAX do período de referência
            double taxa = PtaxDAO.getTaxaCompra(idMoeda, triRef, anoRef);
            
            // Converte e Soma
            double valorConvertido = valorOriginal * taxa;
            somaTotalConvertidaBrl += valorConvertido;
            
            System.out.println("   Item: " + valorOriginal + " (Moeda " + idMoeda + ") * Taxa " + taxa + " = " + valorConvertido);
        }

        System.out.println(">>> TOTAL LOTE (BRL): " + somaTotalConvertidaBrl);

        // 4. Valida o Montante Total contra o Banco
        boolean precisa = Ficha01DAO.verificarNecessidadeJustificativa(somaTotalConvertidaBrl, trimestreAtual, anoAtual);

        // 5. Retorna JSON
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

        System.out.println(">>> SALVANDO LOTE NO BANCO. Justificativa: " + justificativa);

        // Loop para salvar cada item individualmente no banco
        for (JsonElement el : itens) {
            JsonObject item = el.getAsJsonObject();
            Ficha01 ficha = new Ficha01();

            // Dados do Item
            String valorStr = item.get("valor").getAsString();
            String divStr = item.get("dividendos").getAsString();
            int moedaId = Integer.parseInt(item.get("id_moeda").getAsString());
            int paisId = Integer.parseInt(item.get("id_pais").getAsString());

            ficha.setValorDatabase(NumeroUtils.stringToDouble(valorStr));
            ficha.setDividendos(NumeroUtils.stringToDouble(divStr));
            
            // Objetos Relacionados
            ficha.setMoeda(moedaController.getMoedaById(moedaId));
            ficha.setPais(paisController.getPaisById(paisId));
            
            // Dados Padrão da Ficha
            ficha.setTrimestre(DataUtils.validaTrimestre());
            ficha.setDataCriacao(new java.util.Date());
            ficha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
            ficha.setStatus(statusController.getStatusById(1)); // Status "Pendente/Salvo"
            
            // A justificativa é aplicada a todos os itens do lote (pois o lote todo gerou a diferença)
            ficha.setJustificativaGestor(justificativa);

            // Persiste
            Ficha01DAO.create(ficha);
        }
    }
    
    public List<Ficha01> getAllFichas() {
        return Ficha01DAO.getAllFichas();
    }

    public List<Ficha01> getAllFichasByTrimestreAno(int trimestre, int ano) {
        int trimestreParaDAO = trimestre;
        int anoParaDAO = ano;
        switch (trimestre) {
            case 1: trimestreParaDAO = 4; anoParaDAO = ano - 1; break;
            case 2: trimestreParaDAO = 1; break;
            case 3: trimestreParaDAO = 2; break;
            case 4: trimestreParaDAO = 3; break;
            default: trimestreParaDAO = trimestre; break;
        }
        return Ficha01DAO.getAllFichasByTrimestreAno(trimestreParaDAO, anoParaDAO);
    }

    public List<Ficha01> getAllFichasByTrimestre(int trimestre) {
        int trimestreParaDAO = trimestre;
        switch (trimestre) {
            case 1: trimestreParaDAO = 4; break;
            case 2: trimestreParaDAO = 1; break;
            case 3: trimestreParaDAO = 2; break;
            case 4: trimestreParaDAO = 3; break;
            default: trimestreParaDAO = trimestre; break;
        }
        return Ficha01DAO.getAllFichasByTrimestre(trimestreParaDAO);
    }

    public List<Ficha01> getAllFichasByAno(int ano) {
        return Ficha01DAO.getAllFichasByAno(ano);
    }

    public List<Integer> getAnosExistentes() {
        return Ficha01DAO.getAnosExistentes();
    }

    public List<Integer> getTrimestresExistentes() {
        return Ficha01DAO.getTrimestresExistentes();
    }

    public Ficha01 getFichaById(int id) {
        Optional<Ficha01> optFicha01 = Ficha01DAO.getFichaById(id);
        if (optFicha01.isPresent()) {
            return optFicha01.get();
        }
        return null;
    }

    public static List<String> processarValidacao(List<Map<String, Object>> list) {
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

    public static Justificativa processarJustificativa(List<Map<String, Object>> list, String chaveFuncionario) {
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
                        Logger.getLogger(Ficha01Controller.class.getName()).log(Level.SEVERE, null, ex);
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
        List<Ficha01> fichas = Ficha01DAO.getAllFichas();
        return gson.toJson(fichas);
    }

    public List<Ficha01> getFichasPorPeriodo(int ano, int trimestre) {
        return Ficha01DAO.getAllFichasByTrimestreAno(trimestre, ano);
    }
}