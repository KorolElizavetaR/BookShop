package com.bookshop.oz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.oz.dto.PersonDTORegister;
import com.bookshop.oz.model.Person;
import com.bookshop.oz.security.PersonDetails;
import com.bookshop.oz.service.PersonService;
import com.bookshop.oz.validator.UniqueEmailValidator;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/personal")
@RequiredArgsConstructor
public class AuthController {
	private final UniqueEmailValidator uniqueEmailValidator;
	private final PersonService personDetailsService;
	
	@GetMapping("/login")
	public String loginPage() {
		return "personal/login";
	}
	
	@GetMapping()
	public String personalPage(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails personDetails = (PersonDetails)auth.getPrincipal();
		Person person = personDetails.getPerson();
		model.addAttribute("person", person);
		return "/personal/page";
	}
	
	@GetMapping ("/reg")
	public String regPage(Model model)
	{
		PersonDTORegister person = new PersonDTORegister();
		model.addAttribute("person", person);
		return "/personal/reg";
	}
	
	@PostMapping("/reg")
	public String performReg(@ModelAttribute ("person") @Valid PersonDTORegister person, BindingResult bindingResult)
	{
		if (bindingResult.hasErrors())
			return "/personal/reg";
		personDetailsService.register(person);
		return "redirect:/personal/login";
	}
}
