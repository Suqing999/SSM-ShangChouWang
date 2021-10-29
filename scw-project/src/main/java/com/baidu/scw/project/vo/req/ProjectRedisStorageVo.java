package com.baidu.scw.project.vo.req;

import java.util.ArrayList;
import java.util.List;

import com.baidu.scw.project.bean.TReturn;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ProjectRedisStorageVo extends BaseVo{
	//第一步(accessToken 和 projectToken)
	private String projectToken;//项目的临时token
	//第二步
	
	private List<Integer> typeids  = new ArrayList<>();//类别
	private List<Integer> tagids = new ArrayList<>();//类别
	private String name;//项目名称 
    private String remark;//项目简介 
    private Long money;//筹资金额 
    private Integer day;//筹资天数 
    private String headerImage;//项目头部图片 
    private List<String> detailsImage = new ArrayList<>();//项目详情图片 
    //发起人信息：自我介绍，详细自我介绍，联系电话，客服电话
	
    //第三步
    private List<TReturn> projectReturns = new ArrayList<>();//项目回报 

    //第四步
    //法人信息：省略
}
