package com.baidu.scw.user.service;

import com.baidu.scw.vo.req.UserRegistVo;
import com.baidu.scw.vo.resp.UserRespVo;

public interface TMemberService {

	int saveTMember(UserRegistVo vo);

	UserRespVo getUserByLogin(String loginacct, String password);

}
