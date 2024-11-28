package com.bookshop.oz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop.oz.model.Person;
import com.bookshop.oz.security.PersonDetails;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/personal")
@RequiredArgsConstructor
public class AuthController {
	
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
		return "personal/page";
	}
}
