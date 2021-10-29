package com.baidu.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;


@Configuration
@EnableWebSecurity
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	  @Autowired 
	  DataSource dataSource;
	 
	  @Autowired
	  UserDetailsService userDetailsService;
	  
	  @Autowired
	  PasswordEncoder passwordEncoder;
	
	 @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(auth);
		
		
		/*
		 * auth.inMemoryAuthentication().withUser("zhangsan").password("123").roles("学徒"
		 * ,"宗师") .and() .withUser("lisi").password("123").authorities("罗汉拳","武当长拳");
		 *
		 */
		 
		 //DB认证数据
		// auth.userDetailsService(userDetailsService); 
		
		 
		 //DB+密码MD5
		 auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		 
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(http);
		
		//permitAll为所有人授权
		/*
		 * http.authorizeRequests().antMatchers("/layui/**","/index.jsp").permitAll()
		 * .anyRequest().authenticated();
		 */
		
		
		//无权访问友好界面
		
		http.exceptionHandling().accessDeniedPage("/unTo.html");
		
		 http.authorizeRequests().antMatchers("/layui/**","/index.jsp").permitAll()
								  .antMatchers("/level1/**").hasRole("学徒")
								  .antMatchers("/level2/**").hasRole("大师")
								  .antMatchers("/level3/**").hasRole("宗师")
								  .anyRequest().authenticated();
		
		http.formLogin().loginPage("/index.jsp")
						.usernameParameter("loginacct")
						.passwordParameter("userpswd")
						.loginProcessingUrl("/index.jsp")
						.defaultSuccessUrl("/main.html");
		//http.csrf().disable();
		
		
		//注销
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/index.jsp");
		
		
		//记住我(基于cookie方式)
		//http.rememberMe();
		
		
		
		//记住我（数据库）
		
		  JdbcTokenRepositoryImpl ptr = new JdbcTokenRepositoryImpl();
		  ptr.setDataSource(dataSource); 
		  http.rememberMe().tokenRepository(ptr);
		 
	}
}
