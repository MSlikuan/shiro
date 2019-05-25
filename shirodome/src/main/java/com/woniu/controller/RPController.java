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
import com.woniu.entity.RP;
import com.woniu.entity.Role;
import com.woniu.service.PermissionService;
import com.woniu.service.RPSerivece;
import com.woniu.service.RoleService;
import com.woniu.util.Static;

@Controller
public class RPController {
	
	@Resource
	private RoleService roleService;
	@Resource
	private RPSerivece rpService;
	@Resource
	private PermissionService permissionService;
	
   @RequestMapping("rp/rp")
   @RequiresPermissions("/rp/rp")
   public ModelAndView Rp(HttpSession seesion,Integer rid,Integer pid) throws Exception {
	   //1.查询按钮
	  ModelAndView mv=new ModelAndView();
	  mv.setViewName("page/rp/rp");
	   List<Permission> buttons=new ArrayList<Permission>();
	   List<Permission> permissions=(List<Permission>) seesion.getAttribute(Static.PERMISSIONS);
	   for(Permission pe:permissions) {
		   if(pe.getPid()==pid) {
			   buttons.add(pe);
		   }
	   }
	   //2 查询角色
	     List<Role> roles = roleService.list();
	   //3.查询角色拥有的权限
	     
	     if(rid!=null) {
				//3.查询所有权限信息
				List<Permission> permissionList = permissionService.list();
				//定义集合保存分级之后的权限集合
				List<Permission> list=new ArrayList<Permission>();
				for(Permission level1:permissionList) {
					if(level1.getParentId()==0) {
						list.add(level1);
					}
				}
				for(Permission level2:permissionList) {
					if(level2.getParentId()!=0&&level2.getType()==0) {
						//找到二级菜单的父菜单
						for(Permission level1:list) {
							if(level1.getPid()==level2.getParentId()) {
								level1.getChilds().add(level2);
								break;
							}
						}
					}
				}
				for(Permission level3:permissionList) {
					if(level3.getParentId()>0&&level3.getType()>0){
						for(Permission level1:list) {
							for(Permission level2:level1.getChilds()) {
								if(level2.getPid()==level3.getParentId()) {
									level2.getChilds().add(level3);
									break;
								}
							}
						}
					}
				}
				//4.查询rid这个角色拥有的权限信息
				List<RP> rps=rpService.rpList(rid);
				mv.addObject("permissionList", list);
				mv.addObject("rps", rps);
			}
	    
	        mv.addObject("roles", roles);
			mv.addObject("rid", rid);
			mv.addObject("pid", pid);
			mv.addObject("buttons", buttons);
			mv.setViewName("page/rp/rp");
			return mv;
	   
   }
   @RequestMapping("/rp/save")
	@RequiresPermissions("/rp/save")
	public String save(Integer rid,Integer[] pid) throws Exception{
		rpService.save(rid, pid);
		return "page/success";
	}
}
