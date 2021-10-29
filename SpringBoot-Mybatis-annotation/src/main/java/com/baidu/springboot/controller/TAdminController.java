package com.baidu.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.springboot.bean.TAdmin;
import com.baidu.springboot.service.TAdminServcie;

@Controller
public class TAdminController {
	@Autowired
	TAdminServcie adminService;

	@ResponseBody
	@RequestMapping("/getAdmin/{zid}")
	public TAdmin getAdmin(@PathVariable("zid")Integer id) {
		TAdmin admin = adminService.getAdmin(id);
		return admin;
	}
}
