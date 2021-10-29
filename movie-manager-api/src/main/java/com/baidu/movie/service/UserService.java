package com.baidu.movie.service;

import com.baidu.movie.bean.User;

public interface UserService {

	User getUserByLogin(String username, String password);

	int setUserByRegist(String username, String password, String name);

}
