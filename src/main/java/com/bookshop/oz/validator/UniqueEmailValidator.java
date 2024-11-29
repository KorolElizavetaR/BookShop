package com.bookshop.oz.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.PersonDTORegister;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.service.PersonService;
import com.bookshop.oz.validator.annotation.UniqueEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
	private final PersonService peopleService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return peopleService.findUserByUsername(value).isEmpty();
	}
}
