package com.algaworks.algafood.domain.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class CadastroProdutoService {

	private CadastroRestauranteService restauranteService;

	private ProdutoRepository produtoRepository;

	@Transactional
	public Produto adicionarProduto(Long restauranteId, @Valid ProdutoModel produtoModel) {
		var restaurante = restauranteService.buscar(restauranteId);

		var produto = Produto.builder()
							 .nome(produtoModel.getNome())
							 .descricao(produtoModel.getDescricao())
							 .preco(produtoModel.getPreco())
							 .ativo(produtoModel.isAtivo())
							 .restaurante(restaurante)
							 .build();

		return produtoRepository.save(produto);
	}

	public Collection<Produto> buscarPorRestaurante(Long restauranteId, boolean incluirInativos) {
		var restaurante = restauranteService.buscar(restauranteId);

		if (incluirInativos)
			return produtoRepository.findByRestauranteId(restauranteId);
		else
			return produtoRepository.findAtivosByRestaurante(restaurante);
	}

	public Produto buscar(Long restauranteId, Long produtoId) {

		var restaurante = restauranteService.buscar(restauranteId);

		return produtoRepository.findByIdAndRestauranteId(produtoId, restaurante.getId())
								.orElseThrow(() -> ProdutoNaoEncontradoException.builder()
																				.restauranteId(restauranteId)
																				.produtoId(produtoId)
																				.build());
	}

}
