package com.bookshop.oz.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Authority {
	ROLE_CUSTOMER("ROLE_CUSTOMER"), 
	ROLE_SHOP_ASSISTANT("ROLE_SHOP_ASSISTANT"), 
	ROLE_ADMIN("ROLE_ADMIN");

	@Getter
	private final String role;
}
