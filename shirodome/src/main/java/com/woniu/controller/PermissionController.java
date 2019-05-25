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
import com.woniu.service.PermissionService;
import com.woniu.util.Page;
import com.woniu.util.Static;

@Controller
public class PermissionController {
	@Resource
	private PermissionService pe;
	
	@RequestMapping("permission/permission")
	@RequiresPermissions("/permission/permission")
	public ModelAndView permission(HttpSession session, Integer pid, Integer pageIndex) throws Exception {
		ModelAndView mv = new ModelAndView();
		// 1 查询按钮
		List<Permission> permissions = (List<Permission>) session.getAttribute(Static.PERMISSIONS);
		List<Permission> buttons = new ArrayList<Permission>();
		for (Permission button : permissions) {
			if (button.getParentId() == pid) {
				buttons.add(button);
			}
		}
		// 2 查询权限
		int num = 6;
		long count = pe.count();
		if (pageIndex == null) {
			pageIndex = 1;
		}
		List<Permission> perlist = pe.pagelist(pageIndex, num);
		mv.addObject("perlist", perlist);
		// 3分页
		Page page = new Page();
		page.setCount(count);
		page.setNum(num);
		page.setPageIndex(pageIndex);
		page.setPageCount(count % num == 0 ? count / num : count / num + 1);
		// 4存储对象
		mv.addObject("buttons", buttons);
		mv.addObject("pid", pid);
		mv.addObject("page", page);
		mv.setViewName("page/permission/permission");
		return mv;
	}
	
    @RequestMapping("permission/reinsert")
    @RequiresPermissions("/permission/insert")
    public ModelAndView insert() throws Exception {
    	
    	
		ModelAndView mv=new ModelAndView();
		mv.addObject("menus",perlist());
		mv.setViewName("page/permission/insert");
		
		return mv;	
}
    
    @RequestMapping("permission/save") 
    @RequiresPermissions("/permission/permission")
    public ModelAndView save(Permission per) throws Exception {
    	 if(per.getResource()=="") {
    		 per.setResource("/page/error");
    	 }
    	 pe.insert(per);
    	 ModelAndView mv =new ModelAndView(); 
    	 mv.setViewName("page/success");
    	 return mv;
    }
    
    @RequestMapping("permission/delete") 
    @RequiresPermissions("/permission/delete")
    public ModelAndView delete(Integer pid) throws Exception {
    	 System.out.println(pid);
    	 pe.delete(pid);
    	 ModelAndView mv =new ModelAndView(); 
    	 mv.setViewName("page/success");
    	 return mv;
    }
    
    @RequestMapping("permission/update") 
    @RequiresPermissions("/permission/update")
    public ModelAndView update(Integer pid,Permission permission) throws Exception {
    	 
    	
    	 pe.update(permission);
    	 ModelAndView mv =new ModelAndView(); 
    	 mv.setViewName("page/success");
    	 return mv;
    }
    
    @RequestMapping("permission/repupdate") 
    @RequiresPermissions("/permission/update")
    public ModelAndView repupdate(Integer pid) throws Exception {
    	 
    	 Permission permission= pe.selectById(pid);
    	 ModelAndView mv =new ModelAndView(); 
    	 mv.addObject("permission",permission);
    	 mv.addObject("pid",pid);
    	 mv.addObject("menus",perlist());
    	 mv.setViewName("page/permission/update");
    	 return mv;
    }
    
    
    public  List<Permission> perlist() throws Exception{
    	List<Permission>  list =pe.list();
    	// 查询用户的一级菜单放入集
		List<Permission> permissionList = new ArrayList<Permission>();
		for (Permission per : list) {
			if (per.getParentId() == 0) {
				permissionList.add(per);
			}
		}
		// 查询用户的二级菜单放入集合
		for (Permission per : list) {
			if (per.getParentId() != 0 && per.getType() == 0) {
				// 便利permissionList 找到中当前二级菜单的父菜单
				for (Permission parentPer : permissionList) {
					if (parentPer.getPid() == per.getParentId()) {
						parentPer.getChilds().add(per);
						
						break;
					}
				}
				
			}
		}
		
		return permissionList;
    	
    }
    
}