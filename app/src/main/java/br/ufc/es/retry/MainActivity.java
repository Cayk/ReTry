package br.ufc.es.retry;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.ufc.es.retry.model.Aplicacao;
import br.ufc.es.retry.model.Validador;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    GoogleApiClient mGoogleApiClient;
    private String latitude;
    private String longitude;
    private EditText edCategoria;
    private EditText edQuantidade;
    private TextView txNome;
    private TextView txNivel;
    private TextView txExp;
    Aplicacao aplicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edCategoria = (EditText) findViewById(R.id.editCat);
        edQuantidade = (EditText) findViewById(R.id.editQtd);
        aplicacao = (Aplicacao) getApplication();

        txNome = (TextView) findViewById(R.id.textNome);
        txNivel = (TextView) findViewById(R.id.textLvl);
        txExp = (TextView) findViewById(R.id.textExp);

        txNome.setText(aplicacao.getUsuario().getNome());
        txNivel.setText("Nível: "+aplicacao.getUsuario().getNivel()+"");
        txExp.setText("Exp: "+aplicacao.getUsuario().getExp()+"/100");


        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            onStart();
        }
    }

    protected void onStart(){
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        txNome.setText(aplicacao.getUsuario().getNome());
        txNivel.setText("Nível: " + aplicacao.getUsuario().getNivel() + "");
        txExp.setText("Exp: "+aplicacao.getUsuario().getExp()+"/100");

        edCategoria.setText("");
        edQuantidade.setText("");
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if(mLastLocation != null){
                latitude = String.valueOf(mLastLocation.getLatitude());
                longitude = String.valueOf(mLastLocation.getLongitude());

                Log.i("Lat", latitude);   //-3.732705, -38.526763
                Log.i("long", longitude);
            }
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //TODO
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //TODO
    }

    public void confirmarItem(View view){
        Validador.validateNotNull(edCategoria, "Preencha o campo corretamente");
        Validador.validateNotNull(edQuantidade, "Formato inválido");
        boolean numero_valido = Validador.validateInteger(edQuantidade);
        if (!numero_valido){
            edQuantidade.setError("Formato inválido");
            edQuantidade.setFocusable(true);
            edQuantidade.requestFocus();
        }
        else{
            final String categoria = edCategoria.getEditableText().toString();
            final int quantidade = Integer.parseInt(edQuantidade.getEditableText().toString());
            final int totalExpObtida = quantidade * 2;
            final int nivel = aplicacao.getUsuario().getNivel();
            final int expSoma = aplicacao.getUsuario().getExp() + totalExpObtida;

            new Thread(new Runnable() {
                @Override
                public void run() {

                    String nivel1;
                    String exp;
                    OkHttpClient okHttpClient = new OkHttpClient();

                    String id = String.valueOf(aplicacao.getUsuario().getId());
                    String quantidade = edQuantidade.getEditableText().toString();
                    String pont = String.valueOf(totalExpObtida);

                    RequestBody requestBody = new FormEncodingBuilder()
                            .add("id_usuario",id)
                            .add("categoria", categoria)
                            .add("quantidade",quantidade)
                            .add("pontuacao_obtida", pont)
                            .add("latitude", latitude)
                            .add("longitude", longitude)
                            .build();

                    Request request = new Request.Builder()
                            .url("http://" + aplicacao.getIp() + aplicacao.getCaminho() + "FronteiraAdicionarItemReciclado.php")
                            .post(requestBody)
                            .build();

                    try {
                        Response response = okHttpClient.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if( expSoma >= 100){
                        int expSobrando = expSoma - 100;
                        aplicacao.getUsuario().setNivel(nivel + 1);
                        if(expSobrando >1){
                            aplicacao.getUsuario().setExp(expSobrando);
                        }
                        else{
                            aplicacao.getUsuario().setExp(1);
                        }

                        nivel1 = String.valueOf(aplicacao.getUsuario().getNivel());
                        exp = String.valueOf(aplicacao.getUsuario().getExp());

                        RequestBody requestBody1 = new FormEncodingBuilder()
                                .add("id", id)
                                .add("nivel", nivel1)
                                .add("exp", exp)
                                .build();

                        Request request1 = new Request.Builder()
                                .url("http://" + aplicacao.getIp() + aplicacao.getCaminho() + "FronteiraSubirDeNivel.php")
                                .post(requestBody1)
                                .build();

                        try {
                            Response response1 = okHttpClient.newCall(request1).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txNivel.setText("Nível: " + aplicacao.getUsuario().getNivel() + "");
                                txExp.setText("Exp: "+aplicacao.getUsuario().getExp()+"/100");
                                Toast.makeText(MainActivity.this,"Item reciclado com sucesso!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        aplicacao.getUsuario().setExp(expSoma);

                        nivel1 = String.valueOf(aplicacao.getUsuario().getNivel());
                        exp = String.valueOf(aplicacao.getUsuario().getExp());

                        RequestBody requestBody1 = new FormEncodingBuilder()
                                .add("id", id)
                                .add("nivel", nivel1)
                                .add("exp", exp)
                                .build();

                        Request request1 = new Request.Builder()
                                .url("http://" + aplicacao.getIp() + aplicacao.getCaminho() + "FronteiraSubirDeNivel.php")
                                .post(requestBody1)
                                .build();

                        try {
                            Response response1 = okHttpClient.newCall(request1).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txExp.setText("Exp: " + aplicacao.getUsuario().getExp() + "/100");
                                Toast.makeText(MainActivity.this,"Item reciclado com sucesso!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.editar_perfil:
                Intent intent1 = new Intent(getApplicationContext(), EditarPerfil.class);
                startActivity(intent1);
                return true;
            case R.id.rankings:
                Intent intent2 = new Intent(getApplicationContext(), Ranking.class);
                startActivity(intent2);
                return true;
            case R.id.locais:
                Intent intent3 = new Intent(getApplicationContext(), PontosDeReciclagem.class);
                startActivity(intent3);
                return true;
            case R.id.historico:
                Intent intent4 = new Intent(getApplicationContext(), HistoricoReciclagem.class);
                startActivity(intent4);
                return true;
            case R.id.sair:
                Intent intent5 = new Intent(getApplicationContext(), Login.class);
                startActivity(intent5);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
