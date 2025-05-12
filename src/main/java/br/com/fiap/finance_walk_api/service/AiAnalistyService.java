package br.com.fiap.finance_walk_api.service;

import java.time.LocalDate;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.finance_walk_api.model.TransactionType;
import br.com.fiap.finance_walk_api.repository.TransactionRepository;

@Service
public class AiAnalistyService {

    private ChatClient chatClient;
    private TransactionRepository transactionRepository;

    public AiAnalistyService(ChatClient.Builder builder, Resource systemMessage, TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;

        var options = ChatOptions.builder()
                        .temperature(1.0)
                        .topP(1.0)
                        .build();

        chatClient = builder
                        .defaultSystem(systemMessage)
                        .defaultOptions(options)      
                        .build();
    }

    public String getExpenseAnalise(String subject, String lang){
        var expenses = transactionRepository.findByTypeAndDateBetween(TransactionType.EXPENSE, LocalDate.now().withDayOfMonth(1), LocalDate.now());

        var objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writeValueAsString(expenses);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return chatClient.prompt()
            .user("faça uma análise dessas despesas: " + json)
            .system(sp -> sp.param("language", lang))
            .call()
            .content();
    }

    public String getFinantialsScore(){
        var expenses = transactionRepository.findByDateBetween(LocalDate.now().withDayOfMonth(1), LocalDate.now());

        var objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writeValueAsString(expenses);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        
        return chatClient.prompt() 
                            .user("calcule uma nota para minha vida financeira baseado nessas movimentações: " + json) 
                            .call()
                            .content();
    }
    
}
