package com.bookshop.oz.openlibrary;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.bookshop.oz.model.Book;
import com.bookshop.oz.openlibrary.model.Document;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OpenLibraryParser {
	private final OpenLibraryConsumer openLibraryConsumer;
	private ApplicationContext context;
	
	public List <Book> getBooks(String line) throws IOException, InterruptedException, URISyntaxException
	{
		List<Document> rawBooks = openLibraryConsumer.getBooks(line);
		List<Book> books = new ArrayList<>();
		Book book;
		for (Document doc : rawBooks)
		{
			book = context.getBean(Book.class);
			book.setAuthor(doc.getAuthor_name().getFirst());
		}
		books.add(context.getBean(Book.class));
		
		return books;
	}
	
}
