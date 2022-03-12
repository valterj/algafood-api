package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 4321357790018529363L;

	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe um cadastro de restaurante com código %d";

	public RestauranteNaoEncontradoException() {
		this("Não existe restaurante cadastrado");
	}

	public RestauranteNaoEncontradoException(Long id) {
		this(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id));
	}

	private RestauranteNaoEncontradoException(String message) {
		super(message);
	}

}
