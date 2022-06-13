package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //informa que essa interface não deve instanciar uma implementacao para essa interface
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

	Optional<T> buscarPrimeiro();
}
