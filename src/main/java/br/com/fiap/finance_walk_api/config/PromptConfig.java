package br.com.fiap.finance_walk_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class PromptConfig {

    @Bean
    Resource getSystemMessage(){
        return new ClassPathResource("/prompts/system.st");
    }
    
}
