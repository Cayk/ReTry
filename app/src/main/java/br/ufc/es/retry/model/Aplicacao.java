package br.ufc.es.retry.model;

import android.app.Application;

/**
 * Created by kerran on 25/01/16.
 */
public class Aplicacao extends Application{
    private  Usuario usuario;

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

}
