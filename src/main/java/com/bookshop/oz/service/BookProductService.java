package com.bookshop.oz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bookshop.oz.controller.RestControllerJS;
import com.bookshop.oz.dto.BookProductDTO;
import com.bookshop.oz.dto.BookProductDTOBooksForMainPage;
import com.bookshop.oz.mapper.BookProductMapper;
import com.bookshop.oz.mapper.StockMapper;
import com.bookshop.oz.model.Book;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.repository.BookProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookProductService {
	public static final int BOOKS_PER_PAGE = 4;

	@Autowired
	private final BookProductRepository bookProductRepository;
	@Autowired
	private BookProductMapper bookProductMapper;

	public List<BookProductDTOBooksForMainPage> getAllItemsForMainPage() {
		List<BookProduct> books = bookProductRepository.findAllJoin();
		List<BookProductDTOBooksForMainPage> booksDTO = books.stream()
				.map(bookProductMapper::getBookProductDTO__BookFetcher).collect(Collectors.toList());
		return booksDTO;
	}

	public List<BookProductDTOBooksForMainPage> getAllItemsPaganated(Integer page) {
		List<BookProduct> books = bookProductRepository.findAll(PageRequest.of(page, BOOKS_PER_PAGE)).getContent();
		List<BookProductDTOBooksForMainPage> booksDTO = books.stream()
				.map(bookProductMapper::getBookProductDTO__BookFetcher).collect(Collectors.toList());
		return booksDTO;
	}

	public BookProductDTO getBookProductForProductPageById(String isbn) {
		// exception instead of null //
		BookProduct bookProduct = bookProductRepository.findById(isbn).orElse(null);
		return bookProductMapper.getBookProductDTO(bookProduct);
	}

	public int getBookPagesCounter() {
		int pageCounter = (int) Math.ceil((double) bookProductRepository.count() / BOOKS_PER_PAGE);
		System.out.println(pageCounter);
		return pageCounter;
	}

	public List<BookProductDTOBooksForMainPage> findBookLike(String str) {
//		List<BookProduct> books = bookProductRepository.findAll(PageRequest.of(page, BOOKS_PER_PAGE)).getContent();
//		List<BookProductDTOBooksForMainPage> booksDTO = books.stream()
//				.map(bookProductMapper::getBookProductDTO__BookFetcher).collect(Collectors.toList());
//		return booksDTO;
//		return bookProductRepository.findByBookNameContainingIgnoreCase(str);
	}
}
