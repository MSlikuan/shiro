package com.woniu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woniu.entity.User;
import com.woniu.entity.UserRole;
import com.woniu.entity.UserRoleExample;
import com.woniu.mapper.UserRoleMapper;
import com.woniu.service.UserRoleService;
@Service
public class UserRoleServiceImpl implements UserRoleService{
    
	@Resource
	private UserRoleMapper userRoleMapper;
	
	@Override
	public List<UserRole> select(int uid) throws Exception{
		UserRoleExample example=new UserRoleExample();
		example.createCriteria().andUidEqualTo(uid);
		return userRoleMapper.selectByExample(example);
	}
	
	
	
	@Transactional
	@Override
	public void save(Integer uid, Integer[] rid) throws Exception {
		UserRoleExample  ex  =new UserRoleExample();
		ex.createCriteria().andUidEqualTo(uid);
		userRoleMapper.deleteByExample(ex);
		if(rid!=null) {
		for(Integer id: rid) {
			 UserRole userRole = new UserRole();
			 userRole.setRid(id);
			 userRole.setUid(uid);
			 userRoleMapper.insert(userRole) ;
		}		
	}
	}
   
}
