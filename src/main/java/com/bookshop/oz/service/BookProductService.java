package com.bookshop.oz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookshop.oz.dto.BookProductDTO;
import com.bookshop.oz.dto.BookProductDTO_BookFetcher;
import com.bookshop.oz.mapper.BookProductMapper;
import com.bookshop.oz.mapper.StockMapper;
import com.bookshop.oz.model.Book;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.repository.BookProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookProductService {
	@Autowired
	private final BookProductRepository bookProductRepository;
	@Autowired
	private BookProductMapper bookProductMapper;

	public List<BookProductDTO_BookFetcher> getAllItemsForMainPage() {
		List<BookProduct> books = bookProductRepository.findAllJoin();
		List<BookProductDTO_BookFetcher> booksDTO = books.stream().map(bookProductMapper::getBookProductDTO__BookFetcher)
				.collect(Collectors.toList());
		return booksDTO;
	}
	
	public BookProductDTO getBookProductForProductPageById(String isbn) {
		// exception instead of null //
		BookProduct bookProduct = bookProductRepository.findById(isbn).orElse(null);
		return bookProductMapper.getBookProductDTO(bookProduct);
	}
}
