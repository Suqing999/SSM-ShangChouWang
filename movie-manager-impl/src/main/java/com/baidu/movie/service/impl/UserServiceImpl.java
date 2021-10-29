package com.baidu.movie.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.movie.bean.User;
import com.baidu.movie.bean.UserExample;
import com.baidu.movie.mapper.UserMapper;
import com.baidu.movie.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserMapper userMapper;
	
	@Override
	public User getUserByLogin(String username, String password) {
		
		UserExample example = new UserExample();
		
		example.createCriteria().andUsernameEqualTo(username);
		
		List<User> list = userMapper.selectByExample(example);
		
		if(list!=null) {
			User user = list.get(0);
			
			if(user.getPassword().equals(password)) {
				return user;
			}else {
				return null;
			}
		}else {
			return null;
		}
		
	}

	@Override
	public int setUserByRegist(String username, String password, String name) {
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setUsername(username);
		
		return userMapper.insertSelective(user);
	}

}
