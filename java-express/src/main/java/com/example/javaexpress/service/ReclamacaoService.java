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

/**
 * Classe de serviços para Reclamacao, responsável por manipular a lógica de Reclamacao e controla as ações entre
 * a Reclamacao e o Banco de dados.
 * Esta classe é parte da camada de serviço da arquitetura do sistema e manipula as regras de negócio e a
 * comunicação com a camada de persistência.
 *
 * @author André Kaled
 * @since 21-09-2025
 * @version 1.0
 * @see com.example.javaexpress.model.model.Funcionario
 * @see com.example.javaexpress.controller.FuncionarioController
 * */
@Service
public class ReclamacaoService {
    /** Objeto Logger para gerar logs de cada ação feita pelo serviço */
    private static final Logger logger = LoggerFactory.getLogger(ReclamacaoService.class);

    /** Lista para armazenar objetos de funcionario em memória.*/
    private final List<Reclamacao> reclamacoes = new ArrayList<>();

    /**
     * Gerador de ID incremental para novos funcionarios.
     * Este valor é usado para atribuir um ID único a cada novo funcionario. (TEMPORARIO ATE CHEGAR O BD)
     * */
    private int nextId = 1;

    private final EncomendaService encomendaService;

    public ReclamacaoService(EncomendaService encomendaService) {
        this.encomendaService = encomendaService;
    }

    public Reclamacao criarReclamacao(Cliente cliente, Encomenda encomenda, TipoReclamacao tipoReclamacao) {
        logger.info("Criando nova reclamacao do cliente {} para a encomenda {}", cliente.getNome(),
                encomenda.getCodigoRastreio());
        Reclamacao reclamacao = new Reclamacao();
        reclamacao.setIdReclamacao(nextId++);
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
        reclamacao.setIdReclamacao(nextId++);
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
