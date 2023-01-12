package com.algaworks.algafood.domain.service;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.repository.PedidoRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmissaoPedidoService {

	private PedidoRepository pedidoRepository;

	private CadastroRestauranteService cadastroRestaurante;

	private CadastroCidadeService cadastroCidade;

	private CadastroUsuarioService cadastroUsuario;

	private CadastroProdutoService cadastroProduto;

	private FormaPagamentoRepository formaPagamentoRepository;

	public Pedido buscar(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido)
							   .orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
	}

	@Transactional
	public Pedido emitir(Pedido pedido) {
		this.validarPedido(pedido);
		this.validarItens(pedido);

		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calcularValorTotal();

		return pedidoRepository.save(pedido);
	}

	private void validarPedido(Pedido pedido) {
		Cidade cidade = cadastroCidade.buscar(pedido.getEnderecoEntrega().getCidade().getId());
		Usuario cliente = cadastroUsuario.buscar(pedido.getCliente().getId());
		Restaurante restaurante = cadastroRestaurante.buscar(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = formaPagamentoRepository.buscar(pedido.getFormaPagamento().getId());

		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);

		if (!restaurante.aceitaFormaPagamento(formaPagamento)) {
			throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
													 formaPagamento.getDescricao()));
		}
	}

	private void validarItens(Pedido pedido) {
		pedido.getItensPedido().forEach(item -> {
			var produto = cadastroProduto.buscar(pedido.getRestaurante().getId(), item.getProduto().getId());
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}

}
