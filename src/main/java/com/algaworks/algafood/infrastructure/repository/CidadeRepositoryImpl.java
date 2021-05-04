package com.algaworks.algafood.infrastructure.repository;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cidade> listar() {
		return this.manager.createQuery("from cidade", Cidade.class).getResultList();
	}

	@Override
	public Cidade buscar(Long id) {
		return this.manager.find(Cidade.class, id);
	}

	@Transactional
	@Override
	public Cidade salvar(Cidade cidade) {
		return this.manager.merge(cidade);
	}

	@Transactional
	@Override
	public void remover(Cidade cidade) {
		this.manager.remove(cidade);
	}

}
