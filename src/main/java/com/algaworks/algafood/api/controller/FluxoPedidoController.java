package com.algaworks.algafood.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.service.FluxoPedidoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/pedidos/{codigoPedido}")
@AllArgsConstructor
public class FluxoPedidoController {

	private FluxoPedidoService fluxoPedidoService;

	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmar(@PathVariable String codigoPedido) {
		this.fluxoPedidoService.confirmar(codigoPedido);
	}

	@PutMapping("/entregue")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void entregar(@PathVariable String codigoPedido) {
		this.fluxoPedidoService.entregar(codigoPedido);
	}

	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable String codigoPedido) {
		this.fluxoPedidoService.cancelar(codigoPedido);
	}

}
