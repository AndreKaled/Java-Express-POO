package com.example.javaexpress.service;

import com.example.javaexpress.model.enums.Feedback;
import com.example.javaexpress.model.enums.TipoReclamacao;
import com.example.javaexpress.model.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe de serviços para Cliente, responsável por manipular a lógica de Clientes e controla as ações entre
 * o Cliente e o Banco de dados.
 * Esta classe é parte da camada de serviço da arquitetura do sistema e manipula as regras de negócio e a
 * comunicação com a camada de persistência.
 *
 * @author André Kaled
 * @since 19-09-2025
 * @version 1.0
 * @see com.example.javaexpress.model.model.Cliente
 * @see com.example.javaexpress.controller.ClienteController
 * */

@Service
public class ClienteService {

    /** Objeto Logger para gerar logs de cada ação feita pelo serviço */
    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    /**
     * Lista para armazenar objetos de cliente em memória.
     * essa lista simula um banco de dados temporário
     * */
    private List<Cliente> clientes = new ArrayList<>();
    /**
     * Gerador de ID incremental para novos clientes.
     * Este valor é usado para atribuir um ID único a cada novo cliente. (TEMPORARIO ATE CHEGAR O BD)
     * */
    private int nextId = 1;

    private final EncomendaService encomendaService;
    private final ReclamacaoService reclamacaoService;

    /**
     * Construtor da classe, o objeto controla ações entre o Cliente e o Banco de dados ou mudanças de estados de Cliente
     * @param encomendaService Objeto de Serviço de Encomendas
     * @param reclamacaoService Objeto de Serviço de Reclamação
     * */
    public ClienteService(EncomendaService encomendaService, ReclamacaoService reclamacaoService) {
        this.encomendaService = encomendaService;
        this.reclamacaoService = reclamacaoService;
    }

    /**
     * Salva novos clientes e altera clientes existentes a partir do ID
     * @param cliente Objeto Cliente com os dados preenchidos (espero, se não só salva o ID)
     * @return Cliente Objeto Cliente com os seus dados e o ID inserido
     * */
    public Cliente save(Cliente cliente) {
        if(cliente.getId() == 0){
            cliente.setId(nextId++);
            clientes.add(cliente);
            logger.info("Cliente {#{} - {}} salvo com sucesso", cliente.getId(), cliente.getNome());
        }else{
            int index = clientes.indexOf(cliente);
            clientes.set(index, cliente);
            logger.info("Cliente {#{} - {}} alterado com sucesso", cliente.getId(), cliente.getNome());
        }
        return cliente;
    }


    /**
     * Lista todos os clientes existentes (atualmente em memória)
     * @return List<Cliente> uma cópia da lista de clientes, instanciado como ArrayList
     * */
    public List<Cliente> findAll() {
        return new ArrayList<>(clientes);
    }

    /**
     * Busca um determinado cliente por ID
     * @param id Um ID de um cliente que se deseja buscar
     * @return Cliente retorna o cliente buscado
     * */
    public Cliente findById(int id) {
        return clientes.stream().filter(cliente -> cliente.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Cliente nao encontrado"));
    }

    /**
     * Apaga um cliente com base no ID
     * @param id Um ID de um cliente que se deseja apagar
     * */
    public void deleteById(int id){
        clientes.removeIf(cliente -> cliente.getId() == id);
    }

    // outras coisas coisadas
    public Encomenda consultarEncomenda(String codigoRastreio) {
        logger.info("Consultando encomenda com o código {}...", codigoRastreio);
        return encomendaService.findByCodigoRastreio(codigoRastreio);
    }

    public Reclamacao registrarReclamacao(Cliente cliente, Encomenda encomenda, TipoReclamacao tipoReclamacao){
        logger.info("Registrando reclamação do cliente {} ", cliente.getNome());
        return reclamacaoService.criarReclamacao(cliente, encomenda, tipoReclamacao);
    }

    public void darFeedback(Reclamacao reclamacao, Feedback feedback) {
        logger.info("O cliente {} deu o feedback {}", reclamacao.getCliente().getNome(), feedback);
        reclamacaoService.registrarFeedback(reclamacao, feedback);
    }

    /**
     * Adiciona uma encomenda para a lista de encomendas do cliente
     * @param cliente Um objeto Cliente do qual será adicionado a encomenda
     * @param encomenda A encomenda que será adicionada à lista de encomendas do cliente
     * */
    public void adicionarEncomenda(Cliente cliente, Encomenda encomenda) {
        if(cliente != null && encomenda != null) {
            cliente.adicionaEncomenda(encomenda);
            logger.info("Encomenda {} adicionada ao cliente {} ", encomenda.getCodigoRastreio(), cliente.getNome());
        }
    }
}

