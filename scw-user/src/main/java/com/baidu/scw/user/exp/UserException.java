package com.baidu.scw.user.exp;

import com.baidu.scw.user.enums.UserExceptionEnum;

public class UserException extends RuntimeException{
	public UserException() {}
	
	public UserException(UserExceptionEnum enums) {
		super(enums.getMsg());
	}
}
