package com.example.javaexpress.model.model;

import java.time.LocalDate;
import java.util.List;

public class Encomenda {
    String codigoRastreio;
    String origem;
    String destino;
    StatusEncomenda status;
    List<String> historicoRastreio;
    LocalDate dataPrevistaEntrega;
    Cliente cliente;

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
