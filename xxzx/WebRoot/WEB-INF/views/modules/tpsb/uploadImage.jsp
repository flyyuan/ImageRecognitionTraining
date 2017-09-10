<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>上传图片设计</title>
<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
	<link href="${ctxStatic}/bootstrap/2.3.1/css_default/bootstrap.min.css"
		rel="stylesheet">
	<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js"></script>
		
	<script src="${ctxStatic}/jquery/jquery-1.8.3.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctxStatic}/uploadify3.2.1/uploadify.css"></link>
	<script type="text/javascript" src="${ctxStatic}/uploadify3.2.1/jquery.uploadify.min.js"></script>

</head>

<script type="text/javascript">
 $(function() {  
	  $("#file_upload").uploadify({ 
	   'auto':false,       //是否允许自动上传
	   'swf' : '${ctxStatic}/uploadify3.2.1/uploadify.swf', //引入flash
	   'buttonText':'浏览文件',     //设置button文字
	   'width ':'120',      //按钮宽度
	   'method':'PSOT',                     //提交方式
	   'multi':'true',      //是否多文件上传
	   'fileObjName' : 'myFile',   //文件对象名称,用于后台获取文件对象时使用
	   'preventCaching':'true',   //防止浏览器缓存
	   'formData':{'emergencyId':1111}, //动态传参
	   'queueID': 'custom-queue',
	   'uploader' : '${ctx}/tpsb/uploadPicture' ,    //提交后台方法路径
	   
	   //onUploadStart 动态传参的关键
	   'onUploadStart':function(){
	    $("#file_upload").uploadify("settings","formData",
	 {'emergencyId': $("#id").val()});
	   },
	   'onFallback' : function() {//检测FLASH失败调用  
	     alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");  
	    
	    },  
	    
	    'onUploadSuccess':function(file, data, response){  
	      var Data = eval('('+data+')');
	             // alert(file.name+"上传"+Data.result);
	          },
	          
	        onError: function(event, queueID, file)   
	             {    
	              alert(file.name + " 上传失败");    
	             },
	          'onQueueComplete':function(stats) {  
	//版本不一样方法也存在差异
	           alert("上传的文件数:"  + stats.uploadsSuccessful + " ;上传出错的文件数:"  +stats.uploadsErrored + " ;" );
	           cancel();
	         },
	  }); 
	  
	  });
 
/*  $(document).ready(function() {
		$("#file_upload").uploadify({
			'swf' : '${ctxStatic}/uploadify3.2.1/uploadify.swf',//控件flash文件位置
	//后台处理的请求（也就是action请求路径），后面追加了jsessionid，用来标示使用当前session（默认是打开新的session，会导致存在session校验的请求中产生302错误）
	'uploader' : '${ctx}/tpsb/uploadPicture',
			'queueID' : 'queue',//与下面HTML的div.id对应  
			'width' : '100',//按钮宽度
			'height' : '32',//按钮高度
			'fileTypeDesc' : '指定类型文件',
			'fileTypeExts' : '*.jpg;*.png', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc 
			'fileObjName' : '"uploadify"',
			'buttonText' : '批量上传',//上传按钮显示内容，还有个属性可以设置按钮的背景图片
			'fileSizeLimit' : '100KB',
			'multi' : true,
			'overrideEvents' : [ 'onDialogClose', 'onUploadSuccess', 'onUploadError', 'onSelectError' ],//重写默认方法
			'onFallback' : function() {//检测FLASH失败调用
				alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
			},
	//以下方法是对应overrideEvents的重载方法，具体实现是网上找到的一个别的朋友的代码，
	//我把这些方法抽到了一个自定义js中，我会在最后面贴出来
	'onSelect' : uploadify_onSelect,
			'onSelectError' : uploadify_onSelectError,
			'onUploadError' : uploadify_onUploadError,
			'onUploadSuccess' : uploadify_onUploadSuccess
		});
	}); */
</script>


<body>

	<ul class="nav nav-tabs">
		<li class="active"><a href="">上传图片</a></li>
		</ul>
		
<%-- 		<form id="uploadPhoto1" action="${ctx}/tpsb/uploadPicture" method="post" enctype="multipart/form-data"> 
<!--         上传图片1<input type="file" id="uploadPhoto1" name="uploadPhoto1"> <br>
        上传图片2<input type="file" id="uploadPhoto2" name="uploadPhoto2"> <br>
        上传图片3<input type="file" id="uploadPhoto3" name="uploadPhoto3"> <br>
        上传图片4<input type="file" id="uploadPhoto4" name="uploadPhoto4"> <br> -->
        <input type="file" multiple name="upload"><br>
        <input type="submit" value="上传" > 
        </form>
         <br> <br> <br> --%>

         
          <input id="file_upload" type="file" name="file"/>
          
           <a href="javascript:$('#file_upload').uploadify('upload', '*')">上传文件</a> | 
           <a href="javascript:$('#file_upload').uploadify('stop')">停止上传!</a>  


</body>
</html>