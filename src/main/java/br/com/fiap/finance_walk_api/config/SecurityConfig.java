package br.com.fiap.finance_walk_api.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/transactions/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .build();
    }

    @Bean
    UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(List.of(
            User
                .withUsername("joao")
                .password("$2a$12$bTQhrOKvy8u.41Z6MCtAWO324bULDah.LrXdFZ/aWkS9gY0UYRS0G")
                .roles("ADMIN")
                .build(),
            User
                .withUsername("maria")
                .password("$2a$12$nuQqQOe3hA5jWi4bCWQ8bedArKPWo45Mvv1n2kulO/r7AjMwJHxvm")
                .roles("USER")
                .build()
        ));
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
