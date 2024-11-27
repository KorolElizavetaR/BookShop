package com.bookshop.oz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/personal")
@RequiredArgsConstructor
public class AuthController {
	
	@GetMapping("/login")
	public String loginPage() {
		return "personal/login";
	}
	
	@GetMapping()
	public String personalPage() {
		return "personal/page";
	}
}
