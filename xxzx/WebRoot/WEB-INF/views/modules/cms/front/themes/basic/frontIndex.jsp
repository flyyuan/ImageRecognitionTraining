<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>首页</title>
	<meta name="decorator" content="cms_default_${site.theme}"/>
	<meta name="description" content="JeeSite ${site.description}" />
	<meta name="keywords" content="JeeSite ${site.keywords}" />
</head>
<body>
    <div class="hero-unit" style="padding-bottom:35px;margin:10px 0;">
      <c:set var="article" value="${fnc:getArticle('1')}"/>
      <h1>${fns:abbr(article.title,28)}</h1><p></p>
      <p>${fns:abbr(fns:replaceHtml(article.articleData.content),260)}</p>
      <p><a href="${article.url}" class="btn btn-primary btn-large">&nbsp;&nbsp;&nbsp;查看详情 &raquo;&nbsp;&nbsp;&nbsp;</a></p>
    </div>
    <div class="row">
      <div class="span4">
        <h4><small><a href="${ctx}/list-7${urlSuffix}" class="pull-right">更多&gt;&gt;</a></small>公司要闻</h4>
		<ul><c:forEach items="${fnc:getArticleList(site.id, 7, 8, '')}" var="article">
			<li><span class="pull-right"><fmt:formatDate value="${article.updateDate}" pattern="yyyy.MM.dd"/></span><a href="${article.url}" style="color:${article.color}">${fns:abbr(article.title,28)}</a></li>
		</c:forEach></ul>
      </div>
      <div class="span4">
        <h4> <small><a href="${ctx}/list-8${urlSuffix}" class="pull-right">更多&gt;&gt;</a></small>中心要闻</h4>
		<ul><c:forEach items="${fnc:getArticleList(site.id, 8, 8, '')}" var="article">
			<li><span class="pull-right"><fmt:formatDate value="${article.updateDate}" pattern="yyyy.MM.dd"/></span><a href="${article.url}" style="color:${article.color}">${fns:abbr(article.title,28)}</a></li>
		</c:forEach></ul>
      </div>
      <div class="span4">
        <h4><small><a href="${ctx}/list-ec24b4b2f3b84f6fbf663c88f2c3fc72${urlSuffix}" class="pull-right">更多&gt;&gt;</a></small>公示公告</h4>
		<ul><c:forEach items="${fnc:getArticleList(site.id, 'ec24b4b2f3b84f6fbf663c88f2c3fc72', 8, '')}" var="article">
			<li><span class="pull-right"><fmt:formatDate value="${article.updateDate}" pattern="yyyy.MM.dd"/></span><a href="${article.url}" style="color:${article.color}">${fns:abbr(article.title,28)}</a></li>
		</c:forEach></ul>
      </div>
    </div>
    <div class="row">
      <div class="span4">
        <h4><small><a href="${ctx}/list-11${urlSuffix}" class="pull-right">更多&gt;&gt;</a></small>会议纪要</h4>
		<ul><c:forEach items="${fnc:getArticleList(site.id, 11, 8, '')}" var="article">
			<li><span class="pull-right"><fmt:formatDate value="${article.updateDate}" pattern="yyyy.MM.dd"/></span><a href="${article.url}" style="color:${article.color}">${fns:abbr(article.title,28)}</a></li>
		</c:forEach></ul>
      </div>
      <div class="span4">
        <h4> <small><a href="${ctx}/list-12${urlSuffix}" class="pull-right">更多&gt;&gt;</a></small>会议资料</h4>
		<ul><c:forEach items="${fnc:getArticleList(site.id, 12, 8, '')}" var="article">
			<li><span class="pull-right"><fmt:formatDate value="${article.updateDate}" pattern="yyyy.MM.dd"/></span><a href="${article.url}" style="color:${article.color}">${fns:abbr(article.title,28)}</a></li>
		</c:forEach></ul>
      </div>
      <div class="span4">
        <h4><small><a href="${ctx}/list-14${urlSuffix}" class="pull-right">更多&gt;&gt;</a></small>宣传学习</h4>
		<ul><c:forEach items="${fnc:getArticleList(site.id, '14', 8, '')}" var="article">
			<li><span class="pull-right"><fmt:formatDate value="${article.updateDate}" pattern="yyyy.MM.dd"/></span><a href="${article.url}" style="color:${article.color}">${fns:abbr(article.title,28)}</a></li>
		</c:forEach></ul>
      </div>
    </div>
</body>
</html>