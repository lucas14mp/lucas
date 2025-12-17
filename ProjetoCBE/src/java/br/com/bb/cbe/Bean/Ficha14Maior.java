package br.com.bb.cbe.Bean;

import java.util.Date;

public class Ficha14Maior {

    private int id;
    private Double patrimonioLiquido;
    private Double participacaoPatrimonio;
    private Double rendimentosFundo;
    private Double rendimentosDistribuidos;
    private Boolean controlaEmpresas;
    private Date dataCriacao;
    private int trimestre;
    private Moeda moeda;
    private Pais pais;
    private Funcionario funcionario;
    private Empresa empresa;
    private Status status;

    public Ficha14Maior() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPatrimonioLiquido() {
        return patrimonioLiquido;
    }

    public void setPatrimonioLiquido(Double patrimonioLiquido) {
        this.patrimonioLiquido = patrimonioLiquido;
    }

    public Double getParticipacaoPatrimonio() {
        return participacaoPatrimonio;
    }

    public void setParticipacaoPatrimonio(Double participacaoPatrimonio) {
        this.participacaoPatrimonio = participacaoPatrimonio;
    }

    public Double getRendimentosFundo() {
        return rendimentosFundo;
    }

    public void setRendimentosFundo(Double rendimentosFundo) {
        this.rendimentosFundo = rendimentosFundo;
    }

    public Double getRendimentosDistribuidos() {
        return rendimentosDistribuidos;
    }

    public void setRendimentosDistribuidos(Double rendimentosDistribuidos) {
        this.rendimentosDistribuidos = rendimentosDistribuidos;
    }

    public void setControlaEmpresas(boolean controlaEmpresa) {
        this.controlaEmpresas = controlaEmpresa;
    }

    public Boolean isControlaEmpresas() {
        return controlaEmpresas;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
