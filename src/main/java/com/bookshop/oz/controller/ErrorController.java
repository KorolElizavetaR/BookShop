package com.bookshop.oz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	@GetMapping("/forbidden")
	public String forbiddenPage() {
		return "errors/error403";
	}
}
