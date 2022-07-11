package com.algaworks.algafood.core.modelmapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @param <T> Domain object class
 * @param <S> Model class
 */
@Component
public class GenericModelAssembler<T, S> {

	private ModelMapper modelMapper;

	public GenericModelAssembler(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public S toModel(T domainObject, Class<S> type) {
		return modelMapper.map(domainObject, type);
	}

	public List<S> toCollectionModel(Collection<T> objects, Class<S> type) {
		return objects.stream().map(object -> toModel(object, type)).collect(Collectors.toList());
	}

}
