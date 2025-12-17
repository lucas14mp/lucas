package br.com.bb.cbe.controllers;

import br.com.bb.cbe.DAO.EmpresaDAO;
import br.com.bb.cbe.Bean.Empresa;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ficha0")
public class EmpresaController extends HttpServlet {

    private PaisController paisController;
    private FuncionarioController funcionarioController;

    @Override
    public void init() {
        this.paisController = new PaisController();
        this.funcionarioController = new FuncionarioController();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");
        String tipoRequisicao = req.getParameter("tipo-requisicao");

        try {
            Empresa empresa = new Empresa();
            if (tipoRequisicao.equals("post") || tipoRequisicao.equals("edit")) {
                int paisId = Integer.parseInt(req.getParameter("pais"));
                empresa.setNome(req.getParameter("nome-empresa"));
                empresa.setPais(paisController.getPaisById(paisId));
                empresa.setTransacionaPaisesDiferentes(Boolean.parseBoolean(req.getParameter("empresa-transaciona")));
                if (req.getParameter("cdnr").equals("")) {
                    empresa.setCdnr(0);
                } else {
                    empresa.setCdnr(Integer.parseInt(req.getParameter("cdnr")));
                }
                empresa.setRelacaoDeclarante(req.getParameter("relacao-declarante"));
                empresa.setNumeroEmpregados(Integer.parseInt(req.getParameter("numero-empregados")));
                empresa.setAtividadeEconomica(req.getParameter("atividade-economica"));
                if (req.getParameter("atividade-economica").substring(0, 2).equals("64")) {
                    empresa.setDetalhamentoAtividadeEconomica(req.getParameter("detalhamento-atividade-economica"));
                }
                empresa.setFuncionario(funcionarioController.getFuncionarioByChave("T1091905")); // TO DO: Mudar para salvar de forma dinâmica
            }
            System.out.println("id da pagina " + req.getParameter("relacao-declarante"));
            switch (tipoRequisicao) {
                case "delete":
                    int id = Integer.parseInt(req.getParameter("id"));
                    EmpresaDAO.delete(id);
                    break;
                case "post":
                    EmpresaDAO.create(empresa);
                    break;
                case "edit":
                    empresa.setId(Integer.parseInt(req.getParameter("id")));
                    EmpresaDAO.update(empresa);
                    break;
                default:
                    System.out.println("Tipo de requisição desconhecido");
            }

            resp.sendRedirect("views/empresas.jsp");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("mensagemErro", "O valor foi inserido em um formato inválido.\nPor favor, utilize o padrão: \"0.000.000,00\"");
            req.setAttribute("linkPaginaAnterior", "/ProjetoCBE/forms/ficha0.jsp");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/errors/customError.jsp");
            dispatcher.forward(req, resp);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public List<Empresa> listarEmpresas() {
        List<Empresa> listaEmpresas = EmpresaDAO.getAllEmpresas();
        return listaEmpresas;
    }

    public Empresa getEmpresaById(int id) {
        Empresa empresa = EmpresaDAO.getEmpresaById(id);
        return empresa;
    }
    
    public Empresa getEmpresaByNome(String nome) {
        Empresa empresa = EmpresaDAO.getEmpresaByNome(nome);
        return empresa;
    }
    
//    public Empresa getEmpresaById(int id) {
//        Optional<Empresa> optEmpresa = EmpresaDAO.getEmpresaById(id);
//        if (optEmpresa.isPresent()) {
//            return optEmpresa.get();
//        }
//        return null;
//    }
}
