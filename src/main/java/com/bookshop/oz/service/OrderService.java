package com.bookshop.oz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop.oz.dto.OrderDTOShoppingBin;
import com.bookshop.oz.exception.BookNotFoundException;
import com.bookshop.oz.exception.OrderNotFoundException;
import com.bookshop.oz.exception.StockNotFoundException;
import com.bookshop.oz.mapper.OrderMapper;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.model.Order;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.model.Stock;
import com.bookshop.oz.model.enumeration.OrderStatus;
import com.bookshop.oz.repository.BookProductRepository;
import com.bookshop.oz.repository.OrderRepository;
import com.bookshop.oz.repository.StockRepository;

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
	private final StockRepository stockRepository;

	private final OrderMapper orderMapper;

	/*
	 * Положить товар в корзину
	 */
	@Transactional(readOnly = false)
	public Order addOrderToShoppingBin(Person person, String bookId) {
		BookProduct bookProduct = bookProductRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException());
		Order order = new Order().setCustomer(person).setLocation(person.getLocationPoint())
				.setStatus(OrderStatus.SHOPPING_BIN).setBookProduct(bookProduct).setQuantity(1);
		return orderRepository.save(order);
	}

	/**
	 * CUSTOMER подтверждает заказ
	 * 
	 * @implNote PENDING_ARRIVAL - был на складе
	 * @implNote ARRIVED - был в магазине 
	 * ------------------------------ 
	 * Реализовать логику проверки количества товара.
	 * 
	 */
	@Transactional(readOnly = false)
	public Order submitOrderCustomer(Person person, String isbn) {
		Order order = orderRepository.findOrderByPersonStatusAndIsbn(person, OrderStatus.SHOPPING_BIN, isbn);
		Stock stockItem = stockRepository.findByLocationIdAndIsbn(order.getLocation().getLocationId(), isbn).orElse(
				stockRepository.findByIsbnAndIsStorageTrue(isbn).orElseThrow(() -> new StockNotFoundException()));
		if (stockItem.getLocation().getIsStorage()) {
			order.setStatus(OrderStatus.PENDING_ARRIVAL);
		} else {
			order.setStatus(OrderStatus.ARRIVED);
		}
		return order;
	}

	/**
	 * Отвечает за удаление из корзины. ПРИМ: Этот метод ТОЛЬКО для товаров в
	 * корзине. Остальные товары помечаются как CANCELED если покупатель отказался
	 * от товара на любом другом этапе.
	 */
	@Transactional(readOnly = false)
	public void removeItemFromShoppingBin(Long orderId) {
		orderRepository.deleteById(orderId);
	}

	/**
	 * 
	 * @param quantity новое количество. В идеальном мире данный параметр должен
	 *                 проверяться. Если Stock.quantity < quantity, при заказе заказ
	 *                 разобъется на два. ПОКА НЕ РЕАЛИЗОВАНО (и наверное не будет
	 *                 реализовано лол)
	 * @param orderId
	 */
	@Transactional(readOnly = false)
	public void updateQuantity(Integer quantity, Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
		order.setQuantity(quantity);
	}

	public List<OrderDTOShoppingBin> getOrdersFromShoppingBin(Person person) {
		List<Order> orders = orderRepository.findOrdersByPersonAndStatus(person, OrderStatus.SHOPPING_BIN);
		List<OrderDTOShoppingBin> ordersDTO = orders.stream().map(orderMapper::getOrderDTOShoppingBin)
				.collect(Collectors.toList());
		return ordersDTO;
	}
}
