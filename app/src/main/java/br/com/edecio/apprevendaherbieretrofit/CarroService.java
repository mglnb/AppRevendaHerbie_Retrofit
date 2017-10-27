package br.com.edecio.apprevendaherbieretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by edecio on 19/10/2017.
 */

public interface CarroService {

    // nome do WebService que irá retornar a lista de dados
    @GET("lista_carros.php")
    Call<List<Carro>> getCarros();

    // no POST deve-se colocar o nome do WebService PHP que irá receber os dados
    // cada campo do WS de inclusão deve ser indicado no Field
    // gravaProposta é o nome dado para a chamada do método
    @FormUrlEncoded
    @POST("insere_proposta.php")
    Call<Proposta> gravaProposta(@Field("cliente") String cliente,
                                 @Field("email") String email,
                                 @Field("proposta") String proposta,
                                 @Field("carro_id") int carro_id);
}
