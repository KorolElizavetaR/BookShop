package com.bookshop.oz.util;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.oz.dto.BookProductDTO;
import com.bookshop.oz.dto.LocationPointDTO;
import com.bookshop.oz.dto.StockDTO;
import com.bookshop.oz.model.LocationPoint;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.model.enumeration.Authority;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookAvailabibltyChecker {
	private final AuthUtil authChecker;

	private final int CODE_AVAILABLE_IN_SHOP = 1;
	private final int CODE_AVAILABLE_IN_STOCK = 2;
	private final int CODE_NOT_AUTHORIZED = -1;
	private final int CODE_NO_AUTHORITY_CUSTOMER = -2;
	private final int CODE_NO_CURRENT_LOCATION = -3; // No current location set
	private final int CODE_NOT_AVAILABLE = -4;

	/**
	 * Проверяет доступность товара к покупке
	 * 
	 * @param bookProductDTO : принимает на вход текущий товар
	 * 
	 * 
	 * @return {@link #CODE_AVAILABLE_IN_SHOP} книга доступна в магазине
	 *         пользователя.
	 * @return {@link #CODE_AVAILABLE_IN_STOCK} книга доступна на складе
	 * @return {@link #CODE_NOT_AUTHORIZED} пользователь не авторизован.
	 * @return {@link #CODE_NO_AUTHORITY_CUSTOMER} у пользователя нет роли CUSTOMER.
	 * @return {@link #CODE_NO_CURRENT_LOCATION} не установлено текущее
	 *         местоположение у юзера
	 * @return {@link #CODE_NOT_AVAILABLE} книги нет в магазине и на складе
	 */

	public byte getAvailabilityCode(BookProductDTO bookProductDTO) {
		if (!authChecker.isLoggedIntoSystem()) {
			return CODE_NOT_AUTHORIZED;
		}
		Person person = authChecker.getPersonFromAuth();
		if (!person.getAutorities().stream().anyMatch(authority -> authority == Authority.ROLE_CUSTOMER)) {
			return CODE_NO_AUTHORITY_CUSTOMER;
		}
		LocationPoint personLocation = person.getLocationPoint();
		if (personLocation == null) {
			return CODE_NO_CURRENT_LOCATION;
		}
		boolean availableInShop = false;
		boolean availableInStorage = false;
		for (StockDTO stock : bookProductDTO.getStock()) {
			if (stock.getQuantity() == 0) {
				continue;
			}
			LocationPointDTO bookLocation = stock.getLocation();
			if (bookLocation.getLocationId().equals(personLocation.getLocationId())) {
				availableInShop = true;
				break;
			}
			if (Boolean.TRUE.equals(bookLocation.getIsStorage())) {
				availableInStorage = true;
			}
		}

		if (availableInShop) {
			return CODE_AVAILABLE_IN_SHOP;
		}
		if (availableInStorage) {
			return CODE_AVAILABLE_IN_STOCK;
		}
		return CODE_NOT_AVAILABLE;
	}

	public String availabilityCodeMessage(short availabilityCode) {
		switch (availabilityCode) {
		case CODE_NOT_AUTHORIZED:
			return "Авторизируйтесь в системе для покупки товара";
		case CODE_NO_AUTHORITY_CUSTOMER:
			return "Не хватает прав доступа на приобритение товара";
		case CODE_NO_CURRENT_LOCATION:
			return "Установите ближайший к вам магазин во вкладке МАГАЗИНЫ";
		case CODE_NOT_AVAILABLE:
			return "Попробуйте изменить ближайший к вам магазин во вкладке МАГАЗИНЫ на один из представленных в списке доступных магазинов";
		default:
			return "";
		}
	}

	public boolean isButtonToShoppingBinAvailable(short availabilityCode) {
		return availabilityCode == CODE_AVAILABLE_IN_SHOP || availabilityCode == CODE_AVAILABLE_IN_STOCK;
	}

}
