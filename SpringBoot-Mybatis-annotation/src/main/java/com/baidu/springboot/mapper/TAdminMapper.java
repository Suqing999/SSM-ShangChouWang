package com.baidu.springboot.mapper;

 
import org.apache.ibatis.annotations.Select;

import com.baidu.springboot.bean.TAdmin;

public interface TAdminMapper {
 
	@Select("select * from t_admin where id=#{id}")
    TAdmin selectByPrimaryKey(Integer id);
 
}