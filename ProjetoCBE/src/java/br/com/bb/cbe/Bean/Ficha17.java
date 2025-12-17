package br.com.bb.cbe.Bean;

/**
 *
 * @author T1091905
 */
import java.util.Date;

public class Ficha17 {

    private int id;
    private Empresa empresa;
    private Moeda moeda;
    private String prazoDivida;
    private double valorMercado;
    private double jurosRecebidos;
    private Date dataCriacao;
    private int trimestre;
    private Funcionario funcionario;
    private Status status;

    public Ficha17() {
    }

    public Ficha17(int id, Empresa empresa, Moeda moeda, String prazoDivida, double valorMercado, double jurosRecebidos, Date dataCriacao, int trimestre, Funcionario funcionario, Status status) {
        this.id = id;
        this.empresa = empresa;
        this.moeda = moeda;
        this.prazoDivida = prazoDivida;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public String getPrazoDivida() {
        return prazoDivida;
    }

    public void setPrazoDivida(String prazoDivida) {
        this.prazoDivida = prazoDivida;
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
    
}