package br.com.fiap.finance_walk_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.finance_walk_api.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}
