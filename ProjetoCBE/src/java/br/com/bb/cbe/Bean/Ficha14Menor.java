package br.com.bb.cbe.Bean;

import java.util.Date;

public class Ficha14Menor {

    private int id;
    private Double valorParticipacao;
    private Double rendimentoDistribuido;
    private Date dataCriacao;
    private int trimestre;
    private Funcionario funcionario;
    private Moeda moeda;
    private Pais pais;
    private Status status;

    public Ficha14Menor() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getValorParticipacao() {
        return valorParticipacao;
    }

    public void setValorParticipacao(Double valorParticipacao) {
        this.valorParticipacao = valorParticipacao;
    }

    public Double getRendimentoDistribuido() {
        return rendimentoDistribuido;
    }

    public void setRendimentoDistribuido(Double rendimentoDistribuido) {
        this.rendimentoDistribuido = rendimentoDistribuido;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
