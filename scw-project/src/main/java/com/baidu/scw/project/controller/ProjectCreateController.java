package com.baidu.scw.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.baidu.scw.enums.ProjectStatusEnume;
import com.baidu.scw.project.bean.TReturn;
import com.baidu.scw.project.component.OssTemplate;
import com.baidu.scw.project.contants.ProjectConstant;
import com.baidu.scw.project.service.ProjectService;
import com.baidu.scw.project.vo.req.BaseVo;
import com.baidu.scw.project.vo.req.ProjectBaseInfoVo;
import com.baidu.scw.project.vo.req.ProjectRedisStorageVo;
import com.baidu.scw.project.vo.req.ProjectReturnVo;
import com.baidu.scw.vo.resp.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "项目发起模块")
@RequestMapping("/project/create")
@RestController
public class ProjectCreateController {
	@Autowired
	ProjectService projectService;
	@Autowired
	OssTemplate ossTemplate;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	
	@ApiOperation(value = "项目初始创建")
	@PostMapping("/init")
	public AppResponse<Object> init(BaseVo vo) { //小vo接收
		
		try {
			
			
			String accessToken = vo.getAccessToken();
			if(StringUtils.isEmpty(accessToken)) {
				//如果空了
				AppResponse<Object> resp = AppResponse.fail(null);
				resp.setMsg("请求必须提供accessToken值");
				return resp;
			}else {
				//从redis中得到
				String memberId = stringRedisTemplate.opsForValue().get(accessToken);
				if(StringUtils.isEmpty(memberId)) {
					//如果空了
					AppResponse<Object> resp = AppResponse.fail(null);
					resp.setMsg("请先登录");
					return resp;
				}
				
				//2、初始化bigVo 
				
				//对拷VO  小vo到大vo
				ProjectRedisStorageVo bigVo = new ProjectRedisStorageVo();
				BeanUtils.copyProperties(vo, bigVo);
				
				//设置一个projectToken临时标志
				String projectToken = UUID.randomUUID().toString().replace("-", "");
				bigVo.setProjectToken(projectToken);
				
				//3、数据存储到redis中
				
				//把大VO转换为串
				String bigStr = JSON.toJSONString(bigVo);  //运用fastjson组件
				
				//存大vo
				stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX+projectToken, bigStr);
				
				log.debug("大VO数据:{}",bigVo);
				return AppResponse.ok(bigVo); //用jackson组件转化为json串
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return AppResponse.fail(null);
		}
		
 
	}
	
	
	
	
	
	
	
	@ApiOperation("项目发起第2步-保存项目的基本信息")
	@PostMapping("/baseinfo")
	public AppResponse<Object> baseInfo(ProjectBaseInfoVo vo){
		
		try {
			//1、校验
			String accessToken = vo.getAccessToken();
			if(StringUtils.isEmpty(accessToken)) {
				//如果空了
				AppResponse<Object> resp = AppResponse.fail(null);
				resp.setMsg("请求必须提供accessToken值");
				return resp;
			}else {
				//从redis中得到
				String memberId = stringRedisTemplate.opsForValue().get(accessToken);
				if(StringUtils.isEmpty(memberId)) {
					//如果空了
					AppResponse<Object> resp = AppResponse.fail(null);
					resp.setMsg("请先登录");
					return resp;
				}
			 
				
			//2、从Redis中获取bigVo 将小vo封装到大vo
				String projectToken = vo.getProjectToken();
				System.out.println(projectToken);
				
				String bigJson = stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX+projectToken);
				System.out.println(bigJson);
				
				ProjectBaseInfoVo bigVo = JSON.parseObject(bigJson,ProjectBaseInfoVo.class);
				
				System.out.println(bigVo==null);
				System.out.println(vo==null);
				BeanUtils.copyProperties(vo, bigVo);
				
			//3、放回去redis
				
				String bigStr = JSON.toJSONString(bigVo);
				stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX+projectToken,bigStr);
				log.debug("大VO数据:{}",bigVo);
				
				return AppResponse.ok(bigVo);  
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return AppResponse.fail(null);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@ApiOperation(value = "第3步 - 添加项目回报档位")
	@PostMapping("/return")
	public AppResponse<Object> returnDetail(@RequestBody List<ProjectReturnVo> pro) {
		try {
			//1、校验
			String accessToken = pro.get(0).getAccessToken();
			if(StringUtils.isEmpty(accessToken)) {
				//如果空了
				AppResponse<Object> resp = AppResponse.fail(null);
				resp.setMsg("请求必须提供accessToken值");
				return resp;
			}else {
				//从redis中得到
				String memberId = stringRedisTemplate.opsForValue().get(accessToken);
				if(StringUtils.isEmpty(memberId)) {
					//如果空了
					AppResponse<Object> resp = AppResponse.fail(null);
					resp.setMsg("请先登录");
					return resp;
				}
			 
			//2、从Redis中获取bigVo 将小vo封装到大vo
				String bigStr =  stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX+pro.get(0).getProjectToken());
				ProjectRedisStorageVo bigVo = JSON.parseObject(bigStr,ProjectRedisStorageVo.class);
				
				List<TReturn> projectReturns = bigVo.getProjectReturns();  
				
				for (ProjectReturnVo projectReturnVo : pro) {
					TReturn returnObj = new TReturn();
					
					BeanUtils.copyProperties(projectReturnVo,returnObj);
					
					projectReturns.add(returnObj);
				}
				
				//3、放回去redis
				
				bigStr = JSON.toJSONString(bigVo);
				stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX+pro.get(0).getProjectToken(),bigStr);
				log.debug("大VO数据:{}",bigVo);
				
				return AppResponse.ok(bigVo);  
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return AppResponse.fail(null);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@ApiOperation(value = "第4步项目提交审核申请")
	@PostMapping("/submit")
	public AppResponse<Object> submit(String accessToken,String projectToken,String ops) {
		
		try {
			//1、校验
			 
			if(StringUtils.isEmpty(accessToken)) {
				//如果空了
				AppResponse<Object> resp = AppResponse.fail(null);
				resp.setMsg("请求必须提供accessToken值");
				return resp;
			}else {
				//从redis中得到
				String memberId = stringRedisTemplate.opsForValue().get(accessToken);
				if(StringUtils.isEmpty(memberId)) {
					//如果空了
					AppResponse<Object> resp = AppResponse.fail(null);
					resp.setMsg("请先登录");
					return resp;
				}
			 
			if("0".equals(ops)) {
				//保存草稿
				
				projectService.saveProject(accessToken,projectToken,ProjectStatusEnume.DRAFT.getCode());
				
				
				return AppResponse.ok("ok");
			}else if("1".equals(ops)) {
				//保存提交
				
				projectService.saveProject(accessToken,projectToken,ProjectStatusEnume.SUBMIT_AUTH.getCode());
				
				
				return AppResponse.ok("ok");
			}else {
				//有问题
				log.error("请求方式不支持");
				return AppResponse.fail(null);
			}
		 }
		} catch (Exception e) {
			e.printStackTrace();
			log.error("操作失败-{}",e.getMessage());
			return AppResponse.fail(null);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@ApiOperation(value = "删除项目回报档位")
	@DeleteMapping("/return")
	public AppResponse<Object> deleteReturnDetail() {
		return AppResponse.ok("ok");
	}
	
	
	
	
	
	
	
	//文件上传表单要求：
	//1、method=post
	//2、enctype="multtipart/form-data"
	//3、表单域类型为file   name="uploadfile"
	//SpringMVC集成了commons-io 和 upload
	//SpringMVC提供了文件上传解析器，但是Boot自动配置了
	//Controller处理时通过MultipartFile来接收文件
	
	@ApiOperation(value = "上传图片")
	@PostMapping("/upload")
	public AppResponse<Object> upload(@RequestParam("uploadfile")MultipartFile[] files) throws IOException {

		try {
			List<String> filePathList = new ArrayList<>();
			for (MultipartFile file : files) {
				String filename = file.getOriginalFilename();
				filename = UUID.randomUUID().toString().replaceAll("-","")+"_"+filename;
				
				String filepath = ossTemplate.upload(filename, file.getInputStream());
				
				filePathList.add(filepath); //拿到存入list
			}
			
			log.debug("上传成功-{}",filePathList);
			return AppResponse.ok(filePathList); //可以在前端做隐藏
		} catch (Exception e) {
			 	e.printStackTrace();
			 	log.debug("上传失败");
			 	return AppResponse.fail(null);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@ApiOperation(value = "确认项目法人信息")
	@PostMapping("/confirm/legal")
	public AppResponse<Object> legal() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "项目草稿保存")
	@PostMapping("/tempsave")
	public AppResponse<Object> tempsave() {
		return AppResponse.ok("ok");
	}
	
	
	
}
