package com.algaworks.algafood.domain.model;

import static com.algaworks.algafood.domain.model.StatusPedido.CRIADO;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static java.time.OffsetDateTime.now;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.algaworks.algafood.domain.exception.NegocioException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	private String codigo;

	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;

	@Embedded
	private Endereco enderecoEntrega;

	@Enumerated(STRING)
	private StatusPedido status = CRIADO;

	@CreationTimestamp
	private OffsetDateTime dataCriacao;

	private OffsetDateTime dataConfirmacao;

	private OffsetDateTime dataEntrega;

	private OffsetDateTime dataCancelamento;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;

	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itensPedido = new ArrayList<>();

	public void calcularValorTotal() {
		this.getItensPedido().forEach(ItemPedido::calcularPrecoTotal);

		this.subtotal = this.getItensPedido()
							.stream()
							.map(item -> item.getPrecoTotal())
							.reduce(BigDecimal.ZERO, BigDecimal::add);

		this.valorTotal = this.subtotal.add(this.taxaFrete);
	}

	public void confirmar() {
		this.setStatus(StatusPedido.CONFIRMADO);
		this.setDataConfirmacao(now());
	}

	public void entregar() {
		this.setStatus(StatusPedido.ENTREGUE);
		this.setDataEntrega(now());
	}

	public void cancelar() {
		this.setStatus(StatusPedido.CANCELADO);
		this.setDataCancelamento(now());
	}

	private void setStatus(StatusPedido novoStatus) {
		if (!status.podeAlterarPara(novoStatus)) {
			throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s.",
													 getCodigo(),
													 getStatus().getDescricao(),
													 novoStatus.getDescricao()));
		}

		this.status = novoStatus;
	}

	@PrePersist
	private void gerarCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}

}
