package com.bookshop.oz.validator;

import com.bookshop.oz.dto.PersonDTOPasswords;
import com.bookshop.oz.dto.PersonDTORegister;
import com.bookshop.oz.validator.annotation.PasswordMatches;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		try {
			PersonDTORegister user = (PersonDTORegister) obj;
			return user.getPassword().equals(user.getConfirmPassword());
		} catch (ClassCastException ex) {
			PersonDTOPasswords user = (PersonDTOPasswords) obj;
			return user.getPassword().equals(user.getConfirmPassword());
		}
	}

}
