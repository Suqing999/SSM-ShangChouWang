package com.baidu.atcrowdfunding.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.atcrowdfunding.bean.TMenu;
import com.baidu.atcrowdfunding.mapper.TMenuMapper;
import com.baidu.atcrowdfunding.service.TMenuService;

@Service
public class TMenuServiceImpl implements TMenuService{
	
	Logger log = LoggerFactory.getLogger(TMenuServiceImpl.class);
	@Autowired
	TMenuMapper menuMapper;

	@Override
	public List<TMenu> listMenuAll() {
		List<TMenu> menuList = new ArrayList<>(); // 只存放父菜单。但是将孩子赋值
		List<TMenu> allList = menuMapper.selectByExample(null);
		Map<Integer,TMenu> cache = new HashMap<>();
		
		for(TMenu t : allList) {
			if(t.getPid()==0) { //是父亲
				menuList.add(t);
				cache.put(t.getId(), t);
			}
		}
		
		//给孩子赋值
		for(TMenu t : allList) {
			if( t.getPid() != 0 ) { //是子
				 Integer pid = t.getPid();
				 TMenu parent = cache.get(pid);
				 parent.getChildren().add(t); //当前迭代的就是孩子
			}
		}
		
		log.debug("菜单={}",menuList);
		return menuList;
	}

	@Override
	public List<TMenu> loadTree() {
		List<TMenu> selectByExample = menuMapper.selectByExample(null);
		return selectByExample;
	}

	@Override
	public void saveMenu(TMenu menu) {
		 menuMapper.insertSelective(menu);
	}

	@Override
	public TMenu updateMenu(Integer id) {
		 return menuMapper.selectByPrimaryKey(id);
	}

	@Override
	public void doupdateMenu(TMenu menu) {
		menuMapper.updateByPrimaryKeySelective(menu);
	}

	@Override
	public void doDel(Integer id) {
		menuMapper.deleteByPrimaryKey(id);
	}
}
 