package br.com.bb.cbe.controllers;

import br.com.bb.cbe.Bean.Moeda;
import br.com.bb.cbe.DAO.MoedaDAO;
import java.util.List;

public class MoedaController {

    private MoedaDAO moedaDAO = new MoedaDAO();

    public Moeda getMoedaById(int id) {
        Moeda moeda = moedaDAO.getMoedaById(id);
        return moeda;
    }

    public List<Moeda> listarMoedas() {
        List<Moeda> listaMoedas = moedaDAO.getAllMoedas();
        return listaMoedas;
    }

    public List<Moeda> listarMoedasEstrangeiras() {
        List<Moeda> listaMoedas = moedaDAO.getAllMoedasEstrangeiras();
        return listaMoedas;
    }
    
    public List<Moeda> listarSimbolos() {
        List<Moeda> listaMoedas = moedaDAO.getAllMoedasSimbolos();
        return listaMoedas;
    }
    
    public Moeda getMoedaByNome(String nome) {
        Moeda moeda = moedaDAO.getMoedaByNome(nome);
        return moeda;
    }
    
    public Moeda getMoedaBySigla(String nome) {
        Moeda moeda = moedaDAO.getMoedaBySigla(nome);
        return moeda;
    }
    
    public boolean moedaExiste(String sigla){
       boolean existe = moedaDAO.moedaExiste(sigla);
       return existe;
    }
}
