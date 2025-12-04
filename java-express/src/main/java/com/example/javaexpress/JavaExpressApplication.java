package com.example.javaexpress;

import com.example.javaexpress.model.enums.Feedback;
import com.example.javaexpress.model.enums.StatusEncomenda;
import com.example.javaexpress.model.enums.TipoReclamacao;
import com.example.javaexpress.model.model.*;
import com.example.javaexpress.model.util.Validate;
import com.example.javaexpress.service.ClienteService;
import com.example.javaexpress.service.EncomendaService;
import com.example.javaexpress.service.ReclamacaoService;
import com.example.javaexpress.service.ChatBot.ChatBotService; 
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
        // INJEÇÃO DO CHATBOT SERVICE PARA TESTES
        ChatBotService chatBotService = context.getBean(ChatBotService.class);

        
        Cliente andre = new Cliente();
        
        andre.setNome("andré");
        andre.setEmail("andre.andrade@icomp.ufam.edu.br");
        andre.setSenha("123");
        andre = clienteService.registrarCliente(andre);
        Cliente carlos = new Cliente();
        carlos.setNome("carlos");
        carlos.setEmail("carlos@icomp.ufam.edu.br");
        carlos.setSenha("321");
        carlos = clienteService.registrarCliente(carlos);

        Encomenda encomendaAndre = new Encomenda("BR001", "Berlim, Alemanha",
                "Manaus, Brasil", StatusEncomenda.ENVIADO, new ArrayList<String>(),
                LocalDate.of(2021, 06,26));
        Encomenda encomendaAndre2 = new Encomenda("BR010", "Berlim, Alemanha", "Manaus, Brasil",
                StatusEncomenda.ENVIADO, new ArrayList<String>(), LocalDate.of(2026, 06,26));
        Encomenda encomendaCarlos = new Encomenda("BR011", "Helsinque, Finlândia",
                "Manaus, Brasil", StatusEncomenda.ENVIADO, new ArrayList<String>(),
                LocalDate.of(2026, 06,26));
        encomendaAndre = encomendaService.registrarEncomenda(encomendaAndre);
        encomendaAndre2 = encomendaService.registrarEncomenda(encomendaAndre2);
        encomendaCarlos = encomendaService.registrarEncomenda(encomendaCarlos);

        clienteService.associarEncomendaAoCliente(andre, encomendaAndre);
        clienteService.associarEncomendaAoCliente(andre, encomendaAndre2);
        clienteService.associarEncomendaAoCliente(carlos, encomendaCarlos);

        //andré reclama
        reclamacaoService.registrarReclamacao(andre, encomendaAndre, TipoReclamacao.ATRASO,
                "Ta atrasado omi");

        //carlos reclama
        Reclamacao reclamacaoCarlos = reclamacaoService.registrarReclamacao(carlos, encomendaCarlos, TipoReclamacao.OUTRO,
                "Pedi um Iphone 17 e me entregaram um tijolo");

        reclamacaoService.registrarFeedback(reclamacaoCarlos, Feedback.OTIMO);
        
        System.out.println("\n--- TESTES DO CHATBOT INICIADOS ---");

        try {
            // Teste 1: Rastreamento (Função 1)
            String query1 = "Quero rastrear o código BR001.";
            String resultado1 = chatBotService.gerarResposta(query1);
            System.out.println("\n[TESTE 1 - Rastreamento (rastrear_encomenda)]");
            System.out.println("USUÁRIO: " + query1);
            System.out.println("JABOT: " + resultado1);

            // Teste 2: Listagem (Função 2)
            String query2 = "Quais são as minhas encomendas ativas?";
            String resultado2 = chatBotService.gerarResposta(query2);
            System.out.println("\n[TESTE 2 - Listagem (listar_encomendas)]");
            System.out.println("USUÁRIO: " + query2);
            System.out.println("JABOT: " + resultado2);

           
            // Teste 3: Reclamação (Função 3)
            String query4 = "Gostaria de registrar uma reclamação sobre o atraso da encomenda BR011.";
            String resultado4 = chatBotService.gerarResposta(query4);
            System.out.println("\n[TESTE 4 - Reclamação (registrar_nova_reclamacao)]");
            System.out.println("USUÁRIO: " + query4);
            System.out.println("JABOT: " + resultado4);

            System.out.println("\n--- TESTES DO CHATBOT FINALIZADOS ---");

        } catch (Exception e) {
            System.err.println("\nERRO CRÍTICO DURANTE TESTE DO JABOT. Verifique se o ClienteService.consultarEncomenda e os métodos de Reclamação retornam valores válidos.");
            e.printStackTrace();
        }
    }
}
        testeValidacoes();
    }

    public static void testeValidacoes() {
        //mete os testes ae usando a classe Validate
    }

}
