package com.algaworks.algafood.api.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.core.modelmapper.GenericModelAssembler;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	private CadastroProdutoService produtoService;

	private CatalogoFotoProdutoService catalogoFotoProdutoService;

	private GenericModelAssembler<FotoProduto, FotoProdutoModel> fotoProdutoModelAssembler;

	private FotoStorageService fotoStorage;

	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public FotoProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

		FotoProduto foto = catalogoFotoProdutoService.buscar(restauranteId, produtoId);

		return this.fotoProdutoModelAssembler.toModel(foto, FotoProdutoModel.class);
	}

	@DeleteMapping
	@ResponseStatus(NO_CONTENT)
	public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		this.catalogoFotoProdutoService.excluir(restauranteId, produtoId);
	}

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

	@GetMapping
	public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId,
														  @PathVariable Long produtoId) {

		try {

			FotoProduto foto = catalogoFotoProdutoService.buscar(restauranteId, produtoId);

			InputStream inputStream = fotoStorage.recuperar(foto.getNomeArquivo());

			return ResponseEntity.ok()
								 .contentType(MediaType.APPLICATION_PDF)
								 .body(new InputStreamResource(inputStream));

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}

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
