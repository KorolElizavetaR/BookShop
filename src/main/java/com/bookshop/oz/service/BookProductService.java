package com.bookshop.oz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop.oz.dao.BookProductDao;
import com.bookshop.oz.dto.BookProductDTO;
import com.bookshop.oz.dto.BookProductDTOItem;
import com.bookshop.oz.exception.BookNotFoundException;
import com.bookshop.oz.mapper.BookProductMapper;
import com.bookshop.oz.model.Book;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.repository.BookProductRepository;
import com.bookshop.oz.repository.BookRepository;

import lombok.RequiredArgsConstructor;

/**
 * Отвечает за модели Book и BookProduct.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookProductService {
	private final BookProductRepository bookProductRepository;
	private final BookRepository bookRepository;

	private final BookProductMapper bookProductMapper;

	private final BookProductDao bookProductDao;

	public List<BookProductDTOItem> getItems(String order, String like) {
		List<BookProduct> books = bookProductDao.getItems(order, like);
		List<BookProductDTOItem> booksDTO = books.stream().map(bookProductMapper::getBookProductDTOItem)
				.collect(Collectors.toList());
		return booksDTO;
	}

	public BookProductDTO getBookProductForProductPageById(String isbn) {
		BookProduct bookProduct = bookProductRepository.findById(isbn).orElse(null);
		return bookProductMapper.getBookProductDTO(bookProduct);
	}

	public BookProduct getBookProduct(String isbn) {
		BookProduct bookProduct = bookProductRepository.findById(isbn).orElseThrow(() -> new BookNotFoundException());
		return bookProduct;
	}

	public List<BookProductDTOItem> findBookLike(String str) {
		List<BookProduct> books = bookProductRepository.findByBookNameContainingIgnoreCase(str);
		List<BookProductDTOItem> booksDTO = books.stream().map(bookProductMapper::getBookProductDTOItem)
				.collect(Collectors.toList());
		return booksDTO;
	}

	@Transactional(readOnly = false)
	public BookProduct createBook(BookProduct book) {
		book.getBook().setIsbn(book.getIsbn());
		;
		return bookProductRepository.save(book);
	}

	@Transactional(readOnly = false)
	public BookProduct editBook(BookProduct newBook) {
		BookProduct existingBookProduct = bookProductRepository.getBookByIsbn(newBook.getIsbn())
				.orElseThrow(BookNotFoundException::new);

		// Update the associated Book
		Book existingBook = existingBookProduct.getBook();
		Book newBookData = newBook.getBook();

		existingBook.setTitle(newBookData.getTitle()); // Example: Update fields
		existingBook.setAuthor(newBookData.getAuthor());
		// Add other field updates as needed

		// Save the updated Book entity
		bookRepository.save(existingBook);

		// Update the BookProduct fields
		existingBookProduct.setPrice(newBook.getPrice());
		// Add other field updates as needed

		// Save and return the updated BookProduct
		return bookProductRepository.save(existingBookProduct);
	}
}
