<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<LINK rel="Bookmark" href="/favicon.ico">
<LINK rel="Shortcut Icon" href="/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/admin/css/admin.css" />
<title>我的桌面</title>
</head>
<body>
	<div class="page-container" style="min-height: 400px">
		<p class="f-20 text-success">
			欢迎使用 IP测量 <span class="f-14">v1.0</span>系统！
		</p>
		<p>当前IP地址：${cloginIP}</p>
	</div>
	<footer class="footer mt-20">
		<div class="container">
			<p>
				感谢jQuery、h-ui、layui等框架技术支持
				<br>
				Copyright &copy;2017 IP 测量.backEnd v1.0 All Rights Reserved.
				<br>
			</p>
		</div>
	</footer>
</body>
</html>
