package com.gn.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	private static final Logger LOGGER
		= LoggerFactory.getLogger(HomeController.class);
	
	@GetMapping({"","/"})
	public String home() {
		LOGGER.trace("1단계 경보");
		LOGGER.debug("2단계: 오류 찾기");
		LOGGER.info("게시판 프로그램 시작");
		LOGGER.warn("4단계: 경고");
		LOGGER.error("5단계: 오류 발생");
		// src/main/resources/templates/home.html
		return "home";
	}
}
