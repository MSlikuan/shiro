package com.woniu.service;

import java.util.List;

import com.woniu.entity.UserRole;

public interface UserRoleService {
   List<UserRole> select(int uid) throws Exception;
   void save(Integer uid,Integer[] rid) throws Exception;
}
