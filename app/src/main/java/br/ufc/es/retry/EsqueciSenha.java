package br.ufc.es.retry;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufc.es.retry.model.Validador;
import br.ufc.es.retry.service.ServiceEsqueciSenha;

public class EsqueciSenha extends AppCompatActivity {

    private EditText editSenha;
    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editSenha = (EditText) findViewById(R.id.email);

        Button bt = (Button) findViewById(R.id.novaSenha);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean email_valido = Validador.validadeEmail(editSenha.getText().toString());
                if (!email_valido) {
                    editSenha.setError("Email inválido");
                    editSenha.setFocusable(true);
                    editSenha.requestFocus();
                } else {
                    Log.i("Script", "onClickEsqueci");
                    final String email = editSenha.getEditableText().toString();

                    Intent intent = new Intent(EsqueciSenha.this, ServiceEsqueciSenha.class);
                    intent.putExtra("email", email);
                    startService(intent);
                }
            }
        });
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               String resultado = intent.getStringExtra("codigo");

                if(resultado.equals("-2")){
                    Toast.makeText(EsqueciSenha.this, "Erro! Tente novamente", Toast.LENGTH_SHORT).show();

                }
                else if( resultado.equals("2")){
                    Toast.makeText(EsqueciSenha.this, "Nova senha enviada!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("EsqueciSenha"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
