package com.bookshop.oz.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.oz.dto.OrderDTO;
import com.bookshop.oz.model.LocationPoint;
import com.bookshop.oz.model.enumeration.OrderStatus;
import com.bookshop.oz.service.OrderService;
import com.bookshop.oz.util.AuthUtil;

import lombok.RequiredArgsConstructor;

/**
 * Эндпоинты, доступные продавцу 1. может подтвердить, что товар пришел на точку
 * 2. может искать заказы по номеру 3. может подтверждать или отменять ПРИШЕДШИЕ
 * заказы.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("orders")
public class ShopAssistantController {
	private final OrderService orderService;

	private final AuthUtil authUtil;

	@GetMapping()
	public String orders(Model model) {
		LocationPoint location = authUtil.getPersonFromAuth().getLocationPoint();
		List<OrderDTO> pendingArrivalOrders = orderService.getOrdersForShopAssistant(location, OrderStatus.PENDING_ARRIVAL);
		List<OrderDTO> arrivedOrders = orderService.getOrdersForShopAssistant(location, OrderStatus.PENDING_ARRIVAL);
		model.addAttribute("pendingArrivalOrders", pendingArrivalOrders);
		model.addAttribute("arrivedOrders", arrivedOrders);
		return "/pages/shop_assistant_orders";
	}
	
	/**
	 * @param id - id заказа
	 */
	@PatchMapping("/{id}/arrived")
	public String approveArrival(@PathVariable ("id") Long id) {
		
	}
}
