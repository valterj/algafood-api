package com.algaworks.algafood.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioSemSenhaInput;
import com.algaworks.algafood.core.modelmapper.GenericInputDisassembler;
import com.algaworks.algafood.core.modelmapper.GenericModelAssembler;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private GenericModelAssembler<Usuario, UsuarioModel> assembler;

	@Autowired
	private GenericInputDisassembler<Usuario, UsuarioInput> disassembler;

	@Autowired
	private GenericInputDisassembler<Usuario, UsuarioSemSenhaInput> disassemblerSemSenha;

	@Autowired
	private CadastroUsuarioService service;

	@GetMapping
	public List<UsuarioModel> listar() {
		return assembler.toCollectionModel(repository.findAll(), UsuarioModel.class);
	}

	@GetMapping("/{id}")
	public UsuarioModel buscar(@PathVariable Long id) {
		return assembler.toModel(service.buscar(id), UsuarioModel.class);
	}

	@GetMapping("/primeiro")
	public UsuarioModel buscarPrimeiro(String usuario) {
		return assembler.toModel(repository.buscarPrimeiro().orElseThrow(UsuarioNaoEncontradoException::new),
								 UsuarioModel.class);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
		var usuario = disassembler.toDomainObject(usuarioInput, Usuario.class);
		return assembler.toModel(service.salvar(usuario), UsuarioModel.class);
	}

	@PutMapping("/{id}")
	public UsuarioModel atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioSemSenhaInput usuarioInput) {

		Usuario usuarioAtual = service.buscar(id);

		disassemblerSemSenha.copyToDomainObject(usuarioInput, usuarioAtual);

		usuarioAtual = service.salvar(usuarioAtual);

		return assembler.toModel(usuarioAtual, UsuarioModel.class);

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(NO_CONTENT)
	public void remover(@PathVariable Long id) {
		service.excluir(id);
	}

	@PutMapping("/{id}/senha")
	@ResponseStatus(NO_CONTENT)
	public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senhaInput) {
		service.alterarSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
	}

}
