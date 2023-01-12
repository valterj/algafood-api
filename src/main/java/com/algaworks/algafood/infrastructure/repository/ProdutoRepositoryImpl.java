package com.algaworks.algafood.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepositoryQueries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public FotoProduto save(FotoProduto foto) {
		return this.manager.merge(foto);
	}

	@Override
	public void delete(FotoProduto foto) {
		this.manager.remove(foto);
	}

}
