package br.ufc.es.retry.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.ufc.es.retry.R;

/**
 * Created by kerran on 28/01/16.
 */
public class ListAdapterRanking extends ArrayAdapter<Usuario>{

    Context context;
    int layoutResourceId;
    Usuario dados[] = null;

    public ListAdapterRanking(Context context, int layoutResourceId, Usuario[] dados){
        super(context, layoutResourceId, dados);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.dados = dados;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View linha = convertView;
        RankingHolder rankingHolder = null;

        if(linha == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            linha = inflater.inflate(layoutResourceId, parent, false);

            rankingHolder = new RankingHolder();

            rankingHolder.txtNome = (TextView)linha.findViewById(R.id.textNome);
            rankingHolder.txtNivel = (TextView)linha.findViewById(R.id.textRanking);

            linha.setTag(rankingHolder);
        }else{
            rankingHolder = (RankingHolder)linha.getTag();
        }

        Usuario usuario = dados[position];
        rankingHolder.txtNome.setText(usuario.getNome());
        rankingHolder.txtNivel.setText(usuario.getNivel()+"");

        return linha;
    }

    static class RankingHolder{
        TextView txtNome;
        TextView txtNivel;
    }
}
