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
      <input id="condition" class="form-control has-success" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button id="addBtn" type="button" class="btn btn-primary" style="float:right;" ><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				
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


	<!-- 添加的模态框Modal -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">添加角色</h4>
	      </div>
	      <div class="modal-body">
					  <div class="form-group">
						<label for="exampleInputPassword1">角色名字</label>
						<input type="text" class="form-control" id="name" name="name" placeholder="请输入角色名字">
					  </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        <button id="saveBtn" type="button" class="btn btn-primary">保存</button>
	      </div>
	    </div>
	  </div>
	</div>





	<!-- 修改的模态框Modal -->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">修改角色</h4>
	      </div>
	      <div class="modal-body">
					  <div class="form-group">
						<label for="exampleInputPassword1">角色名字</label>
						<input type="hidden" class="form-control" id="id" name="id" >
						<input type="text" class="form-control" id="name" name="name" placeholder="请输入角色名字">
					  </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        <button  id="updateBtn" type="button" class="btn btn-primary">修改</button>
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
            
            //================================分页查询=================================
        	var json={
        			pageNum:1,
        			pageSize:2,
        		};
            
            function initData(pageNum){
            	//发起Ajax请求（获取数据）
            	
            	json.pageNum = pageNum;
            	
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
        			content+='    <td>'+e.name+'</td>';
        			content+='    <td>';
        			content+='		<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
        			content+='		<button type="button" roleId="'+e.id+'" class="updateClass btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
        			content+='		<button type="button" roleId="'+e.id+'" class="deleteClass btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
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
            
        	//=================================================分页查询结束================================
        	
        	
        	$("#queryBtn").click(function(){
        		
        		var condition = $("#condition").val(); //拿到输入的值
        		
        		json.condition = condition;
        		
        		initData(1); //查数据 
        	});
        	
        	
        	
        	
        	
        	
        	
        	//==============添加开始 =======================
        	$("#addBtn").click(function(){
        		//显示模态框
        		$("#addModal").modal({
        			show:true,
        			backdrop:'static',
        			keyboard:false
        		});
        	});
        	
        	$("#saveBtn").click(function(){
        		var name = $("#addModal input[name='name']").val();//获取输入的名字
        		
        		$.ajax({
        			type:"post",
        			url:"${PATH}/role/doAdd",
        			data:{
        				'name':name
        			},
        			beforeSend:function(){
        				
        				return true;
        			},
        			success:function(result){
        				if("ok"==result){
        					layer.msg("保存成功",{time:1000},function(){
        						
        						//清除
        						$("#addModal input[name='name']").val("");
        						$("#addModal").modal('hide');//关闭模态框
        					});
        				}else{
        					layer.msg("保存失败");
        				}
        			}
        		});
        	});
        	//==============添加结束 =======================
        	
        	//==============修改开始 =======================
        	/* $(".updateClass").click(function(){
        		alert("update");
        	}); */
        	
        	
        	$('tbody').on('click','.updateClass',function(){
        		var roleId = $(this).attr("roleId");
        		
        		//发送get请求 查询
        	  $.get("${PATH}/role/getRoleById",{id:roleId},function(result){
        			//console.log(result);
        			//回显数据
	        		//显示模态框
	          		$("#updateModal").modal({
	          			show:true,
	          			backdrop:'static',
	          			keyboard:false
          			});
        			
	          		$("#updateModal input[name='name']").val(result.name);
	          		$("#updateModal input[name='id']").val(result.id);
        	  	}); 
        	});
        	
        	$("#updateBtn").click(function(){
        		var name = $("#updateModal input[name='name']").val();
          		var id = $("#updateModal input[name='id']").val();
				
          		$.post('${PATH}/role/doUpdate',{id:id,name:name},function(result){
          			if("ok"==result){
          				layer.msg("修改成功",{time:1000},function(){
    						//清除
    						$("#updateModal input[name='name']").val("");
    						$("#updateModal").modal('hide');//关闭模态框
    						
    						initData(json.pageNum);
    					});
          			}else{
          				layer.msg("修改失败");
          			}
          		});
        	});
            //==============修改结束 =======================
            	
            	
            	
          	//==============del开始 =======================
      			$("tbody").on('click','.deleteClass',function(){
      				var id = $(this).attr("roleId");
      				$.post('${PATH}/role/doDelete',{id:id},function(result){
              			if("ok"==result){
              				layer.msg("删除成功",{time:1000},function(){
        					initData(json.pageNum);
        					});
              			}else{
              				layer.msg("删除失败");
              			}
              		});
      			});
          	
            //==============del结束 =======================
            	
            	
            	
            	
            	
            	
        </script>
  </body>
</html>
