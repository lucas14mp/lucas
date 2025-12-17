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
public class Justificativa {
    private int id;
    private String just;
    private String numeroFicha;
    private Date dataCriacao;
    private double somatorio;
    private double contabil;
    private double diferenca;
    private Funcionario funcionario;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
     public String getJust() {
        return just;
    }

    public void setJust(String just) {
        this.just = just;
        
    }
    
    public String getNumeroFicha() {
        return numeroFicha;
    }

    public void setNumeroFicha(String numeroFicha) {
        this.numeroFicha = numeroFicha;
    } 
    
    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    } 
    
    public double getSomatorio() {
        return somatorio;
    }

    public void setSomatorio(double somatorio) {
        this.somatorio = somatorio;
    } 
    
    public double getContabil() {
        return contabil;
    }

    public void setContabil(double contabil) {
        this.contabil = contabil;
    } 
    
    public double getDiferenca() {
        return diferenca;
    }

    public void setDiferenca(double diferenca) {
        this.diferenca = diferenca;
    }
     
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
}
