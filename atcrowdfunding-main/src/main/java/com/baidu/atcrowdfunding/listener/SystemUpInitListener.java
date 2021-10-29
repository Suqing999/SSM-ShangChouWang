package com.baidu.atcrowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.atcrowdfunding.util.Const;
/*
 * 监听Application对象创建和销毁
 * 
 * */
public class SystemUpInitListener implements ServletContextListener {
	Logger log = LoggerFactory.getLogger(SystemUpInitListener.class);
	
	
	//Application创建时候执行
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		String contextPath = servletContext.getContextPath();
		log.debug("当前的上下文路径:{}",contextPath);
		servletContext.setAttribute(Const.PATH, contextPath);
		
	}
	//Application销毁时候执行
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.debug("当前的应用application对象销毁");
	}

}
