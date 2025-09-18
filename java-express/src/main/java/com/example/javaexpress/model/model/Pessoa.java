package com.example.javaexpress.model.model;

public abstract class Pessoa {
    private int id;
    protected String nome;
    protected String email;
    private String cpf;
    protected String senha;

    public Pessoa(){

    }

    public Pessoa(int id, String nome, String email, String cpf, String senha) {
        setId(id);
        setNome(nome);
        setCpf(cpf);
        setEmail(email);
        setSenha(senha);
    }

    /**
     * GETTERS E SETTERS
     * */

    public void exibirInformacoes(){
        System.out.println("ID: " + getId());
        System.out.println("Nome: " + getNome());
        System.out.println("Email: " + getEmail());
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
