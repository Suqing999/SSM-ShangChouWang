package com.baidu.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baidu.atcrowdfunding.bean.TRole;
import com.baidu.atcrowdfunding.bean.TRoleExample;
import com.baidu.atcrowdfunding.bean.TRolePermissionExample;
import com.baidu.atcrowdfunding.mapper.TAdminRoleMapper;
import com.baidu.atcrowdfunding.mapper.TRoleMapper;
import com.baidu.atcrowdfunding.mapper.TRolePermissionMapper;
import com.baidu.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageInfo;

@Service
public class TRoleServiceImpl implements TRoleService {
	@Autowired
	TRoleMapper roleMapper;

	@Autowired
	TRolePermissionMapper rolePermissionMapper;
	
	@Autowired
	TAdminRoleMapper adminRoleMapper;
	@Override
	public PageInfo<TRole> listRolePage(Map<String, Object> paramMap) {
		//查出所有
		String condition = (String) paramMap.get("condition");
		List<TRole> list = null;
		if(StringUtils.isEmpty(condition)) { //如果是空就查所有
			list = roleMapper.selectByExample(null);
		}else {
			TRoleExample example = new TRoleExample();
			example.createCriteria().andNameLike("%"+condition+"%");
			list = roleMapper.selectByExample(example);
		}
		
		//返回
		PageInfo<TRole> page = new PageInfo<TRole>(list,5);
		return page;
	}
	//@PreAuthorize("hasRole('PM - 项目经理')")
	@Override
	public void saveTRole(TRole role) {
		
		roleMapper.insertSelective(role);
	}

	@Override
	public TRole getTRole(Integer roleId) {
		TRole role = roleMapper.selectByPrimaryKey(roleId);	
		return role;
	}

	@Override
	public void upateTRole(TRole role) {
		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public void delTole(Integer id) {
		roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<TRole> getAllRole() {
		 
		return roleMapper.selectByExample(null);
	}

	@Override
	public List<Integer> getRoleIdByAdminId(String id) {
		 
		return adminRoleMapper.getRoleIdByAdminId(id);
	}

	@Override
	public void saveAdminAndRoleRelationShip(Integer[] roleId, Integer adminId) {
		adminRoleMapper.saveAdminAndRoleRelationShip(roleId, adminId);
	}

	@Override
	public void deleteAdminAndRoleRelationShip(Integer[] roleId, Integer adminId) {
		 
		adminRoleMapper.deleteAdminAndRoleRelationShip(roleId, adminId);
	}

	@Override
	public void saveRoleAndPermissionRelationship(Integer roleId, List<Integer> ids) {
				//先删除之前分配过的，然后再重新分配所有打钩的
				TRolePermissionExample example = new TRolePermissionExample();
				example.createCriteria().andRoleidEqualTo(roleId);
				rolePermissionMapper.deleteByExample(example);
				
				rolePermissionMapper.saveRoleAndPermissionRelationship(roleId,ids);
	}

	@Override
	public List<Integer> listPermissionIdByRoleId(Integer roleId) {
		 
		return rolePermissionMapper.listPermissionIdByRoleId(roleId); //查某个字段是需要自己手动实现的
	}
	
	
}
