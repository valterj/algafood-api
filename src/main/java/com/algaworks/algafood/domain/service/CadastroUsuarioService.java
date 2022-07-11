package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.SenhaInvalidaException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class CadastroUsuarioService {

	private UsuarioRepository repository;

	private CadastroGrupoService cadastroGrupo;

	public Usuario buscar(Long id) {
		return repository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}

	public Usuario salvar(Usuario usuario) {
		repository.detach(usuario);

		var usuarioExistente = repository.findByEmail(usuario.getEmail());

		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(String.format("Já existe um usuário cadastrado com o e-mail %s",
													 usuario.getEmail()));
		}

		return repository.save(usuario);
	}

	public void excluir(Long id) {
		repository.deleteById(id);
	}

	public void alterarSenha(Long id, String senhaAtual, String novaSenha) {

		var usuario = buscar(id);

		if (usuario.senhaCoincideCom(senhaAtual)) {
			usuario.setSenha(novaSenha);
		} else {
			throw new SenhaInvalidaException();
		}

	}

	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscar(usuarioId);
		Grupo grupo = cadastroGrupo.buscar(grupoId);

		usuario.removerGrupo(grupo);
	}

	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscar(usuarioId);
		Grupo grupo = cadastroGrupo.buscar(grupoId);

		usuario.adicionarGrupo(grupo);
	}

}
