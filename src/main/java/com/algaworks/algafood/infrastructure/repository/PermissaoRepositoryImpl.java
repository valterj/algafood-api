package com.algaworks.algafood.infrastructure.repository;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.repository.PermissaoRepository;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Permissao> listar() {
		return this.manager.createQuery("from Permissao", Permissao.class).getResultList();
	}

	@Override
	public Permissao buscar(Long id) {
		return this.manager.find(Permissao.class, id);
	}

	@Transactional
	@Override
	public Permissao salvar(Permissao permissao) {
		return this.manager.merge(permissao);
	}

	@Transactional
	@Override
	public void remover(Permissao permissao) {
		this.manager.remove(permissao);
	}

}
