package com.woniu.service;

import java.util.List;

import com.woniu.entity.Role;

public interface RoleService {

	List<Role> list();

	void repUpdate(Role role);
	
	Role selectById(int rid);
	
	void deleteById(int rid);
	
	void insert(Role role);
	
	void selectByName(String name);
}
