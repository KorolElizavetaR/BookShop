package com.bookshop.oz.mapper;

import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.PersonDTORegister;
import com.bookshop.oz.model.Person;

@Component
public class PersonMapper {
	public Person getPersonFromPersonDTORegister(PersonDTORegister personDTO) {
		return new Person().setEmail(personDTO.getEmail()).setFirstName(personDTO.getFirstName())
				.setLastName(personDTO.getLastName()).setPhone(personDTO.getPhone());
	}
}
