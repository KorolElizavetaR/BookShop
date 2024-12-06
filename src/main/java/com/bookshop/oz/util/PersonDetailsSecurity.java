package com.bookshop.oz.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bookshop.oz.model.Person;
import com.bookshop.oz.model.enumeration.Authority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PersonDetailsSecurity implements UserDetails {
	@Getter
	private final Person person;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Authority role : this.person.getAutorities()) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.person.getBpassword();
	}

	@Override
	public String getUsername() {
		return this.person.getEmail();
	}
	
	@Override
	public boolean isEnabled() {
		return !this.person.isDeactivated();
	}
}
