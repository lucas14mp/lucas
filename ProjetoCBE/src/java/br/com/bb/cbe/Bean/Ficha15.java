package br.com.bb.cbe.Bean;

/**
 *
 * @author T1091501
 */
import java.util.Date;

public class Ficha15 {

    private int id;
    private String metodoValoracao;
    private double valorDatabase;
    private boolean imovelQuitado;
    private double saldoDatabase;
    private double aluguelRecebido;
    private Date dataCriacao;
    private int trimestre;
    private Moeda moeda;
    private Pais pais;
    private Funcionario funcionario;
    private Status status;

    public Ficha15() {
    }

    public Ficha15(int id, String metodoValoracao, double valorDatabase, boolean imovelQuitado, double saldoDatabase, double aluguelRecebido, Date dataCriacao, int trimestre, Pais pais, Moeda moeda, Funcionario funcionario, Status status) {
        this.id = id;
        this.metodoValoracao = metodoValoracao;
        this.valorDatabase = valorDatabase;
        this.imovelQuitado = imovelQuitado;
        this.saldoDatabase = saldoDatabase;
        this.aluguelRecebido = aluguelRecebido;
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

    public String getMetodoValoracao() {
        return metodoValoracao;
    }

    public void setMetodoValoracao(String metodoValoracao) {
        this.metodoValoracao = metodoValoracao;
    }

    public double getValorDatabase() {
        return valorDatabase;
    }

    public void setValorDatabase(double valorDatabase) {
        this.valorDatabase = valorDatabase;
    }

    public boolean isImovelQuitado() {
        return imovelQuitado;
    }

    public void setImovelQuitado(boolean imovelQuitado) {
        this.imovelQuitado = imovelQuitado;
    }

    public double getSaldoDatabase() {
        return saldoDatabase;
    }

    public void setSaldoDatabase(double saldoDatabase) {
        this.saldoDatabase = saldoDatabase;
    }

    public double getAluguelRecebido() {
        return aluguelRecebido;
    }

    public void setAluguelRecebido(double aluguelRecebido) {
        this.aluguelRecebido = aluguelRecebido;
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

}
