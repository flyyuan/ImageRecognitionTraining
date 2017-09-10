<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存分类成功管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/tpsb/tpsbCat/">保存分类成功列表</a></li>
		<li class="active"><a href="${ctx}/tpsb/tpsbCat/form?id=${tpsbCat.id}">保存分类成功<shiro:hasPermission name="tpsb:tpsbCat:edit">${not empty tpsbCat.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="tpsb:tpsbCat:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tpsbCat" action="${ctx}/tpsb/tpsbCat/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">父类目ID=0时，代表的是一级的类目：</label>
			<form:input path="parentId" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			<div class="controls">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类目名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态。可选值:1(正常),2(删除)：</label>
			<div class="controls">
				<form:input path="status" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数：</label>
			<div class="controls">
				<form:input path="sortOrder" htmlEscape="false" maxlength="4" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">该类目是否为父类目，1为true，0为false：</label>
			<div class="controls">
				<form:input path="isParent" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="created" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tpsbCat.created}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="updated" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tpsbCat.updated}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="tpsb:tpsbCat:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>