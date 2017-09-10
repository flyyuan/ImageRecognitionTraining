<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!DOCTYPE >
<html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>查看考勤统计</title>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrap/4.0.0/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="/xxzx/css/mobiles/kq/student/htmleaf-demo.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-plugin/jquery.eeyellow.Timeline.css" />
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
</head>
<body>
<script src="${ctxStatic}/jquery/jquery-3.1.1.min.js" type="text/javascript"></script>
<script>
//获取课程名JSON
var courseNameJSON;
var coursename = $.ajax('http://10.0.26.2:8080/xxzx/a/kq/teacher/getTongjiCourseName',{
	dataType : 'json'
}).done(function(data){
    console.log('成功,收到课程名数据' + JSON.stringify(data,''));
    courseNameJSON = data;
    console.log(courseNameJSON);
    console.log(data);
    var courseSelect = $('#timeline-header').find('select.courseselect');
    var select = '';
    var i=0;
    for(i;i<courseNameJSON['0'].KeChengNames.length;i++){
    	keChengName = courseNameJSON['0'].KeChengNames[i];
    	courseOption = '<option value="' + keChengName + '">' + keChengName + '</option>';
    	courseSelect.append(courseOption);
    }
    console.log('课程名不为空');

}).fail(function (xhr, status) {
    console.log('失败: ' + xhr.status + ', 原因: ' + status);
}).always(function () {
    console.log('统计数据请求完成: 无论成功或失败都会调用');
});

//获取班级JSON
var classJSON;
var classname = $.ajax('http://10.0.26.2:8080/xxzx/a/kq/teacher/getTongjiShangKeClass?className='+'近代史',{
    dataType : 'json'
}).done(function(data){
    console.log('成功,收到课程名数据' + JSON.stringify(data,''));
    classNameJSON = data;
    console.log(classNameJSON);
    console.log(data);
    var classSelect = $('#timeline-header').find('select.classselect');
    var select = '';
    var i=0;
    for(i;i<classNameJSON["0"].shangKeClass.length;i++){
        className = classNameJSON["0"].shangKeClass[i];
        classOption = '<option value="' + className + '">' + className + '</option>';
        classSelect.append(classOption);
    }
    console.log('班别不为空');

}).fail(function (xhr, status) {
    console.log('失败: ' + xhr.status + ', 原因: ' + status);
}).always(function () {
    console.log('统计数据请求完成: 无论成功或失败都会调用');
});

    var courselistValue = '点击选择框后再查看';
    var classlistValue = null;
    //获取选中的课程名,班级
 window.onload=function(){
	var courselist = document.getElementById('selectList');
	courselist.onchange = function(){
        courselistValue = courselist.value;
		console.log(courselistValue);
	}
    var classlist = document.getElementById('classselectList');
    classlist.onchange = function(){
        classlistValue = classlist.value;
        console.log(classlistValue);
    }
}
   //不选择课程和班级则自动选择第一课程和班级
//     if(courselistValue==null||classlistValue==null){
//         courselistValue=courselist.options[0].value;
//         classlistValue=classlist.options[0].value;
// }
//点击获取课程统计JSON
var btn = document.getElementById('btn');
function GetStatement() {
console.log(courselistValue);
var statementJSON;
$('#test-response-text').val('');
var dianming = $.ajax('http://10.0.26.2:8080/xxzx/a/kq/teacher/getStudents?&keChengName=' +courselistValue+ '&className='+classlistValue, {
    dataType: 'json'
}).done(function (data) {
    console.log('成功, 收到课程统计数据: ' + JSON.stringify(data,''));
    statementJSON = data;
    console.log(statementJSON);
    console.log(data);
    var dl = $('#timeline>dl');
    var i= 0;
    var list = '';
    var v;
    $.each(statementJSON['0'].Second,function(i){
        list += '&nbsp'+'姓名：'+statementJSON['0'].Second[i].name + '<br>'+'&nbsp'+ '学号：' + statementJSON['0'].Second[i].stuid;
        list += '<br>';
        
    })
     dl.prepend('<dd class="pos-left clearfix"><div class="circ"></div><div class="time"></div><div class="events"><div class="events-header">'+statementJSON["0"].keChengName+'--'+statementJSON['0'].className+'</div><div class="events-body"><div><div class="col-md-6 pull-left"></div><div>'+'<tr>'+list+'<tr>'+'</div></div><div><div class="col-md-6 pull-left"></div><div class="events-footer"></div></div></dd>');
     console.log("考勤统计数据不为空");
}).fail(function (xhr, status) {
    console.log('失败: ' + xhr.status + ', 原因: ' + status);
}).always(function () {
    console.log('统计数据请求完成: 无论成功或失败都会调用');
});}
</script>
<div class="htmleaf-container" id=timeline-header>
    <header class="htmleaf-header">
        <h1>考勤统计</h1>
        <div>
            <a class="htmleaf-icon icon-htmleaf-home-outline" id="tongji" href="http://10.0.26.2:8080/xxzx/a?login" title="主页" target="_self"><span> 考勤点名</span></a>
            <a class="htmleaf-icon icon-htmleaf-arrow-forward-outline" id="lisi" href="http://10.0.26.2:8080/xxzx/a/kq/teacher/sysTechHistory" title="返回" target="_self"><span>考勤历史</span></a>
             <br>
           <span >选择课程：
           <select class="courseselect" id="selectList">
           </select>
           <br>
           选择班级：
           <select class="classselect" id="classselectList">
           </select>
           <br>
           <button id= "btn" onclick="GetStatement()">查看考勤统计</button>
           </span>
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
                                <div class="events-header">请选择课程和班级获取内容</div>
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