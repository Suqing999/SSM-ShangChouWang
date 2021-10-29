package com.baidu.scw.project.vo.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baidu.scw.project.bean.TReturn;

import lombok.Data;

@Data
public class ProjectVo implements Serializable{
	
//	private String projectToken;// 项目的临时token(阅读并同意协议时，后台会分配一个项目token值，用来绑定当前项目信息)	
	private Integer memberid;// 会员id
//	
//	//第二步：收集项目基本信息以及发起人信息（暂时不做）
//	private List<Integer> typeids; // 项目的分类id
//	private List<Integer> tagids; // 项目的标签id
//	
	private String name;// 项目名称
	private String remark;// 项目简介
//	private Integer money;// 筹资金额
//	private Integer day;// 筹资天数
	
	private String headerImage;// 项目头部图片
	private List<String> detailsImage = new ArrayList<>();// 项目详情图片
	// 发起人信息：自我介绍，详细自我介绍， 联系电话，客服电话
	
	//第三步：收集回报信息
	private List<TReturn> projectReturns = new ArrayList<>();// 项目回报
}
