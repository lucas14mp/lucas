package br.com.bb.cbe.Bean;

import java.util.Date;

public class Ficha10 {

    private int id;
    private String metodoValoracao;
    private double valorDatabase;
    private Date dataCriacao;
    private int trimestre;
    private Moeda moeda;
    private Pais pais;
    private Funcionario funcionario;
    private Status status;
    
    public Ficha10() {

    }

    public Ficha10(int id, String metodoValoracao, double valorDatabase, Date dataCriacao, int trimestre, Moeda moeda, Pais pais, Funcionario funcionario) {
        this.id = id;
        this.metodoValoracao = metodoValoracao;
        this.valorDatabase = valorDatabase;
        this.dataCriacao = dataCriacao;
        this.trimestre = trimestre;
        this.moeda = moeda;
        this.pais = pais;
        this.funcionario = funcionario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMetodoValoracao() {
        return metodoValoracao;
    }

    public void setMetodoValoracao(String metodoValoracao) {
        this.metodoValoracao = metodoValoracao;
    }

    public double getValorDatabase() {
        return valorDatabase;
    }

    public void setValorDatabase(double valorDatabase) {
        this.valorDatabase = valorDatabase;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
}
