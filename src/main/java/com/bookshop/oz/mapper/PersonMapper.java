package com.bookshop.oz.mapper;

import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.PersonDTORegister;
import com.bookshop.oz.model.Person;

@Component
public class PersonMapper {
	public Person PersonDTORegisterToPerson(PersonDTORegister personDTO) {
		return new Person().setBpassword(personDTO.getBpassword()).setEmail(personDTO.getEmail())
				.setFirstName(personDTO.getFirstName()).setLastName(personDTO.getLastName())
				.setPhone(personDTO.getPhone());
	}
}
