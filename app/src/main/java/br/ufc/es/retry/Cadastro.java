package br.ufc.es.retry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.ufc.es.retry.model.Validador;

public class Cadastro extends AppCompatActivity {

    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private EditText editConfSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editNome = (EditText) findViewById(R.id.nome);
        editEmail = (EditText) findViewById(R.id.email);
        editSenha = (EditText) findViewById(R.id.senha);
        editConfSenha = (EditText) findViewById(R.id.confirmarSenha);

        Button cadastrar = (Button) findViewById(R.id.criar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Validador.validateNotNull(editNome, "Preencha o campo nome");
                Validador.validateNotNull(editSenha, "Preencha o campo senha");
                Validador.validateNotNull(editConfSenha, "Preencha o campo confirmar senha");
                Validador.validateSenha(editSenha, editConfSenha, "Senhas diferentes!");
                boolean email_valido = Validador.validadeEmail(editEmail.getText().toString());

                if (!email_valido) {
                    editEmail.setError("Email inválido");
                    editEmail.setFocusable(true);
                    editEmail.requestFocus();
                } else {
                    final String nome = editNome.getEditableText().toString();
                    final String email = editEmail.getEditableText().toString();
                    final String senha = editSenha.getEditableText().toString();
                    final String confSenha = editConfSenha.getEditableText().toString();
                    final String nivel = "1";
                    final String exp = "1";

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient okHttpClient = new OkHttpClient();

                            RequestBody requestBody = new FormEncodingBuilder()
                                    .add("nome", nome)
                                    .add("senha", senha)
                                    .add("email", email)
                                    .add("nivel", nivel)
                                    .add("exp", exp)
                                    .build();

                            Request request = new Request.Builder()
                                    .url("http://10.0.2.2/webservice/Visao/FronteiraCadastrarUsuario.php")
                                    .post(requestBody)
                                    .build();

                            try {
                                Response response = okHttpClient.newCall(request).execute();

                                final String resultado = response.body().string();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(resultado.equals("-1")){
                                                editEmail.setError("Email já cadastrado");
                                                editEmail.setFocusable(true);
                                                editEmail.requestFocus();
                                            }
                                            else {
                                                Toast.makeText(Cadastro.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }
                                    });

                                Log.i("Script", "Entrou");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }
}