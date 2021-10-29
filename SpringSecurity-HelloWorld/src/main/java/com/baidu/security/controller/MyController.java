package com.baidu.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {
	
	
	@RequestMapping("/unTo.html")
	public String unTo() {
		return "unTo";
	}
}
