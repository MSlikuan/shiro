package com.woniu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

import com.woniu.entity.Permission;
import com.woniu.entity.Role;
import com.woniu.exception.sellExcption;
import com.woniu.service.PermissionService;
import com.woniu.service.RoleService;
import com.woniu.util.Static;

@Controller
public class RoleController {
	@Resource
	private RoleService roleService;
	@Resource
	private PermissionService permissionService;

	@RequestMapping("role/roles")
	@RequiresPermissions("/role/roles")
	public ModelAndView role(HttpSession session,int pid) throws Exception {
		List<Role> roles = roleService.list();
		List<Permission> permissions=(List<Permission>)session.getAttribute(Static.PERMISSIONS);
		List<Permission> buttons=new ArrayList<Permission>();
		for(Permission button:permissions) {
			if(button.getParentId()==pid) {
				buttons.add(button);
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("buttons", buttons);
		mv.addObject("roles", roles);
		mv.setViewName("page/role/roles");
		return mv;
	}
   
	
	@RequestMapping("role/repupdate")
	@RequiresPermissions("/role/roles")
	public ModelAndView selectById(Integer rid) throws Exception {
		if (rid == null) {
			throw new sellExcption("用户id不能为空");
		}
		Role role = roleService.selectById(rid);
		ModelAndView mv = new ModelAndView();
		mv.addObject("role", role);
		mv.setViewName("page/role/update");
		return mv;
	}

	@RequestMapping("role/update")
	@RequiresPermissions("/role/roles")
	public ModelAndView updaterole(@Validated Role role, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			throw new sellExcption("参数错误");
		}
		roleService.repUpdate(role);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/success");
		return mv;
	}

	@RequestMapping("role/delete")
	@RequiresPermissions("/role/delete")
	public ModelAndView deleterole(Integer rid) throws Exception {
		if (rid == null) {
			throw new sellExcption("用户id不能为空");
		}
		roleService.deleteById(rid);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/success");
		return mv;
	}

	@RequestMapping("role/insert")
	@RequiresPermissions("/role/insert")
	public ModelAndView insertrole(@Validated Role role, BindingResult roleResult) throws Exception {
		if (roleResult.hasErrors()) {
			throw new sellExcption("参数错误");
		}
	    
		roleService.insert(role);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/success");
		return mv;
	}
	@RequestMapping("role/reinsert")
	@RequiresPermissions("/role/insert")
	public ModelAndView insert() throws Exception {
		
		
		return new ModelAndView("redirect:/page/role/insert.html");
	  
  }
}
