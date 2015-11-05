<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"
    import="java.lang.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
	String basePath=getServletConfig().getServletContext().getContextPath();
%>
<meta charset="utf-8" />
<title>神州专车车辆调度平台</title>
		<meta name="description" content="User login page" />
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

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=basePath%>/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="<%=basePath%>/assets/js/html5shiv.js"></script>
		<script src="<%=basePath%>/assets/js/respond.min.js"></script>
		<![endif]-->
		<link rel="icon" href="<%=basePath%>/common/image/sz_Logo_Icon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="<%=basePath%>/common/image/sz_Logo_Icon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/common/resources/login.css"/>
		<script type="text/javascript" src="<%=basePath%>/common/js/jquery-1.11.2.js"></script>
		<script type="text/javascript" src="<%=basePath%>/common/js/jquery.cookie-1.4.1.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#login").click(function(){
					$("#actionLoginForm")[0].submit();
				});
				document.onkeydown = function(e){ 
				    var ev = document.all ? window.event : e;
				    if(ev.keyCode==13) {
				    	 $('#login').click();
				     }
				}
			});
		</script>
	</head>

<body class="x-body">
	<div id="header">
		<img id="logo" src="<%=basePath%>/common/image/login_logo.png" alt="神州专车"/>
	</div>
	<div id="wrap">
		<div id="main">
			<div id="leftLogo">
				<img id="loginLogo" src="<%=basePath%>/common/image/login_pic.jpg" />
			</div>

			 <div id="loginForm" style="margin-left:40px;">
				   <table>
					<thead>
						<tr>
							<td>
								<div id="welcomeLabel" class="center">神州专车车辆调度平台</div>
							</td>
						</tr>

					</thead> 
					<form id="actionLoginForm" action="<%=basePath%>/LBS/loginAU" method="post">
						<tbody>
							<tr>
								<td style="padding-top:10px;">账号：<input class="usernameInput inputWithIcon" name="entity.username"  type="text" />
									<span style="color:red">
										<s:fielderror fieldName="username"></s:fielderror><s:fielderror fieldName="errorMsg"></s:fielderror>
									</span>
								</td>
							</tr>
							<tr>
								<td style="padding-top:10px;">密码：<input class="passwordInput inputWithIcon" name="entity.password" type="password" />
									<span style="color:red">
										<s:fielderror fieldName="password"></s:fielderror>
									</span>
									
									</td>
							</tr>
							<tr>
								<td id="loginButton" style="">
									<button id="login" type="button" class="width-35  btn btn-sm btn-warning">
																<i class="icon-key"></i>
																登录
									</button>
									<button id="reset" type="reset" class="width-35 btn btn-sm btn-yellow">
																<i class="icon-refresh"></i>
																重置
									</button>
								</td>
							</tr> 
						</tbody>
					</form>
					
				</table>  
			</div> 
		</div>
	</div>
</body>
</html>