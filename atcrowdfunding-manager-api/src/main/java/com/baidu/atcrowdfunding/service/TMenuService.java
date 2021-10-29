package com.baidu.atcrowdfunding.service;

import java.util.List;

import com.baidu.atcrowdfunding.bean.TMenu;

public interface TMenuService {

	List<TMenu> listMenuAll(); //组合了父子关系

	List<TMenu> loadTree(); //不用组合父子关系

	void saveMenu(TMenu menu);

	TMenu updateMenu(Integer id);

	void doupdateMenu(TMenu menu);

	void doDel(Integer id);

}
