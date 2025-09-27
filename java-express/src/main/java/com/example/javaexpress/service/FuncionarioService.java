package com.example.javaexpress.service;

import com.example.javaexpress.model.model.Funcionario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * **Serviço de gerenciamento de Funcionários.**
 *
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
    private static final Logger logger = LoggerFactory.getLogger(FuncionarioService.class);

    /** Lista para armazenar objetos de funcionario em memória.*/
    private List<Funcionario> funcionarios =  new ArrayList<>();

    /**
     * Gerador de ID incremental para novos funcionarios.
     * Este valor é usado para atribuir um ID único a cada novo funcionario.
     * */
    private int nextId = 1;

    /**
     * Salva novos funcionarios
     * @param funcionario Objeto funcionario com os dados preenchidos
     * @return funcionario Objeto funcionario com os seus dados e o ID inserido
     * */
    public Funcionario registrarFuncionario(Funcionario funcionario){
        funcionario.setId(nextId++);
        funcionarios.add(funcionario);
        logger.info("Funcionario {#{} - {}} criada com sucesso!", funcionario.getId(), funcionario.getNome());
        return funcionario;
    }

    /**
     * Atualiza os dados de um funcionario
     * @param funcionario Objeto funcionario com os dados novos preenchidos
     * @return funcionario Objeto funcionario com os seus dados registrados
     * */
    public Funcionario atualizarFuncionario(Funcionario funcionario){
        int index = funcionarios.indexOf(funcionario);
        funcionarios.set(index, funcionario);
        logger.info("Funcionário {#{} - {}} alterado com sucesso", funcionario.getId(), funcionario.getNome());
        return funcionario;
    }

    /**
     * Lista todos os funcionarios existentes
     * @return listFuncionarios uma cópia da lista de funcionarios, instanciado como ArrayList
     * */
    public List<Funcionario> listarFuncionarios() {
        return new ArrayList<>(funcionarios);
    }

    /**
     * Busca um funcionario por um Id
     * @param id Um ID de um funcionario que se deseja buscar
     * @return funcionario retorna a funcionario buscado
     * */
    public Funcionario buscarFuncionarioPorId(int id){
        logger.info("Buscando funcionario com o ID: {}",  id);
        return funcionarios.stream().filter(funcionario -> funcionario.getId() == id)
                .findFirst().
                orElseThrow(() -> new NullPointerException("Funcionario nao encontrada"));
    }

    /**
     * Exclui um funcionario com base no ID
     * @param id Um ID de um funcionario que se deseja apagar
     * */
    public void excluirFuncionario(int id){
        funcionarios.removeIf(funcionario -> funcionario.getId() == id);
    }
}
