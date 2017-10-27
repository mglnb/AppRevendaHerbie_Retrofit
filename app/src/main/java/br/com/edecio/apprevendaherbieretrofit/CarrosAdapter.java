package br.com.edecio.apprevendaherbieretrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by edecio on 19/10/2017.
 */

public class CarrosAdapter extends BaseAdapter {

    private Context ctx;
    private List<Carro> carros;
    private String pathFoto;

    public CarrosAdapter(Context ctx, List<Carro> carros, String pathFoto) {
        this.ctx = ctx;
        this.carros = carros;
        this.pathFoto = pathFoto;
    }

    @Override
    public int getCount() {
        return carros.size();
    }

    @Override
    public Object getItem(int position) {
        return carros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Carro carro = carros.get(position);

        View linha = LayoutInflater.from(ctx).inflate(R.layout.item_carro, null);

        ImageView imgFoto = (ImageView)linha.findViewById(R.id.imgFoto);
        TextView txtModeloAno = (TextView)linha.findViewById(R.id.txtModeloAno);
        TextView txtMarca = (TextView)linha.findViewById(R.id.txtMarca);
        TextView txtPreco = (TextView)linha.findViewById(R.id.txtPreco);

        Picasso.with(ctx).load(pathFoto+carro.getId()+".jpg").into(imgFoto);
        txtModeloAno.setText(carro.getModelo()+ " - " + carro.getAno());
        txtMarca.setText(carro.getMarca());

        NumberFormat nfBr = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        txtPreco.setText(nfBr.format(carro.getPreco()));

        return linha;
    }
}
