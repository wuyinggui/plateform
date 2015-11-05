<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.lang.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
 <%
	String basePath=getServletConfig().getServletContext().getContextPath();
%>
<!DOCTYPE html>
<html lang="en" style="position: inherit;">
	<head>
		<meta charset="utf-8" />
		<title>菜单编辑</title>
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
		<link rel="stylesheet" href="<%=basePath%>/assets/css/datepicker.css" />
		<link rel="stylesheet" href="<%=basePath%>/assets/css/ui.jqgrid.css" />

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
		<script type="text/javascript">
			var _nextSeq = '0';
			$(document).ready(function(){
				var radioValue = $("#is_leaf").val();
				if(radioValue  == '' || radioValue == '1'){
					radioValue = '1';
					$("#is_leaf").val(radioValue);
					$("#hideUrl").show();
				}else{
					radioValue = '0';
					$("#hideUrl").hide();
				}
				$(".ace[value='"+radioValue+"']").attr("checked", "checked");
				var parent_idVal = $("#parent_id").val();
				setNextSeq(parent_idVal);
				var options = '';
				$.ajax({
					type : 'POST',
					async : false,
					url : '<%=basePath%>/menu/allFirstLevelMenuListMU',
					success : function(data){
						var json = eval("("+data+")");
						for(var item in json){
							var obj = json[item];
							var menuid = obj.menuid;
							if($("#menuid").val()!= menuid){
								options += "<option value='"+menuid+"'>"+obj.menuid+"</option>";
							}
						}
						$("#form-field-select-3").append(options);
						$("#form-field-select-3").val(parent_idVal);
					}
				});
				
				
				$(".ace").change(function(){
					var val = $(this).val();
					$("#is_leaf").val(val);
					if(val * 1.0 == 0){
						$("#hideUrl").hide();
						$("#menu_url").val("");
					}else{
						$("#hideUrl").show();
						var hideIdVal = $("#hideId").val();
						if(hideIdVal != ""){
							$.ajax({
								type : 'POST',
								async : false,
								url : '<%=basePath%>/menu/checkMenuIsLeafMU',
								data : {
									'entity.id' : hideIdVal
								},
								success : function(data){
									var json = eval("("+data+")");
									if(!json.status){
										alert("此菜单有下级菜单,请先删除下级！");
										$(".ace[value='0']").attr("checked", "checked");
										return false;
									}
								}
							});
						}
					}
				});
				
				$("#form-field-select-3").change(function(){
					setNextSeq($(this).val());
				});
				$("#button_ok").click(function(){
					var flag = validForm();
					if(flag){
						var form = document.getElementById("form");
						var is_leaf = $("#is_leaf").val();
						var postData = $("#form").serialize();
						$.ajax({
							async : false,
							type : 'GET',
							url : '<%=basePath%>/menu/editMenuMU?'+postData+"&randomTep="+Math.random(),
							dataType : 'json',
							success : function(data){
								var json = eval("("+data+")");
								if(json.status){
									var hideId = $("#hideId").val();
									if(hideId!=""){
										alert("更新成功！");
									}else{
										alert("添加成功！");
									}
									dialogArguments.location.href=dialogArguments.location.href ;
									window.close();
								}else{
									var type = json.type;
									if(type * 1.0 == 1){
									}else{
										alert("参数错误,请检查菜单ID、菜单名称、菜单中文名称是否有重复！");
									}
								}
							},
							error : function(){
							}
						});
					}
				});
				function validForm(){
					var menuid = $("#menuid").val();
					if(menuid == ''){
						alert("菜单ID不能为空!");
						return false;
					}
					var menu_name = $("#menu_name").val();
					if(menu_name == ''){
						alert("菜单名称不能为空!");
						return false;
					}
					var menu_name_zh_cn = $("#menu_name_zh_cn").val();
					if(menu_name_zh_cn == ''){
						alert("菜单中文名称不能为空!");
						return false;
					}
					var seqVal = $("#hideSeq").val();
					var reg=/^\+?[1-9][0-9]*$/;//整数正则表达式
					if(!reg.test(seqVal)){
						//非整数
						alert("排序号必须为正整数,请重新输入");
						return false;
					}
					var is_leaf = $(".ace:checked").val();
					var parent_id = $("#form-field-select-3").val();
					var menu_url = $("#menu_url").val();
					if(is_leaf *1.0 == 0){
						if(menu_url != ''){
							alert("菜单URL只能为空!");
							return false;
						}
					}else{
						if(menu_url == ''){
							alert("菜单URL不能为空!");
							return false;
						}
					}
					return true;
				}
				$("#button_cancel").click(function(){
					window.close();
				});
			});
			
			function setNextSeq(parent_id){
				if($("#seq").val() == '' && (parent_id != "${entity.parent_id}" || parent_id == '')){
					//parent_id调整了，说明是select值改变
					$.ajax({
						type : 'POST',
						async : false,
						url : '<%=basePath%>/menu/getNextSeqMU',
						data : {
							'entity.parent_id' : parent_id
						},
						success : function(data){
							var json = eval("("+data+")");
							if(json.status){
								_nextSeq = json.nextSeq;
								$("#hideSeq").val(_nextSeq);
							}
							//选中
						}
					});
				}
			}
			function setSeqVal(obj){
				var seqVal = $(obj).val();
				var reg=/^\+?[1-9][0-9]*$/;//整数正则表达式
				if(!reg.test(seqVal)){
					//非整数
					alert("排序号必须为正整数,请重新输入");
					return false;
				}else{
					$("#hideSeq").val(seqVal);
				}
			}
		</script>
	</head>

	<body style="background-color: rgb(255,255,255);">
	<div class="main-container" id="main-container" style="width: 100%">
		<div class="main-container-inner" style="margin-left:100px;margin-top:10px">
					<div class="page-content left">
						<div class="page-header">
							<h1>
								菜单管理
								<small>
									<i class="icon-double-angle-right"></i>
									菜单编辑
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<form class="form-horizontal" id="form" role="form">
									<input type="hidden" id="is_leaf" value="${entity.is_leaf }"/>
									<input type="hidden" id="parent_id" value="${entity.parent_id }"/>
									<input type="hidden" id="hideId" name="entity.id" value="${entity.id }"/>
									<input type="hidden" id="error" value="0"/>
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" > 菜单ID</label>

										<div class="col-sm-9">
											<input type="text" id="menuid" value="${entity.menuid }" name="entity.menuid" class="col-xs-10 col-sm-5"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" > 菜单名称</label>

										<div class="col-sm-9">
											<input type="text" id="menu_name" value="${entity.menu_name }" name="entity.menu_name" class="col-xs-10 col-sm-5"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">菜单中文名称</label>
										<div class="col-sm-9">
											<input type="text" id="menu_name_zh_cn" value="${entity.menu_name_zh_cn }" name="entity.menu_name_zh_cn" class="col-xs-10 col-sm-5"/>
										</div>
									</div>
									
										

										<div class="form-group">
												<label class="col-sm-3 control-label">是否末级菜单</label>

												<div class="radio">
													<label>
														<input name="entity.is_leaf" type="radio"  class="ace" value="1"/>
														<span class="lbl">是</span>
													</label>
													<label>
														<input name="entity.is_leaf" type="radio" class="ace" value="0"/>
														<span class="lbl">否</span>
													</label>
												</div>
										</div>
										<div>
											<div class="form-group">
												<label class="col-sm-3 control-label" > 排序号：</label>
												<div class="col-sm-9">
													<input type="text" id="seq" class="col-xs-10 col-sm-5" value="${entity.seq }" placeholder="请输入排序号,选填" onblur="setSeqVal(this)"/>
													<input type="hidden" id="hideSeq" name="entity.seq" value="${entity.seq }"/>												
												</div>
											</div>
										</div>	
										<div id="hideDiv">
											<div  class="form-group">
										
													<label for="form-field-select-3" class="col-sm-3 control-label ">父级菜单ID</label>
													<select  class="col-sm-3"  name="entity.parent_id" id="form-field-select-3">
														<option value="">&nbsp;</option>
													</select>
											</div>
												
										</div>
										<div id="hideUrl">
											<div class="form-group">
															<label class="col-sm-3 control-label" > 菜单URL</label>
					
															<div class="col-sm-9">
																<input type="text" id="menu_url" name="entity.menu_url" class="col-xs-10 col-sm-5" value="${entity.menu_url }"/>
															</div>
											</div>
										</div>
										
										
										<div class="col-md-offset-3 col-md-9">
											<button id="button_ok" class="btn btn-info" type="button">
												<i class="icon-ok bigger-110"></i>
												提交
											</button>

											&nbsp; &nbsp; &nbsp;
											<button id="button_cancel" class="btn" type="button">
												<i class="icon-undo bigger-110"></i>
												取消
											</button>
										</div>
								</form>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
					</div><!-- /.main-container-inner -->
				</div><!-- /.main-container -->
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


		<script src="<%=basePath%>/assets/js/ace-elements.min.js"></script>
		<script src="<%=basePath%>/assets/js/ace.min.js"></script>
	</body>
</html>
