<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>标签日志查询</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
	//显示缩略图
	function DrawImage(ImgD, width_s, height_s) {
		/*var width_s=139;
		var height_s=104;
		 */
		var image = new Image();
		image.src = ImgD.src;
		if (image.width > 0 && image.height > 0) {
			flag = true;
			if (image.width / image.height >= width_s / height_s) {
				if (image.width > width_s) {
					ImgD.width = width_s;
					ImgD.height = (image.height * width_s) / image.width;
				} else {
					ImgD.width = image.width;
					ImgD.height = image.height;
				}
			} else {
				if (image.height > height_s) {
					ImgD.height = height_s;
					ImgD.width = (image.width * height_s) / image.height;
				} else {
					ImgD.width = image.width;
					ImgD.height = image.height;
				}
			}
		}
		/*else{
		ImgD.src="";
		ImgD.alt=""
		}*/
	}
	
	
	
	
	
	
	
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
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/tpsb/tpsbCat/">保存分类成功列表</a></li>
		<shiro:hasPermission name="tpsb:tpsbCat:edit"><li><a href="${ctx}/tpsb/tpsbCat/form">保存分类成功添加</a></li></shiro:hasPermission>
	</ul> --%>
	<form:form id="searchForm" modelAttribute="tpsbCat" action="${ctx}/tpsb/log/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<div>
			<label>标签：
				</label>
				<input id="label" name="label" type="text"
				maxlength="50" class="input-mini" value="${userLabel.label}" placeholder="标签关键字..." />
				<label>用户ID：</label>
				<input id="createBy.id" name="createBy.id" type="text" maxlength="50"
				class="input-mini" value="${userLabel.createBy.id}" /> 
				<label>图片URI：</label>
				<input id="url" name="url" type="text" maxlength="50"
				class="input-mini" value="${userLabel.url}" />
		</div>
		
		<div style="margin-top:8px;">
			<label>日期范围：&nbsp;</label><input id="beginDateString" name="beginDateString"
				type="text" readonly="readonly" maxlength="20"
				class="input-mini Wdate"
				value="<fmt:formatDate value="${userLabel.beginDate}" pattern="yyyy-MM-dd"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			<label>&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input
				id="endDateString" name="endDateString" type="text" readonly="readonly"
				maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${userLabel.endDate}" pattern="yyyy-MM-dd"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />&nbsp;&nbsp;
			&nbsp;
			&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" />&nbsp;&nbsp;
		</div>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			<th>序号</th>
				<th style="text-align:center">图片名字</th>
				<th style="text-align:center">填写志愿者</th>
				<th style="text-align:center">所在公司</th>
				<th style="text-align:center">所在部门</th>
				<th style="text-align:center">图片预览</th>
				<th style="text-align:center">填写标签</th>
				<th style="text-align:center">填写时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userLabel" varStatus="i">
			<tr>
			<td>${i.count }</td>
				<td>${userLabel.picName}</td>
					<td>${userLabel.createBy.name}</td>
					<td>${userLabel.createBy.company.name}</td>
					<td>${userLabel.createBy.office.name}</td>
					<td width="13%">
						<div><img src="http://114.115.139.232:8080/pic/image/${userLabel.url}" align="center" onload="DrawImage(this,160,160)"></div>
					
					</td>
					<td>${userLabel.label}</td>
					<td><fmt:formatDate value="${userLabel.createDate}" type="both" /></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
