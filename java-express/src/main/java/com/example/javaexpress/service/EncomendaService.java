package com.example.javaexpress.service;

import com.example.javaexpress.model.model.Cliente;
import com.example.javaexpress.model.model.Encomenda;
import com.example.javaexpress.model.enums.StatusEncomenda;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
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
     * Salva novas encomendas e altera encomendas existentes a partir do CodRastreio
     * @param encomenda Objeto Encomenda com os dados preenchidos (exceto o CodRastreio)
     * @return encomenda Objeto Encomenda com os seus dados e o CodRastreio inserido
     * */
    public Encomenda save(Encomenda encomenda){
        if(encomenda.getCodigoRastreio() == null){
            encomendas.add(encomenda);
            logger.info("Encomenda {} criada com sucesso!", encomenda.getCodigoRastreio());
        }else{
            int index = encomendas.indexOf(encomenda);
            encomendas.set(index, encomenda);
            logger.info("Encomenda {#{} - {}} alterado com sucesso", encomenda.getCodigoRastreio(), encomenda.getCliente().getNome());
        }
        return encomenda;
    }

    /**
     * Lista todas as encomendas existentes (atualmente em memória)
     * @return listEncomendas uma cópia da lista de encomendas, instanciado como ArrayList
     * */
    public List<Encomenda> findAll() {
        return new ArrayList<>(encomendas);
    }

    /**
     * Busca uma determinada encomenda por codigoRastreio
     * @param codigoRastreio Um codigoRastreio de uma encomenda que se deseja buscar
     * @return encomenda retorna a encomenda buscado
     * */
    public Encomenda findByCodigoRastreio(String codigoRastreio){
        logger.info("Buscando encomenda com o codigo {}",  codigoRastreio);
        return encomendas.stream().filter(encomenda -> encomenda.getCodigoRastreio().equals(codigoRastreio))
                .findFirst().
                orElseThrow(() -> new NullPointerException("Encomenda nao encontrada"));
    }


    /**
     * Apaga uma encomenda com base no codigoRastreio
     * @param codigoRastreio Um codigoRastreio de uma encomenda que se deseja apagar
     * */
    public void deleteByCodigoRastreio(String codigoRastreio){
        encomendas.removeIf(encomenda -> encomenda.getCodigoRastreio().equals(codigoRastreio));
    }

    //coisas coisadas
    public Encomenda atualizaStatus(String codigoRastreio, StatusEncomenda novoStatus){
        Encomenda encomenda = findByCodigoRastreio(codigoRastreio);
        if(encomenda != null){
            encomenda.atualizarStatus(novoStatus);
            logger.info("Encomenda {} atualizada para {}", encomenda.getCodigoRastreio(), novoStatus);
            return encomenda;
        }
        return null;
    }

    public Encomenda marcarComoEntregue(String codigoRastreio){
        Encomenda encomenda = findByCodigoRastreio(codigoRastreio);
        if(encomenda != null){
            encomenda.estaEntregue();
            logger.info("Encomenda {} marcada como {}", encomenda.getCodigoRastreio(), encomenda.getStatus());
            return encomenda;
        }
        return null;
    }

    public Encomenda adiocionarEventoHistorico(String codigoRastreio, String evento){
        Encomenda encomenda =  findByCodigoRastreio(codigoRastreio);
        if(encomenda != null){
            encomenda.adicionarHistorico(evento);
            logger.info("Evento '{}' adicionado ao historico da encomenda {}", evento, codigoRastreio);
            return encomenda;
        }
        return null;
    }

}