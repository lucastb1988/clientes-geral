package com.clientesgeral.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.clientesgeral.domain.enums.TipoClienteEnum;
import com.clientesgeral.dto.ClienteDTO;
import com.clientesgeral.resources.exception.FieldMessage;
import com.clientesgeral.services.validation.utils.CPFeCNPJUtils;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Override
	public void initialize(ClienteUpdate c) {}
	
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> errors = new ArrayList<>();
		
		if (objDto.getTipo() == null && objDto.getCpfOuCnpj() == null) {
			return true;
		}
		
		if (objDto.getTipo() != null) {
			if (!TipoClienteEnum.PESSOA_FISICA.getCodigo().equals(objDto.getTipo())
					&& !TipoClienteEnum.PESSOA_JURIDICA.getCodigo().equals(objDto.getTipo())) {
				errors.add(new FieldMessage("tipo", "Tipo inválido."));
			}
		}
		
		if (objDto.getTipo() != null && objDto.getCpfOuCnpj() != null) {
			if (TipoClienteEnum.PESSOA_FISICA.getCodigo().equals(objDto.getTipo())
					&& !CPFeCNPJUtils.isValidCpf(objDto.getCpfOuCnpj())) {
				errors.add(new FieldMessage("cpfOuCnpj", "CPF inválido."));
			}
			
			if (TipoClienteEnum.PESSOA_JURIDICA.getCodigo().equals(objDto.getTipo())
					&& !CPFeCNPJUtils.isValidCnpj(objDto.getCpfOuCnpj())) {
				errors.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido."));
			}
		}

		for (FieldMessage e : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return errors.isEmpty();
	}

}
