package com.example.javaexpress.service;

import com.example.javaexpress.model.model.Funcionario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de serviços para Funcionario, responsável por manipular a lógica de Funcionario e controla as ações entre
 * o Funcionario e o Banco de dados.
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
public class FuncionarioService {

    /** Objeto Logger para gerar logs de cada ação feita pelo serviço */
    private static final Logger logger = LoggerFactory.getLogger(EncomendaService.class);

    /** Lista para armazenar objetos de funcionario em memória.*/
    private List<Funcionario> funcionarios;

    /**
     * Gerador de ID incremental para novos funcionarios.
     * Este valor é usado para atribuir um ID único a cada novo funcionario. (TEMPORARIO ATE CHEGAR O BD)
     * */
    private int nextId = 1;

    /**
     * Salva novos funcionarios e altera funcionario existentes a partir do ID
     * @param funcionario Objeto funcionario com os dados preenchidos (exceto o ID)
     * @return funcionario Objeto funcionario com os seus dados e o ID inserido
     * */
    public Funcionario save(Funcionario funcionario){
        if(funcionario.getId() == 0){
            funcionario.setId(nextId++);
            funcionarios.add(funcionario);
            logger.info("Funcionario {#{} - {}} criada com sucesso!", funcionario.getId(), funcionario.getNome());
        }else{
            int index = funcionarios.indexOf(funcionario);
            funcionarios.set(index, funcionario);
            logger.info("Encomenda {#{} - {}} alterado com sucesso", funcionario.getId(), funcionario.getNome());
        }
        return funcionario;
    }

    /**
     * Lista todos os funcionarios existentes (atualmente em memória)
     * @return List<Funcionario> uma cópia da lista de funcionarios, instanciado como ArrayList
     * */
    public List<Funcionario> findAll() {
        return new ArrayList<>(funcionarios);
    }

    /**
     * Busca uma determinada funcionario por ID
     * @param id Um ID de um funcionario que se deseja buscar
     * @return funcionario retorna a funcionario buscado
     * */
    public Funcionario findById(int id){
        logger.info("Buscando funcionario com o ID: {}",  id);
        return funcionarios.stream().filter(funcionario -> funcionario.getId() == id)
                .findFirst().
                orElseThrow(() -> new NullPointerException("Funcionario nao encontrada"));
    }

    /**
     * Apaga um funcionario com base no ID
     * @param id Um ID de um funcionario que se deseja apagar
     * */
    public void deleteByCodigoRastreio(int id){
        funcionarios.removeIf(funcionario -> funcionario.getId() == id);
    }
}
