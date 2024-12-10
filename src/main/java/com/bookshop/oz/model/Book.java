package com.bookshop.oz.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
@Accessors(chain = true)
public class Book {
	@Id
	@Column(name = "isbn", length = 13, nullable = false)
	@Pattern(regexp = "\\d{13}", message = "ISBN обязательно должен быть идентификатором из 13 цифр.")
	private String isbn;

	@NotNull(message = "Название книги не должно быть пустым.")
	@Size(max = 255, message = "Title must be 255 characters or less")
	@Column(name = "title", length = 255, nullable = false)
	private String title;

	@NotNull(message = "Author cannot be null")
	@Size(max = 127, message = "Author name must be 127 characters or less")
	@Column(name = "author", length = 127, nullable = false)
	private String author;

	@NotNull(message = "Publisher cannot be null")
	@Size(max = 127, message = "Publisher name must be 127 characters or less")
	@Column(name = "publisher", length = 127, nullable = false)
	private String publisher;

	@Column(name = "description")
	private String description;

	@Column(name = "cover_url")
	private String coverUrl;
	
	@OneToOne (mappedBy = "book", fetch = FetchType.LAZY)
	private BookProduct bookProduct;
}
