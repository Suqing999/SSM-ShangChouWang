package com.baidu.movie.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baidu.movie.bean.User;
import com.baidu.movie.service.UserService;

@Controller
public class DispacherController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	
	 
	 @RequestMapping("/doLogin") 
	 public String doLogin(String username,String password,HttpSession session,Model model){
		 
		User user = userService.getUserByLogin(username,password);
		if(user==null) {
			return "error";
		}
		session.setAttribute("name", user.getName());
		System.out.println(user.getName());
		return "redirect:http://127.0.0.1:6010/index.html?name="+user.getName();
	 }
	 
	 @RequestMapping("/logout") 
	 public String logout(){
		 
		return "redirect:http://127.0.0.1:6010/index.jsp";
	 }
	 
	 @RequestMapping("/regist") 
	 public String regist(){
		 return "regist";
	 }
	 
	 @RequestMapping("/doRegist") 
	 public String doregist(String username,String password,String name,HttpSession session,Model model){
		
		 
		 
			int flag = userService.setUserByRegist(username,password,name);
			if(flag<=0) {
				return "error";
			}
			System.out.println("ok");
			return "redirect:http://127.0.0.1:6010/index.html?name="+name;
		  
	 }
}
