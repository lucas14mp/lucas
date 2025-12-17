package br.com.bb.cbe.controllers;

import br.com.bb.cbe.Bean.Funcionario;
import br.com.bb.cbe.DAO.FuncionarioDAO;

public class FuncionarioController {
    
    private FuncionarioDAO funcionarioDAO;
    
    public void init() {
        this.funcionarioDAO = new FuncionarioDAO();
    }
 
    public Funcionario getFuncionarioByChave(String chave) {
        return funcionarioDAO.getFuncionarioByChave(chave);
    }
    
    public boolean funcionarioExiste(String chave) {
        return funcionarioDAO.funcionarioExiste(chave);
    }
    
    public void criarFuncionario(Funcionario funcionario) {
        FuncionarioDAO.criarFuncionario(funcionario);
    }
}
