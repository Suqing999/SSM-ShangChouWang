package com.baidu.maven;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloFriendTest {
	@Test
	public void testHelloFriend(){
		HelloFriend helloFriend = new HelloFriend();
		String results = helloFriend.sayHelloToFriend("litingwei");
		assertEquals("Hello litingwei! I am John",results);	
	}
}
