package com.bookshop.oz.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.oz.dto.OrderDTO;
import com.bookshop.oz.model.enumeration.OrderStatus;
import com.bookshop.oz.service.OrderService;
import com.bookshop.oz.util.AuthUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrdersController {
	private final OrderService orderService;
	
	private final AuthUtil authUtil;

	/**
	 * Страница делится на несколько:
	 * 1.	Заказы, которые прибыли и ожидают того, чтобы их закрыли	
	 * 2.	Заказы, которые должны прибыть
	 * 3.	Успешно закрытые заказы
	 */
	@GetMapping("/my-orders")
	public String orders(Model model) {
		List<OrderDTO> pendingArrivalOrders = orderService.getOrders(authUtil.getPersonFromAuth(), OrderStatus.PENDING_ARRIVAL);
		List<OrderDTO> arrivedOrders = orderService.getOrders(authUtil.getPersonFromAuth(), OrderStatus.ARRIVED);
		List<OrderDTO> closedOrders = orderService.getOrders(authUtil.getPersonFromAuth(), OrderStatus.CLOSED);
		model.addAttribute("pendingArrivalOrders", pendingArrivalOrders);
		model.addAttribute("arrivedOrders", arrivedOrders);
		model.addAttribute("closedOrders", closedOrders);
		return "pages/orders";
	}
}
