package com.baidu.scw.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

		@GetMapping("/hello")
		public String hello(String name) {
			return "OK"+name;
		}
}
