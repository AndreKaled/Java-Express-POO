package com.example.javaexpress;

import com.example.javaexpress.model.enums.Feedback;
import com.example.javaexpress.model.enums.StatusEncomenda;
import com.example.javaexpress.model.enums.TipoReclamacao;
import com.example.javaexpress.model.model.*;
import com.example.javaexpress.model.util.Validate;
import com.example.javaexpress.service.ClienteService;
import com.example.javaexpress.service.EncomendaService;
import com.example.javaexpress.service.ReclamacaoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class JavaExpressApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JavaExpressApplication.class, args);
        System.out.println("iniciando");
        testeValidacoes();
    }

    public static void testeValidacoes() {
        //mete os testes ae usando a classe Validate
    }

}
