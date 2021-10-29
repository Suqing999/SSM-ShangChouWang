package com.baidu.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.springboot.bean.TAdmin;
import com.baidu.springboot.mapper.TAdminMapper;
import com.baidu.springboot.service.TAdminServcie;

@Service
public class TAdminServiceImpl implements TAdminServcie {
	@Autowired
	TAdminMapper adminMapper;
	@Override
	public TAdmin getAdmin(Integer id) {
		 TAdmin selectByPrimaryKey = adminMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;
	}

}
