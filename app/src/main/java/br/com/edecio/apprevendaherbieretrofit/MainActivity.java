package br.com.edecio.apprevendaherbieretrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lvCarros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCarros = (ListView) findViewById(R.id.lvCarros);

        lvCarros.setOnItemClickListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/ws_herbie/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CarroService service = retrofit.create(CarroService.class);

        Call<List<Carro>> carros = service.getCarros();

        carros.enqueue(new Callback<List<Carro>>() {

            @Override
            public void onResponse(Call<List<Carro>> call,
                                   Response<List<Carro>> response) {
                if (response.isSuccessful()) {

                    List<Carro> lista = response.body();

                    CarrosAdapter adapter = new CarrosAdapter(
                            getApplicationContext(), lista, "http://10.0.2.2/ws_herbie/fotos/");

                    lvCarros.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Carro>> call, Throwable t) {

            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Carro carro = (Carro)parent.getItemAtPosition(position);

//        Toast.makeText(this, "Você clicou no carro " + carro.getModelo(), Toast.LENGTH_LONG).show();

        Intent it = new Intent(this, PropostaActivity.class);
        it.putExtra("id", carro.getId());
        it.putExtra("modelo", carro.getModelo());
        it.putExtra("marca", carro.getMarca());
        it.putExtra("ano", carro.getAno());
        it.putExtra("preco", carro.getPreco());
        startActivity(it);
    }
}
