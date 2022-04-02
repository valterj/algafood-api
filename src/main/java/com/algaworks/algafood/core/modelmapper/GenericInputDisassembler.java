package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param <T> Domain object class
 * @param <S> Input class
 */
@Component
public class GenericInputDisassembler<T, S> {

	@Autowired
	private ModelMapper modelMapper;

	public T toDomainObject(S input, Class<T> type) {
		return modelMapper.map(input, type);
	}

	public void copyToDomainObject(S input, T type) {
		modelMapper.map(input, type);
	}

}
