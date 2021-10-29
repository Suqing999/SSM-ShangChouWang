package com.baidu.springboot;


import javax.activation.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootMybatisXmlApplicationTests {

	@Autowired
	DataSource dataSource;
	
	@Test
	public void testDataSource() {
		System.out.println(dataSource.getClass());
	}
	
}












 
