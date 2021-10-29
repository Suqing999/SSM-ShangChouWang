package com.baidu.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.baidu.atcrowdfunding.bean.TRole;
import com.github.pagehelper.PageInfo;

public interface TRoleService {

	PageInfo<TRole> listRolePage(Map<String, Object> paramMap);

	void saveTRole(TRole role);

	TRole getTRole(Integer roleId);

	void upateTRole(TRole role);

	void delTole(Integer id);

	List<TRole> getAllRole();

	List<Integer> getRoleIdByAdminId(String id);

	void saveAdminAndRoleRelationShip(Integer[] roleId, Integer adminId);

	void deleteAdminAndRoleRelationShip(Integer[] roleId, Integer adminId);

	void saveRoleAndPermissionRelationship(Integer roleId, List<Integer> ids);

	List<Integer> listPermissionIdByRoleId(Integer roleId);

	

}
