package br.com.bb.cbe.Bean;

import java.util.Date;

public class Ficha06 {

    private int id;
    private double valorDatabase;
    private double dividendos;
    private Moeda moeda;
    private Pais pais;
    private Date dataCriacao;
    private int trimestre;
    private Funcionario funcionario;
    private Status status;

    public Ficha06() {
    }

    public Ficha06(int id, double valorDatabase, double dividendos, Moeda moeda, Pais pais, Date dataCriacao, int trimestre, Funcionario funcionario) {
        this.id = id;
        this.valorDatabase = valorDatabase;
        this.dividendos = dividendos;
        this.moeda = moeda;
        this.pais = pais;
        this.dataCriacao = dataCriacao;
        this.trimestre = trimestre;
        this.funcionario = funcionario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorDatabase() {
        return valorDatabase;
    }

    public void setValorDatabase(double valorDatabase) {
        this.valorDatabase = valorDatabase;
    }

    public double getDividendos() {
        return dividendos;
    }

    public void setDividendos(double dividendos) {
        this.dividendos = dividendos;
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
