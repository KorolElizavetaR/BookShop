package com.bookshop.oz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.oz.service.BookProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestControllerJS {
	private final BookProductService bookProductService;

	@GetMapping("/pagesCounter")
	public int getTotalPages() {
		return bookProductService.getBookPagesCounter();
	}

}
