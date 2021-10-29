package com.baidu.atcrowdfunding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.atcrowdfunding.bean.TPermission;
import com.baidu.atcrowdfunding.bean.TPermissionMenuExample;
import com.baidu.atcrowdfunding.mapper.TPermissionMapper;
import com.baidu.atcrowdfunding.mapper.TPermissionMenuMapper;
import com.baidu.atcrowdfunding.mapper.TRolePermissionMapper;
import com.baidu.atcrowdfunding.service.TPermissionService;

@Service
public class TPerimissionServiceImple implements TPermissionService {

	@Autowired
	TPermissionMapper permissionMapper;
	 

	@Autowired
	TRolePermissionMapper rolePermissionMapper;

	@Autowired
	TPermissionMenuMapper permissionMenuMapper;
	
	@Override
	public List<TPermission> load() {
		return permissionMapper.selectByExample(null);
	}

	@Override
	public void savePermission(TPermission permission) {
		permissionMapper.insertSelective(permission);
	}

	@Override
	public void deletePermission(Integer id) {
		permissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void editPermission(TPermission permission) {
		permissionMapper.updateByPrimaryKeySelective(permission);
	}

	@Override
	public TPermission getPermissionById(Integer id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TPermission> getPermissionByMenuid(Integer mid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assignPermissionToMenu(Integer mid, List<Integer> perIdArray) {
		// TODO Auto-generated method stub
		
	}
	

}
