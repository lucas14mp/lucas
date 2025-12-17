package br.com.bb.cbe.controllers;

import br.com.bb.cbe.Bean.Dependencia;
import br.com.bb.cbe.DAO.DependenciaDAO;

public class DependenciaController {
    
    private DependenciaDAO dependenciaDAO;
    
    public void init() {
        this.dependenciaDAO = new DependenciaDAO();
    }
 
    public Dependencia getDependenciaById(int id) {
        return DependenciaDAO.getDependenciaById(id);
    }
}
