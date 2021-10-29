package com.baidu.atcrowdfunding.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class AtcrowdFundingSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsService userDetailsService;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());//密码编码器
	
		// auth.userDetailsService(userDetailsService).passwordEncoder(null);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/static/**","/welcome.jsp","/toLogin").permitAll()
		.anyRequest().authenticated();//剩下都需要认证
		
		// /login.jsp==POST  用户登陆请求发给Security 
		//登录页是映射
		http.formLogin().loginPage("/toLogin") 
		.usernameParameter("loginacct")
		.passwordParameter("userpswd")
		.loginProcessingUrl("/login")
		.defaultSuccessUrl("/main");
		
		http.csrf().disable();
		http.logout().logoutSuccessUrl("/index");
		//异常处理器
		http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {

			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {
					String type = request.getHeader("X-Requested-With");
				
					if("XMLHttpRequest".equals(type)) { //异步请求
						response.getWriter().print("403"); //访问被拒绝
					}else {
						request.getRequestDispatcher("/WEB-INF/jsp/error/error403.jsp").forward(request, response);
					}
			}
		});
		http.rememberMe();
	}
	
	
	
}
