package com.bookshop.oz.exception;


public class BookNotFoundException extends RuntimeException {
	public BookNotFoundException() {
		super("Книга не найдена.");
	}
}
