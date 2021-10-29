package com.baidu.scw.user.testController;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@ResponseBody
	@GetMapping("/test1")
	public String test1() throws SQLException {
		Connection conn = dataSource.getConnection();
		System.out.println(conn);
		conn.close();
		return "ok";
	}
	
	@ResponseBody
	@GetMapping("/test2")
	public String test2() {
		stringRedisTemplate.opsForValue().set("key111", "value111");
		return "ok2";
	}
	
	@ResponseBody
	@GetMapping("/test3")
	public String test3() {
		
		return stringRedisTemplate.opsForValue().get("key111");
	}
	
}
