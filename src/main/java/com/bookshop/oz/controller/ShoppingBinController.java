package com.bookshop.oz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shopping_bin")
public class ShoppingBinController {
	@GetMapping
	public String shoppingBin() {
		return "pages/shopping_bin";
	}
}
