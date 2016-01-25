package br.ufc.es.retry.model;

/**
 * Created by kerran on 24/01/16.
 */
public class ItemReciclado {
    private int id_item;
    private int id_usuario;
    private String categoria;
    private int quantidade;
    private int pontuacao_obtida;
    private String localizacao;

    public ItemReciclado(int id_usuario, String categoria, int quantidade, int pontuacao_obtida, String localizacao) {
        this.id_usuario = id_usuario;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.pontuacao_obtida = pontuacao_obtida;
        this.localizacao = localizacao;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getPontuacao_obtida() {
        return pontuacao_obtida;
    }

    public void setPontuacao_obtida(int pontuacao_obtida) {
        this.pontuacao_obtida = pontuacao_obtida;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
