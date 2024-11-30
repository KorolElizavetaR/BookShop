package com.bookshop.oz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop.oz.exception.BookNotFoundException;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.model.Order;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.model.enumeration.OrderStatus;
import com.bookshop.oz.repository.BookProductRepository;
import com.bookshop.oz.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

/**
 * Отвечает за модель Order
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
	private final OrderRepository orderRepository;
	private final BookProductRepository bookProductRepository;

	@Transactional(readOnly = false)
	public Order addOrderToShoppingBin(Person person, String bookId) {
		BookProduct bookProduct = bookProductRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException());
		Order order = new Order().setCustomer(person).setLocation(person.getLocationPoint())
				.setStatus(OrderStatus.SHOPPING_BIN).setBookProduct(bookProduct).setQuantity(1);
		return orderRepository.save(order);
	}

}
