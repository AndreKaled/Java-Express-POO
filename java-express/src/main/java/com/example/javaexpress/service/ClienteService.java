package com.example.javaexpress.service;

import com.example.javaexpress.model.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    private List<Cliente> clientes = new ArrayList<>();
    private int nextId = 1;

    private final EncomendaService encomendaService;
    private final ReclamacaoService reclamacaoService;

    public ClienteService(EncomendaService encomendaService, ReclamacaoService reclamacaoService) {
        this.encomendaService = encomendaService;
        this.reclamacaoService = reclamacaoService;
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
        logger.info("Cliente {} adicionado com sucesso!", cliente.getNome());
    }

    public Cliente criarCliente(Cliente cliente) {
        clientes.add(cliente);
        return cliente;
    }

    public Cliente criarCliente(String nome, String email, String senha) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setSenha(senha);
        cliente.setListaEncomendas(new ArrayList<>());
        cliente.setIdCliente(nextId++);
        clientes.add(cliente);
        return cliente;
    }

    public Encomenda consultarEncomenda(String codigoRastreio) {
        logger.info("Consultando encomenda com o código {}...", codigoRastreio);
        return encomendaService.buscaPorCodigoRastreio(codigoRastreio);
    }

    public Reclamacao registrarReclamacao(Cliente cliente, Encomenda encomenda, TipoReclamacao tipoReclamacao){
        logger.info("Registrando reclamação do cliente {} ", cliente.getNome());
        return reclamacaoService.criarReclamacao(cliente, encomenda, tipoReclamacao);
    }

    public void darFeedback(Reclamacao reclamacao, Feedback feedback) {
        logger.info("O cliente {} deu o feedback {}", reclamacao.getCliente().getNome(), feedback);
        reclamacaoService.registrarFeedback(reclamacao, feedback);
    }

    public void adicionarEncomenda(Cliente cliente, Encomenda encomenda) {
        if(cliente != null && encomenda != null) {
            cliente.getListaEncomendas().add(encomenda);
            logger.info("Encomenda {} adicionada ao cliente {} ", encomenda.getCodigoRastreio(), cliente.getNome());
        }
    }
}

