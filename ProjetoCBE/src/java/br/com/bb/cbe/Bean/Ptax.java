/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.bb.cbe.Bean;

import java.util.Date;

 

/**
 *
 * @author T1092407
 */
public class Ptax {
    private int idPtax;
    private double compra;
    private double venda;
    private Moeda moeda;
    private Date data_criacao;
    private int trimestre;
    
    public int getIdPtax(){
        return idPtax;
    }
    
    public void setIdPtax(int idPtax){
        this.idPtax = idPtax;
    }
    
    public double getCompra(){
        return compra;
    }
    
    public void setCompra(double compra){
        this.compra = compra;
    }
    
    public double getVenda(){
        return venda;
    }
    
    public void setVenda(double venda){
        this.venda = venda;
    }
    
    public Moeda getMoeda(){
        return moeda;
    }
    
    public void setMoeda(Moeda moeda){
        this.moeda = moeda;
    }
    
    public Date getData_criacao(){
        return data_criacao;
    }
    
    public void setData_criacao(Date data_criacao ){
        this.data_criacao=data_criacao;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }
    
    
}
