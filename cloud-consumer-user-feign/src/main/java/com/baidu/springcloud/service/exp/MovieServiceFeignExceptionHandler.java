package com.baidu.springcloud.service.exp;

import org.springframework.stereotype.Component;

import com.baidu.springcloud.bean.Movie;
import com.baidu.springcloud.service.MovieServiceFeign;

@Component
public class MovieServiceFeignExceptionHandler implements MovieServiceFeign {

	@Override
	public Movie getMovieById(Integer id) {
		Movie movie = new Movie();
		movie.setId(-100);
		movie.setName("无");
		return movie;
	}

}
