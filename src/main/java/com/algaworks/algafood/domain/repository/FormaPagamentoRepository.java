package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {

	public List<FormaPagamento> findAll();

	public FormaPagamento buscar(Long id);

	public FormaPagamento save(FormaPagamento formaPagamento);

}
