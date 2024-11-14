package com.bookshop.oz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookshop.oz.dto.BookProductDTO;
import com.bookshop.oz.dto.BookProductDTO__BookFetcher;
import com.bookshop.oz.mapper.BookProductMapper;
import com.bookshop.oz.mapper.StockMapper;
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

	public List<BookProductDTO__BookFetcher> getAllItemsForMainPage() {
		List<BookProduct> books = bookProductRepository.findAllJoin();
		List<BookProductDTO__BookFetcher> booksDTO = books.stream().map(bookProductMapper::getBookProductDTO__BookFetcher)
				.collect(Collectors.toList());
		return booksDTO;
	}
}
