package br.com.fiap.finance_walk_api.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.finance_walk_api.model.Category;
import br.com.fiap.finance_walk_api.model.Transaction;
import br.com.fiap.finance_walk_api.model.TransactionType;
import br.com.fiap.finance_walk_api.model.User;
import br.com.fiap.finance_walk_api.model.UserRole;
import br.com.fiap.finance_walk_api.repository.CategoryRepository;
import br.com.fiap.finance_walk_api.repository.TransactionRepository;
import br.com.fiap.finance_walk_api.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Configuration
public class DatabaseSeeder {

        @Autowired
        private CategoryRepository categoryRepository;

        @Autowired
        private TransactionRepository transactionRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        var joao = User.builder()
                        .email("joao@fiap.com.br")
                        .password(passwordEncoder.encode("12345"))
                        .role(UserRole.ADMIN)
                        .build();

        var maria = User.builder()
                        .email("maria@fiap.com.br")
                        .password(passwordEncoder.encode("12345"))
                        .role(UserRole.USER)
                        .build();
        userRepository.saveAll(List.of(joao, maria));

        var categories = List.of(
                Category.builder().name("Educação").icon("Book").user(joao).build(),
                Category.builder().name("Lazer").icon("Dices").user(joao).build(),
                Category.builder().name("Saúde").icon("Heart").user(joao).build(),
                Category.builder().name("Transporte").icon("Bus").user(maria).build());

        categoryRepository.saveAll(categories);

        var descriptions = List.of(
                "Supermercado",
                "Aluguel",
                "Internet",
                "Academia",
                "Transporte Público",
                "Cartão de Crédito",
                "Streaming (Netflix)",
                "Streaming (Spotify)",
                "Gasolina",
                "Manutenção do Carro",
                "Plano de Saúde",
                "Seguro do Carro",
                "Material Escolar",
                "Telefone Celular",
                "Restaurante",
                "Lazer (Cinema)",
                "Presentes",
                "Impostos",
                "Produtos de Limpeza",
                "Cuidados Pessoais");

        var transactions = new ArrayList<Transaction>();
        for (int i = 0; i < 50; i++) {
            transactions.add(
                    Transaction.builder()
                            .description(descriptions.get(new Random().nextInt(descriptions.size())))
                            .amount(BigDecimal.valueOf(10 + new Random().nextDouble() * 500))
                            .date(LocalDate.now().minusDays(new Random().nextInt(30)))
                            .type(TransactionType.EXPENSE)
                            .category(categories.get(new Random().nextInt(categories.size())))
                            .build());
        }

        transactionRepository.saveAll(transactions);

       

    }

}
