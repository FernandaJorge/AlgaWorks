package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.modal.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository{

	@PersistenceContext
	EntityManager manager;
	
	@Override
	public List<Permissao> listaTodos() {
		return manager.createQuery("from Permissao", Permissao.class).getResultList();
	}
	
	@Override
	public Permissao porId(Long id) {
		return manager.find(Permissao.class, id);
	}

	@Transactional
	@Override
	public Permissao adicionar(Permissao permissao) {
		return manager.merge(permissao);
	}

	@Transactional
	@Override
	public void remover(Permissao permissao) {
		permissao = porId(permissao.getId());
		manager.remove(permissao);
	}

}
