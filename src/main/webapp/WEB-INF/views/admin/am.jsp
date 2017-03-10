<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>IP 测量</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/layer/skin/layer.css" />

<link rel="stylesheet" type="text/css" href="${ctx}/static/draw/my.css" />
<style>
</style>
</head>
<body>
	<div id="Do"></div>
	<script type="text/javascript" src="${ctx}/static/lib/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/lib/layer/layer.js"></script>
	<script type="text/javascript" src="${ctx}/static/draw/my.js"></script>
	<script type="text/javascript">
		function postform1(obj) {
			var form = $(obj).closest("form");
			//普通表单
			var ititle = $(form).find("input[name=title]").val();
			var ides = $(form).find("textarea[name=des]").val();
			$.ajax({
					type : form.attr('method'),
					url : form.attr('action'),
					dataType : "json",
					data : "id=" + $(form).find("input[name=amId]").val() + "&title=" + ititle + "&description=" + ides + "&bjson=" + JSON
								.stringify(Do.exportData())
					}).success(function(idata) {
						var errorCode = idata.error_code;
						if (errorCode == 0) {
							layer.msg(idata.reason, {
								icon : 1,
								time : 3500
							});
							Do.$iamId=idata.result[0];
							Do.$description=ides;
							Do.$title=ititle;
							var lb = $(".GooFlow_head").find("label")[0];
							if(lb!=undefined){
								lb.title=ititle;
								$(lb).html(ititle);
							}
							$(':input', form).not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected')
						} else {
							if (idata.reason != null)
								layer.msg(idata.reason, {
									icon : 5,
									time : 6000
								});
						}
					}).fail(function(jqXHR, textStatus, errorThrown) {
						alert('数据加载失败')//错误信息
					});
			return false;
		}
		function getWindowWH() {
			var winWidth = 1210, winHeight = 610;
			if (window.innerWidth)
				winWidth = window.innerWidth;
			else if ((document.body) && (document.body.clientWidth))
				winWidth = document.body.clientWidth;
			// 获取窗口高度
			if (window.innerHeight)
				winHeight = window.innerHeight;
			else if ((document.body) && (document.body.clientHeight))
				winHeight = document.body.clientHeight;
			winHeight -= 2;
			winWidth -= 2;
			return {
				w : winWidth,
				h : winHeight
			};
		}

		jsondata = ${jsonData};
		var winWH = getWindowWH();
		var property = {
			width : winWH.w,
			height : winWH.h,
			toolBtns : ["start", "end", "t", "n", "c", "ln"],
			haveHead : true,
			headBtns : ["save", "undo", "redo", "reload"],//如果haveHead=true，则定义HEAD区的按钮
			haveTool : true,
			haveGroup : true,
			useOperStack : true,
			onBtnSaveClick : function(title, des, amId) {
				var hform = "";
				hform += '<form method="post" class="myform" action="${ctx}${myurl}">';
				hform += '<div style="width:80%;margin-left:10%">';
				hform += '	<span style="color:red">*</span>服务/协议 名称：<br>';
				hform += '		<input type="text" name="title" value="'+title+'" />';
				hform += '		<input hidden="hidden" type="text" name="amId" value="'+amId+'" />';
				hform += '<br>描述：<br>';
				hform += '		<textarea name="des" cols="22" rows="3">' + des + '</textarea>';
				hform += '				<br><button type="button" style="display:inline-block;cursor:pointer;text-align:center; font-weight:400;white-space:nowrap;vertical-align: middle;" onclick="postform1(this)" class="layui-btn">提交</button>';
				hform += '</div>';
				hform += '</form>';
				layer.open({
					type : 1,
					area : [250 + 'px', 200 + 'px'],
					fix : false,
					maxmin : true,
					shade : 0.4,
					title : "更新自动机记录",
					content : hform
				});
			}
		};
		var remark = {
			cursor : "选择指针",
			direct : "转换连线",
			start : "开始结点",
			end : "结束结点",
			t : "回复态（输出banner）",
			n : "发送态",
			c : "回复态",
			ln : "连线节点",
			group : "组织划分框编辑开关"
		};
		var Do;
		$(document).ready(function() {
			Do = $.createGooFlow($("#Do"), property);
			Do.setNodeRemarks(remark);
			Do.onItemDel = function(id, type) {
				return confirm("确定要删除该单元吗?");
			}
			Do.loadData(jsondata);
		});
	</script>
</body>
</html>
