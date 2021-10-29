package com.baidu.springboot.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
@WebFilter(urlPatterns="/*")
public class HelloFilter implements Filter {
	@Override
	public void destroy() {
	
	}
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
	throws IOException, ServletException {
		System.out.println("HelloFilter............放行之前");
		arg2.doFilter(arg0, arg1);
		System.out.println("HelloFilter............放行之后");
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	
	}
}