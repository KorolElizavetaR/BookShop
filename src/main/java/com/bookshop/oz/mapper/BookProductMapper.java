package com.bookshop.oz.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookshop.oz.dto.BookDTO;
import com.bookshop.oz.dto.BookProductDTO;
import com.bookshop.oz.dto.BookProductDTOBooksForMainPage;
import com.bookshop.oz.dto.StockDTO;
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
	@Autowired
	private final StockMapper stockMapper;

	@PostConstruct
	public void setupModelMapper() {
		modelMapper.createTypeMap(BookProduct.class, BookProductDTOBooksForMainPage.class).setPostConverter(context -> {
			BookProduct source = context.getSource();
			BookProductDTOBooksForMainPage bookProduct_bookFetcherDestination = context.getDestination();
			if (source.getBook() != null) {
				bookProduct_bookFetcherDestination.setBook(bookMapper.getBookDTO(source.getBook()));
			}
			return bookProduct_bookFetcherDestination;
		});

//		modelMapper.createTypeMap(BookProduct.class, BookProductDTO.class).setPostConverter(context -> {
//			BookProduct source = context.getSource();
//			BookProductDTO bookProductDestination = context.getDestination();
//			if (source.getBook() != null) {
//				bookProductDestination.setBook(bookMapper.getBookDTO(source.getBook()));
//			}
//			if (source.getStock() != null) {
//				bookProductDestination.setStock(stockMapper.getStockListDTO(source.getStock()));
//			}
//			return bookProductDestination;
//		});
	}

	public BookProductDTOBooksForMainPage getBookProductDTO__BookFetcher(BookProduct bookProduct) {
		return modelMapper.map(bookProduct, BookProductDTOBooksForMainPage.class);
	}

	public BookProductDTO getBookProductDTO(BookProduct bookProduct) {
		BookDTO bookDTO = bookMapper.getBookDTO(bookProduct.getBook());
		// Maybe causes stackOverflow ??
		List<StockDTO> stockListDTO = stockMapper.getStockListDTO(bookProduct.getStock());
		BookProductDTO bookProductDTO = new BookProductDTO();
		bookProductDTO.setDiscount(bookProduct.getDiscount()).setPrice(bookProduct.getPrice()).setBook(bookDTO)
				.setStock(stockListDTO);
		return bookProductDTO;
	}
}
