package com.example.javaexpress.model.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa{

    private List<Encomenda> listaEncomendas;

    public Cliente() {
        super();
        listaEncomendas = new ArrayList<>();
    }

    public Cliente(int id, String nome, String email, String cpf, String senha, List<Encomenda> listaEncomendas) {
        super(id, nome, email, cpf, senha);
        this.listaEncomendas = listaEncomendas;
    }

    /**
     * GETTERS E SETTERS
     * */

    public List<Encomenda> getListaEncomendas() {
        return listaEncomendas;
    }

    public void setListaEncomendas(List<Encomenda> listaEncomendas) {
        this.listaEncomendas = listaEncomendas;
    }
}
