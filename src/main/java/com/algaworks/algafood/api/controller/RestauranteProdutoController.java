package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.core.modelmapper.GenericModelAssembler;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	private CadastroProdutoService produtoService;

	private CatalogoFotoProdutoService catalogoFotoProdutoService;

	private GenericModelAssembler<Produto, ProdutoModel> produtoAssembler;

	private GenericModelAssembler<FotoProduto, FotoProdutoModel> fotoProdutoModelAssembler;

	@GetMapping
	public List<ProdutoModel> listar(@PathVariable Long restauranteId,
									 @RequestParam(required = false) boolean incluirInativos) {

		var produtos = produtoService.buscarPorRestaurante(restauranteId, incluirInativos);

		return produtoAssembler.toCollectionModel(produtos, ProdutoModel.class);
	}

	@GetMapping("/{produtoId}")
	public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

		var produto = produtoService.buscar(restauranteId, produtoId);

		return produtoAssembler.toModel(produto, ProdutoModel.class);
	}

	@PostMapping
	public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoModel produtoModel) {
		return produtoAssembler.toModel(produtoService.adicionarProduto(restauranteId, produtoModel),
										ProdutoModel.class);
	}

	@PutMapping(value = "/{produtoId}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,
										  @PathVariable Long produtoId,
										  @Valid FotoProdutoInput fotoProdutoInput)
			throws IOException {

		var produto = produtoService.buscar(restauranteId, produtoId);

		var arquivo = fotoProdutoInput.getArquivo();

		var fotoProduto = FotoProduto.builder()
									 .produto(produto)
									 .nomeArquivo(arquivo.getOriginalFilename())
									 .descricao(fotoProdutoInput.getDescricao())
									 .contentType(arquivo.getContentType())
									 .tamanho(arquivo.getSize())
									 .build();

		var fotoSalva = this.catalogoFotoProdutoService.salvar(fotoProduto, arquivo.getInputStream());

		return this.fotoProdutoModelAssembler.toModel(fotoSalva, FotoProdutoModel.class);

	}

	@SuppressWarnings("unused")
	private void salvarFotoEmArquivo(FotoProdutoInput fotoProdutoInput) {

		var nomeArquivo = UUID.randomUUID().toString() + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();

		var arquivoFoto = Path.of("../../Downloads", nomeArquivo);

		try {
			fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e); // NOSONAR
		}

	}

}
