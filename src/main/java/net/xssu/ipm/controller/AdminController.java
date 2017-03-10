package net.xssu.ipm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import net.xssu.ipm.json.Resp;
import net.xssu.ipm.pojo.Automata;
import net.xssu.ipm.pojo.Resource;
import net.xssu.ipm.service.ResourceService;
import net.xssu.ipm.service.impl.ResourceServiceImpl;
import net.xssu.ipm.utils.C;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@RequestMapping(value = "/setAM", method = RequestMethod.GET)
	public String importAM(HttpServletRequest request, Model model) {
		model.addAttribute("myurl", "/setAM");
		Automata am = null;
		if(C.isNotEmpty(request.getParameter("id"))){
			int id = Integer.parseInt(request.getParameter("id"));
			am = resourceService.findAutomataInfo(new Automata(id));
		}
		if(am == null){
			model.addAttribute("jsonData", "{title:\"无标题\",iamId:\"\",initNum:1,description:\"\",nodes:{},lines:{},areas:{}}");
			return "admin/am";
		}
		model.addAttribute("jsonData", ResourceServiceImpl.buildJsonData(am));
		return "admin/am";
	}

	@ResponseBody
	@RequestMapping(value = "/setAM", method = RequestMethod.POST)
	public Resp<?> importAmAndCheck(HttpServletRequest request) {
		Resp<String> resp = new Resp<String>();
		String error = null;
		String title = C.notEmpty(request.getParameter("title"));
		String json = C.notEmpty(request.getParameter("bjson"));
		if(title == null)
			error = "请填写标题！";
		else if(json == null)
			error = "自动机数据不能为空！";
		if(error != null){
			resp.setReason(error);
			return resp;
		}
		Automata am = new Automata();
		am.setTitle(title);
		if(C.isNotEmpty(request.getParameter("id"))){
			int id = Integer.parseInt(request.getParameter("id"));
			am.setId(id);
		}
		am.setDescription(C.notEmpty(request.getParameter("description")));
		//error = resourceService.addAutomata(am, json);
		if(error != null){
			resp.setReason(error);
		}else{
			resp.setError_code(0);
			resp.setReason("创建成功");
			List<String> ls = new ArrayList<String>(1);
			ls.add(am.getId() + "");
			resp.setResult(ls);
		}
		return resp;
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
