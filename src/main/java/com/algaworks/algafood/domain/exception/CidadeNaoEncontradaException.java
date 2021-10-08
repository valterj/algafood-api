package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 4321357790018529363L;
    
    private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de cidade com código %d";

    public CidadeNaoEncontradaException(Long id) {
        this(String.format(MSG_CIDADE_NAO_ENCONTRADA, id));
    }
    
    private CidadeNaoEncontradaException(String message) {
        super(message);
    }

}
