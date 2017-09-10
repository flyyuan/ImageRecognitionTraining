<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>用户任务完成情况</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	

	function delSelect(n) {
		var checkBoxSet = document.getElementsByName("delCheckbox");
		var idAll = "";
		for (var i = 0; i < checkBoxSet.length; i++) {
			var sta = checkBoxSet[i];
			if (checkBoxSet[i].checked) {

				idAll = idAll + sta.id + ",";

			}
		}

		confirmx("确认被勾选的内容吗？", this.href = "${ctx}/tpsb/userManage/delete?id="
				+ idAll + "&status=" + n);
	}
	function checkAll(booleanVaule) {
		var checkBoxSet = document.getElementsByName("delCheckbox");
		for (var i = 0; i < checkBoxSet.length; i++) {
			checkBoxSet[i].checked = booleanVaule;
		}

	}

	$(document).ready(function() {
		state2Name();
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	
	function state2Name(){
		
		var sum = document.getElementsByName("stateName").length;
		for(var i=0;i<sum;i++){
			
			if((document.getElementsByName("stateName")[i].innerText)<50){
				document.getElementsByName("stateName")[i].style.color="#FF5555";
			}
			
			if((document.getElementsByName("stateName")[i].innerText)>90){
				document.getElementsByName("stateName")[i].style.color="green";
			}
			
			switch (document.getElementsByName("stateName")[i].innerText){
			case '1':
				document.getElementsByName("stateName")[i].innerText="优先填写";
				document.getElementsByName("stateName")[i].style.color="#7DB343";
				break;
			case '2':
				document.getElementsByName("stateName")[i].innerText="优选选择";
				document.getElementsByName("stateName")[i].style.color="#7DB343";
				break;
			case '3':
				document.getElementsByName("stateName")[i].innerText="禁止填写";
				document.getElementsByName("stateName")[i].style.color="#7600A";
				break;
			case '4':
				document.getElementsByName("stateName")[i].innerText="禁止选择";
				document.getElementsByName("stateName")[i].style.color="#7600A";
				break;
			case '5':
				document.getElementsByName("stateName")[i].innerText="禁止填写选择";
				document.getElementsByName("stateName")[i].style.color="#D7282D";
				break;
			case '0':
				document.getElementsByName("stateName")[i].innerText="正常";
				document.getElementsByName("stateName")[i].style.color="#42ACE8";
				break;	
			case '6':
				document.getElementsByName("stateName")[i].innerText="正常";
				document.getElementsByName("stateName")[i].style.color="#42ACE8";
				break;
			}
		}
	}

</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/tpsb/tpsbCat/">保存分类成功列表</a></li>
		<shiro:hasPermission name="tpsb:tpsbCat:edit"><li><a href="${ctx}/tpsb/tpsbCat/form">保存分类成功添加</a></li></shiro:hasPermission>
	</ul> --%>
	<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/tpsb/userManage/list">任务完成情况</a></li>
	</ul> 
	<form:form id="searchForm" modelAttribute="tpsbCat"
		action="${ctx}/tpsb/userManage/list" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<div>
				<label>志愿者名字： </label> <input id="name" name="name" type="text"
					maxlength="50" class="input-mini" placeholder="关键字..." />
				<%-- <label>用户ID：</label>
				<input id="createBy.id" name="createBy.id" type="text" maxlength="50"
				class="input-mini" value="${userLabel.createBy.id}" /> 
				<label>图片URI：</label>
				<input id="url" name="url" type="text" maxlength="50"
				class="input-mini" value="${userLabel.url}" /> --%>
				&nbsp;&nbsp;&nbsp;<input id="btnSubmit"
					class="btn btn-info btn-primary" type="submit" value="查询" />&nbsp;&nbsp;

			</div>
			<br>
			<div>
				<label>设置权限： </label> <input type="button" class="btn btn-primary"
					onclick=delSelect(6) value="恢复正常"></input>&nbsp;&nbsp; <input
					type="button" class="btn btn-success btn-primary"
					onclick=delSelect(1) value="优先填写"></input>&nbsp;&nbsp; <input
					type="button" class="btn btn-success btn-primary"
					onclick=delSelect(2) value="优先选择"></input>&nbsp;&nbsp; <input
					type="button" class="btn btn-warning btn-primary"
					onclick=delSelect(3) value="禁止填写"></input>&nbsp;&nbsp; <input
					type="button" class="btn btn-warning btn-primary"
					onclick=delSelect(4) value="禁止选择"></input>&nbsp;&nbsp; <input
					type="button" class="btn btn-danger btn-primary"
					onclick=delSelect(5) value="禁止选择填写"></input>
				<!-- &nbsp;&nbsp;
                    <input type="button"  class="btn btn-danger btn-primary" value="删除" ></input> -->

			</div>
			<br>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align:center">序号</th>
				<th style="text-align:center"><input id="${tagResult.id }" name="checkAll"
					type="checkbox" value="<%=request.getParameter("checkAll")%>"
					onclick="checkAll(this.checked)" /></th>
				<th style="text-align:center">详情</th>	
				<th style="text-align:center">志愿者名字</th>
				<th style="text-align:center">权限状态</th>
				<th style="text-align:center">正确填写标签数量</th>
				<th style="text-align:center">总填写标签数量</th>
				<th style="text-align:center">正确选择标签数量</th>
				<th style="text-align:center">总选择标签数量</th>
				<th style="text-align:center">填写标签正确率(%)</th>
				<th style="text-align:center">选择标签正确率(%)</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="tagResult" varStatus="i">
				<tr>
					<input id="id" name="id" type="hidden" value="${tagResult.id}" />
					<td>${i.count }</td>
					<td><input id="${tagResult.id }" name="delCheckbox"
						type="checkbox" value="<%=request.getParameter("delCheckbox")%>" />
					</td>
					<td style="text-align:center">
    					<a href="${ctx}/tpsb/userManage/findUserLabelsByUserId?id=${tagResult.id}"><i class="icon-edit"></i></a>
					</td>
					<td>${tagResult.name}</td>
					<td name="stateName">${tagResult.jurisdicStatus}</td>
					<td>${tagResult.writeSuccNum}</td>
					<td>${tagResult.writeNum}</td>
					<td>${tagResult.selectSuccNum}</td>
					<td>${tagResult.selectNum}</td>
					<td name="stateName"><fmt:formatNumber value="${tagResult.writeSuccRate}"
							pattern="0.00" type="number" /></td>
					<td name="stateName"><fmt:formatNumber value="${tagResult.selectSuccRate}"
							pattern="0.00" type="number" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
