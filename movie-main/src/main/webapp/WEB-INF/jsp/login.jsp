<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
	<link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="static/css/font-awesome.min.css">
	<link rel="stylesheet" href="static/css/login.css">
	<style>

	</style>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="#" style="font-size:32px;">基于JAVA的电影推荐系统</a></div>
        </div>
      </div>
    </nav>

    <div class="container">

      <form  id="loginForm" class="form-signin" role="form" action="doLogin" method="post" > 
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" name="username" id="inputSuccess4" placeholder="请输入登录账号" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  </div> 
		  <div class="form-group has-success has-feedback">
			<input type="password" class="form-control" name="password" id="inputSuccess4" placeholder="请输入登录密码" style="margin-top:10px;">
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		  </div>
		  
        <div class="checkbox">
          <label>
             
          </label>
          <br>
          <label>
             
          </label>
          <label style="float:right">
            
          </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" >登录</a>
      </form>
    </div>
    <script src="static/jquery/jquery-2.1.1.min.js"></script>
    <script src="static/bootstrap/js/bootstrap.min.js"></script>
    <script>
    function dologin() {
       $("#loginForm").submit();
    }
    </script>
  </body>
</html>