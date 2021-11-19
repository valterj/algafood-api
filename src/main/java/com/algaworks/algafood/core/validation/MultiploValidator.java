package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {
	
	private int numeroMultiplo;
	
	@Override
	public void initialize(Multiplo constraintAnnotation) {
		this.numeroMultiplo = constraintAnnotation.numero();
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if (value == null)
			return true;
		
		var valor = BigDecimal.valueOf(value.doubleValue());
		var multiplo = BigDecimal.valueOf(this.numeroMultiplo);
		
		return BigDecimal.ZERO.compareTo(valor.remainder(multiplo)) == 0;
	}
}
