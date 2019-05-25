package com.woniu.service;

import java.util.List;

import com.woniu.entity.Permission;

public interface PermissionService {
	// 根据用户编号查询一级和二级菜单
	List<Permission> selectByID(int id) throws Exception;

	// 根据用户编号和二级菜单编号查询按钮权限
	public List<Permission> selectButton(int uid, int pid) throws Exception;
	//查询该表的所有数据
	public List<Permission> list() throws Exception;
	//查询有多少条数据
	long  count() throws Exception;
	// 分页查询
	public List<Permission> pagelist(Integer pageIndex,int num) throws Exception;
	//新增
	public void insert(Permission permission) throws Exception;
	//删除
	void delete(Integer pid)throws Exception;
    //根据pid查询单条数据
	Permission selectById(Integer pid);
    //更新数据
	void update(Permission permission);
	
	

}
