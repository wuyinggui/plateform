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
		<title>Tables - Ace Admin</title>

		<meta name="description" content="Static &amp; Dynamic Tables" />
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
		<script src="<%=basePath%>/assets/js/bootstrap.min.js"></script>
		<script src="<%=basePath%>/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<script src="<%=basePath%>/assets/js/jquery.dataTables.min.js"></script>
		<script src="<%=basePath%>/assets/js/jquery.dataTables.bootstrap.js"></script>

		<!-- ace scripts -->

		<script src="<%=basePath%>/assets/js/ace-elements.min.js"></script>
		<script src="<%=basePath%>/assets/js/ace.min.js"></script>
		<script type="text/javascript">
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
			
			var menuArray = new Array();
			var cityArray = new Array();
			function loadData(){
				$('#sample-table-2').dataTable().fnDestroy();
				$("#sample-table-2").dataTable({
					"bRetrieve": true,
					"bDestroy": true,
					"bPaginate" : true,// 分页按钮
					"bFilter" : true,// 搜索栏
					"bLengthChange" : true,// 每行显示记录数
					"iDisplayLength" : 10,// 每页显示行数
					"bSort" : false,// 排序
					"bInfo" : true,// Showing 1 to 10 of 23 entries 总记录数没也显示多少等信息
					"bWidth":true,
		            "bScrollCollapse": true,
					"bProcessing" : true,
					"bServerSide" : true,
					"bSortCellsTop": true,	
			        "sAjaxSource": '<%=basePath%>/menu/menuListMU', 
			        "aoColumns":[  
							{"mData":"username","sDefaultContent":"","sClass":"center"},
							{"mData":"menu","sDefaultContent":"","sClass":"center"},
							{"mData":"city","sDefaultContent":"","sClass":"center"}
			        ],
			   
			        "fnServerData" : function(sSource, aoData, fnCallback) {
						$.ajax({
							"type" : 'post',
							"url" : sSource,
							"dataType" : "json",
							"data" : {
								aoData : JSON.stringify(aoData)
							},
							"success" : function(resp) {
								var json = eval("("+resp+")");
								fnCallback(json);
							}
						});
			
					}
					
				}
				);
				$("#sample-table-2_filter").prepend("<a class='btn btn-info' href='javascript:void(0)' onclick='onkeySetMenu(this)'>权限一键设置</a> &nbsp;&nbsp;&nbsp;");
			}
			
			function onkeySetMenu(obj){
				setMenu('',obj);
			}
			var type = 0;
			var menu_th = "<th class='center'><label><input type='checkbox' class='ace' value='0' onclick='selectAll(this)'/><span class='lbl'></span></label></th><th>权限名称</th><th>权限ID</th>";
			var city_th = "<th class='center'><label><input type='checkbox' class='ace' value='0' onclick='selectAll(this)'/><span class='lbl'></span></label></th><th>城市名称</th>"
			var uname = '';
			function setMenu(name,obj){
				menuArray = [];
				$('#data-table').dataTable().fnDestroy();
		      	uname = name;
				type = 1;
				$.ajax({
					type : 'POST',
					async : false,
					url : '<%=basePath%>/menu/getUserAllMenuMU',
					data : {
						'username' : name
					},
					dataType : 'json',
					success : function(data){
						var json = eval("("+data+")");
						var inner = '';
					
						for(var item in json){
							var data = json[item];
							if(data.checked){
								menuArray.push(data.id*1.0);
							}
							inner +="<tr>";
							inner += "<td class='center'><label><input type='checkbox' value='"+data.id+"' class='ace' ";
							inner += (data.checked ? "checked='checked'" : "") +" onclick='changestate(this)'/>";
							inner += "<span class='lbl'></span></label></td>";
							inner += "<td>"+data.menu_name_zh_cn+"</td>";
							inner += "<td>"+data.menuid+"</td>";
							inner +="</tr>";	
						}
						$("#head-info").html("用户权限选择");
						$("#tr_info").html(menu_th);
						$("#tbody_info").html(inner);	
						$("#data-table").css("width","100%");
						var table = $('#data-table').dataTable( {
							"bDestroy" : true,
							"bAutoWidth": false,
							"bSort" : false,
							"fnDrawCallback": function( oSettings ) {
							      //alert( 'DataTables has redrawn the table' );
							      $(".ace[value='0']").prop("checked",false);
							}
						} );
					},
					error : function(){
					}
					
				});
				
				$(obj).attr("href","#modal-table");
				$(obj).attr("data-toggle","modal");
				$("#modal-footer").show();
			}
			function setCity(name,obj){
				cityArray = [];
				$('#data-table').dataTable().fnDestroy();
		        uname = name;
				type = 2;
				$.ajax({
					type : 'POST',
					async : false,
					url : '<%=basePath%>/menu/getUserAllCityMU',
					data : {
						'username' : name
					},
					dataType : 'json',
					success : function(data){
						var json = eval("("+data+")");
						var inner = '';
					
						for(var item in json){
							var data = json[item];
							if(data.checked){
								cityArray.push(data.id*1.0);
							}
							inner +="<tr>";
							inner += "<td class='center'><label><input type='checkbox' value='"+data.id+"' class='ace' ";
							inner += (data.checked ? "checked='checked'" : "") +" onclick='changestate(this)'/>";
							inner += "<span class='lbl'></span></label></td>";
							inner += "<td>"+data.name+"</td>";
							inner +="</tr>";	
						}
						$("#head-info").html("用户城市选择");
						$("#tr_info").html(city_th);
						$("#tbody_info").html(inner);	
						$("#data-table").css("width","100%");
						$('#data-table').dataTable( {
							"bDestroy" : true,
							"bAutoWidth": false,
							"bSort" : false,
							"fnDrawCallback": function( oSettings ) {
							      //alert( 'DataTables has redrawn the table' );
							      $(".ace[value='0']").prop("checked",false);
							}
						} );
					},
					error : function(){
					}
					
				});
				$(obj).attr("href","#modal-table");
				$(obj).attr("data-toggle","modal");
				$("#modal-footer").show();
			}
			function selectAll(obj){
				var status = $(obj).prop("checked");
				if(status){
					$(".ace").prop("checked",true);
					$("#modal-table .ace").each(function(){
						arrayPush(type,$(this).val());
					});
					
				}else{
					$(".ace").prop("checked",false);
					$("#modal-table .ace").each(function(){
						arrayRemove(type,$(this).val());
					});
				}
			}
			function arrayPush(stype,obj){
				if(stype == 1){
					if(menuArray.indexOf(obj*1.0) == -1){
						menuArray.push(obj*1.0);
					}
				}else{
					if(cityArray.indexOf(obj*1.0) == -1){
						cityArray.push(obj*1.0);
					}
				}
			}
			
			function arrayRemove(stype,obj){
				if(stype == 1){
					var sindex = menuArray.indexOf(obj*1.0);
					if(sindex != -1){
						//menuArray.push(obj);
						menuArray.splice(sindex,1);
					}
				}else{
					var sindex = cityArray.indexOf(obj*1.0);
					if(sindex != -1){
						//menuArray.push(obj);
						cityArray.splice(sindex,1);
					}
				}
			}
			
			function changestate(obj){
				var status = $(obj).prop("checked");
				if(status){
					$(obj).prop("checked",true);
					arrayPush(type,$(obj).val());
				}else{
					$(obj).prop("checked",false);
					arrayRemove(type,$(obj).val());
				}
			}
			$(".modal-content").find(".pagination li").on('click',function(){
				if(type == 1){
					$("#modal-table .ace:checked").each(function(){ 
						var menuid = $(this).val();
						if(menuArray.indexOf(menuid) == -1){
							menuArray.push(menuid);
						}
					}) 
				}else{
					$("#modal-table .ace:checked").each(function(){ 
						var cityid = $(this).val();
						if(cityArray.indexOf(cityid) == -1){
							cityArray.push(cityid);
						}
					}) 
				}
			});
			function submitChange(){
				var str=""; 
				if(type == 1){
					str = menuArray.join(",");
				}else{
					str = cityArray.join(",");
				}
				if(str.indexOf("0") == 0){
					str = str.substring(2);
				}
				if(type == 1){
					 $.ajax({
						type : 'POST',
						url : '<%=basePath%>/menu/saveUserInfoMU',
						async : false,
						data : {
							'usermenu.user_name' : uname,
							'usermenu.menu_ids' : str
						},
						success : function(data){
							var json = eval("("+data+")");
							if(json.success){
							
								alert("修改成功");
								$("#page-frame").attr("src","<%=basePath%>/menu/menuListMU");
								
							}
						},
						error : function(){
						}
					}); 
				}else{
					$.ajax({
						type : 'POST',
						url : '<%=basePath%>/menu/saveUserInfoMU',
						async : false,
						data : {
							'usermenu.user_name' : uname,
							'usermenu.city_ids' : str
						},
						success : function(data){
							var json = eval("("+data+")");
							if(json.success){
								alert("修改成功");
								$("#page-frame").attr("src","<%=basePath%>/menu/menuListMU");
							}
						},
						error : function(){
						}
					}); 
				}
				
			}
		</script>
	</head>

	<body onload = "loadData()">
					<div class="page-content">
						<div class="page-header">
							<h1>
								权限管理
								<small>
									<i class="icon-double-angle-right"></i>
									用户列表
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								
								<div class="row">
									<div class="col-xs-12">
										
										<div class="table-header">
											权限管理(用户列表)
										</div>

										<div class="table-responsive">
											<table id="sample-table-2" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														
														<th>用户名</th>
														<th></th>
														<th></th>

													</tr>
												</thead>
											</table>
										</div>
									</div>
								</div>
								
								
								<div id="modal-table" class="modal fade" tabindex="-1">
									<div class="modal-dialog">
										<div class="modal-content" style="width:700px">
											<div class="modal-header no-padding">
												<div class="table-header">
													<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
														<span class="white">&times;</span>
													</button>
													<span id="head-info">用户权限选择</span>
													
												</div>
											</div>

											<div class="modal-body  no-padding">
												<div class="table-responsive" >
													<table id="data-table" class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
													<thead>
														<tr id="tr_info">
															<th class="center">
																<label>
																	<input type="checkbox" class="ace" />
																	<span class="lbl"></span>
																</label>
															</th>
															<th>权限名称</th>
															<th>权限ID</th>

														</tr>
													</thead>

													<tbody id="tbody_info" >
														
													</tbody>
												</table>
												</div>
												
											</div>

											<div id="modal-footer" class="modal-footer no-margin-top" style="display:none;">
												<button id="btn-submit" onclick="submitChange()" class="btn btn-sm btn-success pull-left" data-dismiss="modal">
													<i class="icon-ok"></i>
													Submit
												</button>
												<button id="btn-close" class="btn btn-sm btn-danger pull-left" data-dismiss="modal">
													<i class="icon-remove"></i>
													Close
												</button>
											</div>
										</div><!-- /.modal-content -->
									</div><!-- /.modal-dialog -->
								</div><!-- PAGE CONTENT ENDS -->
								
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->

				

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>

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
		

		<!-- inline scripts related to this page -->

		<script type="text/javascript">
			jQuery(function($) {
				$('#sample-table-2').dataTable( {
				} );
				
				$('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});
				function tooltip_placement(context, source) {
					var $source = $(source);
					var $parent = $source.closest('table')
					var off1 = $parent.offset();
					var w1 = $parent.width();
			
					var off2 = $source.offset();
					var w2 = $source.width();
			
					if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
					return 'left';
				}
			})
		</script>
	</body>
</html>
