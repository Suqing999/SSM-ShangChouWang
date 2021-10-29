package com.baidu.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.atcrowdfunding.bean.TAdmin;
import com.baidu.atcrowdfunding.bean.TAdminExample;
import com.baidu.atcrowdfunding.bean.TAdminExample.Criteria;
import com.baidu.atcrowdfunding.exception.LoginException;
import com.baidu.atcrowdfunding.mapper.TAdminMapper;
import com.baidu.atcrowdfunding.service.TAdminService;
import com.baidu.atcrowdfunding.util.AppDateUtils;
import com.baidu.atcrowdfunding.util.Const;
import com.baidu.atcrowdfunding.util.MD5Util;
import com.github.pagehelper.PageInfo;

@Service
public class TAdminServiceImpl implements TAdminService {
	@Autowired
	TAdminMapper adminMapper;

	@Override
	public TAdmin getTAdminByLogin(Map<String, Object> paramMap) {
		 //密码加密
		 
		//查询用户
		
		String loginacct = (String) paramMap.get("loginacct");
		String userpswd = (String) paramMap.get("userpswd");
		
		
		//判断用户是否为null
		
		TAdminExample example =new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(loginacct);
		List<TAdmin> list = adminMapper.selectByExample(example);
		if(list!=null && list.size()==1) {
			TAdmin admin = list.get(0);
			//判断密码一致
			if(admin.getUserpswd().equals(userpswd)) {
				return admin;
			}else {
				throw new LoginException(Const.LOGIN_USERPSWD_ERROR); //密码错误
			}
		}else {
			//返回
			throw new LoginException(Const.LOGIN_LOGINACCT_ERROR); //用户不存在
		}
		
	}

	
	
	//分页查询
	@Override
	public PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap) {
		TAdminExample example = new TAdminExample();
		String condition  = (String) paramMap.get("condition");
		
		if(!"".equals(condition)) {
			example.createCriteria().andLoginacctLike("%"+condition+"%");
			Criteria B = example.createCriteria().andUsernameLike("%"+condition+"%");
			Criteria C = example.createCriteria().andEmailLike("%"+condition+"%");
			example.or(B);
			example.or(C);
		}
		
		
		List<TAdmin> all = adminMapper.selectByExample(example);
		
		PageInfo<TAdmin> page = new PageInfo<>(all,5);//最多5个导航页（默认8个）
		
		return page;
	}



	@Override
	public Integer saveTAdmin(TAdmin admin) {
		
		//密码单独自定义
		admin.setUserpswd(MD5Util.digest("123456"));
		
		//创建时间
		admin.setCreatetime(AppDateUtils.getFormatTime());
		
		int insertAdmin = adminMapper.insertSelective(admin);//有选择性的保存（动态sql）有属性值为null该字段不加入insert
		return insertAdmin;
	}



	@Override
	public TAdmin getAdminById(Integer id) {
		
		TAdmin admin = adminMapper.selectByPrimaryKey(id);
		
		return admin;
	}



	@Override
	public Integer updateTAdmin(TAdmin admin) {
		
		int updateAdmin = adminMapper.updateByPrimaryKeySelective(admin);//有选择性的更新，只有改变的才更新
		return updateAdmin;
	}



	@Override
	public Integer doDeleteAdmin(Integer id) {
		 int deleteByPrimaryKey = adminMapper.deleteByPrimaryKey(id);
		return deleteByPrimaryKey;
	}



	@Override
	public void deleteBatch(List<Integer> idList) {
		 //自己定义一个接口去删除
		adminMapper.deleteBatch(idList);
	}
 
	
}
