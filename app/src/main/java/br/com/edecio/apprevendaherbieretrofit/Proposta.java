package br.com.edecio.apprevendaherbieretrofit;

/**
 * Created by edecio on 26/10/2017.
 */

public class Proposta {
    private int id;
    private String cliente;
    private String email;
    private String proposta;
    private int carro_id;

    public Proposta(int id, String cliente, String email, String proposta, int carro_id) {
        this.id = id;
        this.cliente = cliente;
        this.email = email;
        this.proposta = proposta;
        this.carro_id = carro_id;
    }

    public int getId() {
        return id;
    }
}
