package com.baidu.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	@GetMapping("/main.html")
	public String main(){
		return "main";
	}
	
	

}
