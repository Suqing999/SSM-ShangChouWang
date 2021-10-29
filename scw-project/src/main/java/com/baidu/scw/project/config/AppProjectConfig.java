package com.baidu.scw.project.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.baidu.scw.project.component.OssTemplate;

@SpringBootConfiguration
public class AppProjectConfig {
	
	@ConfigurationProperties(prefix="oss") //把oss开头的注入到bean中
	@Bean
	public OssTemplate ossTemplate() {
		return new OssTemplate();
	}

}
