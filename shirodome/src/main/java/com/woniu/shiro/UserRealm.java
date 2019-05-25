package com.woniu.shiro;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import com.woniu.entity.Permission;
import com.woniu.entity.Role;
import com.woniu.entity.User;
import com.woniu.service.PermissionService;
import com.woniu.service.UserService;
import com.woniu.util.Static;

public class UserRealm extends AuthorizingRealm {
	@Resource
	private UserService userService;
	@Resource
	private PermissionService permissionService;

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User user = (User) principals.getPrimaryPrincipal();
		// 封装权限信息
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		for (Role role : user.getRoles()) {
			info.addRole(role.getName());
			for (Permission permission : role.getPermissions()) {
				info.addStringPermission(permission.getResource());

			}
		}
		return info;
	}

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取用户的用户名执行查询
		String username = (String) token.getPrincipal();
		// 执行查询
		try {
			User user = userService.selectUserInfo(username);
			System.out.println("认证用户:" + user);
			if (user != null) {
				SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
				Session session = SecurityUtils.getSubject().getSession();
				session.setAttribute(Static.USER, user);
				// 查询用户的一级菜单放入集合
				List<Permission> list = permissionService.selectByID(user.getUid());
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
				
				List<Permission> permissions=new ArrayList<Permission>();
				for(Role role:user.getRoles()) {
					for(Permission permission:role.getPermissions()) {
						boolean flag=false;
						for(int i=0;i<permissions.size();i++) {
							if(permissions.get(i).getPid()==permission.getPid()) {
								flag=true;
								break;
							}
						}
						if(flag==false) {
							permissions.add(permission);
						}
					}
				}
				
				 
				session.setAttribute(Static.PERMISSIONS, permissions);
				session.setAttribute(Static.MENUS, permissionList);
				return info;
			}
		} catch (Exception e) {
			throw new AuthenticationException();
		}
		return null;
	}

}
