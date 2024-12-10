package com.bookshop.oz.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.PersonDTORegister;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.service.PersonService;
import com.bookshop.oz.util.AuthUtil;
import com.bookshop.oz.validator.annotation.UniqueEmail;
import com.bookshop.oz.validator.annotation.UniquePhone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UniquePhoneValidator implements ConstraintValidator<UniquePhone, String> {
	private final PersonService peopleService;
	private final AuthUtil authUtil;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Integer personId = null;
		if (authUtil.isLoggedIntoSystem()) {
			personId = authUtil.getPersonFromAuth().getPersonId();
			System.out.println(personId);
		}
		Optional<Person> person = peopleService.findUserByPhone(value);
		return person.isEmpty() || person.get().getPersonId() == personId;
	}
}
