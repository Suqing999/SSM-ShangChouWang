package com.baidu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient //启动服务注册和发现功能
@SpringBootApplication
public class CloudProviderMovieApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(CloudProviderMovieApplication.class, args);
	}

}
