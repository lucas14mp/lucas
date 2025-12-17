package br.com.bb.cbe.Bean;

public class Empresa {
    
    private int id;
    private String nome;
    private Boolean transacionaPaisesDiferentes;
    private int cdnr;
    private String relacaoDeclarante;
    private int numeroEmpregados;
    private String atividadeEconomica;
    private String detalhamentoAtividadeEconomica;
    private Pais pais;
    private Funcionario funcionario;
 
    public Empresa(){
        
    }

    public Empresa(int id, String nome, Boolean transacionaPaisesDiferentes, int cdnr, String relacaoDeclarante, int numeroEmpregados, String atividadeEconomica, String detalhamentoAtividadeEconomica, Pais pais, Funcionario funcionario) {
        this.id = id;
        this.nome = nome;
        this.transacionaPaisesDiferentes = transacionaPaisesDiferentes;
        this.cdnr = cdnr;
        this.relacaoDeclarante = relacaoDeclarante;
        this.numeroEmpregados = numeroEmpregados;
        this.atividadeEconomica = atividadeEconomica;
        this.detalhamentoAtividadeEconomica = detalhamentoAtividadeEconomica;
        this.pais = pais;
        this.funcionario = funcionario;
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

    public Boolean isTransacionaPaisesDiferentes() {
        return transacionaPaisesDiferentes;
    }

    public void setTransacionaPaisesDiferentes(Boolean transacionaPaisesDiferentes) {
        this.transacionaPaisesDiferentes = transacionaPaisesDiferentes;
    }

    public int getCdnr() {
        return cdnr;
    }

    public void setCdnr(int cdnr) {
        this.cdnr = cdnr;
    }

    public String getRelacaoDeclarante() {
        return relacaoDeclarante;
    }

    public void setRelacaoDeclarante(String relacaoDeclarante) {
        this.relacaoDeclarante = relacaoDeclarante;
    }

    public int getNumeroEmpregados() {
        return numeroEmpregados;
    }

    public void setNumeroEmpregados(int numeroEmpregados) {
        this.numeroEmpregados = numeroEmpregados;
    }

    public String getAtividadeEconomica() {
        return atividadeEconomica;
    }

    public void setAtividadeEconomica(String atividadeEconomica) {
        this.atividadeEconomica = atividadeEconomica;
    }

    public String getDetalhamentoAtividadeEconomica() {
        return detalhamentoAtividadeEconomica;
    }

    public void setDetalhamentoAtividadeEconomica(String detalhamentoAtividadeEconomica) {
        this.detalhamentoAtividadeEconomica = detalhamentoAtividadeEconomica;
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

        
}