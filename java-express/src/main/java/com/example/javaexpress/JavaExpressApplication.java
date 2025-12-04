package com.example.javaexpress;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.javaexpress.model.enums.Feedback;
import com.example.javaexpress.model.enums.StatusEncomenda;
import com.example.javaexpress.model.enums.TipoReclamacao;
import com.example.javaexpress.model.model.Cliente;
import com.example.javaexpress.model.model.Encomenda;
import com.example.javaexpress.model.model.Reclamacao;
import com.example.javaexpress.service.AnaliseSentimentoService;
import com.example.javaexpress.service.ChatBot.ChatBotService;
import com.example.javaexpress.service.ClienteService;
import com.example.javaexpress.service.EncomendaService;
import com.example.javaexpress.service.ReclamacaoService;

@SpringBootApplication
public class JavaExpressApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JavaExpressApplication.class, args);
        System.out.println("iniciando aplicação Java Express...");

        // 1. INJEÇÃO DOS SERVIÇOS
        ClienteService clienteService = context.getBean(ClienteService.class);
        EncomendaService encomendaService = context.getBean(EncomendaService.class);
        ReclamacaoService reclamacaoService = context.getBean(ReclamacaoService.class);
        ChatBotService chatBotService = context.getBean(ChatBotService.class);
        AnaliseSentimentoService analiseSentimentoService = context.getBean(AnaliseSentimentoService.class); 

        // 2. CONFIGURAÇÃO DE DADOS INICIAIS
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
                "Manaus, Brasil", StatusEncomenda.ENVIADO, new ArrayList<>(),
                LocalDate.of(2021, 06,26));
        Encomenda encomendaAndre2 = new Encomenda("BR010", "Berlim, Alemanha", "Manaus, Brasil",
                StatusEncomenda.ENVIADO, new ArrayList<>(), LocalDate.of(2026, 06,26));
        Encomenda encomendaCarlos = new Encomenda("BR011", "Helsinque, Finlândia",
                "Manaus, Brasil", StatusEncomenda.ENVIADO, new ArrayList<>(),
                LocalDate.of(2026, 06,26));
        
        encomendaAndre = encomendaService.registrarEncomenda(encomendaAndre);
        encomendaAndre2 = encomendaService.registrarEncomenda(encomendaAndre2);
        encomendaCarlos = encomendaService.registrarEncomenda(encomendaCarlos);

        clienteService.associarEncomendaAoCliente(andre, encomendaAndre);
        clienteService.associarEncomendaAoCliente(andre, encomendaAndre2);
        clienteService.associarEncomendaAoCliente(carlos, encomendaCarlos);

        reclamacaoService.registrarReclamacao(andre, encomendaAndre, TipoReclamacao.ATRASO,
                "Ta atrasado omi");
        Reclamacao reclamacaoCarlos = reclamacaoService.registrarReclamacao(carlos, encomendaCarlos, TipoReclamacao.OUTRO,
                "Pedi um Iphone 17 e me entregaram um tijolo");
        reclamacaoService.registrarFeedback(reclamacaoCarlos, Feedback.OTIMO);
        
        System.out.println("\n--- TESTES DO CHATBOT INICIADOS ---");

        try {
            // 3. TESTES DE FUNCIONALIDADE DO CHATBOT (E REGISTRO DE SENTIMENTO NO H2)
            
            // Teste 1: Sentimento NEGATIVO (Registra 1 no H2 - Ex: Extravio)
            String query1 = "Quero rastrear o código BR001, mas acho que extraviou.";
            String resultado1 = chatBotService.gerarResposta(query1);
            System.out.println("\n[TESTE 1 - NEGATIVO/RASTREIO | Registro: 7]");
            System.out.println("USUÁRIO: " + query1);
            System.out.println("BOT: " + resultado1);

            // Teste 2: Sentimento POSITIVO (Registra 10 no H2 - Ex: Agradecimento)
            String query2 = "Obrigado, suas informações estão perfeitas!";
            String resultado2 = chatBotService.gerarResposta(query2);
            System.out.println("\n[TESTE 2 - POSITIVO/LISTAGEM | Registro: 8]");
            System.out.println("USUÁRIO: " + query2);
            System.out.println("BOT: " + resultado2);

            // Teste 3: Sentimento NEUTRO (Registra 5 no H2 - Ex: Consulta Simples)
            String query3 = "Qual o status do pedido BR010?";
            String resultado3 = chatBotService.gerarResposta(query3);
            System.out.println("\n[TESTE 3 - NEUTRO/CONSULTA | Registro: 7]");
            System.out.println("USUÁRIO: " + query3);
            System.out.println("BOT: " + resultado3);

            // Teste 4: Sentimento NEGATIVO (Registra 1 no H2 - Ex: Dano/Reclamação)
            String query4 = "Gostaria de registrar uma reclamação, minha encomenda foi danificada.";
            String resultado4 = chatBotService.gerarResposta(query4);
            System.out.println("\n[TESTE 4 - NEGATIVO/RECLAMAÇÃO | Registro: 10]");
            System.out.println("USUÁRIO: " + query4);
            System.out.println("BOT: " + resultado4);

            System.out.println("\n--- TESTES DE SENTIMENTO CONCLUÍDOS ---");
            
            // 4. CÁLCULO E IMPRESSÃO DA MÉDIA GERAL DE SATISFAÇÃO
            Double mediaSentimento = analiseSentimentoService.calcularMediaGeral();
            
            System.out.println("\n========================================================");
            System.out.printf("MÉDIA GERAL DE SATISFAÇÃO (ESCALA 1 a 10): %.4f%n", mediaSentimento);
            System.out.println("========================================================");


        } catch (Exception e) {
            System.err.println("\nERRO CRÍTICO DURANTE TESTE DO CHATBOT. Verifique a inicialização do H2 e as anotações JPA.");
            e.printStackTrace();
        }
    }
}