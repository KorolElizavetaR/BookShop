package com.bookshop.oz.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.oz.dto.OrderDTOShoppingBin;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.service.OrderService;
import com.bookshop.oz.util.AuthUtil;

import lombok.RequiredArgsConstructor;

/**
 * @implNote - изменение кол-ва товаров в корзине 
 * @implNote - кнопка заказать
 * @implNote - кнопка отменить
 * @implNote - указать итоговую цену (цена товара * кол-во товаров)
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/shopping_bin")
public class ShoppingBinController {
	private final OrderService orderService;

	private final AuthUtil authUtil;

	@GetMapping
	public String shoppingBin(Model model) {
		boolean isLoggedIntoSystem = authUtil.isLoggedIntoSystem();
		model.addAttribute("isLoggedIntoSystem", isLoggedIntoSystem);
		if (authUtil.isLoggedIntoSystem()) {
			Person person = authUtil.getPersonFromAuth();
			List<OrderDTOShoppingBin> shoppingBin = orderService.getOrdersFromShoppingBin(person);
			model.addAttribute("shoppingBin", shoppingBin);
		}
		return "pages/shopping_bin";
	}

	/**
	 * Отвечает за удаление товара из корзины.
	 */
	@DeleteMapping()
	public String removeFromShoppingBin() {

	}

	/**
	 * Переносит товар из корзины в заказы.
	 * Логика перемещения указана в сервисном слое
	 */
	@PatchMapping("/{id}")
	public String submitOrderItem(@PathVariable("id") Integer id) {
		
	}
	
	@PatchMapping("/{id}/change_quantity")
	public String submitChangeQuantity(@PathVariable("id") Integer id, Integer quantity) {
		System.out.println(quantity);
		return "redirect:/shopping_bin";
	}
}
