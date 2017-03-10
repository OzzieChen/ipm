package net.xssu.ipm.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.xssu.ipm.json.Resp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

	@RequestMapping(value = "/")
	public String index(HttpServletRequest request, Model model) {
		model.addAttribute("myurl", "/import");
		return "index";
	}

	// 页面出错
	@RequestMapping(value = "/errors/{errCode}")
	public String errorOccur(HttpServletRequest req, HttpServletResponse resp, Model model, @PathVariable("errCode") String errCode) {
		int code = Integer.parseInt(errCode);
		String msg = "";
		if(code == 404)
			msg = "您访问的页面不存在";
		else if(code == 401)
			msg = "您没有权限访问该页面";
		else if(code == 500)
			msg = "您访问的页面出错了";
		else if(code == 400)
			msg = "您访问的页面参数错误";
		resp.setStatus(200);
		if(req.getMethod().equals("POST")){
			model.addAttribute("json", "{error_code : 1,reason : \"" + msg + "\", result:null}");
			return "json";
		}
		model.addAttribute("code", code);
		model.addAttribute("error", msg);
		return "error";
	}

	/**
	 * @Description: 上传文本文件的POST处理
	 * @param request
	 * @return Resp<?> @ResponseBody注解会使Resp<?>自动转为json传到前端
	 */
	@ResponseBody
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public Resp<?> importPOST(HttpServletRequest request, @RequestParam("file") MultipartFile fileUp, HttpServletResponse response) {
		Resp<?> resp = new Resp<String>();
		if(fileUp == null || fileUp.isEmpty()){
			resp.setReason("上传文件为空或没有数据");
			return resp;
		}
		String rootPath = request.getServletContext().getRealPath("/");
		File fileServer = null;
		try{
			File dir = new File(rootPath + "\\txts\\");
			if(!dir.exists()){
				dir.mkdir();
			}
			fileServer = new File(rootPath + "\\txts\\" + (new Random().nextInt(100000) + 100000) + fileUp.getOriginalFilename());
			fileUp.transferTo(fileServer);
			//fileServer.delete();
			resp.setError_code(0);
			resp.setReason("上传成功，文件名为  " + fileUp.getOriginalFilename());
		}catch(Exception e){
			e.printStackTrace();
			resp.setReason("出错了，请稍后再试");
		}
		return resp;
	}

	/**
	 * @Description: 上传文本的POST处理
	 * @param request
	 * @return Resp<?> @ResponseBody注解会使Resp<?>自动转为json传到前端
	 */
	@ResponseBody
	@RequestMapping(value = "/import2", method = RequestMethod.POST)
	public Resp<?> importPOST2(HttpServletRequest request) {
		Resp<?> resp = new Resp<String>();
		String fname = request.getParameter("fname");
		if(request.getParameter("txt") == null){
			resp.setReason("文本不能为空！");
			return resp;
		}
		String rootPath = request.getServletContext().getRealPath("/");
		File fileServer = null;
		try{
			File dir = new File(rootPath + "\\txts\\");
			if(!dir.exists()){
				dir.mkdir();
			}
			fileServer = new File(rootPath + "\\txts\\" + ((fname != null && !fname.equals("")) ? fname + ".txt" : System.currentTimeMillis() + ".txt"));
			PrintWriter pw = new PrintWriter(new FileOutputStream(fileServer));
			pw.write(request.getParameter("txt"));
			//fileServer.delete();
			pw.flush();
			pw.close();
			resp.setError_code(0);
			resp.setReason("上传成功，文件名为  " + fileServer.getName());
		}catch(Exception e){
			e.printStackTrace();
			resp.setReason("出错了，请稍后再试");
		}
		return resp;
	}
}
