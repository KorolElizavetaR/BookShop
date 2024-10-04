package com.bookshop.oz.openlibrary;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.context.ApplicationContext;

import com.bookshop.oz.model.Book;
import com.bookshop.oz.openlibrary.model.Document;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OpenLibraryParser {
	private final OpenLibraryConsumer openLibraryConsumer;
	private ApplicationContext context;

	public List<Book> getBooks(String line) throws IOException, InterruptedException, URISyntaxException {
		List<Document> rawBooks = openLibraryConsumer.getBooks(line);
		List<Book> books = new ArrayList<>();
		Book book;
		for (Document doc : rawBooks) {
			book = new Book();
			try {
				book.setAuthor(doc.getAuthorName()).
					setIsbn(doc.getEditions().getDocs().getIsbn13()).
					setTitle(doc.getTitle()).
					setPublisher(doc.getEditions().getDocs().getPublisher()).
					setLanguage(doc.getEditions().getDocs().getLanguage()).
					setCoverID(doc.getCover_i()).
					setDescription(doc.getSubject());
				books.add(book);
			} catch (NoSuchElementException ex) {
				continue;
			}
			books.add(book);
		}
		return books;
	}

}
