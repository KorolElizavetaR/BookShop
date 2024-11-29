package com.bookshop.oz.model.pk;

import java.io.Serializable;

import com.bookshop.oz.model.enumeration.Authority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorityId implements Serializable {
	private Integer person; // Maps to Person.personId
	private Authority userAuthority; // Maps to the authority
}
