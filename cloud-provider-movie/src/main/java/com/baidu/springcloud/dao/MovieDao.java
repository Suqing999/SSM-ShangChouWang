package com.baidu.springcloud.dao;

import org.springframework.stereotype.Repository;

import com.baidu.springcloud.bean.Movie;

@Repository
public class MovieDao {

	public Movie getMovieById(Integer id) {
		 Movie movie = new Movie();
		 movie.setId(id);
		 movie.setName("猛龙过江");
		return movie;
	}

}
