package com.baidu.springboot.mapper;

 
import com.baidu.springboot.bean.TAdmin;

public interface TAdminMapper {
 

    TAdmin selectByPrimaryKey(Integer id);
 
}