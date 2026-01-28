package br.com.bb.cbe.Bean;

import java.util.Date;

public class Ficha11Menor {

    private int id;
    private String metodoValoracao;
    private Double valorParticipacao;
    private Double lucroDistribuido;
    private Date dataCriacao;
    private Moeda moeda;
    private Pais pais;
    private Funcionario funcionario;
    private int trimestre;
    private Status status;
    private String justificativaGestor;

    public Ficha11Menor() {
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

    public Double getValorParticipacao() {
        return valorParticipacao;
    }

    public void setValorParticipacao(Double valorParticipacao) {
        this.valorParticipacao = valorParticipacao;
    }

    public Double getLucroDistribuido() {
        return lucroDistribuido;
    }

    public void setLucroDistribuido(Double lucroDistribuido) {
        this.lucroDistribuido = lucroDistribuido;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
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

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
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
