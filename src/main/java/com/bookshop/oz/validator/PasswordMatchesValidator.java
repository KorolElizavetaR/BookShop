package com.bookshop.oz.validator;

import com.bookshop.oz.dto.PersonDTORegister;
import com.bookshop.oz.validator.annotation.PasswordMatches;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		PersonDTORegister user = (PersonDTORegister) obj;
		return user.getPassword().equals(user.getConfurmPassword());
	}
}
