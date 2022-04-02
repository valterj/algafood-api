package com.algaworks.algafood.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

public interface FormaPagamentoRepositoryImpl extends FormaPagamentoRepository, JpaRepository<FormaPagamento, Long> {

	@Override
	default FormaPagamento buscar(Long id) {
		return findById(id).orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
	}

}
