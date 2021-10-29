package com.baidu.springcloud.dao;

import org.springframework.stereotype.Repository;

import com.baidu.springcloud.bean.User;

@Repository
public class UserDao {
	public User getUser(Integer id){
		User user = new User();
		user.setId(id);
		user.setUsername("zhangsan");
		return user;
	}
}