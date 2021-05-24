package com.algaworks.algafood.jpa;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Component
public class FormaPagamentoBean {

	@Autowired
	private FormaPagamentoRepository repository;

	public FormaPagamentoBean(FormaPagamentoRepository repository) {
		System.out.println("Construtor FormaPagamentoBean");
		List<FormaPagamento> lista = repository.listar();
		lista.forEach(System.out::println);

	}

}
