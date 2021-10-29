<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
	
	<%@ include file="/WEB-INF/jsp/common/css.jsp" %> <!-- 静态包含 -->
	<style>

	</style>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="#" style="font-size:32px;">基于JAVA的电影推荐系统登录</a></div>
        </div>
      </div>
    </nav>

    <div class="container">

      <form id="loginForm" class="form-signin" role="form" action="${PATH}/login2" method="post">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
        <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
           <div class="form-group has-success has-feedback">
			 	<font color="red">${SPRING_SECURITY_LAST_EXCEPTION.message }</font>
		   </div>
        </c:if>
		  <div class="form-group has-success has-feedback">
			<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"> 
			<input type="text" class="form-control" id="loginacct" name="loginacct" value="${param.loginacct }" placeholder="请输入登录账号" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  </div> 
		  <div class="form-group has-success has-feedback">
			<input type="password" class="form-control" id="userpswd" name="userpswd" placeholder="请输入登录密码" style="margin-top:10px;">
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		  </div>
		   
        <div class="checkbox">
           <label>
          <!--  <input type="checkbox" name="remember-me"> 记住我 -->
          </label>
          <br>
           <label>
            <!--忘记密码-->
          </label> 
          <label style="float:right">
            <a href="reg.html">我要注册</a>
          </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
      </form>
    </div>
   <%@ include file="/WEB-INF/jsp/common/js.jsp" %> <!-- 静态包含 -->
    <script>
    function dologin() {
    	var loginacct = $("#loginacct").val();
    	if($.trim(loginacct)==""){
    		//提示层
    		layer.msg('用户名为空',{time:2000,icon:5});
    		return false;
    	}
         $("#loginForm").submit();
    }
    </script>
  </body>
</html>