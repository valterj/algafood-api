package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import com.algaworks.algafood.core.validation.Multiplo;
import com.algaworks.algafood.core.validation.TaxaFrete;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;

import lombok.Getter;
import lombok.Setter;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatorio = "Frete Grátis")
@Getter
@Setter
public class RestauranteInput {

	@NotBlank
	private String nome;

	@NotNull
	@PositiveOrZero
	@TaxaFrete
	@Multiplo(numero = 1)
	private BigDecimal taxaFrete;

	@Valid
	@NotNull
	private CozinhaIdInput cozinha;

	@Valid
	@NotNull
	private EnderecoInput endereco;

}
