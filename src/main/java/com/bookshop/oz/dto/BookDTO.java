package com.bookshop.oz.dto;

import java.math.BigDecimal;

import com.bookshop.oz.model.BookProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
	private String isbn;
	private String title;
	private String author;
	private String publisher;
	private String description;
	private String coverUrl;
	private BookProduct bookProduct;
}
