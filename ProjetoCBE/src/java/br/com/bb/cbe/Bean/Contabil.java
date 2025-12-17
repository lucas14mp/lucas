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
public class Contabil {
    
    private int id;
    private String cosif;
    private String nome;
    private double saldo;
    private Date dataCriacao;
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getCosif(){
        return cosif;
    }
    
    public void setCosif(String cosif){
        this.cosif = cosif;
    }
   
    public String getNome(){
        return nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public double getSaldo(){
        return saldo;
    }
    
    public void setSaldo(double saldo){
        this.saldo = saldo;
    }
    
    public Date getDataCriacao(){
        return dataCriacao;
    }
    
    public void setDataCriacao(Date dataCriacao){
        this.dataCriacao = dataCriacao;
    }
}
