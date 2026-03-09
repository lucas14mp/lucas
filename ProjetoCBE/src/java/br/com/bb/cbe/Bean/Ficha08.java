package br.com.bb.cbe.Bean;

import java.util.Date;

/**
 *
 * @author T1091501
 */
public class Ficha08 {

    private int id;
    private Pais pais;
    private Moeda moeda;
    private double saldoDatabase;
    private double rendimentos;
    private Date dataCriacao;
    private int trimestre;
    private Funcionario funcionario;
    private Status status;
    private String justificativaGestor;

    public Ficha08() {
    }

    public Ficha08(int id, Pais pais, Moeda moeda, double saldoDatabase, double rendimentos, Date dataCriacao, int trimestre, Funcionario funcionario) {
        this.id = id;
        this.pais = pais;
        this.moeda = moeda;
        this.saldoDatabase = saldoDatabase;
        this.rendimentos = rendimentos;
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

    public double getSaldoDatabase() {
        return saldoDatabase;
    }

    public void setSaldoDatabase(double saldoDatabase) {
        this.saldoDatabase = saldoDatabase;
    }

    public double getRendimentos() {
        return rendimentos;
    }

    public void setRendimentos(double rendimentos) {
        this.rendimentos = rendimentos;
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