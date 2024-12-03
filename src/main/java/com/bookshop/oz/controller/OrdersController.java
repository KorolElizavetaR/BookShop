package com.bookshop.oz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.oz.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrdersController {
	private final OrderService orderService;

	/**
	 * Страница делится на несколько:
	 * 1.	Заказы, которые прибыли и ожидают того, чтобы их закрыли	
	 * 2.	Заказы, которые должны прибыть
	 * 3.	Успешно закрытые заказы
	 */
	@GetMapping("/orders")
	public String orders() {
		return "pages/orders";
	}
}
