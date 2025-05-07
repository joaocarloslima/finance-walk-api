package br.com.fiap.finance_walk_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.finance_walk_api.model.Category;
import br.com.fiap.finance_walk_api.model.User;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUser(User user);


}
