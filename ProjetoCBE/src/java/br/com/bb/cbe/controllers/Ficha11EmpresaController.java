package br.com.bb.cbe.controllers;

import java.util.Date;
import br.com.bb.cbe.DAO.Ficha11ControleDAO;
import br.com.bb.cbe.Bean.Ficha11Controle;
import br.com.bb.cbe.Bean.Ficha11Menor;
import br.com.bb.cbe.Bean.Justificativa;
import br.com.bb.cbe.Bean.Moeda;
import br.com.bb.cbe.Bean.Pais;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ficha11Empresa")

public class Ficha11EmpresaController extends HttpServlet {

    private MoedaController moedaController;
    private PaisController paisController;
    private FuncionarioController funcionarioController;
    private Ficha11MaiorController ficha11MaiorController;

    @Override
    public void init() {
        this.moedaController = new MoedaController();
        this.paisController = new PaisController();
        this.funcionarioController = new FuncionarioController();
        this.ficha11MaiorController = new Ficha11MaiorController();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");
        String tipoRequisicao = req.getParameter("tipo-requisicao");
        HttpSession session = req.getSession();
        String chaveFuncionario = (String) session.getAttribute("chave");
        int idControladora = 0;
        int controladoraidCola = 0;
        try {
            final class Teste {
                String valor;
            }
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
                System.out.println("LISTA: " + list);              
                // Primeiro loop para encontrar "tipoRequisicao"
                for (Map<String, Object> map : list) {
                    if (map.containsKey("tipo-requisicao")) {
                        tipoRequisicao = (String) map.get("tipo-requisicao");
                            break;
                    }
                } 
                for (Map<String, Object> map: list){
                    if (map.containsKey("id-controladora")){
                        String obId = (String) map.get("id-controladora");
                        controladoraidCola =  Integer.parseInt(obId);
                        System.out.println("ID CONTROLADORA: " + controladoraidCola);
                    }
                }
            }
            Ficha11Controle ficha11 = new Ficha11Controle(); // Crie uma nova instância de Ficha1
            Ficha11Controle ficha = new Ficha11Controle();
            String valor = req.getParameter("id");
            
            if (null != valor){
                idControladora = Integer.parseInt(req.getParameter("id"));
            }
            else{
                idControladora = controladoraidCola;
            }
            if (tipoRequisicao.equals("post") || tipoRequisicao.equals("edit")) {
                int moedaId = Integer.parseInt(req.getParameter("moeda"));
                int paisId = Integer.parseInt(req.getParameter("pais"));

                ficha.setMoeda(moedaController.getMoedaById(moedaId));
                ficha.setPais(paisController.getPaisById(paisId));
                ficha.setNome(req.getParameter("nome"));
                ficha.setAtividadeEcn(String.format(req.getParameter("atividade")));
                ficha.setParticipacaoCapital(NumeroUtils.stringToDouble(req.getParameter("participacao")));
                ficha.setPatrimonioLiquido(NumeroUtils.stringToDouble(req.getParameter("patrimonio")));
                ficha.setValorMercado(NumeroUtils.stringToDouble(req.getParameter("valor")));
                ficha.setFinalCadeia(Boolean.parseBoolean(req.getParameter("final")));
                ficha.setDataCriacao(new Date());
                ficha.setFicha11Controladora(ficha11MaiorController.getFichaById(idControladora));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
            }
            switch (tipoRequisicao) {
                case "delete":
                    int id = Integer.parseInt(req.getParameter("id"));
                    idControladora = Integer.parseInt(req.getParameter("idFichaMaior"));
                    Ficha11ControleDAO.delete(id);
                    break;
                case "post":
                    ficha.setDataCriacao(new Date());
                    Ficha11ControleDAO.create(ficha);
                    break;
                case "edit":
                    ficha.setDataCriacao(new Date());
                    ficha.setId(Integer.parseInt(req.getParameter("id")));
                    idControladora = Integer.parseInt(req.getParameter("idFichaMaior"));
                    Ficha11ControleDAO.update(ficha);
                    break;
                case "createBatch":
                    System.out.println("ENTROU");
//                    String diretoria = "";
//                    //CRIANDO UMA CLASSE FINAL PARA PEGAR O VALOR DA DIRETORIA E DEPOIS ATRIBUIR A STRING DIRETORIA
//                    final class Nome {
//                        String valor;
//                    }
//                    Nome nomeDiretoria = new Nome();
                    Set<String> nomesAdicionados = new HashSet<>();
                    List<Ficha11Controle> fichas = new ArrayList<>();;
//                     // Iterar sobre a lista e inserir cada linha na tabela do banco de dados
                    for (Map<String, Object> map : list) {
                        Ficha11Controle tempFicha = new Ficha11Controle(); // Crie uma nova instância de Ficha1;
                        map.forEach((key, value) -> {
                            if (value != null) {                                                              
                                switch (key) {
                                    case "nome":
                                        Object obNome = map.get("nome");
                                        String  nome = obNome.toString();
                                        System.out.println("NOME EMPRESA: " + nome);
                                        tempFicha.setNome(nome);                                                                               
                                        break;
                                    case "pais":
                                        Object obPais = map.get("pais");
                                        String  paisNome = obPais.toString();
                                        System.out.println("NOME PAIS: " + paisNome);
                                        paisNome = paisNome.trim();
                                        if ("Estados Unidos da América".equals(paisNome)){
                                            paisNome = "Estados Unidos";
                                        }
                                        Pais pais = paisController.getPaisByNome((String) (paisNome));
                                        System.out.println("Nome pais " + pais.getNome());
                                        tempFicha.setPais((Pais) pais);                                                                               
                                        break;
                                    case "atividade":
                                        Object obAtividade = map.get("atividade");
                                        String atividade = obAtividade.toString();
                                        System.out.println("NOME Atividade: " + atividade);
                                        tempFicha.setAtividadeEcn(atividade);                                                                               
                                        break;
                                    case "porcentagemSocial":
                                           Object obPorcentagemSocial = map.get("porcentagemSocial");
                                           Double porcentagemSocial = NumeroUtils.stringToDouble(obPorcentagemSocial.toString());
                                           System.out.println("PORCENTAGEM SOCIAL: " + porcentagemSocial);
                                           tempFicha.setParticipacaoCapital(porcentagemSocial);
                                            break;
                                    case "moeda":
                                        Object obMoedaId = map.get("moeda");
                                        String nomeMoeda = obMoedaId.toString();
                                        nomeMoeda = NumeroUtils.verificarString(nomeMoeda);
                                        System.out.println("NOME MOEDA: " + nomeMoeda);
//                                        nomeMoeda = NumeroUtils.extrairConteudoParenteses(nomeMoeda);
                                        System.out.println("MOEDA: " + nomeMoeda);
                                        nomeMoeda = nomeMoeda.trim();
                                        if ("Reais".equals(nomeMoeda)){
                                            System.out.println("REAIS");
                                            nomeMoeda = "Real brasileiro";
                                        }
                                        else if ("Dólar".equals(nomeMoeda)){
                                            System.out.println("DOLARES");
                                            nomeMoeda = "Dólar americano";
                                        }
                                        Moeda moeda = moedaController.getMoedaByNome((String) (nomeMoeda));
                                        System.out.println("MOEDA Nome: " + moeda.getNome());
                                        tempFicha.setMoeda((Moeda) moeda);
                                        System.out.println("MOEDA FICHA: " + tempFicha.getMoeda().getId());
                                        break;
                                    case "patrimonioLiquido":
                                        Object obPatrimonioLiquido = map.get("patrimonioLiquido");
                                        String numeroPL = obPatrimonioLiquido.toString();
                                        System.out.println("NUMERO COM PARENTESES" + numeroPL);
                                        numeroPL = NumeroUtils.removerNumParenteses(numeroPL);
                                        System.out.println("NUMERO SEM PARENTESES" + numeroPL);
                                        double patrimonioLiquido = -0.1f;
                                        try {
                                            patrimonioLiquido = NumeroUtils.formatAndConvertToFloat(numeroPL);
                                        } catch (ParseException ex) {
                                            Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
//                                        Double patrimonioLiquido = NumeroUtils.stringToDouble(obPatrimonioLiquido.toString());
                                        System.out.println("PATRIMONIO LIQUIDO: " + patrimonioLiquido);
                                        tempFicha.setPatrimonioLiquido(patrimonioLiquido);
                                         break;
                                    case "valorMercado":
                                        Object obValorMercado = map.get("valorMercado");
                                        String numeroVL = obValorMercado.toString();
                                        System.out.println("NUMERO COM PARENTESES" + numeroVL);
                                        numeroVL = NumeroUtils.removerNumParenteses(numeroVL);
                                        System.out.println("NUMERO SEM PARENTESES" + numeroVL);
                                        double valorMercado = 0;
                                        if ("VALOR PL INFO COGER".equals(numeroVL)){
                                            numeroVL = "0";
                                        }
                                        numeroVL = numeroVL.trim();
                                        if ("0".equals(numeroVL)){
                                                valorMercado = 0;
                                            }
                                        else{
                                            try {
                                                valorMercado = NumeroUtils.formatAndConvertToFloat(numeroVL);
                                            } catch (ParseException ex) {
                                                Logger.getLogger(Ficha11MaiorController.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }

                                        System.out.println("VALOR MERCADO: " + valorMercado);
                                        tempFicha.setValorMercado(valorMercado);
                                         break;
                                    case "cadeia":
                                            boolean cadeia = false;
                                            Object obCadeia = map.get("cadeia");
                                            String stCadeia = obCadeia.toString();
                                            stCadeia = stCadeia.trim();
                                            System.out.println("FIM DA CADEIA: " + stCadeia);
                                            if ("SIM".equals(stCadeia.toUpperCase())){
                                                cadeia = true;
                                                System.out.println("CADEIA FINAL: " + cadeia);
                                                tempFicha.setFinalCadeia(cadeia);
                                            }
                                            else{
                                                cadeia = false;
                                                System.out.println("CADEIA FINAL: " + cadeia);
                                                tempFicha.setFinalCadeia(cadeia);
                                            }
                                            break;
                                }
                            }
                        });
                        System.out.println("REINICIOU O CILCO");
                        System.out.println("    ");
                        System.out.println("");
                        tempFicha.setDataCriacao(new Date());
                        tempFicha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
                        tempFicha.setFicha11Controladora(ficha11MaiorController.getFichaById(controladoraidCola));
                        if (tempFicha.getNome() != null && !nomesAdicionados.contains(tempFicha.getNome())){
                            fichas.add(tempFicha);
                            nomesAdicionados.add(tempFicha.getNome());
                        }
                    }    
                    for (Ficha11Controle registro : fichas){
                        List<Ficha11Controle> temp = new ArrayList();
                        temp.add(registro);
                        if(registro.getNome() != null){
//                                boolean existe = Ficha11ControleDAO.nomeExiste(registro.getNome());
                            System.out.println("CRIANDO");
                            Ficha11ControleDAO.createBatch(temp);                             
                        }
                        else{
                            System.out.println("IGNORANDO ITENS INDESEJADOS MENORES 2");
                        }
                    }
//                        
                    
                    break;
                default:
                    System.out.println("Tipo de requisição desconhecido");
            }
            
            if (tipoRequisicao.equals("createBatch")) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setHeader("Content-Type", "application/json");
//                resp.getWriter().write("{\"redirectUrl\": \"/ProjetoCBE/views/ficha11.jsp\"}");
                resp.getWriter().write("{\"redirectUrl\": \"/ProjetoCBE/views/empresas-controladas11.jsp?id=" + idControladora + "\"}");
                return;
            }
            else{
                resp.sendRedirect("/ProjetoCBE/views/empresas-controladas11.jsp?id=" + idControladora);
            }       
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

    public List<Ficha11Controle> getAllFichas() {
        return Ficha11ControleDAO.getAllFichas();
    }

    public List<Ficha11Controle> getAllFichasByTrimestreAno(int trimestre, int ano) {
        return Ficha11ControleDAO.getAllFichasByTrimestreAno(trimestre, ano);
    }

    public Ficha11Controle getFichaById(int id) {
        Optional<Ficha11Controle> optFicha11Controle = Ficha11ControleDAO.getFichaById(id);
        if (optFicha11Controle.isPresent()) {
            return optFicha11Controle.get();
        }
        return null;
    }

    public List<Ficha11Controle> getAllFichasByControladoraId(int id) {
        return Ficha11ControleDAO.getAllFichasByControladoraId(id);
    }

    public void deleteAllEmpresasByControladoraId(int idControladora) {
        Ficha11ControleDAO.deleteAllEmpresasByControladoraId(idControladora);
    }
    
    
     public String getAllFichasJson() {
        Gson gson = new Gson(); 
        List<Ficha11Controle> ficha11Controle = Ficha11ControleDAO.getAllFichas();
        String ficha11ControleJson = gson.toJson(ficha11Controle);
        return ficha11ControleJson;
    }
     
}
