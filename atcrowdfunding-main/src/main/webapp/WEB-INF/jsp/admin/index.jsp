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
<form id="queryForm" class="form-inline" role="form" style="float:left;" action="${PATH }/admin/index" method="post">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input class="form-control has-success" name="condition" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button type="button" class="btn btn-warning" onclick="$('#queryForm').submit()"><i class="glyphicon glyphicon-search"></i>查询</button>
</form>
<button id="deleteBatchBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${PATH}/admin/toAdd'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input id="selectAll" type="checkbox"></th>
                  <th>账号</th>
                  <th>名称</th>
                  <th>邮箱地址</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
               
	              <c:forEach items="${page.list }" var="admin">
		                <tr>
		                  <td>${admin.id }</td>
						  <td><input type="checkbox" adminId="${admin.id }"></td>
		                  <td>${admin.loginacct }</td>
		                  <td>${admin.username }</td>
		                  <td>${admin.email }</td>
		                  <td>
						      <button type="button" class="btn btn-success btn-xs" onclick="window.location.href='${PATH}/admin/toAssign?id=${admin.id }'"><i class=" glyphicon glyphicon-check"></i></button>
						      <button type="button" class="btn btn-primary btn-xs" onclick="window.location.href='${PATH}/admin/toUpdate?id=${admin.id }&pageNum=${page.pageNum }'"><i class=" glyphicon glyphicon-pencil"></i></button>
							  <button type="button" id="wx" adminId="${admin.id }" class="deleteBtnClass btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>
						  </td>
		                </tr>
	              </c:forEach>
          
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
						<ul class="pagination">
								
								<c:if test="${page.isFirstPage }">
									<li class="disabled"><a href="#">上一页</a></li>
								</c:if>
								<c:if test="${!page.isFirstPage }">
									<li><a href="${PATH}/admin/index?condition=${param.condition }&pageNum=${page.pageNum-1}">上一页</a></li>
								</c:if>
								
								<c:forEach items="${page.navigatepageNums}" var="num">
									<%-- <li><a href="${PATH}/admin/index?pageNum=${num}">${num }</a></li> --%>
									<c:if test="${num==page.pageNum}">
										<li class="active"><a href="${PATH}/admin/index?condition=${param.condition }&pageNum=${num}">${num}<span class="sr-only">(current)</span></a></li>
									</c:if>
									<c:if test="${num!=page.pageNum}">
										<li><a href="${PATH}/admin/index?condition=${param.condition }&pageNum=${num}">${num }</a></li>
									</c:if>
								</c:forEach>
								
								
								<c:if test="${page.isLastPage }">
									<li class="disabled"><a href="#">下一页</a></li>
								</c:if>
								<c:if test="${!page.isLastPage }">
									<li><a href="${PATH}/admin/index?condition=${param.condition }&pageNum=${page.pageNum+1}">下一页</a></li>
								</c:if>
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
            });
           
            $(".deleteBtnClass").click(function(){
            	
            	//通过自定义属性值来取出id
        		var id = $(this).attr("adminId");
        		
        		//询问框
            	layer.confirm('您是否确认删除？', {
            	  btn: ['确定','取消'] //按钮
            	}, function(index){ //成功
            		
            		window.location.href="${PATH}/admin/doDelete?pageNum=${page.pageNum }&id="+id;
            		layer.close(index);
            	}, function(index){ //失败
            		
            		layer.close(index);
            	  });
            	
            });
 		 
            
            
            $("#selectAll").click(function(){
               //$("tbody input[type='checkbox']").attr(" checked",this.checked);  //用于获取自定义属性好用
               
            	$("tbody input[type='checkbox']").prop("checked",this.checked); //主要是处理attr的bug
            
            });
            
            $("#deleteBatchBtn").click(function(){
            	var checkedBoxList = $("tbody input[type='checkbox']:checked");//拿到被勾选的
          		
            	
            	if(checkedBoxList.length==0) {
            		layer.msg("请选择需要删除的项目！");
					return false;
            	}else{
					var ids = '';
					
					$.each(checkedBoxList,function(i,e){
						var adminId = $(e).attr("adminId"); //获取自定义属性
						if(i!=0){
							ids += ',';
						}
						ids += adminId;
						
					});
					
					//询问框
	            	layer.confirm('您是否确认删除？', {
	            	  btn: ['确定','取消'] //按钮
	            	}, function(index){ //成功
	            		
	            		window.location.href="${PATH}/admin/doDeleteBatch?pageNum=${page.pageNum}&ids="+ids;
	            		layer.close(index);
	            	}, function(index){ //失败
	            		
	            		layer.close(index);
	            	  });
					
					
					
					//alert(ids);
					
            	}
            	
            });
            
        </script>
  </body>
</html>
    