package br.ufc.es.retry.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.ufc.es.retry.model.Aplicacao;

/**
 * Created by kerran on 25/01/16.
 */
public class ServiceEsqueciSenha extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String email = intent.getStringExtra("email");
        new Thread(new Runnable() {
            @Override
            public void run() {

                Aplicacao aplicacao = (Aplicacao) getApplication();
                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody = new FormEncodingBuilder()
                        .add("email", email)
                        .build();

                Request request = new Request.Builder()
                        .url("http://" + aplicacao.getIp() + aplicacao.getCaminho() + "FronteiraEsqueciSenha.php")
                        .post(requestBody)
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String resultado = response.body().string();
                    Log.i("Script", resultado);
                    Intent intent1 = new Intent("EsqueciSenha");
                    intent1.putExtra("codigo", resultado);
                    LocalBroadcastManager.getInstance(ServiceEsqueciSenha.this).sendBroadcast(intent1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return Service.START_STICKY;
    }
}
