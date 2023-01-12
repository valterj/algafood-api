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

import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.core.modelmapper.GenericInputDisassembler;
import com.algaworks.algafood.core.modelmapper.GenericModelAssembler;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
	private GrupoRepository repository;

	@Autowired
	private CadastroGrupoService service;

	@Autowired
	private GenericModelAssembler<Grupo, GrupoModel> assembler;

	@Autowired
	private GenericInputDisassembler<Grupo, GrupoInput> disassembler;

	@GetMapping
	public List<GrupoModel> listar() {
		var grupos = repository.findAll();
		return assembler.toCollectionModel(grupos, GrupoModel.class);
	}

	@GetMapping("/{id}")
	public GrupoModel buscar(@PathVariable Long id) {
		return assembler.toModel(service.buscar(id), GrupoModel.class);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		var grupo = disassembler.toDomainObject(grupoInput, Grupo.class);
		return assembler.toModel(service.salvar(grupo), GrupoModel.class);
	}

	@PutMapping("/{id}")
	public GrupoModel atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
		try {

			Grupo grupoAtual = service.buscar(id);

			disassembler.copyToDomainObject(grupoInput, grupoAtual);

			grupoAtual = service.salvar(grupoAtual);

			return assembler.toModel(grupoAtual, GrupoModel.class);

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		service.remover(id);
	}

}
