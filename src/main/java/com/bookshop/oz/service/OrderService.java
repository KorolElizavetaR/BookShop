package com.bookshop.oz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop.oz.dto.OrderDTOShoppingBin;
import com.bookshop.oz.exception.BookNotFoundException;
import com.bookshop.oz.mapper.OrderMapper;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.model.Order;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.model.enumeration.OrderStatus;
import com.bookshop.oz.repository.BookProductRepository;
import com.bookshop.oz.repository.OrderRepository;
import com.bookshop.oz.util.AuthUtil;

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
	
	//private final AuthUtil authUtil;
	
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
	 */
	public Order submitOrderCustomer() {
		
	}

	
	public List<OrderDTOShoppingBin> getOrdersFromShoppingBin(Person person) {
		List <Order> orders = orderRepository.findOrdersByPersonAndStatus(person, OrderStatus.SHOPPING_BIN);
		List <OrderDTOShoppingBin> ordersDTO = orders.stream().map(orderMapper::getOrderDTOShoppingBin).collect(Collectors.toList());
		return ordersDTO;
	}
}
