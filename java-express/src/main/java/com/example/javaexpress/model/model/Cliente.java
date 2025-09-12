package com.example.javaexpress.model.model;

import java.util.List;

public class Cliente {
    private int idCliente;
    private String email;
    private String nome;
    private String senha;
    private List<Encomenda> listaEncomendas;

    public Cliente() {
    }

    public Cliente(int idCliente, String email, String nome, String senha, List<Encomenda> listaEncomendas) {
        this.idCliente = idCliente;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.listaEncomendas = listaEncomendas;
    }

    /**
     * GETTERS E SETTERS
     * */

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Encomenda> getListaEncomendas() {
        return listaEncomendas;
    }

    public void setListaEncomendas(List<Encomenda> listaEncomendas) {
        this.listaEncomendas = listaEncomendas;
    }
}
