package com.bookshop.oz.mapper;

import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.PersonDTOInfo;
import com.bookshop.oz.dto.PersonDTORegister;
import com.bookshop.oz.model.Person;

@Component
public class PersonMapper {
	public Person getPersonFromPersonDTORegister(PersonDTORegister personDTO) {
		return new Person().setEmail(personDTO.getEmail()).setFirstName(personDTO.getFirstName())
				.setLastName(personDTO.getLastName()).setPhone(personDTO.getPhone());
	}

	public PersonDTORegister getPersonDTORegister(Person person) {
		return new PersonDTORegister().setEmail(person.getEmail()).setFirstName(person.getFirstName())
				.setLastName(person.getLastName()).setPhone(person.getPhone()).setConfirmPassword(person.getBpassword())
				.setPassword(person.getBpassword());
	}

	public PersonDTOInfo getPersonDTOInfo(Person person) {
		return new PersonDTOInfo().setFirstName(person.getFirstName()).setLastName(person.getLastName())
				.setPhone(person.getPhone());
	}
}
