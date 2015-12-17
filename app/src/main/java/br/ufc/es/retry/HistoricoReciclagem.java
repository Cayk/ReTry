package br.ufc.es.retry;

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
