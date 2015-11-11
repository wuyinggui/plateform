<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.lang.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath=getServletConfig().getServletContext().getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>欢迎使用</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<link href="<%=basePath%>/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=basePath%>/assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=basePath%>/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts -->

		<link rel="stylesheet" href="<%=basePath%>/assets/css/ace-fonts.css" />

		<!-- ace styles -->

		<link rel="stylesheet" href="<%=basePath%>/assets/css/ace.min.css" />
		<link rel="stylesheet" href="<%=basePath%>/assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="<%=basePath%>/assets/css/ace-skins.min.css" />
		<link rel="icon" href="<%=basePath%>/common/image/sz_Logo_Icon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="<%=basePath%>/common/image/sz_Logo_Icon.ico" type="image/x-icon" />
		<style  type="text/css">
		body{width:100%;height:100%;}
		.page-div{position:absolute;width:98%;height:98%;margin:5px 0px 5px 5px;float:left;margin-top:2px;}
		</style>
		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=basePath%>/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="<%=basePath%>/assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="<%=basePath%>/assets/js/html5shiv.js"></script>
		<script src="<%=basePath%>/assets/js/respond.min.js"></script>
		<![endif]-->
		<script type="text/javascript" src="<%=basePath%>/common/js/jquery-1.11.2.js"></script>
		<script type="text/javascript" src="<%=basePath%>/common/js/jquery.cookie-1.4.1.js"></script>
		<script type="text/javascript" src="<%=basePath %>/common/js/cookie.js"></script>
		<script type="text/javascript">
			//全局的AJAX访问，处理AJAX清求时SESSION超时
			$.ajaxSetup({
				contentType:"application/x-www-form-urlencoded;charset=utf-8",
				complete:function(XMLHttpRequest,textStatus){
			          //通过XMLHttpRequest取得响应头，sessionstatus           
			         var resText = XMLHttpRequest.responseText;   
			          if(resText=="timeout"){
			               //这里怎么处理在你，这里跳转的登录页面
			               alert("登录超时,请重试!");
			               top.location.href =  '<%=basePath%>/index.jsp';
				   }
				}
			});
			

			
		</script>
	</head>

	<body>
		登录成功！
	</body>
</html>
