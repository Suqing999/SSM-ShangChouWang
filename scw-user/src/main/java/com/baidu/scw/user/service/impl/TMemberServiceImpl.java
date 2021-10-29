package com.baidu.scw.user.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.scw.user.bean.TMember;
import com.baidu.scw.user.bean.TMemberExample;
import com.baidu.scw.user.enums.UserExceptionEnum;
import com.baidu.scw.user.exp.UserException;
import com.baidu.scw.user.mapper.TMemberMapper;
import com.baidu.scw.user.service.TMemberService;
import com.baidu.scw.vo.req.UserRegistVo;
import com.baidu.scw.vo.resp.UserRespVo;
import com.netflix.discovery.converters.Auto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly=true)
public class TMemberServiceImpl implements TMemberService {
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	TMemberMapper memberMapper;

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.REPEATABLE_READ)
	@Override
	public int saveTMember(UserRegistVo vo) {
		
		try {
			//对拷数据
			//太复杂
			TMember mem = new TMember();
			//mem.setLoginacct(vo.getLoginacct());
			//mem.setUserpswd(vo.getUserpswd());
			//BeanUtil工具，将vo到do中
			BeanUtils.copyProperties(vo, mem);
			mem.setUsername(vo.getLoginacct());
			
			//加密密码
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();//编码器
			mem.setUserpswd(encoder.encode(vo.getUserpswd()));
			
			mem.setAuthstatus("0"); //新注册的为0
			int i = memberMapper.insertSelective(mem);
			log.debug("注册会员成功--{}",mem);
			return i;
		} catch (Exception e) {
			 e.printStackTrace();
			 log.error("注册会员失败--{}",e.getMessage());
			 //抛异常
			 //throw new RuntimeException("保存会员业务逻辑失败!");
			 //个性化异常  //用枚举指定消息UserExceptionEnum
			 throw new UserException(UserExceptionEnum.USER_SAVE_ERROR);
		
		}
		
		
		
	}

	@Override
	public UserRespVo getUserByLogin(String loginacct, String password) {
		 
		UserRespVo userRespVo = new UserRespVo();
		
		//校验密码
		TMemberExample example = new TMemberExample();
		example.createCriteria().andLoginacctEqualTo(loginacct);//用户名
		List<TMember> list = memberMapper.selectByExample(example);
		if(list==null || list.size()==0) {
			//没查到
			throw new UserException(UserExceptionEnum.USER_UNEXIST);
		}
		//用户在
		TMember member = list.get(0);
		
		//判断密码
		BCryptPasswordEncoder encode= new BCryptPasswordEncoder();
		if(!encode.matches(password, member.getUserpswd())) {
			//密码不对
			throw new UserException(UserExceptionEnum.PASSWORD_ERROR);
		} 
		//密码正确，也存在人
		//对拷
		BeanUtils.copyProperties(member, userRespVo);
		
		//添加没有的属性
		String uuid = UUID.randomUUID().toString().replace("-","");
		userRespVo.setAccessToken(uuid); //随机生成
		
		//uuid进入redis
		stringRedisTemplate.opsForValue().set(uuid,member.getId().toString());
		
		return userRespVo;
	}
}
