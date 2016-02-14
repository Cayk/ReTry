package br.ufc.es.retry.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.ufc.es.retry.model.Aplicacao;

public class ServicePontosMaps extends Service {
    public ServicePontosMaps() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();

                Aplicacao aplicacao = (Aplicacao) getApplication();
                String id_usuario = String.valueOf(aplicacao.getUsuario().getId());

                Log.i("id", id_usuario);
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("id_usuario", id_usuario)
                        .build();

                Request request = new Request.Builder()
                        .url("http://" + aplicacao.getIp() + aplicacao.getCaminho() + "FronteiraListarTodosItensReciclados.php")
                        .post(requestBody)
                        .build();

                try {
                    Response response = okHttpClient.newCall(request).execute();

                    String resultado = response.body().string();

                    Log.i("ItensRes", resultado);

                    Intent intent1 = new Intent("Pontos");
                    intent1.putExtra("PontosMapa", resultado);
                    LocalBroadcastManager.getInstance(ServicePontosMaps.this).sendBroadcast(intent1);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        return Service.START_STICKY;
    }
}
