package com.example.javaexpress.model.model;

import java.time.LocalDateTime;

public class Reclamacao {
    private int idReclamacao;
    private Encomenda encomenda;
    private Cliente cliente;
    private String descricao;
    private StatusReclamacao statusReclamacao;
    private TipoReclamacao tipoReclamacao;
    private LocalDateTime atualizadoEm;
    private Feedback feedback;

    public Reclamacao() {
    }

    public Reclamacao(int idReclamacao, Encomenda encomenda, Cliente cliente, String descricao, StatusReclamacao statusReclamacao, TipoReclamacao tipoReclamacao, LocalDateTime atualizadoEm, Feedback feedback) {
        this.idReclamacao = idReclamacao;
        this.encomenda = encomenda;
        this.cliente = cliente;
        this.descricao = descricao;
        this.statusReclamacao = statusReclamacao;
        this.tipoReclamacao = tipoReclamacao;
        this.atualizadoEm = atualizadoEm;
        this.feedback = feedback;
    }

    public void marcarComoEmAnalise(){
        if(StatusReclamacao.ABERTA.equals(this.statusReclamacao))
            setStatusReclamacao(StatusReclamacao.EM_ANALISE);
    }

    public void marcarComoResolvida(){
        if(StatusReclamacao.EM_ANALISE.equals(this.statusReclamacao))
            setStatusReclamacao(StatusReclamacao.RESOLVIDA);
    }

    public void registrarFeedback(Feedback feedback){
        if(feedback != null)
            setFeedback(feedback);
    }

    /**
     * GETTERS E SETTERS
     * */

    public int getIdReclamacao() {
        return idReclamacao;
    }

    public void setIdReclamacao(int idReclamacao) {
        this.idReclamacao = idReclamacao;
    }

    public Encomenda getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusReclamacao getStatusReclamacao() {
        return statusReclamacao;
    }

    public void setStatusReclamacao(StatusReclamacao statusReclamacao) {
        this.statusReclamacao = statusReclamacao;
    }

    public TipoReclamacao getTipoReclamacao() {
        return tipoReclamacao;
    }

    public void setTipoReclamacao(TipoReclamacao tipoReclamacao) {
        this.tipoReclamacao = tipoReclamacao;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}
