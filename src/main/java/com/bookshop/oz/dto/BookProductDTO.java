package com.bookshop.oz.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.List;

import com.bookshop.oz.model.Book;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BookProductDTO {
	private BookDTO book;
	private BigDecimal price;
	private BigDecimal discount;
	private List<StockDTO> stock;
}
