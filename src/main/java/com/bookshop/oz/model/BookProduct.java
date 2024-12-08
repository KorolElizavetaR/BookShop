package com.bookshop.oz.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_product")
@Accessors(chain = true)
@NamedEntityGraph(name = "BookProduct.withBookOnly", attributeNodes = @NamedAttributeNode("book"))
public class BookProduct {
	@Id
	@Column(name = "isbn")
	@NotNull
	@NotEmpty
	private String isbn;

	@MapsId
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn(name = "isbn", referencedColumnName = "isbn", nullable = false)
	private Book book;

	@NotNull(message = "Price cannot be null")
	@Digits(integer = 10, fraction = 2, message = "Price must have up to 10 digits in the integer part and 2 in the fractional part")
	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@DecimalMin(value = "0.0000", message = "Discount cannot be less than 0")
	@DecimalMax(value = "1.0000", message = "Discount cannot be more than 1")
	@Digits(integer = 1, fraction = 4, message = "Discount must have up to 1 digit in the integer part and 4 in the fractional part")
	@Column(name = "discount", precision = 5, scale = 4, columnDefinition = "DECIMAL(5, 4) DEFAULT 0")
	private BigDecimal discount = BigDecimal.ZERO;

	@OneToMany(mappedBy = "bookProduct")
	private List<Stock> stock;
}
