package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

import lombok.AllArgsConstructor;

@Service
@Transactional
@Repository
@AllArgsConstructor
public class CatalogoFotoProdutoService {

	private ProdutoRepository produtoRepository;

	private FotoStorageService fotoStorage;

	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {

		var nomeArquivoAnterior = this.excluirFoto(foto);

		String nomeArquivoAtual = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
		foto.setNomeArquivo(nomeArquivoAtual);

		this.salvarFoto(foto);
		this.armazenarFoto(nomeArquivoAnterior, nomeArquivoAtual, dadosArquivo);

		return foto;

	}

	private String excluirFoto(FotoProduto foto) {

		var restauranteId = foto.getRestauranteId();
		var produtoId = foto.getProduto().getId();
		String nomeArquivoAnterior = null;

		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);

		if (fotoExistente.isPresent()) {
			nomeArquivoAnterior = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
		}

		return nomeArquivoAnterior;

	}

	private void salvarFoto(FotoProduto foto) {
		this.produtoRepository.save(foto);
		this.produtoRepository.flush();
	}

	private void armazenarFoto(String nomeArquivoAnterior, String nomeArquivoAtual, InputStream dadosArquivo) {
		NovaFoto novaFoto = NovaFoto.builder().nomeArquivo(nomeArquivoAtual).inputStream(dadosArquivo).build();
		this.fotoStorage.substituir(nomeArquivoAnterior, novaFoto);
	}

}
