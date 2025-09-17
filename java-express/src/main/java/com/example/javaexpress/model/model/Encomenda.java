package com.example.javaexpress.model.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Encomenda {
    private String codigoRastreio;
    private String origem;
    private String destino;
    private StatusEncomenda status;
    private List<String> historicoRastreio;
    private LocalDate dataPrevistaEntrega;
    private Cliente cliente;

    public Encomenda() {
    }

    public Encomenda(String codigoRastreio, String origem, String destino, StatusEncomenda status,
                     List<String> historicoRastreio, LocalDate dataPrevistaEntrega) {
        this.codigoRastreio = codigoRastreio;
        this.origem = origem;
        this.destino = destino;
        this.status = status;
        this.historicoRastreio = historicoRastreio;
        this.dataPrevistaEntrega = dataPrevistaEntrega;
    }

    public void atualizarStatus(StatusEncomenda novoStatus) {
        if(novoStatus != null && !novoStatus.equals(this.status)) {
            setStatus(novoStatus);
            adicionarHistorico("Status alterado para: " +novoStatus.getStatus());
        }
    }

    public boolean estaEntregue(){
        return StatusEncomenda.ENTREGUE.equals(this.status);
    }

    public void adicionarHistorico(String historico){
        if(historico == null){
            historicoRastreio =  new ArrayList<>();
        }
        historicoRastreio.add(historico);
    }

    /**
     * GETTERS E SETTERS
     * */

    public void setDataPrevistaEntrega(LocalDate dataPrevistaEntrega) {
        this.dataPrevistaEntrega = dataPrevistaEntrega;
    }

    public List<String> getHistoricoRastreio() {
        return historicoRastreio;
    }

    public void setHistoricoRastreio(List<String> historicoRastreio) {
        this.historicoRastreio = historicoRastreio;
    }

    public StatusEncomenda getStatus() {
        return status;
    }

    public void setStatus(StatusEncomenda status) {
        this.status = status;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }

    public void setCodigoRastreio(String codigoRastreio) {
        this.codigoRastreio = codigoRastreio;
    }

    public LocalDate getDataPrevistaEntrega() {
        return dataPrevistaEntrega;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
