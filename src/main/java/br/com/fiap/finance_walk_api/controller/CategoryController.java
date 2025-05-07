package br.com.fiap.finance_walk_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.finance_walk_api.model.Category;
import br.com.fiap.finance_walk_api.model.User;
import br.com.fiap.finance_walk_api.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/categories")
// @CrossOrigin(origins = "http://localhost:3000")
@Slf4j
//@Cacheable(value = "categories")
public class CategoryController {

    @Autowired // injeção de dependência
    private CategoryRepository repository;

    @GetMapping
    //@Cacheable
    @Operation(summary = "Listar todas categorias", description = "Lista todas as categorias salvas para um determinado usuário", tags = "Category")
    public List<Category> index(@AuthenticationPrincipal User user) {
        return repository.findByUser(user);
    }

    @PostMapping
    //@CacheEvict(allEntries = true)
    @Operation(responses = @ApiResponse(responseCode = "400"))
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody @Valid Category category, @AuthenticationPrincipal User user) {
        log.info("Cadastrando categoria " + category.getName());
        category.setUser(user);
        return repository.save(category);
    }

    @GetMapping("{id}")
    public Category get(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.info("Buscando categoria " + id);
        var category = getCategory(id);
        if(!category.getUser().equals(user)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Categoria não pertence ao usuário");
        }
        return category;
    }

    @DeleteMapping("{id}")
    //@CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.info("Apagando categoria " + id);
        var category = getCategory(id);
        if(!category.getUser().equals(user)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Categoria não pertence ao usuário");
        }
        repository.delete(category);
    }

    @PutMapping("{id}")
    //@CacheEvict(allEntries = true)
    public Category update(@PathVariable Long id, @RequestBody Category category, @AuthenticationPrincipal User user) {
        log.info("Atualizando categoria " + id + " " + category);

        var categoryOld = getCategory(id);
        if(!categoryOld.getUser().equals(user)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Categoria não pertence ao usuário");
        }
        category.setId(id);
        category.setUser(user);
        return repository.save(category);
    }

    private Category getCategory(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Categoria " + id + "  não encontrada"));
    }

}
