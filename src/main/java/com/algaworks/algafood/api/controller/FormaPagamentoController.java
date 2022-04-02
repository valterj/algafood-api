package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.core.modelmapper.GenericInputDisassembler;
import com.algaworks.algafood.core.modelmapper.GenericModelAssembler;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository repository;

	@Autowired
	private CadastroFormaPagamentoService service;

	@Autowired
	private GenericModelAssembler<FormaPagamento, FormaPagamentoModel> assembler;

	@Autowired
	private GenericInputDisassembler<FormaPagamento, FormaPagamentoInput> disassembler;

	@GetMapping
	public List<FormaPagamentoModel> listar() {
		var formasPagamento = repository.findAll();
		return assembler.toCollectionModel(formasPagamento, FormaPagamentoModel.class);
	}

	@GetMapping("/{id}")
	public FormaPagamentoModel buscar(@PathVariable Long id) {
		return assembler.toModel(repository.buscar(id), FormaPagamentoModel.class);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput input) {
		var formaPagamento = disassembler.toDomainObject(input, FormaPagamento.class);
		return assembler.toModel(service.salvar(formaPagamento), FormaPagamentoModel.class);
	}

}
