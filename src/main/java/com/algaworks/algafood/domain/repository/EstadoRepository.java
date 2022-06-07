package com.algaworks.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algafood.domain.modal.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
	
}
