package br.com.bb.cbe.Bean;

import java.util.Date;

public class Ficha19 {

    private int id;
    private boolean exportaMercadoria;
    private Date dataCriacao;
    private int trimestre;
    private Funcionario funcionario;
    private Status status;

    public Ficha19() {

    }

    public Ficha19(int id, boolean exportaMercadoria, Date dataCriacao, int trimestre, Funcionario funcionario, Status status) {
        this.id = id;
        this.exportaMercadoria = exportaMercadoria;
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

    public boolean isExportaMercadoria() {
        return exportaMercadoria;
    }

    public void setExportaMercadoria(boolean exportaMercadoria) {
        this.exportaMercadoria = exportaMercadoria;
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
