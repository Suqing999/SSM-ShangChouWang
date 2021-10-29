package com.baidu.springcloud.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.baidu.springcloud.bean.Movie;
import com.baidu.springcloud.bean.User;
import com.baidu.springcloud.service.MovieServiceFeign;
import com.baidu.springcloud.service.UserService;
import com.netflix.discovery.converters.Auto;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MovieServiceFeign movieServiceFeign; //Feign接口
	
	@GetMapping("/getUser/{id}")
	public User getUser(@PathVariable("id")Integer id) {
		return userService.getUser(id);
	}
	
	@GetMapping("/buyMovie/{uid}/{mid}")
	public Map<String,Object> buyMovie(@PathVariable("uid")Integer uid,
			@PathVariable("mid")Integer mid){
				
			Map<String,Object> map = new HashMap<>();
			
			//获取用户
			User user = userService.getUser(uid);
			map.put("user", user);
			
			//拿到movie（另一个电影服务获取的）
			//涉及到远程调用
			
			Movie movie = movieServiceFeign.getMovieById(mid);
			map.put("movie", movie);
			
			
			return map;
		
	}
	
	
}
