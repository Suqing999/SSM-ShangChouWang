package com.baidu.springcloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.springcloud.bean.Movie;
import com.baidu.springcloud.dao.MovieDao;
import com.baidu.springcloud.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService{
	
	@Autowired
	MovieDao movieDao;

	@Override
	public Movie getMovieById(Integer id) {
		 
		return movieDao.getMovieById(id);
	}
}
