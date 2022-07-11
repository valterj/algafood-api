package com.algaworks.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CadastroRestauranteService {

	private RestauranteRepository restauranteRepository;

	private CadastroCozinhaService cadastroCozinha;

	private CadastroCidadeService cadastroCidade;

	private CadastroUsuarioService cadastroUsuario;

	private FormaPagamentoRepository formaPagamentoRepository;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();

		var cozinha = cadastroCozinha.buscar(cozinhaId);
		var cidade = cadastroCidade.buscar(cidadeId);

		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);

		return this.restauranteRepository.save(restaurante);
	}

	@Transactional
	public void ativar(Long id) {
		var restaurante = buscar(id);
		restaurante.ativar();
	}

	@Transactional
	public void ativar(List<Long> restaurantesId) {
		restaurantesId.forEach(this::ativar);
	}

	@Transactional
	public void inativar(Long id) {
		var restaurante = buscar(id);
		restaurante.inativar();
	}

	@Transactional
	public void inativar(List<Long> restaurantesId) {
		restaurantesId.forEach(this::inativar);
	}

	public Restaurante buscar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}

	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		var restaurante = buscar(restauranteId);
		var formaPagamento = formaPagamentoRepository.buscar(formaPagamentoId);

		restaurante.adicionarFormaPagamento(formaPagamento);
	}

	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		var restaurante = buscar(restauranteId);
		var formaPagamento = formaPagamentoRepository.buscar(formaPagamentoId);

		restaurante.removerFormaPagamento(formaPagamento);
	}

	@Transactional
	public void abrir(Long id) {
		var restaurante = buscar(id);
		restaurante.abrir();
	}

	@Transactional
	public void fechar(Long id) {
		var restaurante = buscar(id);
		restaurante.fechar();
	}

	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscar(restauranteId);
		Usuario usuario = cadastroUsuario.buscar(usuarioId);

		restaurante.removerUsuario(usuario);
	}

	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscar(restauranteId);
		Usuario usuario = cadastroUsuario.buscar(usuarioId);

		restaurante.adicionarUsuario(usuario);
	}

}
