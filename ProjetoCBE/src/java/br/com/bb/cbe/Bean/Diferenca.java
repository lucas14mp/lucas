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
public class Diferenca {
    private int id;
    private double somatorio;
    private double contabil_real;
    private double contabil_dolar;
    private double diferenca_real;
    private double diferenca_dolar;
    private Date data_criacao;
    private int trimestre;
    private String numero_ficha;
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public double getSomatorio(){
        return somatorio;
    }
    
    public void setSomatorio(double somatorio){
        this.somatorio = somatorio;
    }
    
    public double getContabilReal(){
        return contabil_real;
    }
    
    public void setContabilReal(double contabil_real){
        this.contabil_real = contabil_real;
    }
    
    public double getContabilDolar(){
        return contabil_dolar;
    }
    
    public void setContabilDolar(double contabil_dolar){
        this.contabil_dolar = contabil_dolar;
    }
    
    public double getDiferencaReal(){
        return diferenca_real;
    }
    
    public void setDiferencaReal(double diferenca_real){
        this.diferenca_real = diferenca_real;
    }
    
    public double getDiferencaDolar(){
        return diferenca_dolar;
    }
    
    public void setDiferencaDolar(double diferenca_dolar){
        this.diferenca_dolar = diferenca_dolar;
    }
    
    public Date getDataCriacao() {
        return data_criacao;
    }

    public void setDataCriacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }
    
    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }
    
    public String getNumeroFicha() {
        return numero_ficha;
    }

    public void setNumeroFicha(String numero_ficha) {
        this.numero_ficha = numero_ficha;
    }
}
