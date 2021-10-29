package com.baidu.springcloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.springcloud.bean.User;
import com.baidu.springcloud.dao.UserDao;
import com.baidu.springcloud.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public User getUser(Integer id) {
		// TODO Auto-generated method stub
		return userDao.getUser(id);
	}

}
