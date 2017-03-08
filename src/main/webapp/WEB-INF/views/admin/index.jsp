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
<!--[if lt IE 9]>
<script type="text/javascript" src="${ctx}/static/lib/html5.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/respond.min.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/PIE_IE678.js"></script>
<![endif]-->
<LINK rel="Bookmark" href="${ctx}/favicon.ico">
<LINK rel="Shortcut Icon" href="${ctx}/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/admin/css/admin.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/layer/skin/layer.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/layui/css/layui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/laypage/laypage.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/Hui-iconfont/1.0.7/iconfont.css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>IP 测量</title>
<meta name="keywords" content="">
<meta name="description" content="">
</head>
<body>
	<%@include file="/WEB-INF/common/header/header.jspf"%>
	<div class="dislpayArrow2 hidden-xs">
		<a class="pngfix open" href="javascript:void(0);" onclick="displayheader(this)"></a>
	</div>
	<div class="dislpayArrow3 hidden-xs">
		<a class="pngfix open" href="javascript:void(0);" onclick="displaytabnav(this)"></a>
	</div>
	<%@include file="/WEB-INF/common/content/aside.jspf"%>
</body>
</html>