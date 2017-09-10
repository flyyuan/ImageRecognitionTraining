<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!DOCTYPE >
<html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <script src="${ctxStatic}/jquery/jquery-1.8.3.js"></script>
    <title>课堂签到</title>
    <!-- Bootstrap core CSS -->
    <link href="${ctxStatic}/bootstrap/4.0.0/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="/xxzx/css/mobiles/kq/student/login.css" rel="stylesheet">
</head>
<body>
<div class="login-page">
    <div class="form">
          <form class="register-form" >
            <p>首次进行信息绑定</p>
            <input type="text" placeholder="手机号"/>
            <input type="text" placeholder="MAC地址"/>
            <input type="text" placeholder="序列号"/>
            <button>注册</button>
            <p>如需更换设备</p>
            <p>请到网络中心进行解除</p>
            <p class="message">已经绑定 <a href="#">登录签到</a></p>
        </form>
        <form id="loginForm" class="login-form" action="${ctx}/login" method="post" novalidate="novalidate">
            <h1 class="form-signin-heading">课堂签到</h1>
            <input type="text" placeholder="学号" id="username" name="username"/>
            <input type="password" placeholder="密码" id="password" name="password"/>
            <button type="submit" id="submit" >登录签到</button>
            <p class="message">没有登记? <a href="#">点击登记个人信息</a></p>
        </form>
    </div>
</div>
<script src="/xxzx/js/mobiles/kq/student/login.js"></script>
</body>
<script type="text/javascript">
var sessionid = '${not empty fns:getPrincipal() ? fns:getPrincipal().sessionid : ""}';
$('body').delegate('#login_section','pageinit',function(){
	$("#loginForm").submit(function(){
		if ($('#username').val() == ''){
			J.showToast('请填写账号', 'info');
		}else if ($('#password').val() == ''){
			J.showToast('请填写密码', 'info');
		}else if ($('#validateCodeDiv').is(':visible') && $('#validateCode').val() == ''){
			J.showToast('请填写验证码', 'info');
		}else{
			var loginForm = $("#loginForm");
			$.post(loginForm.attr('action'), loginForm.serializeArray(), function(data){
				if (data && data.sessionid){
					sessionid = data.sessionid;
					J.showToast('登录成功！', 'success');
					J.Router.goTo('#index_section?index');
				}else{
					J.showToast(data.message, 'error');
					if (data.shiroLoginFailure == 'org.apache.shiro.authc.AuthenticationException'){
						$('#validateCodeDiv').show();
					}
					$('#validateCodeDiv a').click();
				}
				//console.log(data);
			});
		}
		return false;
	});
});
$('body').delegate('#login_section','pageshow',function(){
	if (sessionid != ''){
        var targetHash = location.hash;
        if (targetHash == '#login_section'){
    		//J.showToast('你已经登录！', 'success');
    		J.Router.goTo('#index_section?index');
        }
	}else{
		$('#login_article').addClass('active');
	}
});
</script>
</body>
</html>