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
		  <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<link rel="stylesheet" href="<%=basePath%>/assets/css/jquery-ui-1.10.3.full.min.css" />
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
		
	</head>
		<body onload="initialTable();">
					<div class="page-content">
						<div class="page-header">
							<h1>
								菜单管理
								<small>
									<i class="icon-double-angle-right"></i>
									菜单列表
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								
								<div class="row">
									<div class="col-xs-12">
										
										<div class="table-header">
											菜单管理(菜单列表)
										</div>

										<div class="table-responsive">
											<table id="sample-table-2" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th class='center'>菜单ID</th>
														<th class='center'>菜单名称</th>
														<th class='center'>菜单中文名称</th>
														<th class='center'>是否末级菜单</th>
														<th class='center'>父级菜单ID</th>
														<th class='center'></th>
													</tr>
												</thead>
											</table>
										</div>
									</div>
								</div>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->

		<!-- basic scripts -->
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=basePath%>/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<!-- inline scripts related to this page -->

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
			jQuery(function($) {
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
			
			function initialTable(){
				$("#sample-table-2").dataTable().fnDestroy();
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
			        "sAjaxSource": '<%=basePath%>/menu/listMU', 
			        "aoColumns":[  
							{"mData":"menuid","sDefaultContent":"","sWidth":"15%","sClass":"center"},
							{"mData":"menu_name","sDefaultContent":"","sWidth":"20%","sClass":"center"},
							{"mData":"menu_name_zh_cn","sDefaultContent":"","sWidth":"20%","sClass":"center"},
							{"mData":"is_leaf_cn","sDefaultContent":"","sWidth":"15%","sClass":"center"},
							{"mData":"parent_id","sDefaultContent":"","sWidth":"20%","sClass":"center"},
							{"mData":"control","sDefaultContent":"","sWidth":"10%","sClass":"center"}
			        ],
			        "fnServerData" : function(sSource, aoData, fnCallback) {
						$.ajax({
							"type" : 'post',
							async : false,
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
				});
				$("#sample-table-2_filter").prepend("<a class='btn btn-info' href='javascript:void(0)' onclick='addMenu()'>新增</a> &nbsp;&nbsp;&nbsp;");
			}
			function addMenu(){
				var url = '<%=basePath%>/menu/menuEditMU';
				var ret = window.showModalDialog(url,window,'dialogWidth=800px;dialogHeight=600px;dialogTop:no;scroll:no');
			}
			function editMenu(id){
				var url = '<%=basePath%>/menu/menuEditMU?entity.id='+id;
				var ret = window.showModalDialog(url,window,'dialogWidth=800px;dialogHeight=600px;dialogTop:no;scroll:no');
			}
			function delMenu(id){
				var bool = window.confirm("删除后不可恢复，是否删除？");
				if(bool){
					$.ajax({
						type : 'POST',
						async : false,
						url : '<%=basePath%>/menu/checkMenuIsLeafMU',
						data : {
							'entity.id' : id
						},
						success : function(data){
							var json = eval("("+data+")");
							if(!json.status){
								alert( '此菜单有下级菜单,请先删除下级！');
								return false;
							}
							$.ajax({
								type : 'POST',
								async : false,
								url : '<%=basePath%>/menu/editMenuMU',
								data : {
									'entity.id' : id,
									'entity.is_del' : 1
								},
								success : function(ret){
									var _json = eval("("+ret+")");
									if(_json.status){
										alert("删除成功！");
										parent.document.getElementById("page-frame").src="<%=basePath%>/pages/menu/menu_list.jsp";
									}
								}
							});
						}
					});
				}
			}
		</script>
	</body>
</html>
