package com.example.javaexpress.service;

import com.example.javaexpress.model.model.Encomenda;
import com.example.javaexpress.model.model.StatusEncomenda;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EncomendaService {

    private static final Logger logger = LoggerFactory.getLogger(EncomendaService.class);

    private List<Encomenda> encomendas = new ArrayList<>();

    public Encomenda criarEncomenda(String codigoRastreio, String origem,
                                    String destino,
                                    LocalDate dataPrevistaEntrega){
        Encomenda encomenda = new Encomenda(codigoRastreio, origem, destino,
                StatusEncomenda.ENVIADO, new ArrayList<>(), dataPrevistaEntrega);
        encomendas.add(encomenda);
        logger.info("Encomenda {} criada com sucesso!", encomenda.getCodigoRastreio());
        return encomenda;
    }

    public Encomenda buscaPorCodigoRastreio(String codigoRastreio){
        logger.info("Buscando encomenda com o codigo {}",  codigoRastreio);
        for (Encomenda encomenda : encomendas){
            if(encomenda.getCodigoRastreio().equals(codigoRastreio)){
                return encomenda;
            }
        }
        logger.warn("Encomenda com o código {} não encontrada!", codigoRastreio);
        return null;
    }

    public Encomenda atualizaStatus(String codigoRastreio, StatusEncomenda novoStatus){
        Encomenda encomenda = buscaPorCodigoRastreio(codigoRastreio);
        if(encomenda != null){
            encomenda.setStatus(novoStatus);
            logger.info("Encomenda {} atualizada para {}", encomenda.getCodigoRastreio(), novoStatus);
            return encomenda;
        }
        return null;
    }

    public Encomenda adiocionarEventoHistorico(String codigoRastreio, String evento){
        Encomenda encomenda =  buscaPorCodigoRastreio(codigoRastreio);
        if(encomenda != null){
            encomenda.getHistoricoRastreio().add(evento);
            logger.info("Evento '{}' adicionado ao historico da encomenda {}", evento, codigoRastreio);
            return encomenda;
        }
        return null;
    }

}