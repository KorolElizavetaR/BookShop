package com.bookshop.oz.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.oz.openlibrary.OpenLibraryConsumer;
import com.bookshop.oz.openlibrary.model.Document;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping ("/catalog")
@RequiredArgsConstructor
public class CatalogController {
	public final OpenLibraryConsumer openLibraryConsumer;
  
	@GetMapping ("/external")
	public List<Document> getExternalBooks() throws IOException, InterruptedException, URISyntaxException {
		return openLibraryConsumer.getBooks("harry potter");
	}
}
