package br.com.bb.cbe.controllers;

import br.com.bb.cbe.Bean.Status;
import br.com.bb.cbe.DAO.StatusDAO;
import java.util.Optional;

public class StatusController {
    
    public Status getStatusById(int id) {
        return StatusDAO.getStatusById(id);
    }
    
}