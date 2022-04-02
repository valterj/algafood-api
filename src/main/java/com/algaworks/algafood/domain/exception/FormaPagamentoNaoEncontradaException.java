package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 8905115553876497668L;

	private static final String MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA = "Não existe um cadastro de forma de pagamento com código %d";

	public FormaPagamentoNaoEncontradaException(Long id) {
		this(String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA, id));
	}

	private FormaPagamentoNaoEncontradaException(String message) {
		super(message);
	}

}
