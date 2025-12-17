package br.com.bb.cbe.Bean;

import java.util.Date;

public class Ficha11Controle {
    
    private int id;
    private String nome;
    private String atividadeEcn;
    private Double participacaoCapital;
    private Double patrimonioLiquido;
    private Double valorMercado;
    private Boolean finalCadeia;
    private Moeda moeda;
    private Pais pais;
    private Funcionario funcionario;
    private Ficha11Maior ficha11Controladora;
    private Date dataCriacao;

    public Ficha11Controle(){
        
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

    public Double getParticipacaoCapital() {
        return participacaoCapital;
    }

    public void setParticipacaoCapital(Double participacaoCapital) {
        this.participacaoCapital = participacaoCapital;
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

    public Ficha11Maior getFicha11Controladora() {
        return ficha11Controladora;
    }

    public void setFicha11Controladora(Ficha11Maior ficha11Controladora) {
        this.ficha11Controladora = ficha11Controladora;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
}
