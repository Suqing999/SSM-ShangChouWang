package com.baidu.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.baidu.springcloud.bean.Movie;
import com.baidu.springcloud.service.exp.MovieServiceFeignExceptionHandler;

@FeignClient(value="cloud-provider-movie",fallback=MovieServiceFeignExceptionHandler.class)
public interface MovieServiceFeign {
		
	@GetMapping("/getMovieById/{id}")
	public Movie getMovieById(@PathVariable("id") Integer id) ;
	
}
