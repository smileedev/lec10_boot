package com.gn.spring.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberViewController {
	
	@GetMapping("/login")
	public String loginPage() {
		return "member/login";
	}
	
	@GetMapping("/join")
	public String joinPage() {
		return "member/join";
	}
}
