<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>>
<!DOCTYPE html>
<html lang="zh_CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<%@ include file="/WEB-INF/jsp/common/css.jsp" %> <!-- 静态包含 -->
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
	</style>
  </head>

  <body>
 
	<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include> <!-- 动态包含 -->

    <div class="container-fluid">
      <div class="row">
        <jsp:include page="/WEB-INF/jsp/common/sidebar.jsp"></jsp:include> <!-- 动态包含 -->
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
<form class="form-inline" role="form" style="float:left;">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input class="form-control has-success" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='form.html'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input type="checkbox"></th>
                  <th>名称</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
               		<!-- 数据正在加载中 -->
               		
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
						<ul class="pagination">
								<!-- 分页 --> 
							 </ul>
					 </td>
				 </tr>

			  </tfoot>
            </table>
          </div>
			  </div>
			</div>
        </div>
      </div>
    </div>

    <%@ include file="/WEB-INF/jsp/common/js.jsp" %> <!-- 静态包含 -->
     <script src="${PATH}/static/jquery/layer/layer.js"></script>
        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
			    
			    //页面加载完就要执行（初始化数据第一页）
			    initData(1);
            });
            
            function initData(pageNum){
            	//发起Ajax请求（获取数据）
            	var json={
            			pageNum:pageNum,
            			pageSize:2
            		};
            	
            	var index = -1;
            	
            	$.ajax({
            		type:'post',
            		url:'${PATH}/role/loadData',
            		data:json,
            		beforSend:function(){
            			index = layer.load(0,{time:10000}); //加载中 0图片
            			return true;
            		},
            		success:function(result){
            			console.log(result); //查询出来的数据
       					layer.close(index);//成功就关闭弹层     			
       					//展示数据
       	            	initShow(result);
       	            	//展示分页条
       					initNavg(result);
            		}
            		
            	});
            }
          
         	//展示数据
        	function initShow(result){
        		console.log("show"+result);
        		
        		//先删除之前的加的语句
        		$("tbody").empty();
        		
        		
        		var list = result.list; //pageinfo的数据集
        		
        		//往tbody展示
        		var content = '';
        		
        		$.each(list,function(i,e){
        			content+='<tr>';
        			content+='    <td>'+(i+1)+'</td>';
        			content+='	<td><input type="checkbox"></td>';
        			content+='    <td>'+e.name+'</td>';
        			content+='    <td>';
        			content+='		<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
        			content+='		<button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
        			content+='		<button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
        			content+='	</td>';
        			content+='</tr>';
        		});
        		
        		//局部刷新
        		$("tbody").html(content);
        		
         	}
        	//展示分页条
			function initNavg(result){
				$(".pagination").empty();
				var navigatePages = result.navigatepageNums; //pageinfo的数据集
        		
				console.log("Nag"+result);
				
				if(result.isFirstPage){
					$(".pagination").append($('<li class="disabled"><a href="#">上一页</a></li>'));
				}else{
					$(".pagination").append($('<li><a onclick="initData('+(result.pageNum-1)+')">上一页</a></li>'));
				}
				
				
				//循环拼接
				$.each(navigatePages,function(i,e){
					if(e == result.pageNum){
						$(".pagination").append($('<li class="active"><a onclick="initData('+e+')">'+e+'<span class="sr-only">(current)</span></a></li>'));					
					}else{
						$(".pagination").append($('<li><a onclick="initData('+e+')">'+e+'</a></li>'));
					}
        		});
				
				if(result.isLastPage){
					$(".pagination").append($('<li class="disabled"><a href="#">下一页</a></li>'));
				}else{
					$(".pagination").append($('<li><a onclick="initData('+(result.pageNum+1)+')">下一页</a></li>'));
				}
				
        	}
            
        </script>
  </body>
</html>
