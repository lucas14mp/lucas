package br.com.bb.cbe.Bean;
/**
 *
 * @author T1091501
 */

public class Funcionario {
    
    private String chave;
    private String nome;
    private Dependencia dependencia;

    public Funcionario(){
        
    }
    public Funcionario(String chave, String nome, Dependencia dependencia) {
        this.chave = chave;
        this.nome = nome;
        this.dependencia = dependencia;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }
}
