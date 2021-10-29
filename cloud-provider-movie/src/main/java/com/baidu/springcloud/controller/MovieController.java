package com.baidu.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.springcloud.bean.Movie;
import com.baidu.springcloud.service.MovieService;

//@Controller
@RestController  //组合注解
public class MovieController {
	@Autowired 
	MovieService movieService;
	
	@Value("${server.port}")
	int port;
	//@ResponseBody
	@GetMapping("/getMovieById/{id}")
	public Movie getMovieById(@PathVariable("id") Integer id) {
		
		System.out.println("Port="+port);
		return movieService.getMovieById(id);
	}
}
