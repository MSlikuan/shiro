package com.woniu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woniu.entity.User;
import com.woniu.mapper.UserMapper;
import com.woniu.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper userMapper;

	@Override
	public User selectUserInfo(String username) throws Exception {
		return userMapper.selectUserInfo(username);
	}



	@Override
	public List<User> select() throws Exception {
		return userMapper.selectByExample(null);
	}



	@Override
	public List<User> user() throws Exception {
		
		return  userMapper.selectusers();
	}
   
}
