package br.com.bb.cbe.Bean;

import java.util.Date;

public class Ficha11Maior {

    private int id;
    private boolean possuiCotacaoEmBolsa;
    private String metodoValoracao;
    private double valorEmpresa;
    private double patrimonioTotal;
    private double porcentoParticipacaoCapital;
    private double porcentoPoderVoto;
    private double ativoDatabase;
    private double passivoExigivel;
    private double valorTotalLucroPrejuizo;
    private double resultadoLiquidoItensNaoRecorrentes;
    private double resultadoLiquidoReavaliacoes;
    private double resultadoLiquidoVariacaoCambial;
    private double lucroDistribuido;
    private boolean controlaEmpresa;
    private Date dataCriacao;
    private int trimestre;
    private Moeda moeda;
    private Empresa empresa;
    private Funcionario funcionario;
    private Status status;

    public Ficha11Maior() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPossuiCotacaoEmBolsa() {
        return possuiCotacaoEmBolsa;
    }

    public void setPossuiCotacaoEmBolsa(boolean possuiCotacaoEmBolsa) {
        this.possuiCotacaoEmBolsa = possuiCotacaoEmBolsa;
    }

    public String getMetodoValoracao() {
        return metodoValoracao;
    }

    public void setMetodoValoracao(String metodoValoracao) {
        this.metodoValoracao = metodoValoracao;
    }

    public double getValorEmpresa() {
        return valorEmpresa;
    }

    public void setValorEmpresa(double valorEmpresa) {
        this.valorEmpresa = valorEmpresa;
    }

    public double getPatrimonioTotal() {
        return patrimonioTotal;
    }

    public void setPatrimonioTotal(double patrimonioTotal) {
        this.patrimonioTotal = patrimonioTotal;
    }

    public double getPorcentoParticipacaoCapital() {
        return porcentoParticipacaoCapital;
    }

    public void setPorcentoParticipacaoCapital(double porcentoParticipacaoCapital) {
        this.porcentoParticipacaoCapital = porcentoParticipacaoCapital;
    }

    public double getPorcentoPoderVoto() {
        return porcentoPoderVoto;
    }

    public void setPorcentoPoderVoto(double porcentoPoderVoto) {
        this.porcentoPoderVoto = porcentoPoderVoto;
    }

    public double getAtivoDatabase() {
        return ativoDatabase;
    }

    public void setAtivoDatabase(double ativoDatabase) {
        this.ativoDatabase = ativoDatabase;
    }

    public double getPassivoExigivel() {
        return passivoExigivel;
    }

    public void setPassivoExigivel(double passivoExigivel) {
        this.passivoExigivel = passivoExigivel;
    }

    public double getValorTotalLucroPrejuizo() {
        return valorTotalLucroPrejuizo;
    }

    public void setValorTotalLucroPrejuizo(double valorTotalLucroPrejuizo) {
        this.valorTotalLucroPrejuizo = valorTotalLucroPrejuizo;
    }

    public double getResultadoLiquidoItensNaoRecorrentes() {
        return resultadoLiquidoItensNaoRecorrentes;
    }

    public void setResultadoLiquidoItensNaoRecorrentes(double resultadoLiquidoItensNaoRecorrentes) {
        this.resultadoLiquidoItensNaoRecorrentes = resultadoLiquidoItensNaoRecorrentes;
    }

    public double getResultadoLiquidoReavaliacoes() {
        return resultadoLiquidoReavaliacoes;
    }

    public void setResultadoLiquidoReavaliacoes(double resultadoLiquidoReavaliacoes) {
        this.resultadoLiquidoReavaliacoes = resultadoLiquidoReavaliacoes;
    }

    public double getResultadoLiquidoVariacaoCambial() {
        return resultadoLiquidoVariacaoCambial;
    }

    public void setResultadoLiquidoVariacaoCambial(double resultadoLiquidoVariacaoCambial) {
        this.resultadoLiquidoVariacaoCambial = resultadoLiquidoVariacaoCambial;
    }

    public double getLucroDistribuido() {
        return lucroDistribuido;
    }

    public void setLucroDistribuido(double lucroDistribuido) {
        this.lucroDistribuido = lucroDistribuido;
    }

    public boolean isControlaEmpresa() {
        return controlaEmpresa;
    }

    public void setControlaEmpresa(boolean controlaEmpresa) {
        this.controlaEmpresa = controlaEmpresa;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
