package br.ufc.es.retry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

public class HistoricoReciclagem extends AppCompatActivity {

    SparseArray<Group> groups = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_reciclagem);
        createData();
        ExpandableListView list = (ExpandableListView) findViewById(R.id.listView);
        ListaHistoricoReciclagem adapter = new ListaHistoricoReciclagem(this, groups);
        list.setAdapter(adapter);
    }

    private void createData() {
        int k = 1;
        for (int j = 0; j < 5; j++) {
            k = j * j + k;
            Group group = new Group("Item de reciclagem " + j);
            group.children.add("Tipo do item reciclado");
            group.children.add("Quantidade de itens: " +j);
            group.children.add("Pontuação obtida: " +k);
            groups.append(j, group);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historico_reciclagem, menu);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
