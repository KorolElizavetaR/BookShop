package com.bookshop.oz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.oz.model.Person;
import com.bookshop.oz.security.PersonDetails;
import com.bookshop.oz.service.BookProductService;
import com.bookshop.oz.service.StockService;

import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

/**
 * Этот контроллер будет отвечать за главную страницу и товары на ней, а также
 * детали о товарах.
 */
@Controller
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class MainPageController {
	@Autowired
	private final BookProductService bookProductService;

	@GetMapping()
	public String catalogPage(Model model) {
		model.addAttribute("stock", bookProductService.getAllItemsForMainPage());
		return "catalog/main";
	}
	
	@GetMapping("/{id}")
	public String bookPage(@PathVariable("id") String id, Model model) {
		model.addAttribute("bookProduct", bookProductService.getBookProductForProductPageById(id));
		return "catalog/product";
	}

	@GetMapping("/shopping_bin")
	public String shoppingBin() {
		
		return "catalog/shoppingBin";
	}
	
	@PostMapping("/{id}/shop")
	public String toShoppingBin(@PathVariable("id") String id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails personDetails = (PersonDetails)auth.getPrincipal();
		Person person = personDetails.getPerson();
		//model.addAttribute("person", person);
		
		
		return "catalog/shoppingBin";
	}
}
