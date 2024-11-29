package com.bookshop.oz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookshop.oz.dto.BookProductDTO;
import com.bookshop.oz.dto.BookProductDTOBooksForMainPage;
import com.bookshop.oz.mapper.BookProductMapper;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.repository.BookProductRepository;

import lombok.RequiredArgsConstructor;

/**
 * Отвечает за модели Book и BookProduct.
 */
@Service
@RequiredArgsConstructor
public class BookProductService {
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

	public BookProductDTO getBookProductForProductPageById(String isbn) {
		BookProduct bookProduct = bookProductRepository.findById(isbn).orElse(null);
		return bookProductMapper.getBookProductDTO(bookProduct);
	}

	public List<BookProductDTOBooksForMainPage> findBookLike(String str) {
		List<BookProduct> books = bookProductRepository.findByBookNameContainingIgnoreCase(str);
		List<BookProductDTOBooksForMainPage> booksDTO = books.stream()
				.map(bookProductMapper::getBookProductDTO__BookFetcher).collect(Collectors.toList());
		return booksDTO;
	}
}
