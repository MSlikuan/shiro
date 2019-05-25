package com.woniu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.woniu.entity.RPExample;
import com.woniu.entity.Role;
import com.woniu.entity.UserRoleExample;
import com.woniu.mapper.RPMapper;
import com.woniu.mapper.RoleMapper;
import com.woniu.mapper.UserRoleMapper;
import com.woniu.service.RoleService;



@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private UserRoleMapper urMapper;
	@Resource
	private RPMapper rpMapper;

	@Override
	public List<Role> list() {
		return roleMapper.selectByExample(null);
	}

	@Override
	@Transactional
	public void repUpdate(Role role) {
		roleMapper.updateByPrimaryKeySelective(role);
		
	}

	@Override
	@Transactional
	public Role selectById(int rid) {
		return roleMapper.selectByPrimaryKey(rid);
	}

	@Override
	@Transactional
	public void deleteById(int rid) {
		roleMapper.deleteByPrimaryKey(rid);
		UserRoleExample urExample=new UserRoleExample();
		urExample.createCriteria().andRidEqualTo(rid);
		urMapper.deleteByExample(urExample);
		RPExample rpExample=new RPExample();
		rpExample.createCriteria().andRidEqualTo(rid);
		rpMapper.deleteByExample(rpExample);
		
	}

	@Override
	@Transactional
	public void insert(Role role) {
		 roleMapper.insert(role);
	}

	@Override
	public void selectByName(String name) {
		roleMapper.equals(name);
		
	}

}
