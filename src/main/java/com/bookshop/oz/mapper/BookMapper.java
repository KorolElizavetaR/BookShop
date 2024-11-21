package com.bookshop.oz.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.BookDTO;
import com.bookshop.oz.model.Book;

@Component
public class BookMapper {
	@Autowired
	private ModelMapper modelMapper;

	public BookDTO getBookDTO(Book book) {
		return modelMapper.map(book, BookDTO.class);
	}
}
