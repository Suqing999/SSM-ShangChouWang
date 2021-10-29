package com.baidu.scw.project.vo.req;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProjectBaseInfoVo extends BaseVo{
	
	private String projectToken;
	
	private List<Integer> typeids;//类别
	private List<Integer> tagids;//类别
	private String name;//项目名称 
    private String remark;//项目简介 
    private Integer money;//筹资金额 
    private Integer day;//筹资天数 
    private String headerImage;//项目头部图片 
    private List<String> detailsImage;//项目详情图片 
}
