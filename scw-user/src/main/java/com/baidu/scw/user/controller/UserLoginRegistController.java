package com.baidu.scw.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baidu.scw.user.bean.TMember;
import com.baidu.scw.user.component.SmsTemplate;
import com.baidu.scw.user.service.TMemberService;
import com.baidu.scw.vo.req.UserRegistVo;
import com.baidu.scw.vo.resp.AppResponse;
import com.baidu.scw.vo.resp.UserRespVo;
import com.netflix.discovery.util.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.Local;

@Slf4j
@Api(tags = "用户登陆注册模块")
@RequestMapping("/user")
@RestController
public class UserLoginRegistController {
	
	@Autowired
	TMemberService memberService;
	
	@Autowired
	SmsTemplate smsTemplate;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	
	@ApiOperation(value="用户登陆") 
	@ApiImplicitParams(value={
					@ApiImplicitParam(value="登陆账号",name="loginacct"),
					@ApiImplicitParam(value="用户密码",name="password")
					})
	@PostMapping("/login")
	public AppResponse<UserRespVo> login(@RequestParam("loginacct")String loginacct,@RequestParam("password")String password){
		try {
			log.debug("登陆数据loginacct-{}",loginacct);
			log.debug("登陆数据psd-{}",password);
			UserRespVo vo = memberService.getUserByLogin(loginacct,password);
			log.debug("登陆成功-{}",loginacct);
			return AppResponse.ok(vo);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("登陆失败-{}-{}",loginacct,e.getMessage());
			AppResponse<UserRespVo> resp = AppResponse.fail(null);
			resp.setMsg(e.getMessage());
			return resp;
		}
	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@ApiOperation(value="用户注册") 
	@PostMapping("/register")
	public AppResponse<Object> register(UserRegistVo vo){
		
		//从页面拿到
		String loginacct = vo.getLoginacct();
		
		if(StringUtils.isEmpty(loginacct)) {
			//如果空则返回错误
			AppResponse<Object> resp = AppResponse.fail(null);
			resp.setMsg("用户名为空错误！");
			return AppResponse.fail(resp);
		}else {
			//不空
			//获取数据（从redis中查验证码）
			String yzm = stringRedisTemplate.opsForValue().get(loginacct);
			
			if(StringUtils.isEmpty(yzm)) {
				//如果空则返回错误
				AppResponse<Object> resp = AppResponse.fail(null);
				resp.setMsg("验证码失效！");
				return AppResponse.fail(resp);
			}else {
				//不空
				//判断输入
				if(vo.getCode().equals(yzm)) {
					//验证码一样
					
					//校验账号唯一性（略）
					//校验email是否唯一（略）
					
					//保存数据
					int i = memberService.saveTMember(vo);
					
					if(i==1) {
						
						//马上就删除用户的验证码
						//stringRedisTemplate.delete(loginacct);
						return AppResponse.ok("ok");
					}else {
						return AppResponse.fail(null);
					}
				}else {
					//验证码错误
					AppResponse<Object> resp = AppResponse.fail(null);
					resp.setMsg("验证码错误！");
					return AppResponse.fail(resp);
				}
				
			}
		}
	} 
	
	@ApiOperation(value="发送短信验证码") 
	@PostMapping("/sendsms")
	public AppResponse<Object> sendsms(String loginacct){
		
		StringBuilder code = new StringBuilder();
		for (int i = 1; i <= 4; i++) {
			code.append(new Random().nextInt(10));
		}
		
		Map<String, String> querys = new HashMap<String, String>();
	    querys.put("mobile", loginacct);
	    querys.put("param", "code:"+code.toString());
	    querys.put("tpl_id", "TP1711063");
	    
		smsTemplate.sendSms(querys);
		
		stringRedisTemplate.opsForValue().set(loginacct, code.toString());
		
		//stringRedisTemplate.opsForValue().set(loginacct, code.toString(),5,TimeUnit.HOURS); //5小时有效
		
		log.debug("发送短信成功-验证码：{}",code.toString());
		
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="验证短信验证码") 
	@PostMapping("/valide")
	public AppResponse<Object> valide(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="重置密码") 
	@PostMapping("/reset")
	public AppResponse<Object> reset(){
		return AppResponse.ok("ok");
	} 
	
	

}
