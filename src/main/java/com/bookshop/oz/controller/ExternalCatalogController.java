package com.bookshop.oz.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.oz.model.Book;
import com.bookshop.oz.openlibrary.OpenLibraryConsumer;
import com.bookshop.oz.openlibrary.OpenLibraryParser;
import com.bookshop.oz.openlibrary.model.Document;

import lombok.RequiredArgsConstructor;

/*
 * 	На данный момент OpenLibrary API по какой-то причине не работает :(
*/

//@Controller
@RequestMapping ("/catalog/external")
@RequiredArgsConstructor
public class ExternalCatalogController {
	public final OpenLibraryParser openLibrary;
  
	@GetMapping ()
	public String getExternalBooks(Model model, @RequestParam (value = "q", required = false) String line) throws IOException, InterruptedException, URISyntaxException {
		//is q absent or not
		model.addAttribute("books", openLibrary.getBooks(line));
		return "catalog/external/book_list_external";
	}
	
	@GetMapping ("/{isbn}")
	public String getExternalBook(Model model, @PathVariable("isbn") Integer isbn) throws IOException, InterruptedException, URISyntaxException
	{
		model.addAttribute("books", openLibrary.getBooks(String.valueOf(isbn)));
		return "catalog/external/external_book";
	}
	
}
