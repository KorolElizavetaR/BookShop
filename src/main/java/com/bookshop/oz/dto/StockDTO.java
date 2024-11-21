package com.bookshop.oz.dto;

import com.bookshop.oz.model.Book;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.model.LocationPoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 		Book is never used by itself, that is why StockDTO handles three models:
 * 		 Stock -> BookProduct -> Book
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
	private LocationPointDTO location;
	private Short quantity;
}
