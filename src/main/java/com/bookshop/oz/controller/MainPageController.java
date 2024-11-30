package com.bookshop.oz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookshop.oz.dto.BookProductDTO;
import com.bookshop.oz.dto.LocationPointDTO;
import com.bookshop.oz.dto.StockDTO;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.model.LocationPoint;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.security.PersonDetails;
import com.bookshop.oz.service.BookProductService;
import com.bookshop.oz.service.OrderService;
import com.bookshop.oz.util.BookAvailabibltyChecker;

import lombok.RequiredArgsConstructor;

/**
 * Этот контроллер будет отвечать за главную страницу и товары на ней, а также
 * детали о товарах.
 */
@Controller
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class MainPageController {
	private final BookProductService bookProductService;
	private final OrderService orderService;

	private final BookAvailabibltyChecker bookAvailabibltyChecker;

	@GetMapping()
	public String catalogPage(Model model, @RequestParam(value = "like", required = false) String like) {
		if (like == null || like.isBlank()) {
			model.addAttribute("stock", bookProductService.getAllItemsForMainPage());
		} else {
			model.addAttribute("stock", bookProductService.findBookLike(like));
		}
		return "catalog/main";
	}

	@GetMapping("/{id}")
	public String bookPage(@PathVariable("id") String id, Model model) {
		/*
		 * Пользователь попал на страницу: 
		 * - Если он не авторизирован:
		 * "Авторизируйтесь в системе" -> FALSE 
		 * - Если нет роли User:
		 * "Нет прав доступа: СUSTOMER" -> FALSE 
		 * - Если пользователь авторизирован но у
		 * него не установлено место:
		 * "Установите ближайший к вам магазин во вкладке МАГАЗИНЫ" FALSE
		 * - Если авторизирован и есть в системе, проверить, может ли он купить книгу: Если есть на складе
		 * 	или на его точке TRUE 
		 * при FALSE кнопка купить заблокирована. Сообщение:
		 * "Попробуйте изменить ближайший к вам магазин во вкладке МАГАЗИНЫ на один из представленных в списке доступных магазинов"
		 */

		BookProductDTO bookProduct = bookProductService.getBookProductForProductPageById(id);

		boolean isAvailable;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null)
			isAvailable = true;
		PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
		Person person = personDetails.getPerson();
		LocationPoint personLocation = person.getLocationPoint();
		String availability = "";

		boolean bookAvailableInShop = false;
		boolean bookAvailableInStorage = false;

		for (StockDTO stock : bookProduct.getStock()) {
			LocationPointDTO bookLocation = stock.getLocation();
			if (bookLocation.getLocationId().equals(personLocation.getLocationId())) {
				System.out.println("Book is available in your shop");
				availability = "Book is available in your shop";
				bookAvailableInShop = true;
				break;
			}
			if (Boolean.TRUE.equals(bookLocation.getIsStorage())) {
				bookAvailableInStorage = true;
			}
		}

		if (!bookAvailableInShop) {
			if (bookAvailableInStorage) {
				System.out.println("Book is available in stock");
				availability = "Book is available in stock";
			} else {
				System.out.println("Book is not available");
				availability = "Book is not available";
			}
		}
		model.addAttribute("isAvailable", bookAvailableInShop || bookAvailableInStorage);
		model.addAttribute("availability", availability);
		model.addAttribute("bookProduct", bookProductService.getBookProductForProductPageById(id));
		return "catalog/product";
	}

	@PostMapping("/{id}/shoppingBin")
	public String toShoppingBin(@PathVariable("id") String id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
		Person person = personDetails.getPerson();
		orderService.addOrderToShoppingBin(person, id);
		return "redirect:/catalog";
	}
}
