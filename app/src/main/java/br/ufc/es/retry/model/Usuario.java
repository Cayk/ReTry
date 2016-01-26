package br.ufc.es.retry.model;

/**
 * Created by kerran on 24/01/16.
 */
public class Usuario {

    private int id;
    private String nome;
    private String senha;
    private String email;
    private int nivel;
    private int exp;

    public Usuario(int nome, String senha, String email, int nivel, int exp) {
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.nivel = nivel;
        this.exp = exp;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
