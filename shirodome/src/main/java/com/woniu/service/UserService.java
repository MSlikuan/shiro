package com.woniu.service;

import java.util.List;

import com.woniu.entity.User;

public interface UserService {
	public User selectUserInfo(String username) throws Exception;
	
	
	 List<User> user()  throws Exception;
	public List<User> select() throws Exception;
}
