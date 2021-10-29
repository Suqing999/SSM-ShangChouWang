package com.baidu.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ComponentScan("com.baidu.springboot")  //指定掃描地方
@SpringBootApplication
public class SpringBootHelloworld3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHelloworld3Application.class, args);
	}

}
