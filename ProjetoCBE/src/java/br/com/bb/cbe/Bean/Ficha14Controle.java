package br.com.bb.cbe.Bean;

import java.util.Date;

public class Ficha14Controle {

    private int id;
    private String nome;
    private String atividadeEcn;
    private Double porcentoCapitalSocial;
    private Double patrimonioLiquido;
    private Double valorMercado;
    private Boolean finalCadeia;
    private Date dataCriacao;
    private Moeda moeda;
    private Pais pais;
    private Funcionario funcionario;
    private Ficha14Maior ficha14Controladora;

    public Ficha14Controle() {
    }

    public Ficha14Controle(int id, String nome, String atividadeEcn, Double porcentoCapitalSocial, Double patrimonioLiquido, Double valorMercado, Boolean finalCadeia, Date dataCriacao, Moeda moeda, Pais pais, Funcionario funcionario, Ficha14Maior ficha14Controladora) {
        this.id = id;
        this.nome = nome;
        this.atividadeEcn = atividadeEcn;
        this.porcentoCapitalSocial = porcentoCapitalSocial;
        this.patrimonioLiquido = patrimonioLiquido;
        this.valorMercado = valorMercado;
        this.finalCadeia = finalCadeia;
        this.dataCriacao = dataCriacao;
        this.moeda = moeda;
        this.pais = pais;
        this.funcionario = funcionario;
        this.ficha14Controladora = ficha14Controladora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAtividadeEcn() {
        return atividadeEcn;
    }

    public void setAtividadeEcn(String atividadeEcn) {
        this.atividadeEcn = atividadeEcn;
    }

    public Double getPorcentoCapitalSocial() {
        return porcentoCapitalSocial;
    }

    public void setPorcentoCapitalSocial(Double porcentoCapitalSocial) {
        this.porcentoCapitalSocial = porcentoCapitalSocial;
    }

    public Double getPatrimonioLiquido() {
        return patrimonioLiquido;
    }

    public void setPatrimonioLiquido(Double patrimonioLiquido) {
        this.patrimonioLiquido = patrimonioLiquido;
    }

    public Double getValorMercado() {
        return valorMercado;
    }

    public void setValorMercado(Double valorMercado) {
        this.valorMercado = valorMercado;
    }

    public Boolean isFinalCadeia() {
        return finalCadeia;
    }

    public void setFinalCadeia(Boolean finalCadeia) {
        this.finalCadeia = finalCadeia;
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

    public Ficha14Maior getFicha14Controladora() {
        return ficha14Controladora;
    }

    public void setFicha14Controladora(Ficha14Maior ficha14Controladora) {
        this.ficha14Controladora = ficha14Controladora;
    }

}
