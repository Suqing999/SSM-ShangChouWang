package com.baidu.atcrowdfunding.component;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.baidu.atcrowdfunding.bean.TAdmin;

public class TSecurityAdmin extends User{
	
	
	TAdmin admin;
	
	public TSecurityAdmin(TAdmin admin,Set<GrantedAuthority> authority) {
		//super(admin.getLoginacct(), admin.getUserpswd(),authority);
		
		super(admin.getLoginacct(), admin.getUserpswd(),true,true,true,true,authority);
		
		this.admin = admin;
	}
}
