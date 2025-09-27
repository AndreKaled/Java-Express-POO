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
     */
    private int nextId = 1;

    private final EncomendaService encomendaService;

    public ReclamacaoService(EncomendaService encomendaService) {
        this.encomendaService = encomendaService;
    }

    /**
     * Salva as novas reclamações no sistema
     * @param cliente Objeto de Cliente do qual vem a reclamação
     * @param encomenda Objeto de Encomenda da qual encomenda será a reclamacao
     * @param tipoReclamacao Enum do tipo de reclamacao que será registrado
     * @return reclamacao Objeto de Reclamacao com os seus dados */
    public Reclamacao registrarReclamacao(Cliente cliente, Encomenda encomenda, TipoReclamacao tipoReclamacao) {
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

    /**
     * Salva as novas reclamações no sistema
     * @param cliente Objeto de Cliente do qual vem a reclamação
     * @param encomenda Objeto de Encomenda da qual encomenda será a reclamacao
     * @param tipoReclamacao Enum do tipo de reclamacao que será registrado
     * @param descricao String de uma breve descrição sobre a reclamação
     * @return reclamacao Objeto de Reclamacao com os seus dados */
    public Reclamacao registrarReclamacao(Cliente cliente, Encomenda encomenda, TipoReclamacao tipoReclamacao, String descricao) {
        Reclamacao reclamacao = new Reclamacao();
        reclamacao.setIdReclamacao(nextId++);
        reclamacao.setCliente(cliente);
        reclamacao.setEncomenda(encomenda);
        reclamacao.setTipoReclamacao(tipoReclamacao);
        reclamacao.setStatusReclamacao(StatusReclamacao.ABERTA);
        reclamacao.setAtualizadoEm(LocalDateTime.now());
        adicionarDescricaoParaReclamacao(reclamacao, descricao);
        reclamacoes.add(reclamacao);
        logger.info("Reclamacao #{} criada com sucesso, status {}", reclamacao.getIdReclamacao(), reclamacao.getStatusReclamacao());
        return reclamacao;
    }

    /**
     * Busca por uma reclamação no sistema
     * @param id Id de uma reclamação que se deseja encontrar
     * @return reclamacao Objeto de Reclamacao com os seus dados */
    public Reclamacao buscarReclamacaoPorId(int id){
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

    /**
     * Atualiza o status de uma reclamação
     * @param reclamacao Objeto de Reclamação do qual vai atualizar o status
     * @param novoStatus Enum de qual será o novo status da reclamação */
    public void atualizarStatusReclamacao(Reclamacao reclamacao, StatusReclamacao novoStatus){
        if(reclamacao != null){
            reclamacao.setStatusReclamacao(novoStatus);
            reclamacao.setAtualizadoEm(LocalDateTime.now());
            logger.info("Status da reclamacao #{} atualizada para {}", reclamacao.getIdReclamacao(), reclamacao.getStatusReclamacao());
        }
    }

    /**
     * Marca uma reclamação como resolvida
     * @param id Id de uma reclamação que será marcado como Resolvida
     * @return reclamacao Objeto de Reclamacao com os seus dados */
    public Reclamacao marcarReclamacaoResolvida(int id){
        Reclamacao reclamacao = buscarReclamacaoPorId(id);
        if(reclamacao != null){
            reclamacao.marcarComoResolvida();
            logger.info("Reclamacao #{} marcada como {}", reclamacao, reclamacao.getStatusReclamacao());
        }
        return reclamacao;
    }

    /**
     * Marca uma reclamação como em análise
     * @param id Id de uma reclamação que será marcado como Em Análise
     * @return reclamacao Objeto de Reclamacao com os seus dados */
    public Reclamacao marcarReclamacaoEmAnalise(int id){
        Reclamacao reclamacao = buscarReclamacaoPorId(id);
        if(reclamacao != null){
            reclamacao.marcarComoEmAnalise();
            logger.info("Reclamacao #{} marcada como {}", reclamacao, reclamacao.getStatusReclamacao());
        }
        return reclamacao;
    }

    /**
     * Registra um Feedback sobre o atendimento de uma reclamação
     * @param reclamacao Objeto de Reclamação que receberá o feedback
     * @param feedback  Enum de qual o feedback sobre o atendimento*/
    public void registrarFeedback(Reclamacao reclamacao, Feedback feedback){
        if (reclamacao != null) {
            reclamacao.registrarFeedback(feedback);
            logger.info("Feedback registrado para a reclamacao #{}.", reclamacao.getIdReclamacao());
        }
    }

    /**
     * Adiciona uma descrição para uma reclamação
     * @param reclamacao Objeto de Reclamação que terá uma descrição
     * @param descricao String com a descrição */
    public void adicionarDescricaoParaReclamacao(Reclamacao reclamacao, String descricao){
        if(reclamacao != null){
            reclamacao.setDescricao(descricao);
            logger.info("Descricao adicionada para a reclamacao #{}: {}", reclamacao.getIdReclamacao(), reclamacao.getDescricao());
        }
    }


}
