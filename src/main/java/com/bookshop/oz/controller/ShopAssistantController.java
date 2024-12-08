package com.bookshop.oz.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookshop.oz.dto.OrderDTO;
import com.bookshop.oz.model.LocationPoint;
import com.bookshop.oz.model.Order;
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
	public String orders(Model model, @RequestParam(value = "order", required = false) String orderId) {
		LocationPoint location = authUtil.getPersonFromAuth().getLocationPoint();
		Long orderIdDig;
		if (orderId != null && !orderId.isBlank()) {
			try {
				orderIdDig = Long.valueOf(orderId);
				Optional<Order> orderDTO = orderService.getOrderByIdAndLocation(location, orderIdDig);
				if (orderDTO.isPresent()) {
					model.addAttribute("searched_order", orderDTO.get());
					model.addAttribute("isArrived", orderDTO.get().getStatus() == OrderStatus.ARRIVED);
				} else {
					model.addAttribute("searched_order", "Заказ не найден");
				}
			} catch (NumberFormatException ex) {
				model.addAttribute("searched_order", "Параметр должен быть целочисленным значением.");
			}
		} else {
			List<OrderDTO> pendingArrivalOrders = orderService.getOrdersForShopAssistant(location,
					OrderStatus.PENDING_ARRIVAL);
			List<OrderDTO> arrivedOrders = orderService.getOrdersForShopAssistant(location, OrderStatus.ARRIVED);
			model.addAttribute("pendingArrivalOrders", pendingArrivalOrders);
			model.addAttribute("arrivedOrders", arrivedOrders);
		}
		return "/pages/shop_assistant_orders";
	}

	/**
	 * @param id - id заказа
	 */
	@PatchMapping("/{id}-arrived")
	public String approveArrival(@PathVariable("id") Long id) {
		orderService.approveArrival(id);
		return "redirect:/orders";
	}

	@PatchMapping("/{id}-closed")
	public String closeOrder(@PathVariable("id") Long id) {
		orderService.closeOrder(id, authUtil.getPersonFromAuth().getPersonId());
		return "redirect:/orders";
	}

	@PatchMapping("/{id}-cancel")
	public String cancelOrder(@PathVariable("id") Long id) {
		LocationPoint location = authUtil.getPersonFromAuth().getLocationPoint();
		orderService.cancelOrder(location, id);
		return "redirect:/orders";
	}
}
