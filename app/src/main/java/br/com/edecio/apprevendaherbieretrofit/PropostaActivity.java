package br.com.edecio.apprevendaherbieretrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PropostaActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgCarro;
    private TextView txtCarro;
    private TextView txtPreco;
    private EditText edtCliente;
    private EditText edtEmail;
    private EditText edtProposta;
    private Button btnEnviar;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposta);

        // associa objetos a suas respectivas views
        imgCarro = (ImageView)findViewById(R.id.imgCarro);
        txtCarro = (TextView)findViewById(R.id.txtCarro);
        txtPreco = (TextView)findViewById(R.id.txtPreco);
        edtCliente = (EditText)findViewById(R.id.edtCliente);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtProposta = (EditText)findViewById(R.id.edtProposta);
        btnEnviar = (Button)findViewById(R.id.btnEnviar);

        // registra "ouvinte" para o botão
        btnEnviar.setOnClickListener(this);

        // para obter dados da MainActivity
        Intent it = getIntent();

        // obtém dados passados da MainActivity
        id = it.getIntExtra("id", -1);
        String modelo = it.getStringExtra("modelo");
        String marca = it.getStringExtra("marca");
        int ano = it.getIntExtra("ano", -1);
        double preco = it.getDoubleExtra("preco", -1);

        // carrega a imagem
        Picasso.with(this).load("http://10.0.2.2/ws_herbie/fotos/"+id+".jpg").into(imgCarro);
        // exibe texto no TextView
        txtCarro.setText(marca+ " " + modelo + " " + ano);

        // define formatação do preço
        NumberFormat nfBr = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        // exibe texto no TextView
        txtPreco.setText("Preço: "+nfBr.format(preco));

    }

    @Override
    public void onClick(View v) {
        // obtém conteúdos dos EditText's
        String cliente = edtCliente.getText().toString();
        String email = edtEmail.getText().toString();
        String proposta = edtProposta.getText().toString();

        // verifica se campos de edição estão preenchidos
        // trim(): retira espaços em branco no início e final da string
        if (cliente.trim().isEmpty() || email.trim().isEmpty() || proposta.trim().isEmpty()) {
            Toast.makeText(this, "Preencha os dados", Toast.LENGTH_LONG).show();
            edtCliente.requestFocus();   // Joga o foco no edtCliente
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/ws_herbie/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CarroService service = retrofit.create(CarroService.class);

        Call<Proposta> callProposta = service.gravaProposta(cliente, email, proposta, id);

        callProposta.enqueue(new Callback<Proposta>() {

            @Override
            public void onResponse(Call<Proposta> call, Response<Proposta> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Ok! Proposta Enviada - Cód: " + response.body().getId(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Proposta> call, Throwable t) {

            }
        });



    }
}
