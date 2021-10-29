package com.baidu.atcrowdfunding.component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.baidu.atcrowdfunding.bean.TAdmin;
import com.baidu.atcrowdfunding.bean.TAdminExample;
import com.baidu.atcrowdfunding.bean.TPermission;
import com.baidu.atcrowdfunding.bean.TRole;
import com.baidu.atcrowdfunding.mapper.TAdminMapper;
import com.baidu.atcrowdfunding.mapper.TPermissionMapper;
import com.baidu.atcrowdfunding.mapper.TRoleMapper;

@Component
public class SecurityUserDetailServiceImpl implements UserDetailsService{

	@Autowired
	TAdminMapper adminMapper;
	
	@Autowired
	TRoleMapper roleMapper;
	
	@Autowired
	TPermissionMapper permissionMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//查用户
		TAdminExample example = new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(username);
		List<TAdmin> list = adminMapper.selectByExample(example);
		
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
		if(list!=null&&list.size()==1) {
			TAdmin a = list.get(0);//拿到用户
			
			System.out.println(a);
			
			//查询角色集合
			Integer id = a.getId();
			List<TRole> roleList = roleMapper.listRoleByAdminId(id);
			
			System.out.println(id);
			
			//查权限
			List<TPermission> permissionList = permissionMapper.listPermissionByAdminId(id);
			
			System.out.println(permissionList);
			//总的权限集合（ROLE_角色+权限）
			Set<GrantedAuthority> authority = new HashSet<GrantedAuthority>();
			for(TRole role : roleList) {
				authority.add(new SimpleGrantedAuthority("ROLE_"+role.getName())); //角色名字要前缀
			}
			
			for(TPermission per : permissionList) {
				authority.add(new SimpleGrantedAuthority(per.getName())); //权限名字不用前缀
			}
			
			System.out.println("ppADD"+a.getLoginacct());
		//	return  new User(a.getLoginacct(),a.getUserpswd(),authority); //放到session域中
		//信息不够 返回所有admin信息
			
			return new TSecurityAdmin(a, authority);
		
		}else {
			return null;
		}
		
	}
	
}
