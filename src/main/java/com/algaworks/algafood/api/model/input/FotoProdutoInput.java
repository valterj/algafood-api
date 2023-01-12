package com.algaworks.algafood.api.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.core.validation.FileContentType;
import com.algaworks.algafood.core.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {

	@NotNull
	@FileSize(max = "100KB")
	@FileContentType(allowed = MediaType.APPLICATION_PDF_VALUE)
	private MultipartFile arquivo;

	@NotBlank
	private String descricao;

}
