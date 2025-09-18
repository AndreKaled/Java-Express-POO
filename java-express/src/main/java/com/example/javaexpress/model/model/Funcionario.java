package com.example.javaexpress.model.model;

public class Funcionario extends Pessoa {
    private String cargo;

    public Funcionario() {
        super();
        this.cargo = "Sem cargo";
    }

    public Funcionario(int id, String nome, String email, String cpf, String senha, String cargo) {
        super(id, nome, email, cpf, senha);
        this.cargo = cargo;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Cargo: " + this.cargo);
    }

    /**
     * GETTERS E SETTERS
     * */

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
