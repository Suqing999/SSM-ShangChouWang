package com.baidu.springboot;

import javax.annotation.ManagedBean;
import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@MapperScan("com.baidu.springboot.mapper")
@SpringBootApplication
public class SpringBootMybatisXmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMybatisXmlApplication.class, args);
	}

}
