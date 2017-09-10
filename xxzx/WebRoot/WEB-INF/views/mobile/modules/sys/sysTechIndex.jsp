<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!DOCTYPE >
<html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>考勤点名</title>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrap/4.0.0/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="/xxzx/css/mobiles/kq/student/htmleaf-demo.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-plugin/jquery.eeyellow.Timeline.css" />
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
</head>
<body>
<script src="${ctxStatic}/jquery/jquery-3.1.1.min.js" type="text/javascript"></script>
<script>
var dianmingJSON;
$('#test-response-text').val('');
var dianming = $.ajax('/xxzx/a/kq/', {
    dataType: 'json'
}).done(function (data) {
    console.log('成功, 收到的数据: ' + JSON.stringify(data,''));
    dianmingJSON = data;
    console.log(dianmingJSON);
    console.log(data);
    var dl = $('#timeline>dl');
    var list="";
    
     $.each(dianmingJSON['0'].Third,function(i){
        list += '&nbsp'+'姓名：'+dianmingJSON['0'].Third[i].name + '<br>'+'&nbsp'+ '学号：' + dianmingJSON['0'].Third[i].stuId
        + '<br>'+'&nbsp'+ '考勤情况：'+dianmingJSON['0'].Third[i].kqstautschan+'<br>'+'&nbsp班别:'+dianmingJSON["0"].Third[i].className;
        list += '<br>';
    });
     dl.prepend('<dd class="pos-left clearfix"><div class="circ"></div><div class="time"></div><div class="events"><div class="events-header">'+dianmingJSON["0"].KeCheng+'--课程时间第'+dianmingJSON['0'].ShiJiN+'节'+'</div><div class="events-body"><div><div class="col-md-6 pull-left"></div><div><tr>'+list+'<tr></div></div><div><div class="col-md-6 pull-left"></div><div class="events-footer"></div></div></dd>');
     console.log("不为空");
}).fail(function (xhr, status) {
    console.log('失败: ' + xhr.status + ', 原因: ' + status);
}).always(function () {
    console.log('请求完成: 无论成功或失败都会调用');
});
</script>
<div class="htmleaf-container">
    <header class="htmleaf-header">
        <h1>考勤点名</h1>
        <div class="htmleaf-links">
             <a class="htmleaf-icon icon-htmleaf-home-outline" id="tongji" href="http://10.0.26.2:8080/xxzx/a/kq/teacher/sysTechstatement" title="考勤统计" target="_self"><span> 考勤统计</span></a>
            <a class="htmleaf-icon icon-htmleaf-arrow-forward-outline" id="lisi" href="http://10.0.26.2:8080/xxzx/a/kq/teacher/sysTechHistory" title="考勤历史" target="_self"><span>考勤历史</span></a>
        </div>
    </header>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="VivaTimeline" id="timeline">
                    <dl>
                        <dd class="pos-left clearfix">
                            <div class="circ"></div>
                            <div class="time"></div>
                            <div class="events">
                                <div class="events-header">Event Heading</div>
                                <div class="events-body">
                                    <div class="row">
                                        <div class="col-md-6 pull-left">
                                        </div>
                                        <div class="events-desc">
                                            目前没有数据
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 pull-left">
                                        </div>
                                        <div class="events-desc">
                                            目前没有数据
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 pull-left">
                                        </div>
                                        <div class="events-desc">
                                            目前没有数据
                                        </div>
                                    </div>
                                </div>
                                <div class="events-footer">
                                </div>
                            </div>
                        </dd>
                    </dl>
                </div>
            </div>
        </div>
    </div>
</div>
<script>window.jQuery || document.write('<script src="js/jquery-1.11.0.min.js"><\/script>')</script>
<script src="/xxzx/js/mobiles/kq/student/tether.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/bootstrap/4.0.0/bootstrap.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-plugin/jquery.eeyellow.Timeline.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $('.VivaTimeline').vivaTimeline({
            carousel: true,
            carouselTime: 3000
        });
    });
</script>
</body>
</html>