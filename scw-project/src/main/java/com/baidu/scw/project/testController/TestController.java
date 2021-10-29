package com.baidu.scw.project.testController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

@RestController
public class TestController {

	@GetMapping("/test1")
	public String test1() throws FileNotFoundException {
		// yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
		String endpoint = "oss-cn-beijing.aliyuncs.com";
		
		// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
		String accessKeyId = "LTAI5tSCUryGY73BbmKZTYKf";
		String accessKeySecret = "QTT7UQ1BuvtrvzvWftblaLaOYe8ayV";
		
		
		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		// 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
		InputStream inputStream = new FileInputStream("D:\\io.txt");
		// 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
		ossClient.putObject("scwwx2021", "doc/io.txt", inputStream);

		// 关闭OSSClient。
		ossClient.shutdown();
		return "ok";
	}
}
