package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 4321357790018529363L;
    
    private static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe um cadastro de cozinha com código %d";

    public CozinhaNaoEncontradaException(Long id) {
        this(String.format(MSG_COZINHA_NAO_ENCONTRADA, id));
    }
    
    private CozinhaNaoEncontradaException(String message) {
        super(message);
    }

}
