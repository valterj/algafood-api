package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.core.modelmapper.GenericModelAssembler;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	private CadastroProdutoService produtoService;

	private GenericModelAssembler<Produto, ProdutoModel> assembler;

	@GetMapping
	public List<ProdutoModel> listar(@PathVariable Long restauranteId,
									 @RequestParam(required = false) boolean incluirInativos) {

		var produtos = produtoService.buscarPorRestaurante(restauranteId, incluirInativos);

		return assembler.toCollectionModel(produtos, ProdutoModel.class);
	}

	@GetMapping("/{produtoId}")
	public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

		var produto = produtoService.buscar(restauranteId, produtoId);

		return assembler.toModel(produto, ProdutoModel.class);
	}

	@PostMapping
	public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoModel produtoModel) {
		return assembler.toModel(produtoService.adicionarProduto(restauranteId, produtoModel), ProdutoModel.class);
	}

}
