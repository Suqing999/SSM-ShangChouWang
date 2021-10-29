package com.baidu.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baidu.atcrowdfunding.bean.TAdmin;
import com.baidu.atcrowdfunding.bean.TMenu;
import com.baidu.atcrowdfunding.service.TAdminService;
import com.baidu.atcrowdfunding.service.TMenuService;
import com.baidu.atcrowdfunding.util.Const;
import com.baidu.atcrowdfunding.util.MD5Util;
 
@Controller
public class DispatcherController {
	Logger log = LoggerFactory.getLogger(DispatcherController.class);
	
	@Autowired
	TAdminService adminService;
	
	@Autowired
	TMenuService menuService;
	
	@RequestMapping("/index")
	public String index() {
		log.debug("跳到了系统主页面了"); //用于调试程序
		return "index";
	}
	
	   
	
	 
	@RequestMapping("/main")
	public String main1(HttpSession session) {
		log.debug("跳到后台系统页面"); //用于调试程序
 
		List<TMenu> menuList = menuService.listMenuAll();
		System.out.println(menuList);
		session.setAttribute("menuList", menuList);
		 
		return "main";
	}
	
	
	
	
	
	
	
	 
	
	
	@RequestMapping("/toLogin")
	public String login() {
		log.debug("跳到了登录页面了"); //用于调试程序
		return "loginMovie";
	}
	
	
	
	
	
	@RequestMapping("/loginMovie")
	public String dologin() {
		log.debug("点击登录了"); //用于调试程序
		return "main";
	}
	
	
	
	
	/*
	 * @RequestMapping("/logout") public String logout(HttpSession session) {
	 * log.debug("退出系统了"); //用于调试程序 if(session!=null) {
	 * session.removeAttribute(Const.LOGIN_ADMIN); //删除 session.invalidate();//销毁 }
	 * return "redirect:/index"; }
	 */
	
	/*
	 * @RequestMapping("/doLogin") 
	 * public String doLogin(String loginacct,String
	 * userpswd,HttpSession session,Model model) {
	 * log.debug("loginacct={},userpswd={}",loginacct,userpswd); //用于调试程序
	 * 
	 * Map<String,Object> paramMap = new HashMap<>();
	 * paramMap.put("loginacct",loginacct);
	 * paramMap.put("userpswd",MD5Util.digest(userpswd)); try { TAdmin admin =
	 * adminService.getTAdminByLogin(paramMap);
	 * session.setAttribute(Const.LOGIN_ADMIN,admin); log.debug("登录成功"); return
	 * "redirect:/main"; } catch (Exception e) {
	 * log.debug("登录失败，原因:{}",e.getMessage()); model.addAttribute("message",
	 * e.getMessage()); return "login"; }
	 * 
	 * }
	 */
	
}	
