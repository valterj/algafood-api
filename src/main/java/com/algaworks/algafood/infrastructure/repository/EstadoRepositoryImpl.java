package com.algaworks.algafood.infrastructure.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Estado> listar() {
		return this.manager.createQuery("from Estado", Estado.class).getResultList();
	}

	@Override
	public Estado buscar(Long id) {
		return this.manager.find(Estado.class, id);
	}

	@Transactional
	@Override
	public Estado salvar(Estado estado) {
		return this.manager.merge(estado);
	}

	@Transactional
	@Override
	public void remover(Long id) {
	    Estado estado = this.buscar(id);
	    
	    if (estado == null) {
	        throw new EmptyResultDataAccessException(1);
	    }
	    
		this.manager.remove(estado);
	}

}
