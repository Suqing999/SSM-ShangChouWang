package com.baidu.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.atcrowdfunding.bean.TRole;
import com.baidu.atcrowdfunding.service.TRoleService;
import com.baidu.atcrowdfunding.util.Datas;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class TRoleController {
	@Autowired
	TRoleService roleService;
	
	@RequestMapping("/role/index")
	public String toRole() {
		return "role/index";
	}
	

	@ResponseBody  //直接返回数据
	@RequestMapping("/role/loadData")
	public PageInfo<TRole> loadData(@RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
			@RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
			@RequestParam(value="condition",required=false,defaultValue="")String condition) {
		
		PageHelper.startPage(pageNum, pageSize);
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("condition", condition);
		PageInfo<TRole> page = roleService.listRolePage(paramMap);
 
		return page; //转化为json串了
	}
	
	@PreAuthorize("hasRole('PM - 项目经理')")
	@ResponseBody  //直接返回数据
	@RequestMapping("/role/doAdd")
	public String doAdd(TRole role) {
		roleService.saveTRole(role);
		 
		return "ok"; 
	}
	
	@ResponseBody  //直接返回数据
	@RequestMapping("/role/getRoleById")
	public TRole getRoleById(Integer id) {
		TRole role = roleService.getTRole(id);
		return role; 
	}
	@ResponseBody  //直接返回数据
	@RequestMapping("/role/doUpdate")
	public String doUpdate(TRole role) {
		roleService.upateTRole(role);
		 
		return "ok"; 
	}
	
	@ResponseBody  //直接返回数据
	@RequestMapping("/role/doDelete")
	public String doDel(Integer id) {
		roleService.delTole(id);
		 
		return "ok"; 
	}
	
	@ResponseBody  //直接返回数据
	@RequestMapping("/role/doAssignPermissionToRole")
	public String doAssignPermissionToRole(Integer roleId,Datas ds) {
		System.out.println("王霄"+roleId+"长度:"+ds.getIds().size());
		roleService.saveRoleAndPermissionRelationship(roleId,ds.getIds());
		return "ok";
	}
	
	@ResponseBody  //直接返回数据
	@RequestMapping("/role/listPermissionIdByRoleId")
	public List<Integer> listPermissionIdByRoleId(Integer roleId) {
		System.out.println("王霄"+roleId);
		
		return roleService.listPermissionIdByRoleId(roleId); 
	}
	
	
	
	
	
}	
