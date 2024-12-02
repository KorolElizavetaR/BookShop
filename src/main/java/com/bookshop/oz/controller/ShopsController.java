package com.bookshop.oz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

/**
 * Отвечает за вкладку магазины
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/shops")
public class ShopsController {
	/**
	 * На этой странице отображены адреса всех магазинов (isStorage = false) Сверху
	 * страницы написано, к какому магазину относится покупатель. Нажатием Кнопки
	 * пользователь может изменить свой текущий магазин. На заказы это никак не
	 * влияет.
	 */
	@GetMapping
	public String shops() {
		
		return "pages/shops";
	}
}
