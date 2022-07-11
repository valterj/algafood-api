package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FluxoPedidoService {

	private EmissaoPedidoService emissaoPedidoService;

	@Transactional
	public void confirmar(String codigoPedido) {
		var pedido = this.emissaoPedidoService.buscar(codigoPedido);
		pedido.confirmar();
	}

	@Transactional
	public void entregar(String codigoPedido) {
		var pedido = this.emissaoPedidoService.buscar(codigoPedido);
		pedido.entregar();
	}

	public void cancelar(String codigoPedido) {
		var pedido = this.emissaoPedidoService.buscar(codigoPedido);
		pedido.cancelar();
	}

}
