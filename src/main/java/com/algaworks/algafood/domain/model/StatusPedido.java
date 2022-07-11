package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

	CRIADO("Criado"), CONFIRMADO("Confirmado", CRIADO), ENTREGUE("Entregue", CONFIRMADO),
	CANCELADO("Cancelado", CRIADO, CONFIRMADO);

	private String descricao;

	private List<StatusPedido> statusAnterioresPermitidos;

	StatusPedido(String descricao, StatusPedido... statusAnterioresPermitidos) {
		this.descricao = descricao;
		this.statusAnterioresPermitidos = Arrays.asList(statusAnterioresPermitidos);
	}

	public String getDescricao() {
		return this.descricao;
	}

	public boolean podeAlterarPara(StatusPedido novoStatus) {
		return novoStatus.statusAnterioresPermitidos.contains(this);
	}

}
