<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%><!DOCTYPE >
<html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>查看考勤情况</title>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrap/4.0.0/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="/xxzx/css/mobiles/kq/student/htmleaf-demo.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-plugin/jquery.eeyellow.Timeline.css" />
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
</head>
<body>
<input type="hidden" id="macAddress" value="${requestScope.macAddress}"/>
<input type="hidden" id="phoneNum" value="${requestScope.phoneNum}"/>
<input type="hidden" id="seriesNum" value="${requestScope.seriesNum}"/>
<script src="${ctxStatic}/jquery/jquery-3.1.1.min.js" type="text/javascript"></script>
<script>
var kqinf;
var macAddress = $("#macAddress")[0].value;
var phoneNum = $("#phoneNum")[0].value;
var seriesNum= $("#seriesNum")[0].value;
var kqinf;
$('#test-response-text').val('');
var kqinfjson = $.ajax('/xxzx/a/kq/student/getStuVal?phoneNum='+phoneNum+'&mac='+macAddress+'&sNum='+seriesNum, {
    dataType: 'json'
}).done(function (data) {
    console.log('成功, 收到的数据: ' + JSON.stringify(data,''));
    kqinf = data;
    // theFirstLesson = kqinf["0"].absentList[0];
    // theSecondLesson = kqinf["0"].absentList[1];
    // theThireLesson = kqinf["0"].absentList[2];
    // theFourthLesson = kqinf["0"].absentList[3];
    console.log(kqinf);
    console.log(data);
    var dl = $('#timeline>dl');
    switch(kqinf["0"].absentList.length){
    	case 1:
    	theFirstLesson = kqinf["0"].absentList[0];
    	dl.prepend('<dd class="pos-left clearfix"><div class="circ"></div><div class="time"></div><div class="events"><div class="events-header">'+ theFirstLesson.nowdate+'</div><div class="events-body"><div class="row"><div class="col-md-6 pull-left"></div><div class="events-desc">'+ theFirstLesson.cdate + '<br>'+ theFirstLesson.curriculumName+ '<br>'+theFirstLesson.kqstautschan+ '</div></div><div class="row"><div class="col-md-6 pull-left"></div><div class="events-desc">Cras condimentum, metus ut vehicul</div></div></div><div class="events-footer"></div></div></dd>');
    	break;
    	case 2:
    	theFirstLesson = kqinf["0"].absentList[0];
    	theSecondLesson = kqinf["0"].absentList[1];
    	dl.prepend('<dd class="pos-left clearfix"><div class="circ"></div><div class="time"></div><div class="events"><div class="events-header">'+ theFirstLesson.nowdate+'</div><div class="events-body"><div class="row"><div class="col-md-6 pull-left"></div><div class="events-desc">'+ theFirstLesson.cdate + '<br>'+ theFirstLesson.curriculumName+ '<br>'+theFirstLesson.kqstautschan+ '<br>&nbsp&nbsp&nbsp&nbsp'+ theSecondLesson.cdate + '<br>'+theSecondLesson.curriculumName+'<br>' + theSecondLesson.kqstautschan + '</div></div><div class="row"><div class="col-md-6 pull-left"></div><div class="events-desc">Cras condimentum, metus ut vehicul</div></div></div><div class="events-footer"></div></div></dd>');
    	break;
    	case 3:
    	theFirstLesson = kqinf["0"].absentList[0];
    	theSecondLesson = kqinf["0"].absentList[1];
    	theThireLesson = kqinf["0"].absentList[2];
    	dl.prepend('<dd class="pos-left clearfix"><div class="circ"></div><div class="time"></div><div class="events"><div class="events-header">'+ theFirstLesson.nowdate+'</div><div class="events-body"><div class="row"><div class="col-md-6 pull-left"></div><div class="events-desc">'+ theFirstLesson.cdate + '<br>'+ theFirstLesson.curriculumName+ '<br>'+theFirstLesson.kqstautschan+ '<br>&nbsp&nbsp&nbsp&nbsp'+ theSecondLesson.cdate + '<br>'+theSecondLesson.curriculumName+'<br>' + theSecondLesson.kqstautschan + '</div></div><div class="row"> <div class="col-md-6 pull-left"></div> <div class="events-desc">'+ theThireLesson.cdate + '<br>'+theThireLesson.curriculumName+ '<br>'+theThireLesson.kqstautschan+'</div></div><div class="row"><div class="col-md-6 pull-left"></div><div class="events-desc">Cras condimentum, metus ut vehicul</div></div></div><div class="events-footer"></div></div></dd>');
    	case 4:
    	theFirstLesson = kqinf["0"].absentList[0];
    	theSecondLesson = kqinf["0"].absentList[1];
    	theThireLesson = kqinf["0"].absentList[2];
    	theFourthLesson = kqinf["0"].absentList[3];
    	dl.prepend('<dd class="pos-left clearfix"><div class="circ"></div><div class="time"></div><div class="events"><div class="events-header">'+ theFirstLesson.nowdate+'</div><div class="events-body"><div class="row"><div class="col-md-6 pull-left"></div><div class="events-desc">'+ theFirstLesson.cdate + '<br>'+ theFirstLesson.curriculumName+ '<br>'+theFirstLesson.kqstautschan+ '<br>&nbsp&nbsp&nbsp&nbsp'+ theSecondLesson.cdate + '<br>'+theSecondLesson.curriculumName+'<br>' + theSecondLesson.kqstautschan + '</div></div><div class="row"> <div class="col-md-6 pull-left"></div> <div class="events-desc">'+ theThireLesson.cdate + '<br>'+theThireLesson.curriculumName+ '<br>'+theThireLesson.kqstautschan + '<br>&nbsp&nbsp&nbsp&nbsp'+theFourthLesson.cdate +'<br>'+theFourthLesson.curriculumName+'<br>'+theFourthLesson.kqstautschan +'</div></div><div class="row"><div class="col-md-6 pull-left"></div><div class="events-desc">Cras condimentum, metus ut vehicul</div></div></div><div class="events-footer"></div></div></dd>');
    	break;
    	case 5:
    	theFirstLesson = kqinf["0"].absentList[0];
    	theSecondLesson = kqinf["0"].absentList[1];
    	theThireLesson = kqinf["0"].absentList[2];
    	theFourthLesson = kqinf["0"].absentList[3];
    	theFifthLesson = kqinf["0"].absentList[4];
    	dl.prepend('<dd class="pos-left clearfix"><div class="circ"></div><div class="time"></div><div class="events"><div class="events-header">'+ theFirstLesson.nowdate+'</div><div class="events-body"><div class="row"><div class="col-md-6 pull-left"></div><div class="events-desc">'+ theFirstLesson.cdate + '<br>'+ theFirstLesson.curriculumName+ '<br>'+theFirstLesson.kqstautschan+ '<br>&nbsp&nbsp&nbsp&nbsp'+ theSecondLesson.cdate + '<br>'+theSecondLesson.curriculumName+'<br>' + theSecondLesson.kqstautschan + '</div></div><div class="row"> <div class="col-md-6 pull-left"></div> <div class="events-desc">'+ theThireLesson.cdate + '<br>'+theThireLesson.curriculumName+ '<br>'+theThireLesson.kqstautschan + '<br>&nbsp&nbsp&nbsp&nbsp'+theFourthLesson.cdate +'<br>'+theFourthLesson.curriculumName+'<br>'+theFourthLesson.kqstautschan + '<br>&nbsp&nbsp&nbsp&nbsp'+theFifthLesson.cdate +'<br>'+theFifthLesson.curriculumName+'<br>'+theFifthLesson.kqstautschan +'</div></div><div class="row"><div class="col-md-6 pull-left"></div><div class="events-desc">Cras condimentum, metus ut vehicul</div></div></div><div class="events-footer"></div></div></dd>');
    	break;
    	default :
    	dl.prepend('<dd class="pos-left clearfix"><div class="circ"></div><div class="time"></div><div class="events"><div class="events-header">暂时没数据</div></div><div class="row"><div class="col-md-6 pull-left"></div><div class="events-desc">Cras condimentum, metus ut vehicul</div></div></div><div class="events-footer"></div></div></dd>');
    }
    // dl.prepend('<dd class="pos-left clearfix"><div class="circ"></div><div class="time"></div><div class="events"><div class="events-header">'+ theFirstLesson.nowdate+'</div><div class="events-body"><div class="row"><div class="col-md-6 pull-left"></div><div class="events-desc">'+ theFirstLesson.cdate + '<br>'+ theFirstLesson.curriculumName+ '<br>'+theFirstLesson.kqstautschan+ '<br>&nbsp&nbsp&nbsp&nbsp'+ theSecondLesson.cdate + '<br>'+theSecondLesson.curriculumName+'<br>' + theSecondLesson.kqstautschan + '</div></div><div class="row"> <div class="col-md-6 pull-left"></div> <div class="events-desc">'+ theThireLesson.cdate + '<br>'+theThireLesson.curriculumName+ '<br>'+theThireLesson.kqstautschan + '<br>&nbsp&nbsp&nbsp&nbsp'+theFourthLesson.cdate +'<br>'+theFourthLesson.curriculumName+'<br>'+theFourthLesson.kqstautschan +'</div></div><div class="row"><div class="col-md-6 pull-left"></div><div class="events-desc">Cras condimentum, metus ut vehicul</div></div></div><div class="events-footer"></div></div></dd>');


}).fail(function (xhr, status) {
    console.log('失败: ' + xhr.status + ', 原因: ' + status);
}).always(function () {
    console.log('请求完成: 无论成功或失败都会调用');
});
</script>
<div class="htmleaf-container">
    <header class="htmleaf-header">
        <h1>考勤时间轴</h1>
        <div class="htmleaf-links">
            <a class="htmleaf-icon icon-htmleaf-home-outline" href="/WEB-INF/views/modules/sys/sysTechHistory.jsp" title="主页" target="_blank"><span> 主页</span></a>
            <a class="htmleaf-icon icon-htmleaf-arrow-forward-outline" href="/WEB-INF/views/modules/sys/sysTechstatement.jsp" title="返回" target="_blank"><span> 返回</span></a>
        </div>
    </header>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="VivaTimeline" id="timeline">
                    <dl>
                        <dd class="pos-left clearfix">
                            <div class="circ"></div>
                            <div class="time">无数据</div>
                            <div class="events">
                                <div class="events-header">无数据</div>
                                <div class="events-body">
                                    <div class="row">
                                        <div class="col-md-6 pull-left">
                                        </div>
                                        <div class="events-desc">
                                            无数据
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 pull-left">
                                        </div>
                                        <div class="events-desc">
                                            无数据
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 pull-left">
                                        </div>
                                        <div class="events-desc">
                                            无数据
                                        </div>
                                    </div>
                                </div>
                                <div class="events-footer">
                                </div>
                            </div>
                        </dd>
                        <dd class="pos-right clearfix">
                            <div class="circ"></div>
                            <div class="time">无数据</div>
                            <div class="events">
                                <div class="events-header">无数据</div>
                                <div class="events-body">
                                    <div class="row">
                                        <div class="col-md-6 pull-left">
                                        </div>
                                        <div class="events-desc">
                                            无数据
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 pull-left">
                                        </div>
                                        <div class="events-desc">
                                            无数据
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 pull-left">
                                        </div>
                                        <div class="events-desc">
                                            无数据
                                        </div>
                                    </div>
                                </div>
                                <div class="events-footer">

                                </div>
                            </div>
                        </dd>
                        <dt>Jan 2017</dt>
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