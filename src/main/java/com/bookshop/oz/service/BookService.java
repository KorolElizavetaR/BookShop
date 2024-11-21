package com.bookshop.oz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookshop.oz.exception.BookNotFoundException;
import com.bookshop.oz.model.Book;
import com.bookshop.oz.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {
	@Autowired
	private final BookRepository bookRepository;
	
	public Book getBookByIsbn(String isbn) {
		return bookRepository.findById(isbn).orElseThrow(() -> new BookNotFoundException());
	}
}
