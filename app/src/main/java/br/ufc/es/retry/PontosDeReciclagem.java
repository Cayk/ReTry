package br.ufc.es.retry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class PontosDeReciclagem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontos_de_reciclagem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pontos_de_reciclagem, menu);
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
}
