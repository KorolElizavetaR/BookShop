package com.bookshop.oz.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.oz.dto.LocationPointDTOShops;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.service.LocationPointService;
import com.bookshop.oz.service.PersonService;
import com.bookshop.oz.util.AuthUtil;
import com.bookshop.oz.util.PersonDetailsSecurity;

import lombok.RequiredArgsConstructor;

/**
 * Отвечает за вкладку магазины
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/shops")
public class ShopsController {
	private final AuthUtil authUtil;
	private final LocationPointService locationPointService;
	private final PersonService personService;

	/**
	 * На этой странице отображены адреса всех магазинов (isStorage = false) Сверху
	 * страницы написано, к какому магазину относится покупатель. Нажатием Кнопки
	 * пользователь может изменить свой текущий магазин. На заказы это никак не
	 * влияет.
	 */
	@GetMapping
	public String shops(Model model) {
		Person person;
		boolean isAuthorized = authUtil.isLoggedIntoSystem();
		model.addAttribute("isAuthorized", isAuthorized);
		if (isAuthorized) {
			person = authUtil.getPersonFromAuth();
			model.addAttribute("personLocation", person.getLocationPoint());
		}
		List<LocationPointDTOShops> shops = locationPointService.getShops();
		model.addAttribute("shops", shops);
		return "pages/shops";
	}

	/**
	 * Исправляет текущую локацию человека на другую
	 */
	@PatchMapping("/{id}")
	public String changeLocationPoint(@PathVariable("id") String id) {
		personService.changeLocationPoint(id);
		return "redirect:/shops";
	}
}
