package com.bookshop.oz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	   @GetMapping("/forbidden")
	    public String forbiddenPage(Model model) {
	        model.addAttribute("errorMessage", "You don't have permission to access this page.");
	        return "errors/error403"; // Path to your custom error page (e.g., `src/main/resources/templates/errors/error403.html`)
	    }
}
