/* -----------H-ui前端框架-------------
 * H-ui.admin.js v2.4
 * http://www.h-ui.net/
 * Created & Modified by guojunhui
 * Date modified 15:42 2016.03.14
 *
 * Copyright 2013-2016 北京颖杰联创科技有限公司 All rights reserved.
 * Licensed under MIT license.
 * http://opensource.org/licenses/MIT
 *
 */
var num = 0, oUl = $("#min_title_list"), hide_nav = $("#Hui-tabNav");
function displayheader(obj) {
	if ($(obj).hasClass("open")) {
		$(obj).removeClass("open");
		$("body").addClass("big-big-page");
	} else {
		$(obj).addClass("open");
		$("body").removeClass("big-big-page");
	}
}
function displaytabnav(obj) {
	if ($(obj).hasClass("open")) {
		$(obj).removeClass("open");
		$("body").addClass("big-big-big-page");
	} else {
		$(obj).addClass("open");
		$("body").removeClass("big-big-big-page");
	}
}
/* 获取顶部选项卡总长度 */
function tabNavallwidth() {
	var taballwidth = 0, $tabNav = hide_nav.find(".acrossTab"), $tabNavWp = hide_nav.find(".Hui-tabNav-wp"), $tabNavitem = hide_nav.find(".acrossTab li"), $tabNavmore = hide_nav
			.find(".Hui-tabNav-more");
	if (!$tabNav[0]) {
		return;
	}
	$tabNavitem.each(function(index, element) {
		taballwidth += Number(parseFloat($(this).width() + 60))
	});
	$tabNav.width(taballwidth + 25);
	var w = $tabNavWp.width();
	if (taballwidth + 25 > w) {
		$tabNavmore.show()
	} else {
		$tabNavmore.hide();
		$tabNav.css({
			left : 0
		})
	}
}

/* 左侧菜单响应式 */
function Huiasidedisplay() {
	if ($(window).width() >= 768) {
		$(".Hui-aside").show()
	}
}
function getskincookie() {
	var v = getCookie("Huiskin");
	var hrefStr = $("#skin").attr("href");
	if (v == null || v == "") {
		v = "default";
	}
	if (hrefStr != undefined) {
		var hrefRes = hrefStr.substring(0, hrefStr.lastIndexOf('skin/')) + 'skin/' + v + '/skin.css';
		$("#skin").attr("href", hrefRes);
	}
}
var tabnum=0;
function Hui_admin_tab(obj, ford) {
	if ($(obj).attr('data-href')) {
		var bStop = false;
		var bStopIndex = 0;
		var _href = $(obj).attr('data-href');
		var _titleName = $(obj).attr("data-title");
		var topWindow = $(window.parent.document);
		var show_navLi = topWindow.find("#min_title_list li");
		show_navLi.each(function() {
			if ($(this).find('span').attr("data-href") == _href) {
				bStop = true;
				bStopIndex = show_navLi.index($(this));
				return false;
			}
		});
		if (!bStop) {
			if (ford == 'I')
				creatIframe(_href, _titleName);
			else
				creatIframeDiv(_href, _titleName);
			min_titleList();
			tabnum++;
		} else {
			show_navLi.removeClass("active").eq(bStopIndex).addClass("active");
			var iframe_box = topWindow.find("#iframe_box");
			if (ford == 'I')
				iframe_box.find(".show_iframe").hide().eq(bStopIndex).show().find(".boboiframe").attr("src", _href);
			else
				$.ajax({
					headers : {
						OpenWay : "div"
					},
					dataType : "html",
					type : "GET",
					url : _href
				}).success(function(data) {
					iframe_box.find(".show_iframe").hide().eq(bStopIndex).show().find(".boboiframe").html(data);
				}).fail(function(jqXHR, textStatus, errorThrown) {
					alert('数据加载失败')
				});
		}
	}
	if(tabnum>5){
		$('#iframe_box').find('.show_iframe').eq(1).remove();
		$('#min_title_list').find('li').eq(1).remove();
		num == 0 ? num = 0 : num--;
		tabNavallwidth();
		tabnum--;
	}
}
function min_titleList() {
	var topWindow = $(window.document);
	var show_nav = topWindow.find("#min_title_list");
	var aLi = show_nav.find("li");
};
function creatIframeDiv(href, titleName) {
	var topWindow = $(window.document);
	var show_nav = topWindow.find('#min_title_list');
	show_nav.find('li').removeClass("active");
	var iframe_box = topWindow.find('#iframe_box');
	show_nav.append('<li class="active"><span data-href="' + href + '">' + titleName + '</span><i></i><em></em></li>');
	var taballwidth = 0, $tabNav = topWindow.find(".acrossTab"), $tabNavWp = topWindow.find(".Hui-tabNav-wp"), $tabNavitem = topWindow.find(".acrossTab li"), $tabNavmore = topWindow
			.find(".Hui-tabNav-more");
	if (!$tabNav[0]) {
		return;
	}
	$tabNavitem.each(function(index, element) {
		taballwidth += Number(parseFloat($(this).width() + 60))
	});
	$tabNav.width(taballwidth + 25);
	var w = $tabNavWp.width();
	if (taballwidth + 25 > w) {
		$tabNavmore.show()
	} else {
		$tabNavmore.hide();
		$tabNav.css({
			left : 0
		})
	}
	var iframeBox = iframe_box.find('.show_iframe');
	iframeBox.hide();
	iframe_box.append('<div class="show_iframe"><div class="loading"></div><div data-href="' + href + '" class="boboiframe" style="width: 100%;height: 100%"></div></div>');
	var showBox = iframe_box.find('.show_iframe:visible');
	$.ajax({
		headers : {
			OpenWay : "div"
		},
		dataType : "html",
		type : "GET",
		url : href
	}).success(function(data) {
		showBox.find(".boboiframe").html(data);
		showBox.find('.loading').hide();
	}).fail(function(jqXHR, textStatus, errorThrown) {
		alert('数据加载失败');
	});
}
function removeIframe() {
	var topWindow = $(window.document);
	var iframe = topWindow.find('#iframe_box .show_iframe');
	var tab = topWindow.find(".acrossTab li");
	var showTab = topWindow.find(".acrossTab li.active");
	var showBox = topWindow.find('.show_iframe:visible');
	var i = showTab.index();
	tab.eq(i - 1).addClass("active");
	iframe.eq(i - 1).show();
	tab.eq(i).remove();
	iframe.eq(i).remove();
}
function creatIframe(href, titleName) {
	var topWindow = $(window.parent.document);
	var show_nav = topWindow.find('#min_title_list');
	show_nav.find('li').removeClass("active");
	var iframe_box = topWindow.find('#iframe_box');
	show_nav.append('<li class="active"><span data-href="' + href + '">' + titleName + '</span><i></i><em></em></li>');
	var taballwidth = 0, $tabNav = topWindow.find(".acrossTab"), $tabNavWp = topWindow.find(".Hui-tabNav-wp"), $tabNavitem = topWindow.find(".acrossTab  li"), $tabNavmore = topWindow
			.find(".Hui-tabNav-more");
	if (!$tabNav[0]) {
		return;
	}
	$tabNavitem.each(function(index, element) {
		taballwidth += Number(parseFloat($(this).width() + 60))
	});
	$tabNav.width(taballwidth + 25);
	var w = $tabNavWp.width();
	if (taballwidth + 25 > w) {
		$tabNavmore.show()
	} else {
		$tabNavmore.hide();
		$tabNav.css({
			left : 0
		})
	}
	var iframeBox = iframe_box.find('.show_iframe');
	iframeBox.hide();
	iframe_box.append('<div class="show_iframe"><div  class="loading"></div><iframe class="boboiframe" style="border: 0;overflow: scroll" src="' + href + '"></iframe></div>');
	var showBox = iframe_box.find('.show_iframe:visible');
	showBox.find('iframe').load(function() {
		showBox.find('.loading').hide();
	});
}
function layer_show(title, url, w, h) {
	if (title == null || title == '') {
		title = false;
	};
	if (url == null || url == '') {
		url = "/errors/404";
	};
	if (w == null || w == '') {
		w = 800;
	};
	if (h == null || h == '') {
		h = ($(window).height() - 50);
	};
	layer.open({
		type : 2,
		area : [w + 'px', h + 'px'],
		fix : false,
		maxmin : true,
		shade : 0.4,
		title : title,
		content : url
	});
}
function layer_close() {
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
function layer_show_div(title, url, idata, w, h) {
	if (title == null || title == '') {
		title = false;
	};
	if (url == null || url == '') {
		url = "/errors/404";
	};
	if (w == null || w == '') {
		w = 800;
	};
	if (h == null || h == '') {
		h = ($(window).height() - 50);
	};
	$.ajax({
		headers : {
			OpenWay : "div"
		},
		data : idata,
		dataType : "text",
		type : "GET",
		url : url
	}).success(function(data) {
		layer.open({
			type : 1,
			area : [w + 'px', h + 'px'],
			fix : false,
			maxmin : true,
			shade : 0.4,
			title : title,
			content : data
		});
	}).fail(function(jqXHR, textStatus, errorThrown) {
		if (layer != null)
			layer.msg('数据加载失败', {
				icon : 5
			});
		else
			alert('数据加载失败')
	});
}
/* 关闭弹出框口 */
function layer_close_div() {
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
$(function() {
	Huiasidedisplay();
	var resizeID;
	$(window).resize(function() {
		clearTimeout(resizeID);
		resizeID = setTimeout(function() {
			Huiasidedisplay();
		}, 500);
	});

	$(".nav-toggle").click(function() {
		$(".Hui-aside").slideToggle();
	});
	$(".menu1j").click(function() {
		var m2 = $(this).parent().children(".menu2j");
		var xx = $(m2).css("display");
		if (xx == "block") {
			$(m2).css("display", "none");
			$(this).removeClass("selected");
		} else {
			$(m2).css("display", "block");
			$(this).addClass("selected");
			var ll = $(this).parent().siblings();
			$.each(ll, function(n, l) {
				$(l).children('.menu2j').css("display", "none");
				$(l).children('.menu1j').removeClass("selected");
			});
		}
	});
	$(".Hui-aside").on("click", ".menu_dropdown dd li a", function() {
		if ($(window).width() < 768) {
			$(".Hui-aside").slideToggle();
		}
	});
	$.Huifold(".menu_dropdown dl dt", ".menu_dropdown dl dd", "fast", 1, "click");
	$(document).on("click", "#min_title_list li", function() {
		var bStopIndex = $(this).index();
		var iframe_box = $("#iframe_box");
		$("#min_title_list li").removeClass("active").eq(bStopIndex).addClass("active");
		iframe_box.find(".show_iframe").hide().eq(bStopIndex).show();
	});
	$(document).on("click", "#min_title_list li i", function() {
		var aCloseIndex = $(this).parents("li").index();
		$(this).parent().remove();
		$('#iframe_box').find('.show_iframe').eq(aCloseIndex).remove();
		num == 0 ? num = 0 : num--;
		tabNavallwidth();
		tabnum--;
	});
	$(document).on("dblclick", "#min_title_list li", function() {
		var aCloseIndex = $(this).index();
		var iframe_box = $("#iframe_box");
		if (aCloseIndex > 0) {
			$(this).remove();
			$('#iframe_box').find('.show_iframe').eq(aCloseIndex).remove();
			num == 0 ? num = 0 : num--;
			$("#min_title_list li").removeClass("active").eq(aCloseIndex - 1).addClass("active");
			iframe_box.find(".show_iframe").hide().eq(aCloseIndex - 1).show();
			tabNavallwidth();
			tabnum--;
		} else {
			return false;
		}
	});
	tabNavallwidth();

	$('#js-tabNav-next').click(function() {
		num == oUl.find('li').length - 1 ? num = oUl.find('li').length - 1 : num++;
		toNavPos();
	});
	$('#js-tabNav-prev').click(function() {
		num == 0 ? num = 0 : num--;
		toNavPos();
	});

	$('#iframe_box').on('click', '.show_iframe table thead input[type=checkbox]', function() {
		$(this).closest("table").find("tr > td:first-child input:checkbox").prop("checked", $(this).prop("checked"));
	});

	function toNavPos() {
		oUl.stop().animate({
			'left' : -num * 100
		}, 100);
	}
});
function boborefresh(obj, href, data) {
	var f = $(obj).closest('.boboiframe')[0];
	if (f == undefined)
		location.replace(location.href);
	else if (f.tagName == 'DIV' || f.tagName == 'div') {
		$(f).html("<div class=\"loading\"></div>");
		$.ajax({
			headers : {
				OpenWay : "div"
			},
			dataType : "html",
			type : "GET",
			data : data,
			url : $(f).attr("data-href")
		}).success(function(data) {
			$(f).html(data);
		}).fail(function(jqXHR, textStatus, errorThrown) {
			if (layer != null)
				layer.msg('数据加载失败', {
					icon : 5
				});
			else
				alert('数据加载失败')
		});
	} else {
		$(f).parent().find('.loading').show();
		location.replace(href);
		$(f).parent().find('.loading').hide();
	}

}
function boboreplace(obj, href, data) {
	var f = $(obj).closest('.boboiframe')[0];
	if (f == undefined)
		location.replace(href);
	else if (f.tagName == 'DIV' || f.tagName == 'div') {
		$(f).html("<div class=\"loading\"></div>");
		$.ajax({
			headers : {
				OpenWay : "div"
			},
			dataType : "html",
			type : "GET",
			data : data,
			url : href
		}).success(function(data) {
			$(f).html(data);
		}).fail(function(jqXHR, textStatus, errorThrown) {
			if (layer != null)
				layer.msg('数据加载失败', {
					icon : 5
				});
			else
				alert('数据加载失败')
		});
	} else {
		$(f).parent().find('.loading').show();
		location.replace(href);
		$(f).parent().find('.loading').hide();
	}

}

function oneRowOprt(obj, url, info, func) {
	layer.confirm('确认要' + info + '吗？', function(index) {
		var id = $(obj).closest("tr").find("input[type=checkbox]").val();
		$.post(url, {
			id : id
		}, function(data, status) {
			if (status == "success") {
				var errorCode = data.error_code;
				if (errorCode == 0) {
					if (func == null)
						$(obj).closest("tr").remove();
					else
						func();
					layer.msg(data.reason, {
						icon : 1,
						time : 3500
					});
				} else
					layer.msg(data.reason, {
						icon : 5,
						time : 6000
					});
			} else {
				layer.msg("数据加载失败！", {
					icon : 5,
					time : 6000
				});
			}
		});
	});
}
function getCurrentFrame() {
	var topWindow = $(window.top.document);
	var fs = topWindow.find(".show_iframe");
	for (var i = 0; i < fs.length; i++) {
		var f = fs[i];
		var xx = $(f).css("display");
		if (xx == "block") {
			var xxx = $(f).find(".boboiframe").get(0);
			return xxx;
		}
	}
	return null;
}

function multiRowsOprt(obj, url, info, func) {
	var inputs = $(obj).closest(".page-container").find("table [name=dlts]:checked");
	layer.confirm('确认要' + info + '它们吗？', function(index) {
		var dlts = "";
		$.each(inputs, function(n, d) {
			dlts += "id=" + d.value + "&";
		});
		dlts = dlts.substring(0, dlts.length - 1);
		$.post(url, dlts, function(data, status) {
			if (status == "success") {
				var errorCode = data.error_code;
				if (errorCode == 0) {
					if (func == null)
						$.each(inputs, function(n, d) {
							$(d).closest("tr").remove();
						});
					else
						func();
					layer.msg(data.reason, {
						icon : 1,
						time : 3500
					});
				} else
					layer.msg(data.reason, {
						icon : 5,
						time : 6000
					});
			} else {
				layer.msg("数据加载失败！", {
					icon : 5,
					time : 6000
				});
			}
		});
	});
}
function bosubmitAsync(form, async, blankOnSuccess) {
	$.ajax({
		type : $(form).attr('method'),
		async : async,
		url : $(form).attr('action'),
		data : $(form).serialize()
	}).success(function(idata) {
		var errorCode = idata.error_code;
		if (errorCode == 0) {
			layer.msg(idata.reason, {
				icon : 1,
				time : 3500
			});
			if (blankOnSuccess)
				$(':input', form).not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected')
		} else {
			if (idata.result != null)
				$.each(idata.result[0], function(key, value) {
					var err = $(form).find("#" + key + "-error");
					if (err != undefined) {
						err.html(value);
					}
				});
			if (idata.reason != null)
				layer.msg(idata.reason, {
					icon : 5,
					time : 6000
				});
		}
	}).fail(function(jqXHR, textStatus, errorThrown) {
		layer.msg('数据加载失败', {
			icon : 5
		});
	});
}
function bobosubmit(form) {
	bosubmitAsync(form, true, true);
}

function submitNotAsync(form) {
	bosubmitAsync(form, false, true);
}

function bobosubmit2(form) {
	bosubmitAsync(form, true, false);
}
function pageHelper(obj, href, data) {
	var f = $(obj).closest('.boboiframe')[0];
	var myurl;
	if (data != null && data.currUrl == false)
		myurl = href;
	else
		myurl = $(f).attr("data-href")
	if (f == undefined)
		location.replace(href);
	else if (f.tagName == 'DIV' || f.tagName == 'div') {
		$(f).parent().find('.loading').show();
		$.ajax({
			headers : {
				OpenWay : "div"
			},
			dataType : "html",
			type : "GET",
			data : data,
			url : myurl
		}).success(function(data) {
			$(f).html(data);
		}).fail(function(jqXHR, textStatus, errorThrown) {
			if (layer != null)
				layer.msg('数据加载失败', {
					icon : 5
				});
			else
				alert('数据加载失败')
		});
		$(f).parent().find('.loading').hide();
	} else {
		$(f).parent().find('.loading').show();
		location.replace($(f).attr("data-href"));
		$(f).parent().find('.loading').hide();
	}
}
function bobotrnew_tab(obj) {
	var id = $(obj).closest("tr").find("input[name=dlts]").val();
	var idname = $(obj).attr("data-prop");
	if (idname == undefined || idname == null)
		idname = "id";
	var url = $(obj).attr("data-href");
	$(obj).attr("data-href", url + "?" + idname + "=" + id);
	Hui_admin_tab(obj, 'D');
	$(obj).attr("data-href", url);
}
function layer_show_full_div(title, url, idata) {
	if (title == null || title == '') {
		title = false;
	};
	if (url == null || url == '') {
		url = "/errors/404";
	};
	$.ajax({
		headers : {
			OpenWay : "div"
		},
		data : idata,
		dataType : "text",
		type : "GET",
		url : url
	}).success(function(data) {
		var index = layer.open({
			type : 1,
			title : title,
			content : data
		});
		layer.full(index);
	}).fail(function(jqXHR, textStatus, errorThrown) {
		if (layer != null)
			layer.msg('数据加载失败', {
				icon : 5
			});
		else
			alert('数据加载失败')
	});
}
function bobopost(url, mydata) {
	if (url == null || url == '') {
		return false;
	};
	$.ajax({
		data : mydata,
		type : "POST",
		async : false,
		url : url
	}).success(function(data) {
		if (data.error_code == 0) {
			layer.msg(data.reason, {
				icon : 1,
				time : 3500
			});
			return true;
		} else {
			if (data.reason != null)
				layer.msg(data.reason, {
					icon : 5,
					time : 6000
				});
			return false;
		}
	}).fail(function(jqXHR, textStatus, errorThrown) {
		if (layer != null)
			layer.msg('数据加载失败', {
				icon : 5
			});
		else
			alert('数据加载失败')
		return false;
	});
}
function bobopostr(obj, url, mydata) {
	if (bobopost(url, mydata)) {
		boborefresh(obj, '', null);
	}
}
function bobonew_tab(obj, url, title, w) {
	$(obj).attr("data-href", url);
	$(obj).attr("data-title", title);
	Hui_admin_tab(obj, w);
}
function oneRowPost(obj, url, mydata) {
	if (mydata == null)
		mydata = {};
	var data = new Object();
	var ok = true;
	var ipts = $(':input', $(obj).closest('tr')).not(':button, :submit, :reset');
	$.each(ipts, function(n, ipt) {
		if (($(ipt).val() == "" || $(ipt).val() == "不能为空-") && $(ipt).attr("required") == "required") {
			ok = false;
			$(ipt).val("不能为空-");
			$(ipt).css("color", "red");
		}
		$(data).attr($(ipt).attr('name'), $(ipt).val());
	});
	if (ok) {
		bobopost(url, $.extend(data, mydata));
	} else {
		if (layer != null)
			layer.msg('请填写完整', {
				icon : 5,
				time : 6000
			});
		else
			alert('请填写完整')
		return false;
	}
	return true;
}
function oneRowPostCfm(obj, info, url, mydata) {
	layer.confirm('确认要' + info + '吗？', function(index) {
		var xx = oneRowPost(obj, url, mydata);
		if (xx)
			boborefresh(obj, '', null);
	});
}
function bobopostCfm(info, url, data) {
	layer.confirm('确认要' + info + '吗？', function(index) {
		bobopost(url, data);
	});
}
function oneRowPostRfh(obj, url, mydata) {
	var xx = oneRowPost(obj, url, mydata);
	if (xx)
		boborefresh(obj, '', null);
}
function oneRowPostRmv(obj, info, url, mydata) {
	layer.confirm('确认要' + info + '吗？', function(index) {
		oneRowPost(obj, url, mydata);
		$(obj).closest("tr").remove();
	});
}
function isearch(obj, url) {
	var ipts = $(':input', $(obj).closest('#searchDomain')).not(':button, :submit, :reset');
	var data = "";
	var co = 0;
	$.each(ipts, function(n, ipt) {
		if ($(ipt).val() != '') {
			if (co != 0)
				data += '&';
			data += $(ipt).attr('name') + '=' + $(ipt).val();
			co++;
		}
	});
	if (co > 0)
		boborefresh(obj, url, data);
}
function isearchr(obj) {
	var url = $(obj).closest('.boboiframe').attr('data-href');
	isearch(obj, url);
}
function isearch2(obj, url) {
	var ipts = $(':input', $(obj).closest('#searchDomain')).not(':button, :submit, :reset');
	var data = "";
	var co = 0;
	$.each(ipts, function(n, ipt) {
		if ($(ipt).val() != '') {
			if (co != 0)
				data += '&';
			data += $(ipt).attr('name') + '=' + $(ipt).val();
			co++;
		}
	});
	if (co > 0)
		boboreplace(obj, url, data);
}
