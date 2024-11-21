package com.bookshop.oz.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.BookProductDTO;
import com.bookshop.oz.dto.BookProductDTO__BookFetcher;
import com.bookshop.oz.model.Book;
import com.bookshop.oz.model.BookProduct;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookProductMapper {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private final BookMapper bookMapper;

	@PostConstruct
	public void setupModelMapper() {
		modelMapper.createTypeMap(BookProduct.class, BookProductDTO__BookFetcher.class).setPostConverter(context -> {
			BookProduct source = context.getSource();
			BookProductDTO__BookFetcher destination = context.getDestination();
			if (source.getBook() != null) {
				destination.setBook(bookMapper.getBookDTO(source.getBook()));
			}
			return destination;
		});
	}

	public BookProductDTO__BookFetcher getBookProductDTO__BookFetcher(BookProduct bookProduct) {
		return modelMapper.map(bookProduct, BookProductDTO__BookFetcher.class);
	}
}
