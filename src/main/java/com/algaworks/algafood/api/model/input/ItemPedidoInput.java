package com.algaworks.algafood.api.model.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

	@NotNull
	private Long produtoId;

	@Positive
	private int quantidade;

	private String observacao;

}
