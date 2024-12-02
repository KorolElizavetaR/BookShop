package com.bookshop.oz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NavBarController {

	@GetMapping("/orders")
	public String orders() {
		return "customer/orders";
	}
}
