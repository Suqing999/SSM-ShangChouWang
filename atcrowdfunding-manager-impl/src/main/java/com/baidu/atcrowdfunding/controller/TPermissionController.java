package com.baidu.atcrowdfunding.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.atcrowdfunding.bean.TPermission;
import com.baidu.atcrowdfunding.service.TPermissionService;

@Controller
@RequestMapping("/permission")
public class TPermissionController {
	@Autowired
	TPermissionService permissionService;
	
	@GetMapping("/index")
	public String index() {
		return "permission/index";
	}
	
	
	@ResponseBody
	@GetMapping("/listAllPermissionTree")
	public List<TPermission> loadTree() {
		List<TPermission> list = new ArrayList<>();
		list = permissionService.load();
		return list;
	}
	
	@ResponseBody
	@PostMapping("/add")
	public String addPermission(TPermission permission) {
		permissionService.savePermission(permission);
		return "ok";
	}

	@ResponseBody
	@DeleteMapping("/delete")
	public String deletePermission(Integer id) {
		permissionService.deletePermission(id);
		return "ok";
	}

	@ResponseBody
	@PostMapping("/edit")
	public String editPermission(TPermission permission) {
		permissionService.editPermission(permission);
		return "ok";
	}

	@ResponseBody
	@GetMapping("/get")
	public TPermission getPermission(Integer id) {
		return permissionService.getPermissionById(id);
	}
}
