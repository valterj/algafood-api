package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Autowired
	private EstadoModelAssembler estadoModelAssembler;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;

	@GetMapping
	public List<EstadoModel> listar() {
		return estadoModelAssembler.toCollectionModel(this.estadoRepository.findAll());
	}

	@GetMapping("/{id}")
	public EstadoModel buscar(@PathVariable Long id) {
		return estadoModelAssembler.toModel(cadastroEstado.buscar(id));
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		var estado = estadoInputDisassembler.toDomainObject(estadoInput);
		return estadoModelAssembler.toModel(cadastroEstado.salvar(estado));
	}

	@PutMapping("/{id}")
	public EstadoModel atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoAtual = cadastroEstado.buscar(id);

		estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

		estadoAtual = cadastroEstado.salvar(estadoAtual);

		return estadoModelAssembler.toModel(estadoAtual);
	}

	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		cadastroEstado.excluir(id);
	}

}
