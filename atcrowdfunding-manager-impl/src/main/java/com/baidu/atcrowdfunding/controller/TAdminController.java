package com.baidu.atcrowdfunding.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.atcrowdfunding.bean.TAdmin;
import com.baidu.atcrowdfunding.bean.TRole;
import com.baidu.atcrowdfunding.service.TAdminService;
import com.baidu.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
 

@Controller
public class TAdminController {
	@Autowired
	TAdminService adminService;
	
	@Autowired
	TRoleService roleService;
	
	@RequestMapping("/admin/index")
	public String index(@RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
						@RequestParam(value="pageSize",required=false,defaultValue="2")Integer pageSize,
						Model model,
						@RequestParam(value="condition",required=false,defaultValue="")String condition) {
		System.out.println(condition);
		
		PageHelper.startPage(pageNum,pageSize); //线程绑定(不用传数据)
 
		Map<String,Object> paramMap = new HashMap<>();
		
		//放入条件
		paramMap.put("condition", condition);
		
		//查询所有管理员
		//mybatis分页插件
		PageInfo<TAdmin> page = adminService.listAdminPage(paramMap);
		
		model.addAttribute("page",page);
		return "admin/index";
	}

	@RequestMapping("/admin/toAdd")
	public String toAdd() {
		return "admin/add";
	}
	
	@PreAuthorize("hasRole('PM - 项目经理')")
	@RequestMapping("/admin/doAdd")
	public String doAdd(TAdmin admin) {
		Integer res = adminService.saveTAdmin(admin);
		return "redirect:/admin/index?pageNum="+1000;
	}
	
	
	@RequestMapping("/admin/toUpdate")
	public String toUpdate(Integer id,Model model) {
		TAdmin admin = adminService.getAdminById(id);
		model.addAttribute("admin", admin);
		return "admin/update"; //请求转发会带上请求域数据Integer pageNum 页面上通过param.pageNum来得到
	}
	
	@RequestMapping("/admin/doUpdate")
	public String doUpdate(TAdmin admin,Integer pageNum) {
		Integer res = adminService.updateTAdmin(admin);
		
		return "redirect:/admin/index?pageNum="+pageNum;
	}
	
	@RequestMapping("/admin/doDelete")
	public String doDelete(Integer id,Integer pageNum) {
		Integer res = adminService.doDeleteAdmin(id);
		
		return "redirect:/admin/index?pageNum="+pageNum;
	}
	
	@RequestMapping("/admin/doDeleteBatch")
	public String doDeleteBatch(String ids,Integer pageNum) {
		//拿到是1，2，3，4
		//用逗号分解即可
		 List<Integer> idList = new ArrayList<>();
		 String[] sid = ids.split(",");
		 
		 for(String s : sid) {
			 int id = Integer.parseInt(s);
			 idList.add(id);
		 }
		 System.out.println("idList"+idList.size());
		 adminService.deleteBatch(idList);
		
		return "redirect:/admin/index?pageNum="+pageNum;
	} 
	
	
	@RequestMapping("/admin/toAssign")
	public String toAssign(String id,Model model) {
		List<TRole> listAll = new ArrayList<>();
		List<Integer> listTmp = new ArrayList<>();
		List<TRole> assignList = new ArrayList<>();
		List<TRole> unAssignList = new ArrayList<>();
		//查询所有角色
		listAll =  roleService.getAllRole();
		
		//根据用户id查询已经分配的角色id（Service用多对多的两个其中一个即可，不用再建中间表服务对象）
		listTmp = roleService.getRoleIdByAdminId(id);
		
		//划分
		for(TRole r :listAll) {
			if(listTmp.contains(r.getId())) {
				 assignList.add(r);
			}else {
				unAssignList.add(r);
			}
		}
		
		model.addAttribute("assignList", assignList);
		model.addAttribute("unAssignList", unAssignList);
		
		return "admin/assignRole";
	}
	
	
	@ResponseBody
	@RequestMapping("/admin/doAssign")
	public String doAssign(Integer[] roleId,Integer adminId) {
		 
		roleService.saveAdminAndRoleRelationShip(roleId,adminId);
		
		return "ok";
	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping("/admin/doUnAssign")
	public String doUnAssign(Integer[] roleId,Integer adminId) {
		 
		roleService.deleteAdminAndRoleRelationShip(roleId,adminId);
		
		return "ok";
	}
	
	
	
	
}
