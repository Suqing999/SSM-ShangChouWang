package com.baidu.security.component;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class testPass {
	  private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	    public static String encodePassword(String password){
	        return bCryptPasswordEncoder.encode(password);
	    }
	 
	    public static void main(String[] args) {
	        String password = "123456";
	        String pwd = encodePassword(password);
	        System.out.println(pwd);
	    }
}

 