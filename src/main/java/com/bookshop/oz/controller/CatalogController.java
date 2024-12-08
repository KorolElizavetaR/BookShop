package com.bookshop.oz.controller;

import java.util.List;

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
import com.bookshop.oz.dto.BookProductDTOItem;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.service.BookProductService;
import com.bookshop.oz.service.OrderService;
import com.bookshop.oz.util.BookAvailabibltyChecker;
import com.bookshop.oz.util.PersonDetailsSecurity;

import lombok.RequiredArgsConstructor;

/**
 * Этот контроллер будет отвечать за главную страницу и товары на ней, а также
 * детали о товарах.
 */
@Controller
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {
	private final BookProductService bookProductService;
	private final OrderService orderService;

	private final BookAvailabibltyChecker bookAvailabibltyChecker;

	@GetMapping()
	public String catalogPage(Model model, @RequestParam(value = "like", required = false) String like,
			@RequestParam(value = "sort", required = false) String sort) {
		List<BookProductDTOItem> books = bookProductService.getItems(sort, like);
		model.addAttribute("stock", books);
		
		model.addAttribute("like", like);
		model.addAttribute("sort", sort);
		
		return "catalog/main";
	}

	@GetMapping("/{id}")
	public String bookPage(@PathVariable("id") String id, Model model) {
		BookProductDTO bookProduct = bookProductService.getBookProductForProductPageById(id);
		short availabilityCode = bookAvailabibltyChecker.getAvailabilityCode(bookProduct);
		boolean isAvailable = bookAvailabibltyChecker.isButtonToShoppingBinAvailable(availabilityCode);
		String availabilityMessageString = bookAvailabibltyChecker.availabilityCodeMessage(availabilityCode);

		model.addAttribute("isAvailableForBuying", isAvailable);
		model.addAttribute("availabilityMessage", availabilityMessageString);
		model.addAttribute("bookProduct", bookProduct);
		return "catalog/product";
	}

	/**
	 * Отвечает за перекладывание книги в Shopping Bin
	 * 
	 * @param id - isbn книги
	 * 
	 *           --------------------------------- РЕАЛИЗОВАТЬ: - Если книга уже
	 *           обнаружена у человека в корзине, ничего не делать
	 */
	@PostMapping("/{id}/shoppingBin")
	public String toShoppingBin(@PathVariable("id") String id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PersonDetailsSecurity personDetails = (PersonDetailsSecurity) auth.getPrincipal();
		Person person = personDetails.getPerson();
		orderService.addOrderToShoppingBin(person, id);
		return "redirect:/shopping_bin";
	}
}
