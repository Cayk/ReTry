package br.ufc.es.retry.model;

import android.app.Application;

/**
 * Created by kerran on 25/01/16.
 */
public class Aplicacao extends Application{
    private  Usuario usuario;
    //10.0.2.2 192.168.1.36 31.170.165.31
    private String ip = "retry.hol.es";
    private String caminho = "/webservice/Visao/";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
