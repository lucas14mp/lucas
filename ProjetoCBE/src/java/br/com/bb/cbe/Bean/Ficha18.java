package br.com.bb.cbe.Bean;

import java.util.Date;

public class Ficha18 {

    private int id;
    private String prazoDivida;
    private Pais pais;
    private Moeda moeda;
    private double valorMercado;
    private double jurosRecebidos;
    private Date dataCriacao;
    private int trimestre;
    private Funcionario funcionario;
    private Status status;
    private String justificativaGestor;

    public Ficha18() {

    }

    public Ficha18(int id, String prazoDivida, Pais pais, Moeda moeda, double valorMercado, double jurosRecebidos, Date dataCriacao, int trimestre, Funcionario funcionario, Status status) {
        this.id = id;
        this.prazoDivida = prazoDivida;
        this.pais = pais;
        this.moeda = moeda;
        this.valorMercado = valorMercado;
        this.jurosRecebidos = jurosRecebidos;
        this.dataCriacao = dataCriacao;
        this.trimestre = trimestre;
        this.funcionario = funcionario;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrazoDivida() {
        return prazoDivida;
    }

    public void setPrazoDivida(String prazoDivida) {
        this.prazoDivida = prazoDivida;
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

    public double getValorMercado() {
        return valorMercado;
    }

    public void setValorMercado(double valorMercado) {
        this.valorMercado = valorMercado;
    }

    public double getJurosRecebidos() {
        return jurosRecebidos;
    }

    public void setJurosRecebidos(double jurosRecebidos) {
        this.jurosRecebidos = jurosRecebidos;
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

    public String getJustificativaGestor() {
        return justificativaGestor;
    }

    public void setJustificativaGestor(String justificativaGestor) {
        this.justificativaGestor = justificativaGestor;
    }

}