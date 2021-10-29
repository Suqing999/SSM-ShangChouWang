package com.baidu.atcrowdfunding.controller;
import com.baidu.atcrowdfunding.bean.TMenu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.atcrowdfunding.service.TMenuService;

@Controller
public class TMenuController {
	@Autowired
	TMenuService menuService;
	
	//跳转
	@RequestMapping("/menu/index")
	public String index() {
		return "menu/index";
	}
	
	@ResponseBody
	@RequestMapping("/menu/loadTree")
	public List<TMenu> loadTree() {
		List<TMenu> list = new ArrayList<>();
		list = menuService.loadTree();
		return list;
	}
	
	

	@ResponseBody
	@RequestMapping("/menu/doAdd")
	public String doAdd(TMenu menu) {
		menuService.saveMenu(menu);
		return "ok";
	}
	
	
	
	@ResponseBody
	@RequestMapping("/menu/getMenuById")
	public TMenu getMenuById(Integer id) {
		
		return menuService.updateMenu(id);
	}
	
	@ResponseBody
	@RequestMapping("/menu/doUpdate")
	public String doUpdate(TMenu menu) {
		menuService.doupdateMenu(menu);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/menu/doDelete")
	public String doDel(Integer id) {
		menuService.doDel(id);
		return "ok";
	}
	
}
