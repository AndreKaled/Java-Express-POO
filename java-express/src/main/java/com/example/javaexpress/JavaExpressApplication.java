package com.example.javaexpress;

import com.example.javaexpress.model.enums.Feedback;
import com.example.javaexpress.model.enums.StatusEncomenda;
import com.example.javaexpress.model.enums.TipoReclamacao;
import com.example.javaexpress.model.model.*;
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
        ClienteService clienteService = context.getBean(ClienteService.class);
        EncomendaService encomendaService = context.getBean(EncomendaService.class);
        ReclamacaoService reclamacaoService = context.getBean(ReclamacaoService.class);
        Cliente andre = new Cliente();
        andre.setNome("andré");
        andre.setEmail("andre.andrade@icomp.ufam.edu.br");
        andre.setSenha("123");
        andre = clienteService.save(andre);
        Cliente carlos = new Cliente();
        carlos.setNome("carlos");
        carlos.setEmail("carlos@icomp.ufam.edu.br");
        carlos.setSenha("321");
        carlos = clienteService.save(carlos);

        Encomenda encomendaAndre = new Encomenda(null, "Berlim, Alemanha",
                "Manaus, Brasil", StatusEncomenda.ENVIADO, new ArrayList<String>(),
                LocalDate.of(2021, 06,26));
        Encomenda encomendaAndre2 = new Encomenda(null, "Berlim, Alemanha", "Manaus, Brasil",
                StatusEncomenda.ENVIADO, new ArrayList<String>(), LocalDate.of(2026, 06,26));
        Encomenda encomendaCarlos = new Encomenda("BR011", "Helsinque, Finlândia",
                "Manaus, Brasil", StatusEncomenda.ENVIADO, new ArrayList<String>(),
                LocalDate.of(2026, 06,26));
        encomendaAndre = encomendaService.save(encomendaAndre);
        encomendaAndre2 = encomendaService.save(encomendaAndre2);
        encomendaCarlos = encomendaService.save(encomendaCarlos);

        clienteService.adicionarEncomenda(andre, encomendaAndre);
        clienteService.adicionarEncomenda(andre, encomendaAndre2);
        clienteService.adicionarEncomenda(carlos, encomendaCarlos);

        //andré reclama
        reclamacaoService.criarReclamacao(andre, encomendaAndre, TipoReclamacao.ATRASO,
                "Ta atrasado omi");

        //carlos reclama
        Reclamacao reclamacaoCarlos = reclamacaoService.criarReclamacao(carlos, encomendaCarlos, TipoReclamacao.OUTRO,
                "Pedi um Iphone 17 e me entregaram um tijolo");

        reclamacaoService.registrarFeedback(reclamacaoCarlos, Feedback.OTIMO);
    }
}
