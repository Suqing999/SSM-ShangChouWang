package com.baidu.security.component;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderImpl implements PasswordEncoder {

 

	@Override
	public String encode(CharSequence rawPassword) {
		return MD5Util.digest(rawPassword.toString()); //加密返回
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword.equals(MD5Util.digest(rawPassword.toString()));
	}

}
