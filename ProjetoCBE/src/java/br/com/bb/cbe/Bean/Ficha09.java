package br.com.bb.cbe.Bean;

import java.util.Date;

public class Ficha09 {

    private int id;
    private String metodoValoracao;
    private double valorDatabase;
    private Date dataCriacao;
    private Pais pais;
    private Moeda moeda;
    private int trimestre;
    private Funcionario funcionario;
    private Status status;

    public Ficha09() {

    }

    public Ficha09(int id, String metodoValoracao, double valorDatabase, Date dataCriacao, Pais pais, Moeda moeda, int trimestre, Funcionario funcionario) {
        this.id = id;
        this.metodoValoracao = metodoValoracao;
        this.valorDatabase = valorDatabase;
        this.dataCriacao = dataCriacao;
        this.pais = pais;
        this.moeda = moeda;
        this.trimestre = trimestre;
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

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
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
