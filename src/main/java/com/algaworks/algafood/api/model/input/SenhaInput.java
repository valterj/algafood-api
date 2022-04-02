package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class SenhaInput {

	@NotBlank
	private String senhaAtual;

	@NotBlank
	private String novaSenha;

}
