package com.baidu.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {
	 public static void main(String[] args) {
			Logger log = LoggerFactory.getLogger(Slf4jTest.class);
			log.debug("debug消息id={},name={}",1,"zhangsan"); //用于调试程序
	} 
}
