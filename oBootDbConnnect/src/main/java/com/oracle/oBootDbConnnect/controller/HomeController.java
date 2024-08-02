package com.oracle.oBootDbConnnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
// 컨틀롤러에 선언이 되어있다면 static 보다 우선순위가 높다
public class HomeController {
	@GetMapping("/")
	public String home() {
		System.out.println("HomeConroller home start");

		return "home";
	}
}
