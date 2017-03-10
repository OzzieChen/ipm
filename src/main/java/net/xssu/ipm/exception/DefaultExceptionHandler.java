package net.xssu.ipm.exception;

import javax.inject.Inject;

import net.xssu.ipm.service.ResourceService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author bonult
 * @description 通用异常处理器
 * 
 */
@ControllerAdvice
public class DefaultExceptionHandler {

	@Inject
	ResourceService resourceService;

	@ExceptionHandler({ IndexOutOfBoundsException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ModelAndView processUnauthenticatedException(NativeWebRequest request, IndexOutOfBoundsException e) {
		ModelAndView model = new ModelAndView();
		e.printStackTrace();
		if("div".equals(request.getHeader("OpenWay"))){
			model.setViewName("errors/div-404");
		}else
			model.setViewName("errors/404");
		return model;
	}
}
