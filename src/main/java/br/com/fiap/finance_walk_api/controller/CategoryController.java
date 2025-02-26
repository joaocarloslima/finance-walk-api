package br.com.fiap.finance_walk_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.finance_walk_api.model.Category;

@RestController
public class CategoryController {

    private List<Category> repository = new ArrayList<>();

    // Listar todas as categorias
    // GET :8080/categories
    @GetMapping("/categories")
    public List<Category> index() {
        return repository;
    }

    // Cadastrar categoria
    @PostMapping("/categories")
    // @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Category> create(@RequestBody Category category) {
        repository.add(category);
        System.out.println("Cadastrando categoria " + category.getName());
        return ResponseEntity.status(201).body(category);
    }

    // Buscar uma categoria
    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> get(@PathVariable Long id) {
        System.out.println("Buscando categoria " + id);
        var category = repository.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (category.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(category.get());
    }

    // Apagar uma categoria

    // Atualizar uma categoria

}
