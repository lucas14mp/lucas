package br.com.bb.cbe.Bean;

/**
 *
 * @author T1091501
 */
import java.util.Date;

public class Ficha07 {

    private int id;
    private Pais paisNegociacao;
    private Pais paisEmissor;
    private Moeda moeda;
    private double valorDatabase;
    private double dividendos;
    private Date dataCriacao;
    private int trimestre;
    private Funcionario funcionario;
    private Status status;

    public Ficha07() {
    }

    public Ficha07(int id, Pais paisNegociacao, Pais paisEmissor, Moeda moeda, double valorDatabase, double dividendos, Date dataCriacao, int trimestre, Funcionario funcionario) {
        this.id = id;
        this.paisNegociacao = paisNegociacao;
        this.paisEmissor = paisEmissor;
        this.moeda = moeda;
        this.valorDatabase = valorDatabase;
        this.dividendos = dividendos;
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

    public Pais getPaisNegociacao() {
        return paisNegociacao;
    }

    public void setPaisNegociacao(Pais paisNegociacao) {
        this.paisNegociacao = paisNegociacao;
    }

    public Pais getPaisEmissor() {
        return paisEmissor;
    }

    public void setPaisEmissor(Pais paisEmissor) {
        this.paisEmissor = paisEmissor;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
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
