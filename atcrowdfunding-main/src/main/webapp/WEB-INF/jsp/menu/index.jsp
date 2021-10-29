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
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i>菜单树</h3>
			  </div>
			  <div class="panel-body">
 				<!-- 树的地方 -->
 				  <ul id="treeDemo" class="ztree"></ul>
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
	        <h4 class="modal-title" id="myModalLabel">添加菜单</h4>
	      </div>
	      <div class="modal-body">
					  <div class="form-group">
						<label for="name">菜单名字</label>
						<input type="hidden" name="pid" >
						<input type="text" class="form-control" id="name" name="name" placeholder="请输入菜单名字">
					  </div>
					    <div class="form-group">
						<label for="url">菜单url</label>
						<input type="text" class="form-control" id="url" name="url" placeholder="请输入菜单url">
					  </div>
					    <div class="form-group">
						<label for="icon">菜单图标</label>
						<input type="text" class="form-control" id="icon" name="icon" placeholder="请输入菜单icon">
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
	        <h4 class="modal-title" id="myModalLabel">修改菜单</h4>
	      </div>
	      <div class="modal-body">
					  <div class="form-group">
						<label for="name">菜单名字</label>
						<input type="hidden" name="id" >
						<input type="text" class="form-control" id="name" name="name" placeholder="请输入菜单名字">
					  </div>
					    <div class="form-group">
						<label for="url">菜单url</label>
						<input type="text" class="form-control" id="url" name="url" placeholder="请输入菜单url">
					  </div>
					    <div class="form-group">
						<label for="icon">菜单图标</label>
						<input type="text" class="form-control" id="icon" name="icon" placeholder="请输入菜单icon">
					  </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        <button id="updateBtn" type="button" class="btn btn-primary">修改</button>
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
			    
			    
			    initTree();
            });

            
            function initTree(){
            	 var setting = {
                 		data: {
                 			simpleData: {
                 				enable:true,
                 				idKey:'id',
                 				pIdKey:'pid' 
                 			}
                 		},
                 		
                 		
                 		//自己弄图标
                 		view:{
                 			addDiyDom:function(treeId,treeNode){
                 				$("#"+treeNode.tId+"_ico").removeClass();
                 				$("#"+treeNode.tId+"_span").before("<span class='"+treeNode.icon+"'></span>");
                 			},
                 			
                 			addHoverDom: function(treeId, treeNode){  
    							var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
    							aObj.attr("href", "javascript:;");
    							if (treeNode.editNameFlag || $("#btnGroup"+treeNode.tId).length>0) return;
    							
    							
    							//按钮组
    							var s = '<span id="btnGroup'+treeNode.tId+'">';
    							if ( treeNode.level == 0 ) {  //根节点
    								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addBtn('+treeNode.id+')">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
    							} else if ( treeNode.level == 1 ) {//分支节点
    								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="updateBtn('+treeNode.id+')" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
    								if (treeNode.children.length == 0) {
    									s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteBtn('+treeNode.id+')" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
    								}
    								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addBtn('+treeNode.id+')" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
    							} else if ( treeNode.level == 2 ) { //叶子节点
    								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="updateBtn('+treeNode.id+')" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
    								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteBtn('+treeNode.id+')">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
    							}
    			
    							s += '</span>';
    							aObj.after(s);
    						},
    						removeHoverDom: function(treeId, treeNode){
    							$("#btnGroup"+treeNode.tId).remove();
    						}
                 		}
                 		
                 		
                 		
                 		
            	 
            	 
            	 		
                 		 
                 	};
				
            	 
            	 $.get('${PATH}/menu/loadTree',{},function(result){
						 //返回的是list集合，放的是TMenu，转化为json了
            		 var zNodes =result;
						 zNodes.push({id:"0",name:"系统菜单"});
                       $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                     
                       var treeObj  =  $.fn.zTree.getZTreeObj("treeDemo");
                       treeObj.expandAll(true);
                       
            	 });
                 
            	
            }
            
            
            
            
            
      
        	//======================添加开始===========================
           	function addBtn(id){
        		$('#addModal').modal({
        			show:true,
        			backdrop:'static',
        			keyboard:false
        		});
        		
        		$("#addModal input[name='pid']").val(id);  //挂上pid
        		
        	}
        		
        	$("#saveBtn").click(function(){
        		var pid = $("#addModal input[name='pid']").val();  //取pid
        		var name = $("#addModal input[name='name']").val();  //取pid
        		var url = $("#addModal input[name='url']").val();  //取pid
        		var icon = $("#addModal input[name='icon']").val();  //取pid
        		
        		//发起ajax请求
        		$.ajax({
        			type:"post",
        			url:"${PATH}/menu/doAdd",
        			data:{
        				'pid':pid,
        				'name':name,
        				'url':url,
        				'icon':icon
        			},
        			beforeSend:function(){
        				
        				return true;
        			},
        			success:function(result){
        				if("ok"==result){
        					layer.msg("保存成功",{time:1000},function(){
        						
        						//清除
        						$("#addModal input[name='pid']").val("");  //取pid
        	  					$("#addModal input[name='name']").val("");  //取pid
        						$("#addModal input[name='url']").val("");  //取pid
        		 				$("#addModal input[name='icon']").val("");  //取pid
        						$("#addModal").modal('hide');//关闭模态框
        						
        						initTree();  //重新构造树
        					});
        				}else{
        					layer.msg("保存失败");
        				}
        			}
        		});
        		
        	});
        	//======================添加结束===========================
    	 
            
            //======================修改开始===========================
           function updateBtn(id){
        		
        		
        		$.get("${PATH}/menu/getMenuById",{id:id},function(result){
       			//console.log(result);
       			//回显数据
	        		//显示模态框
	          		$("#updateModal").modal({
	          			show:true,
	          			backdrop:'static',
	          			keyboard:false
         			});
       			
	          		$("#updateModal input[name='id']").val(result.id);  //取pid
  					$("#updateModal input[name='name']").val(result.name);  //取pid
					$("#updateModal input[name='url']").val(result.url);  //取pid
	 				$("#updateModal input[name='icon']").val(result.icon);  //取pid
					
       	  		}); 
        	}
            
           $("#updateBtn").click(function(){
       		var id = $("#updateModal input[name='id']").val();  //取pid
       		var name = $("#updateModal input[name='name']").val();  //取pid
       		var url = $("#updateModal input[name='url']").val();  //取pid
       		var icon = $("#updateModal input[name='icon']").val();  //取pid
       		
       		//发起ajax请求
       		$.ajax({
       			type:"post",
       			url:"${PATH}/menu/doUpdate",
       			data:{
       				'id':id,
       				'name':name,
       				'url':url,
       				'icon':icon
       			},
       			beforeSend:function(){
       				
       				return true;
       			},
       			success:function(result){
       				if("ok"==result){
       					layer.msg("保存成功",{time:1000},function(){
       						
       						//清除
       						$("#updateModal input[name='id']").val("");  //取pid
       	  					$("#updateModal input[name='name']").val("");  //取pid
       						$("#updateModal input[name='url']").val("");  //取pid
       		 				$("#updateModal input[name='icon']").val("");  //取pid
       						$("#updateModal").modal('hide');//关闭模态框
       						
       						initTree();  //重新构造树
       					});
       				}else{
       					layer.msg("保存失败");
       				}
       			}
       		});
       		
       	});
        	
            	
            	
            	
           	//======================修改结束===========================
    	 
        	   
            //======================删除开始===========================
           function deleteBtn(id){
        	    
 				
        	   	$.post('${PATH}/menu/doDelete',{id:id},function(result){
         			if("ok"==result){
         				layer.msg("删除成功",{time:1000},function(){
   					initTree();
   					});
         			}else{
         				layer.msg("删除失败");
         			}
         		});
           }
            	
            	
            	
            //======================删除结束===========================
    	 
        </script>
  </body>
</html>
    