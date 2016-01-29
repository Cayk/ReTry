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

/**
 * Created by kerran on 28/01/16.
 */
public class ServiceRanking extends Service{

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody = new FormEncodingBuilder()
                        .add("busca", "busca")
                        .build();

                Request request = new Request.Builder()
                        .url("http://10.0.2.2/webservice/Visao/FronteiraBuscarTodosUsuarios.php")
                        .post(requestBody)
                        .build();

                try {
                    Response response = okHttpClient.newCall(request).execute();

                    String resultado = response.body().string();

                    Log.i("Script", resultado);

                    Intent intent1 = new Intent("Ranking");
                    intent1.putExtra("listaUsuarios", resultado);
                    LocalBroadcastManager.getInstance(ServiceRanking.this).sendBroadcast(intent1);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        return Service.START_STICKY;
    }
}
