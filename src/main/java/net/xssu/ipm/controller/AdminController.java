package net.xssu.ipm.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import net.xssu.ipm.pojo.Resource;
import net.xssu.ipm.service.ResourceService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

	@Inject
	private ResourceService resourceService;

	/**
	 * @Title: indexAdmin
	 * @Description: 系统主页
	 * @param request
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/admin/", method = RequestMethod.GET)
	public String indexAdmin(HttpServletRequest request, Model model) {
		List<Resource> menus = null;
		menus = resourceService.findMenus(null);
		model.addAttribute("menus", menus);
		model.addAttribute("welcome", "/welcome");
		return "admin/index";
	}

	/**
	 * @Title: welcome
	 * @Description: 系统欢迎页，主要展示一些操作者的信息
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/welcome")
	public String welcome(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		request.setAttribute("cloginIP", ip);
		return "welcome";
	}
}
