package com.baidu.scw.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringSessionController {
	
	@GetMapping("/set")
	 public String test1(HttpSession session) {
		session.setAttribute("msg", "Hello");
		return "ok";
	}
	
	@GetMapping("/get")
	 public String test2(HttpSession session) {
		Object attribute = session.getAttribute("msg");
		return (String)attribute;
	}
	
}
