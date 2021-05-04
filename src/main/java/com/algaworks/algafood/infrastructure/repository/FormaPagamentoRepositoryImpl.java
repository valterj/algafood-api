package com.algaworks.algafood.infrastructure.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.repository.FormaPagamentoRepository;

@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<FormaPagamento> listar() {
		return this.manager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
	}

	@Override
	public FormaPagamento buscar(Long id) {
		return this.manager.find(FormaPagamento.class, id);
	}

	@Transactional
	@Override
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return this.manager.merge(formaPagamento);
	}

	@Transactional
	@Override
	public void remover(FormaPagamento formaPagamento) {
		this.manager.remove(formaPagamento);
	}

}
