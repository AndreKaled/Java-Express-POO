package com.example.javaexpress.service;

import com.example.javaexpress.model.enums.Feedback;
import com.example.javaexpress.model.enums.StatusReclamacao;
import com.example.javaexpress.model.enums.TipoReclamacao;
import com.example.javaexpress.model.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReclamacaoService {

    private static final Logger logger = LoggerFactory.getLogger(ReclamacaoService.class);

    private final List<Reclamacao> reclamacoes = new ArrayList<>();
    private int nextReclamacaoId = 1;

    private final EncomendaService encomendaService;

    public ReclamacaoService(EncomendaService encomendaService) {
        this.encomendaService = encomendaService;
    }

    public Reclamacao criarReclamacao(Cliente cliente, Encomenda encomenda, TipoReclamacao tipoReclamacao) {
        logger.info("Criando nova reclamacao do cliente {} para a encomenda {}", cliente.getNome(),
                encomenda.getCodigoRastreio());
        Reclamacao reclamacao = new Reclamacao();
        reclamacao.setIdReclamacao(nextReclamacaoId++);
        reclamacao.setCliente(cliente);
        reclamacao.setEncomenda(encomenda);
        reclamacao.setTipoReclamacao(tipoReclamacao);
        reclamacao.setStatusReclamacao(StatusReclamacao.ABERTA);
        reclamacao.setAtualizadoEm(LocalDateTime.now());
        reclamacoes.add(reclamacao);
        logger.info("Reclamacao #{} criada com sucesso, status {}", reclamacao.getIdReclamacao(), reclamacao.getStatusReclamacao());
        return reclamacao;
    }
    public Reclamacao criarReclamacao(Cliente cliente, Encomenda encomenda, TipoReclamacao tipoReclamacao, String descricao) {
        logger.info("Criando nova reclamacao do cliente {} para a encomenda {}", cliente.getNome(),
                encomenda.getCodigoRastreio());
        Reclamacao reclamacao = new Reclamacao();
        reclamacao.setIdReclamacao(nextReclamacaoId++);
        reclamacao.setCliente(cliente);
        reclamacao.setEncomenda(encomenda);
        reclamacao.setTipoReclamacao(tipoReclamacao);
        reclamacao.setStatusReclamacao(StatusReclamacao.ABERTA);
        reclamacao.setAtualizadoEm(LocalDateTime.now());
        adicionarDescricao(reclamacao, descricao);
        reclamacoes.add(reclamacao);
        logger.info("Reclamacao #{} criada com sucesso, status {}", reclamacao.getIdReclamacao(), reclamacao.getStatusReclamacao());
        return reclamacao;
    }

    public Reclamacao buscarPorId(int id){
        logger.info("Buscando reclamacao #{}", id);
        for (Reclamacao reclamacao : reclamacoes) {
            if (reclamacao.getIdReclamacao() == id) {
                logger.info("Reclamacao #{} encontrada", reclamacao.getIdReclamacao());
                return reclamacao;
            }
        }
        logger.info("Reclamacao #{} nao encontrada", id);
        return null;
    }

    public void atualizarStatus(Reclamacao reclamacao, StatusReclamacao novoStatus){
        if(reclamacao != null){
            reclamacao.setStatusReclamacao(novoStatus);
            reclamacao.setAtualizadoEm(LocalDateTime.now());
            logger.info("Status da reclamacao #{} atualizada para {}", reclamacao.getIdReclamacao(), reclamacao.getStatusReclamacao());
        }
    }

    public Reclamacao marcarResolvida(int id){
        Reclamacao reclamacao = buscarPorId(id);
        if(reclamacao != null){
            reclamacao.marcarComoResolvida();
            logger.info("Reclamacao #{} marcada como {}", reclamacao, reclamacao.getStatusReclamacao());
        }
        return reclamacao;
    }

    public Reclamacao marcarEmAnalise(int id){
        Reclamacao reclamacao = buscarPorId(id);
        if(reclamacao != null){
            reclamacao.marcarComoEmAnalise();
            logger.info("Reclamacao #{} marcada como {}", reclamacao, reclamacao.getStatusReclamacao());
        }
        return reclamacao;
    }

    public void registrarFeedback(Reclamacao reclamacao, Feedback feedback){
        if (reclamacao != null) {
            reclamacao.registrarFeedback(feedback);
            logger.info("Feedback registrado para a reclamacao #{}.", reclamacao.getIdReclamacao());
        }
    }

    public void adicionarDescricao(Reclamacao reclamacao, String descricao){
        if(reclamacao != null){
            reclamacao.setDescricao(descricao);
            logger.info("Descricao adicionada para a reclamacao #{}: {}", reclamacao.getIdReclamacao(), reclamacao.getDescricao());
        }
    }


}
