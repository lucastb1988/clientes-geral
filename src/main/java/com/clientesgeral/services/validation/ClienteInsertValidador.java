package com.clientesgeral.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.clientesgeral.domain.enums.TipoClienteEnum;
import com.clientesgeral.dto.ClienteNewDTO;
import com.clientesgeral.resources.exception.FieldMessage;
import com.clientesgeral.services.validation.utils.CPFeCNPJUtils;

public class ClienteInsertValidador implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Override
	public void initialize(ClienteInsert c) {}
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> errors = new ArrayList<>();

		if (!TipoClienteEnum.PESSOA_FISICA.getCodigo().equals(objDto.getTipo())
				&& !TipoClienteEnum.PESSOA_JURIDICA.getCodigo().equals(objDto.getTipo())) {
			errors.add(new FieldMessage("tipo", "Tipo inválido."));
		}

		if (TipoClienteEnum.PESSOA_FISICA.getCodigo().equals(objDto.getTipo())
				&& !CPFeCNPJUtils.isValidCpf(objDto.getCpfOuCnpj())) {
			errors.add(new FieldMessage("cpfOuCnpj", "CPF inválido."));
		}

		if (TipoClienteEnum.PESSOA_JURIDICA.getCodigo().equals(objDto.getTipo())
				&& !CPFeCNPJUtils.isValidCnpj(objDto.getCpfOuCnpj())) {
			errors.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido."));
		}

		for (FieldMessage e : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return errors.isEmpty();
	}

}
