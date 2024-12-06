package com.bookshop.oz.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.oz.dto.BookProductDTOItem;
import com.bookshop.oz.service.BookProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping ("/ADMIN")
@RequiredArgsConstructor
public class AdminController {
	private final BookProductService bookProductService;
	/**
	 * Тут можно удалить книгу, создать книгу, назначив ей локации, изменить существующую книгу.
	 */
	@GetMapping("/books")
	public String books(Model model, @RequestParam(value = "like", required = false) String like) {
		List<BookProductDTOItem> books = bookProductService.getItems(null, like);
		model.addAttribute("stock", books);
		return "/ADMIN/books";
	}
	
	/**
	 * Тут можем назначать юзерам роли:
	 * - покупатель
	 * - продавец (не может быть покупателем)
	 * - бухгалтер (может быть покупателем)
	 */
	@GetMapping("/users")
	public String users() {
		
	}
}
