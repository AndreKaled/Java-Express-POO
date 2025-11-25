package com.example.javaexpress.service;

import com.example.javaexpress.model.model.Encomenda;
import com.example.javaexpress.model.enums.StatusEncomenda;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * **Serviço de gerenciamento de Encomendas.**
 *
 * Classe de serviços para Encomenda, responsável por manipular a lógica de Encomenda e controla as ações entre
 * a Encomenda e o Banco de dados.
 * Esta classe é parte da camada de serviço da arquitetura do sistema e manipula as regras de negócio e a
 * comunicação com a camada de persistência.
 *
 * @author André Kaled
 * @since 21-09-2025
 * @version 1.0
 * @see com.example.javaexpress.model.model.Encomenda
 * @see com.example.javaexpress.controller.EncomendaController
 * */
@Service
public class EncomendaService {

    /** Objeto Logger para gerar logs de cada ação feita pelo serviço */
    private static final Logger logger = LoggerFactory.getLogger(EncomendaService.class);

    /** Lista para armazenar objetos de cliente em memória.*/
    private List<Encomenda> encomendas = new ArrayList<>();

    /**
     * Salva novas encomendas
     * @param encomenda Objeto Encomenda com os dados preenchidos
     * @return encomenda Objeto Encomenda com os seus dados e o CodRastreio inserido
     * */
    public Encomenda registrarEncomenda(Encomenda encomenda){
        encomendas.add(encomenda);
        logger.info("Encomenda {} criada com sucesso!", encomenda.getCodigoRastreio());
        return encomenda;
    }

    /**
     * Altera uma encomenda existente
     * @param encomenda Objeto Encomenda com os dados preenchidos
     * @return encomenda Objeto Encomenda com os seus dados novos
     * */
    public Encomenda atualizaEncomenda(Encomenda encomenda){
        int index = encomendas.indexOf(encomenda);
        encomendas.set(index, encomenda);
        logger.info("Encomenda {#{} - {}} alterado com sucesso", encomenda.getCodigoRastreio(), encomenda.getCliente().getNome());
        return encomenda;
    }

    /**
     * Lista todas as encomendas existentes (atualmente em memória)
     * @return listEncomendas uma cópia da lista de encomendas, instanciado como ArrayList
     * */
    public List<Encomenda> listarEncomendas() {
        return new ArrayList<>(encomendas);
    }

    /**
     * Busca uma determinada encomenda por codigoRastreio
     * @param codigoRastreio Um codigoRastreio de uma encomenda que se deseja buscar
     * @return encomenda retorna a encomenda buscado
     * */
    public Encomenda buscarPorCodigoRastreio(String codigoRastreio){
        logger.info("Buscando encomenda com o codigo {}",  codigoRastreio);
        return encomendas.stream().filter(encomenda -> encomenda.getCodigoRastreio().equals(codigoRastreio))
                .findFirst().
                orElseThrow(() -> new NullPointerException("Encomenda nao encontrada"));
    }

    /**
     * Apaga uma encomenda com base no codigoRastreio
     * @param codigoRastreio Um codigoRastreio de uma encomenda que se deseja apagar
     * */
    public void excluirEncomenda(String codigoRastreio){
        encomendas.removeIf(encomenda -> encomenda.getCodigoRastreio().equals(codigoRastreio));
    }

    /**
     * Atualiza o status de uma encomenda
     * @param codigoRastreio O codigo de rastreio de uma Encomenda que terá o status alterado
     * @param novoStatus O novo staus da encomenda
     * @return encomenda Objeto de Encomenda com os seus dados */
    public Encomenda atualizarStatus(String codigoRastreio, StatusEncomenda novoStatus){
        Encomenda encomenda = buscarPorCodigoRastreio(codigoRastreio);
        if(encomenda != null){
            encomenda.atualizarStatus(novoStatus);
            logger.info("Encomenda {} atualizada para {}", encomenda.getCodigoRastreio(), novoStatus);
            return encomenda;
        }
        return null;
    }

    /**
     * Atualiza o status de uma encomenda para Entregue
     * @param codigoRastreio O codigo de rastreio de uma Encomenda que terá o status alterado para Entregue
     * @return encomenda Objeto de Encomenda com os seus dados */
    public Encomenda marcarEncomendaComoEntregue(String codigoRastreio){
        Encomenda encomenda = buscarPorCodigoRastreio(codigoRastreio);
        if(encomenda != null){
            encomenda.estaEntregue();
            logger.info("Encomenda {} marcada como {}", encomenda.getCodigoRastreio(), encomenda.getStatus());
            return encomenda;
        }
        return null;
    }

    /**
     * Adiciona um evento histórico para uma encomenda
     * @param codigoRastreio O codigo de rastreio de uma Encomenda que terá um evento adicionado ao histórico
     * @param evento O evento que será adicionado
     * @return encomenda Objeto de Encomenda com os seus dados */
    public Encomenda adicionarEventoHistoricoParaEncomenda(String codigoRastreio, String evento){
        Encomenda encomenda =  buscarPorCodigoRastreio(codigoRastreio);
        if(encomenda != null){
            encomenda.adicionarHistorico(evento);
            logger.info("Evento '{}' adicionado ao historico da encomenda {}", evento, codigoRastreio);
            return encomenda;
        }
        return null;
    }

    // Dentro de com.example.javaexpress.service.EncomendaService.java

// Adicione este método à classe EncomendaService:

public String rastrear(String codigoRastreio) {
    // Aqui é onde você implementaria a lógica real de busca no seu banco de dados
    
    if (codigoRastreio != null && codigoRastreio.length() > 5) {
        // Retorno de dados simulado para o chatbot
        return "Objeto " + codigoRastreio + " em trânsito. Último status: Saiu para entrega em Manaus.";
    }
    return "Código de rastreio não encontrado ou inválido.";
}

}