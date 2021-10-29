package com.baidu.scw.user.enums;

public enum UserExceptionEnum {
	USER_EXIST(1,"用户已经存在"),
	EMAIL_EXIST(2,"邮箱已经存在"),
	USER_LOCKED(3,"用户已经被锁定"),
	USER_SAVE_ERROR(4,"用户注册失败"), 
	USER_UNEXIST(5,"用户不存在"), 
	PASSWORD_ERROR(6,"密码错误");
	private int code;
	
	private String msg;
	
	private UserExceptionEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	 
}