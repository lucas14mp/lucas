package br.com.bb.cbe.controllers;

import java.util.Date;
import br.com.bb.cbe.DAO.Ficha14ControleDAO;
import br.com.bb.cbe.Bean.Ficha14Controle;
import br.com.bb.cbe.Utils.NumeroUtils;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ficha14Empresa")
public class Ficha14EmpresaController extends HttpServlet {

    private MoedaController moedaController;
    private PaisController paisController;
    private FuncionarioController funcionarioController;
    private Ficha14MaiorController ficha14MaiorController;

    @Override
    public void init() {
        this.moedaController = new MoedaController();
        this.paisController = new PaisController();
        this.funcionarioController = new FuncionarioController();
        this.ficha14MaiorController = new Ficha14MaiorController();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");
        String tipoRequisicao = req.getParameter("tipo-requisicao");
        HttpSession session = req.getSession();
        String chaveFuncionario = (String) session.getAttribute("chave");
        try {
            Ficha14Controle ficha = new Ficha14Controle();
            int idControladora = Integer.parseInt(req.getParameter("id"));
            if (tipoRequisicao.equals("post") || tipoRequisicao.equals("edit")) {
                int paisId = Integer.parseInt(req.getParameter("pais"));
                int moedaId = Integer.parseInt(req.getParameter("moeda"));

                ficha.setNome(req.getParameter("nome"));
                ficha.setPais(paisController.getPaisById(paisId));
                ficha.setPorcentoCapitalSocial(NumeroUtils.stringToDouble(req.getParameter("percentual")));
                ficha.setMoeda(moedaController.getMoedaById(moedaId));
                ficha.setPatrimonioLiquido(NumeroUtils.stringToDouble(req.getParameter("patrimonio")));
                ficha.setValorMercado(NumeroUtils.stringToDouble(req.getParameter("valor")));
                ficha.setAtividadeEcn(req.getParameter("atividade"));
                ficha.setFinalCadeia(Boolean.parseBoolean(req.getParameter("final")));
                ficha.setDataCriacao(new Date());
                ficha.setFicha14Controladora(ficha14MaiorController.getFichaById(idControladora));
                ficha.setFuncionario(funcionarioController.getFuncionarioByChave(chaveFuncionario));
            }
            switch (tipoRequisicao) {
                case "delete":
                    int id = Integer.parseInt(req.getParameter("id"));
                    idControladora = Integer.parseInt(req.getParameter("idFichaMaior"));
                    Ficha14ControleDAO.delete(id);
                    break;
                case "post":
                    ficha.setDataCriacao(new Date());
                    Ficha14ControleDAO.create(ficha);
                    break;
                case "edit":
                    ficha.setDataCriacao(new Date());
                    ficha.setId(Integer.parseInt(req.getParameter("id")));
                    idControladora = Integer.parseInt(req.getParameter("idFichaMaior"));
                    System.out.println(idControladora);
                    Ficha14ControleDAO.update(ficha);
                    break;
                default:
                    System.out.println("Tipo de requisição desconhecido");
            }
            resp.sendRedirect("/ProjetoCBE/views/empresas-controladas14.jsp?id=" + idControladora);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("mensagemErro", "O valor foi inserido em um formato inválido.\nPor favor, utilize o padrão: \"0.000.000,00\"");
            req.setAttribute("linkPaginaAnterior", "/ProjetoCBE/forms/ficha14.jsp");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/errors/customError.jsp");
            dispatcher.forward(req, resp);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public List<Ficha14Controle> getAllFichas() {
        return Ficha14ControleDAO.getAllFichas();
    }

    public List<Ficha14Controle> getAllFichasByTrimestreAno(int trimestre, int ano) {
        return Ficha14ControleDAO.getAllFichasByTrimestreAno(trimestre, ano);
    }

    public Ficha14Controle getFichaById(int id) {
        Optional<Ficha14Controle> optFicha14Controle = Ficha14ControleDAO.getFichaById(id);
        if (optFicha14Controle.isPresent()) {
            return optFicha14Controle.get();
        }
        return null;
    }

    public List<Ficha14Controle> getAllFichasByControladoraId(int idControladora) {
        return Ficha14ControleDAO.getAllFichasByControladoraId(idControladora);
    }

    public void deleteAllEmpresasByControladoraId(int idControladora) {
        Ficha14ControleDAO.deleteAllEmpresasByControladoraId(idControladora);
    }
}
