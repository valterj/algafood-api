package com.algaworks.algafood.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.core.modelmapper.GenericInputDisassembler;
import com.algaworks.algafood.core.modelmapper.GenericModelAssembler;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
public class PedidoController {

	private GenericModelAssembler<Pedido, PedidoModel> pedidoAssembler;

	private GenericModelAssembler<Pedido, PedidoResumoModel> pedidoResumoAssembler;

	private GenericInputDisassembler<Pedido, PedidoInput> pedidoDisassembler;

	private PedidoRepository pedidoRepository;

	private EmissaoPedidoService emissaoPedidoService;

	@GetMapping
	public List<PedidoResumoModel> listar() {
		return pedidoResumoAssembler.toCollectionModel(pedidoRepository.findAll(), PedidoResumoModel.class);
	}

	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		return pedidoAssembler.toModel(emissaoPedidoService.buscar(codigoPedido), PedidoModel.class);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public PedidoModel adicionar(@RequestBody PedidoInput pedido) {
		try {
			Pedido novoPedido = pedidoDisassembler.toDomainObject(pedido, Pedido.class);

			// TODO pegar usuário autenticado
			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);

			novoPedido = emissaoPedidoService.emitir(novoPedido);

			return pedidoAssembler.toModel(novoPedido, PedidoModel.class);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

}
