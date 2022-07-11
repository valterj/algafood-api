package com.algaworks.algafood.domain.exception;

import lombok.Builder;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 4321357790018529363L;

	private static final String MSG_PRODUTO_NAO_ENCONTRADO = "Não existe um cadastro de produto com código %d para o restaurante %d";

	public ProdutoNaoEncontradoException() {
		this("Não existe produto cadastrado");
	}

	@Builder
	public ProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
		this(String.format(MSG_PRODUTO_NAO_ENCONTRADO, produtoId, restauranteId));
	}

	private ProdutoNaoEncontradoException(String message) {
		super(message);
	}

}
