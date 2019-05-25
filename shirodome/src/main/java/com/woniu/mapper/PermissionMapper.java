package com.woniu.mapper;

import com.woniu.entity.Permission;
import com.woniu.entity.PermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionMapper {
	
	//根据用户编号查询菜单
	public List<Permission> selectUserOneMenu(int uid) throws Exception;
    long countByExample(PermissionExample example);
    
    void deletepaerentid(Integer parentId) throws Exception;
    List<Permission> selectpid(Integer pid) throws Exception;
    
    int deleteByExample(PermissionExample example);

    int deleteByPrimaryKey(Integer pid);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Integer pid);

    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByExample(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}