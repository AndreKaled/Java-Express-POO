package com.example.javaexpress;

import com.example.javaexpress.model.model.*;
import com.example.javaexpress.service.ClienteService;
import com.example.javaexpress.service.EncomendaService;
import com.example.javaexpress.service.ReclamacaoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class JavaExpressApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JavaExpressApplication.class, args);

        System.out.println("iniciando");

        ClienteService clienteService = context.getBean(ClienteService.class);
        EncomendaService encomendaService = context.getBean(EncomendaService.class);
        ReclamacaoService reclamacaoService = context.getBean(ReclamacaoService.class);

        Cliente andre = clienteService.criarCliente("andré", "andre.andrade@icomp.ufam.edu.br",
                "123");
        Cliente carlos = clienteService.criarCliente("carlos", "carlos@icomp.ufam.edu.br",
                "321");

        Encomenda encomendaAndre = encomendaService.criarEncomenda("BR001",
                "Berlim, Alemanha", "Manaus, Brasil",
                LocalDate.of(2021, 06,26));

        Encomenda encomendaAndre2 = encomendaService.criarEncomenda("BR010",
                "Berlim, Alemanha", "Manaus, Brasil",
                LocalDate.of(2026, 06,26));


        Encomenda encomendaCarlos = encomendaService.criarEncomenda("BR011",
                "Moscou Russia", "Manaus, Brasil",
                LocalDate.of(2026, 06,26));

        clienteService.adicionarEncomenda(andre, encomendaAndre);
        clienteService.adicionarEncomenda(andre, encomendaAndre2);
        clienteService.adicionarEncomenda(carlos, encomendaCarlos);

        //andré reclama
        reclamacaoService.criarReclamacao(andre, encomendaAndre, TipoReclamacao.ATRASO,
                "Ta atrasado omi");

        //carlos reclama
        Reclamacao reclamacaoCarlos = reclamacaoService.criarReclamacao(carlos, encomendaCarlos, TipoReclamacao.DANIFICADA,
                "ta amassado essa bomba ae");

        reclamacaoService.registrarFeedback(reclamacaoCarlos, Feedback.OTIMO);
    }
}
