package com.woniu.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.woniu.service.PermissionService;
import com.woniu.service.UserService;

@Controller
public class UserController {
	@Resource
	private UserService userService;
	@Resource
	private PermissionService permissionService;

	@RequestMapping("login")
	public ModelAndView login(String username, String password) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("username", username);
		// 封装用户名和密码信息
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		// 判断当前主体是否通过认证
		if (subject.isAuthenticated() == false) {
			// 执行Shiro的登陆认证
			subject.login(token);
		}
		// mv.addObject("permissionList", permissionList);
		// 查询当前用户的一级和二级菜单
		mv.setViewName("redirect:/index");
		return mv;
	}

	// 测试权限
	@RequestMapping("user/users")
	@RequiresPermissions(value = { "/user/users" })
	public ModelAndView insert(int pid,HttpSession session) {
		 ModelAndView  mv =new ModelAndView();
		 mv.setViewName ("page/user/users");
		return mv;
	}
}
