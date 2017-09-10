<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存分类成功管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/tpsb/tpsbCat/">保存分类成功列表</a></li>
		<shiro:hasPermission name="tpsb:tpsbCat:edit"><li><a href="${ctx}/tpsb/tpsbCat/form">保存分类成功添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tpsbCat" action="${ctx}/tpsb/tpsbCat/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>类目名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>类目名称</th>
				<th>父类目ID</th>
				<th>状态</th>
				<th>排列序号</th>
				<th>是否为父类目</th>
				
				<shiro:hasPermission name="tpsb:tpsbCat:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tpsbCat">
			<tr>
				<td><a href="${ctx}/tpsb/tpsbCat/form?id=${tpsbCat.id}">
					${tpsbCat.name}
				</a></td>
				
				<td>
					${tpsbCat.parentId}
				</td>
				
				<td>
					${tpsbCat.status}
				</td>
				
				<td>
					${tpsbCat.sortOrder}
				</td>
				
				<td>
					${tpsbCat.isParent}
				</td>
				
				
				
				
				<shiro:hasPermission name="tpsb:tpsbCat:edit"><td>
    				<a href="${ctx}/tpsb/tpsbCat/form?id=${tpsbCat.id}">修改</a>
					<a href="${ctx}/tpsb/tpsbCat/delete?id=${tpsbCat.id}" onclick="return confirmx('确认要删除该保存分类成功吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
