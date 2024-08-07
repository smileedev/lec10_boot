package com.gn.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping({"","/"})
	public String home() {
		System.out.println("수정");
		// src/main/resources/templates/home.html
		return "home";
	}
}
