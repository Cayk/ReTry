package br.ufc.es.retry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.ufc.es.retry.list.Group;
import br.ufc.es.retry.list.ListaHistoricoReciclagem;
import br.ufc.es.retry.model.Aplicacao;
import br.ufc.es.retry.model.ItemReciclado;

public class HistoricoReciclagem extends AppCompatActivity {

    SparseArray<Group> groups = new SparseArray<>();
    private Aplicacao aplicacao;
    private TextView textView;
    private ExpandableListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_reciclagem);
        aplicacao = (Aplicacao) getApplication();
        textView = (TextView) findViewById(R.id.textView18);
        createData();
        list = (ExpandableListView) findViewById(R.id.listView);
        ListaHistoricoReciclagem adapter = new ListaHistoricoReciclagem(this, groups);
        list.setAdapter(adapter);
    }

    private void createData() {

        final String id = String.valueOf(aplicacao.getUsuario().getId());
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody = new FormEncodingBuilder()
                        .add("id_usuario", id)
                        .build();

                Request request = new Request.Builder()
                        .url("http://" + aplicacao.getIp() + aplicacao.getCaminho() + "FronteiraListarTodosItensReciclados.php")
                        .post(requestBody)
                        .build();

                try {
                    Response response = okHttpClient.newCall(request).execute();

                    final String resultado = response.body().string();
                    final String resultado1 = resultado;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(resultado1.equals("-6")){
                                textView.setText("Erro ao acessar o banco de dados, tente novamente.");
                            }
                            else if(!resultado1.equals("-5")){
                                GsonBuilder builder = new GsonBuilder();
                                Gson gson = builder.create();
                                ItemReciclado[] itemReciclado = gson.fromJson(resultado, ItemReciclado[].class);
                                textView.setVisibility(View.GONE);
                                for(int i=0; i< itemReciclado.length; i++){
                                    Group group = new Group(itemReciclado[i].getCategoria());
                                    group.children.add("Categoria: "+itemReciclado[i].getCategoria()+"");
                                    group.children.add("Quantidade: "+String.valueOf(itemReciclado[i].getQuantidade())+"");
                                    group.children.add("Pontuação obtida: "+String.valueOf(itemReciclado[i].getPontuacao_obtida())+"");
                                    groups.append(i, group);
                                }
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void limparHist(View view){
        final String id = String.valueOf(aplicacao.getUsuario().getId());
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody = new FormEncodingBuilder()
                        .add("id_usuario", id)
                        .build();

                Request request = new Request.Builder()
                        .url("http://" + aplicacao.getIp() + aplicacao.getCaminho() + "FronteiraDeletarTodosItens.php")
                        .post(requestBody)
                        .build();

                try {
                    Response response = okHttpClient.newCall(request).execute();

                    final String resultado = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(resultado.equals("-6")){
                                Toast.makeText(HistoricoReciclagem.this, "Erro nos parametros", Toast.LENGTH_SHORT).show();
                            }
                            else if(resultado.equals("-7")){
                                Toast.makeText(HistoricoReciclagem.this, "Erro ao deletar. Tente novamente", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                list.setVisibility(View.GONE);
                                textView.setText("Nenhum item reciclado!");
                                textView.setVisibility(View.VISIBLE);
                                Toast.makeText(HistoricoReciclagem.this, "Itens deletados com sucesso", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
