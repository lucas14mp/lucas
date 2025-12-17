package br.com.bb.cbe.Bean;

/**
 *
 * @author T1091501
 */
import java.util.Date;

public class Ficha12 {

    private int id;
    private String prazoEmprestimo;
    private double saldoDatabase;
    private double jurosPeriodoBase;
    private Date dataCriacao;
    private int trimestre;
    private Moeda moeda;
    private Funcionario funcionario;
    private Empresa empresa;
    private Status status;

    public Ficha12() {

    }

    public Ficha12(int id, String prazoEmprestimo, double saldoDatabase, double jurosPeriodoBase, Date dataCriacao, int trimestre, Moeda moeda, Funcionario funcionario, Empresa empresa, Status status) {
        this.id = id;
        this.prazoEmprestimo = prazoEmprestimo;
        this.saldoDatabase = saldoDatabase;
        this.jurosPeriodoBase = jurosPeriodoBase;
        this.dataCriacao = dataCriacao;
        this.trimestre = trimestre;
        this.moeda = moeda;
        this.funcionario = funcionario;
        this.empresa = empresa;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrazoEmprestimo() {
        return prazoEmprestimo;
    }

    public void setPrazoEmprestimo(String prazoEmprestimo) {
        this.prazoEmprestimo = prazoEmprestimo;
    }

    public double getSaldoDatabase() {
        return saldoDatabase;
    }

    public void setSaldoDatabase(double saldoDatabase) {
        this.saldoDatabase = saldoDatabase;
    }

    public double getJurosPeriodoBase() {
        return jurosPeriodoBase;
    }

    public void setJurosPeriodoBase(double jurosPeriodoBase) {
        this.jurosPeriodoBase = jurosPeriodoBase;
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
