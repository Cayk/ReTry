package br.ufc.es.retry.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.ufc.es.retry.EsqueciSenha;
import br.ufc.es.retry.R;
import br.ufc.es.retry.model.ListAdapterRanking;
import br.ufc.es.retry.model.Usuario;
import br.ufc.es.retry.service.ServiceRanking;

/**
 * Created by user on 18/12/2015.
 */
public class Fragment2 extends Fragment{

    private static final String ARG_SECTION_NUMBER = "section_number";
    private BroadcastReceiver broadcastReceiver;
    private ListView listView1;

    public static Fragment2 newInstance(int sectionNumber) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment2,container,false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String resultado = intent.getStringExtra("listaUsuarios");
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Usuario[] usuarios = gson.fromJson(resultado, Usuario[].class);

                if(resultado.equals("-8")){
                    Toast.makeText(getActivity(), "Erro ao acessar o banco de dados.", Toast.LENGTH_SHORT).show();

                }
                else if( resultado.equals("-9")){
                    Toast.makeText(getContext(), "Erro nos parametros.", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                else{
                    ListAdapterRanking adapterRanking = new ListAdapterRanking(getActivity(), R.layout.fragment2_itens, usuarios);
                    listView1 = (ListView) getView().findViewById(R.id.listView2);

                    listView1.setAdapter(adapterRanking);
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter("Ranking"));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
    }

}


