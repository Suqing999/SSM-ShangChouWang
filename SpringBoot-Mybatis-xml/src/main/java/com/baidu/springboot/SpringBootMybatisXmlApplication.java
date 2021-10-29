package com.baidu.springboot;

import javax.annotation.ManagedBean;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.baidu.springboot.mapper")
@SpringBootApplication
public class SpringBootMybatisXmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMybatisXmlApplication.class, args);
	}

}
