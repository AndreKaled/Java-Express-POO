package com.example.javaexpress.model.model;

import com.example.javaexpress.model.util.Validate;

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

    public void adicionaEncomenda(Encomenda encomenda) {
        if (this.listaEncomendas == null) {
            this.listaEncomendas = new ArrayList<>();
        }
        listaEncomendas.add(encomenda);
    }

    public void alterarSenha(String senhaAntiga, String senhaNova){
        if (senhaAntiga.equals(getSenha()) && !senhaAntiga.equals(senhaNova)){
            setSenha(senhaNova);
        }
    }

    public boolean validarEmail(){
        return Validate.validarEmail(getEmail());
    }

    @Override
    public void exibirInformacoes(){
        super.exibirInformacoes();
        System.out.println("Encomendas = [");
        for (Encomenda encomenda : listaEncomendas) {
            System.out.println(encomenda.toString() +";\n");
        }
        System.out.println("]");
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
