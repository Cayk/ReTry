package br.ufc.es.retry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.ufc.es.retry.model.Aplicacao;
import br.ufc.es.retry.model.Usuario;
import br.ufc.es.retry.model.Validador;

public class Login extends AppCompatActivity {

    private EditText edEmail;
    private EditText edSenha;
    Aplicacao aplicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("");
        aplicacao = (Aplicacao) getApplication();
        edEmail = (EditText) findViewById(R.id.email);
        edSenha = (EditText) findViewById(R.id.senha);
        Button bt = (Button) findViewById(R.id.entrar);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Validador.validateNotNull(edSenha, "Preencha o campo senha");

                boolean email_valido = Validador.validadeEmail(edEmail.getText().toString());

                if(!email_valido){
                    edEmail.setError("Email inválido!");
                    edEmail.setFocusable(true);
                    edEmail.requestFocus();

                }
                else{

                    final String email = edEmail.getEditableText().toString();
                    final String senha = edSenha.getEditableText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient okHttpClient = new OkHttpClient();

                            RequestBody requestBody = new FormEncodingBuilder()
                                    .add("email", email)
                                    .add("senha", senha)
                                    .build();

                            Request request = new Request.Builder()
                                    .url("http://" + aplicacao.getIp() + aplicacao.getCaminho() + "FronteiraBuscarUsuario.php")
                                    .post(requestBody)
                                    .build();

                            try {
                                Response response = okHttpClient.newCall(request).execute();

                                final String resultado = response.body().string();

                                Log.i("Script", resultado+"");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (resultado.equals("-3")) {
                                            Toast.makeText(Login.this, "Erro nos parametros! Tente novamente", Toast.LENGTH_SHORT).show();

                                        }else if(resultado.equals("-8")){
                                            Toast.makeText(Login.this, "Erro! Tente novamente", Toast.LENGTH_SHORT).show();
                                        } else if(resultado.equals("-9")){
                                            Toast.makeText(Login.this, "Preencha os campos corretamente", Toast.LENGTH_SHORT).show();
                                        }
                                        else {

                                            GsonBuilder builder = new GsonBuilder();
                                            Gson gson = builder.create();
                                            Usuario usuario = gson.fromJson(resultado, Usuario.class);

                                            Aplicacao aplicacao = (Aplicacao) getApplication();
                                            aplicacao.setUsuario(usuario);
                                            carregarTelaPrincipal();
                                            finish();
                                        }
                                    }
                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }


    public void carregarTelaPrincipal(){
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void carregarTelaEsqueciSenha(View view){
        Intent intent;
        intent = new Intent(this, EsqueciSenha.class);
        startActivity(intent);
    }

    public void carregarTelaCadastrar(View view){
        Intent intent;
        intent = new Intent(this, Cadastro.class);
        startActivity(intent);
    }
}
