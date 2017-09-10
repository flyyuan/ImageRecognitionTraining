<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>用户标签详细信息</title>
<meta name="decorator" content="default" />
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
				switch (document.getElementsByName("stateName")[i].innerText){
				case '0':
					document.getElementsByName("stateName")[i].innerText="系统未确认";
					document.getElementsByName("stateName")[i].style.color="#FF5555";
					break;
				case '1':
					document.getElementsByName("stateName")[i].innerText="系统已确认";
					document.getElementsByName("stateName")[i].style.color="#157ab5";
					break;
				}
			}
		}
	
	
</script>
</head>
<body>
	 <ul class="nav nav-tabs">
		<li><a href="${ctx}/tpsb/userManage/list">任务完成情况</a></li>
		<shiro:hasPermission name="tpsb:userManage:view"><li class="active"><a href="#">用户标签详细信息</a></li></shiro:hasPermission>
	</ul> 
	<form:form id="searchForm" modelAttribute="tpsbCat"
		action="${ctx}/tpsb/userManage/findUserLabelsByUserId" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
			
			<input name="id" id="id" value="${userLabel.id}" type="hidden" />
		<ul class="ul-form">
			<div>
				<label>标签：</label>
				<input id="label" name="label" type="text"
				maxlength="50" class="input-mini" value="${userLabel.label}" placeholder="标签关键字..." />
				<label>图片URI：</label>
				<input id="url" name="url" type="text" maxlength="50"
				class="input-mini" value="${userLabel.url}" />
			<%-- <label>日期范围：&nbsp;</label><input id="beginDateString" name="beginDateString"
				type="text" readonly="readonly" maxlength="20"
				class="input-mini Wdate"
				value="<fmt:formatDate value="${userLabel.beginDate}" pattern="yyyy-MM-dd"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			<label>&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input
				id="endDateString" name="endDateString" type="text" readonly="readonly"
				maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${userLabel.endDate}" pattern="yyyy-MM-dd"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />&nbsp;&nbsp;
			
			<label for="modifyStatus"><input id="modifyStatus"
				name="modifyStatus" type="checkbox"
				${userLabel.modifyStatus eq '1'?' checked':''} value="1" />只显示系统确认标签</label> --%>
			&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" />&nbsp;&nbsp;
		</div>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="text-align:center">序号</th>
				<th style="text-align:center">图片名字</th>	
				<th style="text-align:center">图片预览</th>	
				<th style="text-align:center">未确认标签</th>
				<th style="text-align:center">确认标签</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="userLabel" varStatus="i">
				<tr>
					<td style="text-align:center">${i.count}</td>
					<td style="text-align:center">${userLabel.picName}</td>
					<td width="13%" style="text-align:center"><img src="http://114.115.139.232:8080/pic/image/${userLabel.url}" align="center" onload="DrawImage(this,160,160)">
					</td>
					<td style="text-align:center">
					<c:forEach items="${userLabel.errorLabels}" var="label1">
					${label1}&nbsp;
					</c:forEach>
					
					</td>
					<td style="text-align:center">
					<c:forEach items="${userLabel.succLabels}" var="label2">
					${label2}&nbsp;
					</c:forEach>
					</td>
					<%-- <td name="stateName" style="text-align:center">${userLabel.modifyStatus}</td>
					<td style="text-align:center">${userLabel.updateNum}</td>
					<td style="text-align:center"><fmt:formatDate value="${userLabel.updateDate}" pattern="yyyy-MM-dd"/></td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
