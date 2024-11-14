package com.bookshop.oz.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.BookProductDTO;
import com.bookshop.oz.dto.BookProductDTO__BookFetcher;
import com.bookshop.oz.model.BookProduct;

@Component
public class BookProductMapper {
	@Autowired
	private ModelMapper modelMapper;

	public BookProductDTO__BookFetcher getBookProductDTO__BookFetcher(BookProduct bookProduct) {
		return modelMapper.map(bookProduct, BookProductDTO__BookFetcher.class);
	}
}
