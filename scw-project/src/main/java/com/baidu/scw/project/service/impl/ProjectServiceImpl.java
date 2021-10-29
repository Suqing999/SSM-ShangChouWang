package com.baidu.scw.project.service.impl;

import java.util.List;

import javax.swing.text.html.parser.TagElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.baidu.scw.project.bean.TProject;
import com.baidu.scw.project.bean.TProjectImages;
import com.baidu.scw.project.bean.TProjectTag;
import com.baidu.scw.project.bean.TProjectType;
import com.baidu.scw.project.bean.TReturn;
import com.baidu.scw.project.contants.ProjectConstant;
import com.baidu.scw.project.mapper.TProjectImagesMapper;
import com.baidu.scw.project.mapper.TProjectMapper;
import com.baidu.scw.project.mapper.TProjectTagMapper;
import com.baidu.scw.project.mapper.TProjectTypeMapper;
import com.baidu.scw.project.mapper.TReturnMapper;
import com.baidu.scw.project.service.ProjectService;
import com.baidu.scw.project.vo.req.ProjectRedisStorageVo;
import com.baidu.scw.util.AppDateUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	TProjectImagesMapper projectImagesMapper; 
	@Autowired
	TReturnMapper returnMapper; 
	@Autowired
	TProjectTypeMapper projectTypeMapper;
	@Autowired
	TProjectTagMapper projectTagMapper;
	@Autowired
	TProjectMapper projectMapper;
	
	
	@Transactional
	@Override
	public void saveProject(String accessToken, String projectToken, byte code) {
		 //1、从redis获取bigVo
		String bigStr =  stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX+projectToken);
		ProjectRedisStorageVo bigVo = JSON.parseObject(bigStr,ProjectRedisStorageVo.class);
		String memberid = stringRedisTemplate.opsForValue().get(accessToken);
		
		//2、保存项目
		TProject project = new TProject();//大Vo转Do
		//可以对拷
		project.setName(bigVo.getName());
		project.setRemark(bigVo.getRemark());
		project.setMoney(bigVo.getMoney());
		project.setDay(bigVo.getDay());
		project.setStatus(code+"");
		project.setMemberid(Integer.parseInt(memberid));
		project.setCreatedate(AppDateUtils.getFormatTime());
		
		projectMapper.insertSelective(project);
		
		//保存后再拿该项目id
		Integer projectId = project.getId(); //主键回填 配置useGeneratedKeys="true"  keyProperty="id
		log.debug("项目id-{}",projectId);
		
		
		//3、保存图片 
		String headerImage = bigVo.getHeaderImage(); //头图
		TProjectImages projectImages = new TProjectImages();
		projectImages.setProjectid(projectId);
		projectImages.setImgurl(bigVo.getHeaderImage());
		projectImages.setImgtype((byte)0);
		
		projectImagesMapper.insertSelective(projectImages);
		
		 
		List<String> detailsImage = bigVo.getDetailsImage(); //对象图
		for(String imgPath:detailsImage) {
			TProjectImages pi = new TProjectImages();
			pi.setProjectid(projectId);
			pi.setImgurl(imgPath);
			pi.setImgtype((byte)1);
			projectImagesMapper.insertSelective(pi);
		}
		
		
		//4、保存回报
		
		List<TReturn> projectReturns = bigVo.getProjectReturns();
		for(TReturn retObj:projectReturns) {
			retObj.setProjectid(projectId);
			returnMapper.insertSelective(retObj);
		}
		
		
		//5、保存项目和分类的关系
		
		List<Integer> typeids = bigVo.getTypeids();
		
		for(Integer typeId : typeids) {
			TProjectType pt = new TProjectType();
			pt.setProjectid(projectId);
			pt.setTypeid(typeId);
			projectTypeMapper.insertSelective(pt);
			
		}
		 
		
		//6、保存项目和标签的关系
		List<Integer> tagids = bigVo.getTagids();
		for(Integer tagId : tagids) {
			TProjectTag ti = new TProjectTag();
			ti.setProjectid(projectId);
			ti.setTagid(tagId);
			projectTagMapper.insertSelective(ti);
			
		}
		
		//7、保存发起人  保存法人(省略)
		//end  清理缓存redis
	
		stringRedisTemplate.delete(ProjectConstant.TEMP_PROJECT_PREFIX+projectToken);
	
	}

}
