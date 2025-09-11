package com.example.javaexpress.model.model;

public class Funcionario {
    int idFuncionario;
    String nome;
    String cargo;

    public Funcionario() {
    }

    public Funcionario(int idFuncionario, String cargo, String nome) {
        this.idFuncionario = idFuncionario;
        this.cargo = cargo;
        this.nome = nome;
    }

    /**
     * GETTERS E SETTERS
     * */

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
