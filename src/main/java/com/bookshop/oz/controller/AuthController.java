package com.bookshop.oz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.oz.dto.PersonDTO;
import com.bookshop.oz.dto.PersonDTOInfo;
import com.bookshop.oz.dto.PersonDTOPasswords;
import com.bookshop.oz.dto.PersonDTORegister;
import com.bookshop.oz.mapper.PersonMapper;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.service.PersonService;
import com.bookshop.oz.util.AuthUtil;
import com.bookshop.oz.util.PersonDetailsSecurity;
import com.bookshop.oz.validator.UniqueEmailValidator;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/personal")
@RequiredArgsConstructor
public class AuthController {
	private final PersonService personDetailsService;

	private final AuthUtil authUtil;

	private final PersonMapper personMapper;

	@GetMapping("/login")
	public String loginPage() {
		return "personal/login";
	}

	/**
	 * Страница с общей информацией о пользователе. под общей информацией есть
	 * возможность изменить определенные поля Пароль Имя и фамилию Номер телефона
	 */
	@GetMapping()
	public String personalPage(Model model) {
		Person person = authUtil.getPersonFromAuth();
		model.addAttribute("person", person);
		return "/personal/page";
	}

	@GetMapping("/edit")
	public String editPersonalPage(Model model) {
		Person person = authUtil.getPersonFromAuth();
		PersonDTOInfo personInfo = personMapper.getPersonDTOInfo(person);
		model.addAttribute("personInfo", personInfo);
		PersonDTOPasswords passwords = new PersonDTOPasswords();
		model.addAttribute("passwords", passwords);
		return "/personal/page_edit";
	}

	@PatchMapping("/edit/change-info")
	public String changeInfo(@ModelAttribute("personInfo") @Valid PersonDTOInfo person, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			// If there are errors, add the person object and errors to the model
			model.addAttribute("passwords", new PersonDTOPasswords()); // Ensure passwords is in model for password form
			return "/personal/page_edit"; // return the same view with validation errors
		}
		personDetailsService.changeInfo(person);
		return "redirect:/personal/edit"; // Redirect to prevent resubmission
	}

	@PatchMapping("/edit/change-password")
	public String changePassword(@ModelAttribute("passwords") @Valid PersonDTOPasswords passwords,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			Person person = authUtil.getPersonFromAuth();
			PersonDTOInfo personInfo = personMapper.getPersonDTOInfo(person);
			model.addAttribute("personInfo", personInfo);
			return "/personal/page_edit";
		}

		// Proceed with changing the password if no errors
		personDetailsService.changePassword(passwords);
		return "redirect:/personal/edit"; // Redirect to prevent resubmission
	}

	@GetMapping("/reg")
	public String regPage(Model model) {
		PersonDTORegister person = new PersonDTORegister();
		model.addAttribute("person", person);
		return "/personal/reg";
	}

	@PostMapping("/reg")
	public String performReg(@ModelAttribute("person") @Valid PersonDTORegister person, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "/personal/reg";
		personDetailsService.register(person);
		return "redirect:/personal/login";
	}
}
