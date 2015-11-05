<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.lang.*"%>
<%
	String basePath=getServletConfig().getServletContext().getContextPath();
%>
<html lang="en"><head>
		<meta charset="utf-8">
		<title>Blank Page - Ace Admin</title>

		<meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">

		<script type="text/javascript">
		      window.onload= function(){
		    	  alert("登录失效，请重新登录");
		    	  parent.window.location.href="<%=basePath%>";
		      } 
		</script>
		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="assets/js/html5shiv.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body>
	</body>
</html>