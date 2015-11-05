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
		<title>欢迎使用神州专车LBS平台</title>
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
			

			var clickTimer = null;
			$(document).ready(function(){
				var uname = "${sessionScope.PLATFORM_USER_INFO.username }";
				if(uname == ''){
					uname = getCookie("JSESSIONUID");
					var uauth = getCookie("JSESSIONAUTH");
					if(uname != null){
						$(".user-info").find("small").html(uname+",欢迎");
						var rlogin = cookieLogin(uname, uauth);
						if(rlogin == ''){
							alert('登录超时,请重试!'); 
							top.location.href = '<%=basePath%>/index.jsp';
							return false;
						}else{
							uname = rlogin;
						}
					}
				}
				
				$("#logout").click(function(){
					
					top.location.href = '<%=basePath%>/LBS/logoutAU';
				});
				$.ajax({
					type : 'POST',
					url : '<%=basePath%>/menu/getMenuMU',
					async : false,
					data : {
						'username' : uname
					},
					dataType : 'json',
					success : function(data){
						var json = eval("("+data+")");
						var count = 0 ;
						var inner = '';
						var arrays = json.menus;
						for(var item in arrays){
							inner +="<li "
							if(count == 0){
								inner += "class='active'>"
							}else{
								inner += ">";
							}
							var _data = arrays[item];
							var is_leaf = _data.is_leaf;
							if(is_leaf *1.0 == 1){
								var menu_url = _data.menu_url;
								if(menu_url.indexOf("http") != -1){
									inner += "<a href='javascript:void(0)' onclick='menuclick(this)' ondblclick='menudbclick(this)'  name='"+_data.menu_url+"?cityIds="+json.cityIds+"'>";
								}else{
									inner += "<a href='javascript:void(0)' onclick='menuclick(this)' ondblclick='menudbclick(this)' name='<%=basePath%>/"+ _data.menu_url +"'>";
								}
								
							}else{
								inner += "<a href='javascript:void(0)' onclick='menuclick(this)' ondblclick='menudbclick(this)' id='"+_data.menu_name_zh_cn+"' class='sub_menulist' name='"+_data.menuid+"'>";
							}
							inner += "<i class='icon-globe'></i>";
							inner += "<span class='menu-text'>"+ _data.menu_name_zh_cn +"</span>";
							inner +="</li>";	
							count ++;
						}
						$(".user-info").find("small").html(json.uname+",欢迎");
						$("#menulist").html(inner);
					},
					error : function(){
					}
					
				});
			});
			function menuclick(obj){
				if(clickTimer) {  
					  window.clearTimeout(clickTimer);  
					  clickTimer = null;  
				}  
			    clickTimer = window.setTimeout(function(){
			    	$("#menulist li").removeClass("active");
			    	$("#menulist li").removeClass("open");
					$("#menulist li ul").each(
						function(){
							if($(this).parent().find(".sub_menulist").attr("name") != $(obj).attr("name")){
								$(this).parent().removeClass("active");
								$(this).hide();
							}
						}		
					);
					if($(obj).hasClass("sub_menulist")){
						$(obj).parent().addClass("open");
						//有下级菜单
						if($(obj).parent().find(".submenu").length == 0){
							$.ajax({
								type : 'POST',
								async : false,
								url : '<%=basePath%>/menu/getSubMenuMU',
								data : {
									'usermenu.menu_ids' : $(obj).attr("name")
								},
								success : function(data){
									var json = eval("("+data+")");
									if(json.length > 0){
										var inner = "<a href='javascript:void(0)' onclick='menuclick(this)' ondblclick='menudbclick(this)' id='"+$(obj).attr("id")+"'  class='dropdown-toggle sub_menulist' name='"+$(obj).attr("name")+"'>";
										inner += "<i class='icon-globe'></i>";
										inner += "<span class='menu-text'>"+$(obj).attr("id")+"</span>";
										inner += "<b class='arrow icon-angle-down'></b>";
										inner += "</a>";
										
										inner += "<ul class='submenu' style='display: block;'>";
										for(var item in json){
											var data = json[item];
											inner += "<li>";
											var _smenuurl = data.menu_url;
											if(data.hasOwnProperty("menu_url")){
												if(_smenuurl.indexOf("http") != -1){
													_smenuurl = _smenuurl +"?cityIds=${sessionScope.PLATFORM_USER_INFO.cities }";
												}
											}else{
												_smenuurl = "<%=basePath%>/LBS/errorAU";
											}
											
											inner += "<a href='javascript:void(0)' onclick='submenuclick(this)' ondblclick='menudbclick(this)'  name='"+_smenuurl +"'>";
											inner += "<i class='icon-double-angle-right'></i><span class='namespan'>";
											inner += data.menu_name_zh_cn;
											inner += "</span></a>";
											inner += "</li>";
										}
										inner +="</ul>";
										$(obj).parent().html(inner);
									}
									
								},
								error : function(){
								}
							});
						}else{
							
						}
						
					}else{
						//直接操作
						$(obj).parent().addClass("active");
						var menu_name_zh_cn = $(obj).find(".menu-text").html();
						$(".breadcrumb .active").html(menu_name_zh_cn);
						$("#page-frame").attr("src",$(obj).attr("name"));
					}
			   	}, 300);
				
			}
			
			function submenuclick(obj){
				$("#menulist li").removeClass("active");
				$(obj).parent().addClass("active");
				$(obj).parent().parent().parent().addClass("open");
				var menu_name_zh_cn = $(obj).find(".namespan").html();
				$(".breadcrumb .active").html(menu_name_zh_cn);
				$("#page-frame").attr("src",$(obj).attr("name"));
			}
			
			function menudbclick(obj){
				if(clickTimer) {
			         window.clearTimeout(clickTimer);
			         clickTimer = null;
			    }
				if(!$(obj).hasClass("sub_menulist")){
					var dialogWidth = window.screen.width;
					var dialogHeight = window.screen.height;
					window.showModalDialog($(obj).attr("name"),window,'dialogWidth:'+dialogWidth+'px;dialogHeight:'+dialogHeight+'px;scroll:yes;resizable:yes;minimize:yes;maximize:yes;');
				}
			}
			function welcome(){
				$(".breadcrumb .active").html("欢迎");
				$("#page-frame").attr("src","http://lbsweb.10101111.com/ucarlbsweb/onlineDriverStatistics.jsp");
			}
			function calcPageHeight(doc) {
			    var cHeight = Math.max(doc.body.clientHeight, doc.documentElement.clientHeight)
			    var sHeight = Math.max(doc.body.scrollHeight, doc.documentElement.scrollHeight)
			    var height  = Math.max(cHeight, sHeight)
			    return height
			}
			function iFrameHeight(){
				var doc = document;
			    var height = calcPageHeight(doc);
			    var myifr = doc.getElementById('page-frame')
			    myifr.style.height = height+"px";
			}
		</script>
	</head>

	<body>
		<div style="width:100%;height:100%;">
		<div class="navbar navbar-default" id="navbar">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="javascript:void(0)" class="navbar-brand">
						<h5>神州专车车辆调度平台</h5>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->

				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="<%=basePath%>/assets/avatars/user.jpg" alt="Jason's Photo" />
								<span class="user-info">
									<small>${sessionScope.PLATFORM_USER_INFO.username },欢迎  </small>
									
								</span>

								<i class="icon-caret-down"></i>
							</a>

							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a id="logout" href="#">
										<i class="icon-off"></i>
										Logout
									</a>
								</li>
							</ul>
						</li>
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
		</div>

		<div class="main-container" id="main-container"  style="width:100%;height:100%;">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner"  style="height:100%;width:100%;">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>

				<div class="sidebar" id="sidebar">
					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
					</script>

					<div class="sidebar-shortcuts" id="sidebar-shortcuts">

						<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
							<span class="btn btn-success"></span>

							<span class="btn btn-info"></span>

							<span class="btn btn-warning"></span>

							<span class="btn btn-danger"></span>
						</div>
					</div><!-- #sidebar-shortcuts -->
					
					<ul class="nav nav-list" id="menulist">
					</ul><!-- /.nav-list -->

					<div class="sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>

					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>
				</div>
				
				<div class="main-content" style="height:100%;">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="javascript:void(0)" onclick="welcome()">Home</a>
							</li>
							<li class="active">欢迎</li>
						</ul><!-- .breadcrumb -->
						
					</div>
					<div id="page-div" >
							 <iframe id="page-frame" scroll="yes" style="width:100%;height:100%;" src="http://lbsweb.10101111.com/ucarlbsweb/onlineDriverStatistics.jsp" frameborder="0" onLoad="iFrameHeight()"></iframe>
					</div>
					
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		</div>
		<!-- basic scripts -->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath%>/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='<%=basePath%>/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=basePath%>/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="<%=basePath%>/assets/js/bootstrap.min.js"></script>
		<script src="<%=basePath%>/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		  <script src="<%=basePath%>/assets/js/excanvas.min.js"></script>
		<![endif]-->

		<script src="<%=basePath%>/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="<%=basePath%>/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="<%=basePath%>/assets/js/jquery.slimscroll.min.js"></script>
		<script src="<%=basePath%>/assets/js/jquery.easy-pie-chart.min.js"></script>
		<script src="<%=basePath%>/assets/js/jquery.sparkline.min.js"></script>
		<script src="<%=basePath%>/assets/js/flot/jquery.flot.min.js"></script>
		<script src="<%=basePath%>/assets/js/flot/jquery.flot.pie.min.js"></script>
		<script src="<%=basePath%>/assets/js/flot/jquery.flot.resize.min.js"></script>

		<!-- ace scripts -->

		<script src="<%=basePath%>/assets/js/ace-elements.min.js"></script>
		<script src="<%=basePath%>/assets/js/ace.min.js"></script>
		<!-- inline scripts related to this page -->
	</body>
</html>
