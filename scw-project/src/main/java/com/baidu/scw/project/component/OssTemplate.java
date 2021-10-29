package com.baidu.scw.project.component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baidu.scw.vo.resp.AppResponse;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Data
@Slf4j
public class OssTemplate {
	// yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
	 
	String endpoint;
	
	// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
	String accessKeyId;
	String accessKeySecret;
	
	String bucket; 
	
	public String upload(String filename,InputStream inputStream) {
		try {
			// 创建OSSClient实例。
			OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
			// 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
			//InputStream inputStream = new FileInputStream("D:\\io.txt");
			// 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
			ossClient.putObject(bucket, "pic/"+filename, inputStream);
			// 关闭OSSClient。
			ossClient.shutdown();
			
			log.debug("上传成功-{}","https://"+bucket+"."+endpoint+"/pic/"+filename);
		
			return "https://"+bucket+"."+endpoint+"/pic/"+filename;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("上传失败-{}","https://"+bucket+"."+endpoint+"/pic/"+filename);
			 return null;
		}
		  
	}
}
