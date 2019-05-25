package com.woniu.controller;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.woniu.entity.Permission;
import com.woniu.entity.Role;
import com.woniu.entity.User;
import com.woniu.entity.UserRole;
import com.woniu.service.RoleService;
import com.woniu.service.UserRoleService;
import com.woniu.service.UserService;
import com.woniu.util.Static;
@Controller
public class UserRoleController {
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private UserRoleService userRoleService;
	@RequestMapping("userrole/userroles")
    @RequiresPermissions("/userrole/userroles")
	public ModelAndView userroles(HttpSession session,Integer pid, Integer uid) throws Exception{
		ModelAndView mv=new ModelAndView();
		mv.setViewName("page/userrole/userrole");
		//1查询按钮
		 List<Permission>  permissions= (List<Permission>) session.getAttribute(Static.PERMISSIONS);
		 List<Permission>  buttons= new ArrayList<Permission>();
		 for(Permission permission: permissions) {
			 if(permission.getPid()==pid) {
				 buttons.add(permission);
			 }
		 }
		//查询所有的用户列表
		List<User> users = userService.select();
				if(uid!=null) {
					//如果uid不为空
					//查询所有的角色
					List<Role> roles=roleService.list();
					//查询uid用户所拥有的角色
					List<UserRole> userRoles = userRoleService.select(uid);
					mv.addObject("roles", roles);
					mv.addObject("userRoles", userRoles);
				}
				mv.addObject("users", users);
				mv.addObject("pid",pid);
				mv.addObject("uid",uid);
				mv.addObject("buttons", buttons);
				return mv;
	}
	@RequestMapping("userrole/save")
	@RequiresPermissions("/userrole/save")
	public ModelAndView save(Integer uid,Integer[] rid) throws Exception {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("page/success");
		userRoleService.save(uid, rid);
		return mv;
	}	
}
