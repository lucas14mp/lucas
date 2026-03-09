package br.com.bb.cbe.Bean;

import java.util.Date;

public class Ficha16 {

    private int id;
    private String tipoOutrosDireito;
    private double valorDatabase;
    private Date dataCriacao;
    private int trimestre;
    private Pais pais;
    private Moeda moeda;
    private Funcionario funcionario;
    private Status status;
    private String justificativaGestor;

    public Ficha16() {

    }

    public Ficha16(int id, String tipoOutrosDireito, double valorDatabase, Date dataCriacao, int trimestre, Pais pais, Moeda moeda, Funcionario funcionario, Status status) {
        this.id = id;
        this.tipoOutrosDireito = tipoOutrosDireito;
        this.valorDatabase = valorDatabase;
        this.dataCriacao = dataCriacao;
        this.trimestre = trimestre;
        this.pais = pais;
        this.moeda = moeda;
        this.funcionario = funcionario;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoOutrosDireito() {
        return tipoOutrosDireito;
    }

    public void setTipoOutrosDireito(String tipoOutrosDireito) {
        this.tipoOutrosDireito = tipoOutrosDireito;
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
    
    public String getJustificativaGestor() {
    return justificativaGestor;
    }

public void setJustificativaGestor(String justificativaGestor) {
    this.justificativaGestor = justificativaGestor;
    }
}