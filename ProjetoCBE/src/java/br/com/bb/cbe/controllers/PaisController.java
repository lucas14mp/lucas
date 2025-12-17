package br.com.bb.cbe.controllers;

import br.com.bb.cbe.Bean.Pais;
import br.com.bb.cbe.DAO.PaisDAO;
import java.util.List;

public class PaisController {
    
    private PaisDAO paisDAO = new PaisDAO();
    
    public Pais getPaisById(int id) {
        Pais pais = paisDAO.getPaisById(id);
        return pais;
    }
    
    public List<Pais> listarPaises() {
        List<Pais> listaPaises = paisDAO.getAllPaises();
        return listaPaises;
    }
    
    public List<Pais> listarPaisesEstrangeiros() {
        List<Pais> listaPaises = paisDAO.getAllPaisesEstrangeiros();
        return listaPaises;
    }
    
    public Pais getPaisByNome(String nome) {
        Pais pais = PaisDAO.getPaisByNome(nome);
        return pais;
    }
}
