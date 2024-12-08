package com.bookshop.oz.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop.oz.dto.LocationReport;
import com.bookshop.oz.dto.OrderDTO;
import com.bookshop.oz.exception.BookNotFoundException;
import com.bookshop.oz.exception.OrderNotFoundException;
import com.bookshop.oz.exception.StockNotFoundException;
import com.bookshop.oz.mapper.OrderMapper;
import com.bookshop.oz.model.BookProduct;
import com.bookshop.oz.model.LocationPoint;
import com.bookshop.oz.model.Order;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.model.Stock;
import com.bookshop.oz.model.enumeration.OrderStatus;
import com.bookshop.oz.repository.BookProductRepository;
import com.bookshop.oz.repository.LocationPointRepository;
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
	private final LocationPointRepository locationPointRepository;

	private final OrderMapper orderMapper;

	/*
	 * Положить товар в корзину
	 */
	@Transactional(readOnly = false)
	public Order addOrderToShoppingBin(Person person, String bookId) {
		BookProduct bookProduct = bookProductRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException());
		Order order = new Order().setCustomer(person).setLocation(person.getLocationPoint())
				.setStatus(OrderStatus.SHOPPING_BIN).setBookProduct(bookProduct).setQuantity((short) 1);
		return orderRepository.save(order);
	}

	/**
	 * CUSTOMER подтверждает заказ. Изменяем статус заказа. Изменяем кол-во товара.
	 * 
	 * @implNote PENDING_ARRIVAL - был на складе
	 * @implNote ARRIVED - был в магазине ------------------------------ Реализовать
	 *           логику проверки количества товара.
	 * 
	 */
	@Transactional(readOnly = false)
	public Order submitCustomerOrder(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
		Stock stockItem = stockRepository
				.findByLocationIdAndIsbn(order.getLocation().getLocationId(), order.getBookProduct().getIsbn())
				.orElse(stockRepository.findByIsbnAndIsStorageTrue(order.getBookProduct().getIsbn())
						.orElseThrow(() -> new StockNotFoundException()));
		order.setOrderedAt(LocalDateTime.now());
		if (stockItem.getLocation().getIsStorage()) {
			order.setStatus(OrderStatus.PENDING_ARRIVAL);
		} else {
			order.setStatus(OrderStatus.ARRIVED).setArrivedAt(order.getOrderedAt());
		}
		stockItem.setQuantity((short) (stockItem.getQuantity() - order.getQuantity()));
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
	public void updateQuantity(Short quantity, Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
		order.setQuantity(quantity);
	}

	public List<OrderDTO> getOrders(Person person, OrderStatus status) {
		List<Order> orders = orderRepository.findOrdersByPersonAndStatus(person, status);
		List<OrderDTO> ordersDTO = orders.stream().map(orderMapper::getOrderDTO).collect(Collectors.toList());
		return ordersDTO;
	}

	public List<OrderDTO> getOrdersForShopAssistant(LocationPoint location, OrderStatus status) {
		List<Order> orders = orderRepository.findOrdersByLocationAndStatus(location, status);
		List<OrderDTO> ordersDTO = orders.stream().map(orderMapper::getOrderDTO).collect(Collectors.toList());
		return ordersDTO;
	}

	public Optional<Order> getOrderByIdAndLocation(LocationPoint location, Long orderId) {
		Optional<Order> order = orderRepository.findByLocationAndOrderId(location, orderId);
		return order;
	}

	@Transactional(readOnly = false)
	public void approveArrival(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
		order.setStatus(OrderStatus.ARRIVED).setArrivedAt(LocalDateTime.now());
	}

	@Transactional(readOnly = false)
	public void closeOrder(Long orderId, Integer shopAssistantId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
		order.setStatus(OrderStatus.CLOSED).setClosedAt(LocalDateTime.now()).setShopAssistantId(shopAssistantId);
	}

	@Transactional(readOnly = false)
	public void cancelOrder(LocationPoint location, Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
		order.setStatus(OrderStatus.CANCELED).setClosedAt(LocalDateTime.now());

		LocationPoint existingLocation = locationPointRepository.findById(location.getLocationId())
				.orElseThrow(() -> new RuntimeException("Location not found"));

		Optional<Stock> stock = stockRepository.findByLocationIdAndIsbn(location.getLocationId(),
				order.getBookProduct().getIsbn());
		if (stock.isPresent()) {
			stock.get().setQuantity((short) (stock.get().getQuantity() + order.getQuantity()));
		} else {
			Stock newStock = new Stock(existingLocation, order.getBookProduct(), order.getQuantity());
			stockRepository.save(newStock);
		}
	}

	public List<LocationReport> getLocationReports() {
		List<Order> closedOrders = orderRepository.findByStatusAndLocationIsStorageFalse(OrderStatus.CLOSED);

		Map<LocationPoint, List<Order>> ordersGroupedByLocation = closedOrders.stream()
				.collect(Collectors.groupingBy(Order::getLocation));

		List<LocationReport> reports = ordersGroupedByLocation.entrySet().stream()
				.map(entry -> new LocationReport(entry.getKey(), entry.getValue(),
						entry.getValue().stream()
								.mapToDouble(
										order -> order.getBookProduct().getPrice().doubleValue() * order.getQuantity())
								.sum()))
				.toList();
		return reports;
	}
}
