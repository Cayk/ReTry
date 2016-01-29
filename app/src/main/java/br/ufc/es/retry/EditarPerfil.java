package br.ufc.es.retry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.ufc.es.retry.model.Aplicacao;
import br.ufc.es.retry.model.Validador;

public class EditarPerfil extends AppCompatActivity {

    private EditText edNome;
    private EditText edEmail;
    private EditText edSenha;
    private EditText edConfSenha;
    Aplicacao aplicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        edNome = (EditText) findViewById(R.id.nome);
        edEmail = (EditText) findViewById(R.id.email);
        edSenha = (EditText) findViewById(R.id.senha);
        edConfSenha = (EditText) findViewById(R.id.confirmarSenha);

        aplicacao = (Aplicacao) getApplication();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editar_perfil, menu);
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
                finish();
                return true;
            case R.id.editar_perfil:
                Intent intent1 = new Intent(getApplicationContext(), EditarPerfil.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.rankings:
                Intent intent2 = new Intent(getApplicationContext(), Ranking.class);
                startActivity(intent2);
                finish();
                return true;
            case R.id.locais:
                Intent intent3 = new Intent(getApplicationContext(), PontosDeReciclagem.class);
                startActivity(intent3);
                finish();
                return true;
            case R.id.historico:
                Intent intent4 = new Intent(getApplicationContext(), HistoricoReciclagem.class);
                startActivity(intent4);
                finish();
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

    public void editarPerfil(View view){

        Validador.validateNotNull(edNome, "Preencha corretamente");
        Validador.validateNotNull(edSenha, "Senha inválida");
        Validador.validateNotNull(edConfSenha, "Senha inválida");
        Validador.validateSenha(edSenha, edConfSenha, "Senhas diferentes");

        boolean email_valido = Validador.validadeEmail(edEmail.getText().toString());

        if(!email_valido){
            edEmail.setError("Email inválido");
            edEmail.setFocusable(true);
            edEmail.requestFocus();
        }
        else{
            final String nome = edNome.getEditableText().toString();
            final String email = edEmail.getEditableText().toString();
            final String senha = edSenha.getEditableText().toString();
            final String id = String.valueOf(aplicacao.getUsuario().getId());

            aplicacao.getUsuario().setNome(nome);
            aplicacao.getUsuario().setEmail(email);
            aplicacao.getUsuario().setSenha(senha);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient okHttpClient = new OkHttpClient();

                    RequestBody requestBody = new FormEncodingBuilder()
                            .add("id", id)
                            .add("nome", nome)
                            .add("senha", senha)
                            .add("email", email)
                            .build();

                    Request request = new Request.Builder()
                            .url("http://10.0.2.2/webservice/Visao/FronteiraAtualizarUsuario.php")
                            .post(requestBody)
                            .build();

                    try {
                        Response response = okHttpClient.newCall(request).execute();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(EditarPerfil.this, "Perfil Atualizado com sucesso!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
