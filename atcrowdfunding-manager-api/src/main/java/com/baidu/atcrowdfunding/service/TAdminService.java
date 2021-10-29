package com.baidu.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.baidu.atcrowdfunding.bean.TAdmin;
import com.github.pagehelper.PageInfo;

public interface TAdminService {

	TAdmin getTAdminByLogin(Map<String, Object> paramMap);


	PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap);


	Integer saveTAdmin(TAdmin admin);
	Integer updateTAdmin(TAdmin admin);

	TAdmin getAdminById(Integer id);


	Integer doDeleteAdmin(Integer id);


	void deleteBatch(List<Integer> list);
	
}	
