package com.woniu.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception ex) {
		ex.printStackTrace();
		ModelAndView mv = new ModelAndView();
		if (ex instanceof UnknownAccountException) {
			mv.addObject("message", "用户名不存在");
			mv.setViewName("page/login");
		} else if (ex instanceof IncorrectCredentialsException) {
			mv.addObject("message", "密码错误");
			mv.setViewName("page/login");
		} else if (ex instanceof UnauthorizedException) {
			mv.setViewName("page/unpermission");
		} else if(ex instanceof sellExcption){
			mv.addObject("message", ex.getMessage());
			mv.setViewName("page/error");
		}
		else {
			mv.addObject("message", "服务器未知错误");
			mv.setViewName("page/error");
		}
		return mv;
	}
}
