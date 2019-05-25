package com.woniu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woniu.entity.Permission;
import com.woniu.entity.PermissionExample;
import com.woniu.entity.RPExample;
import com.woniu.mapper.PermissionMapper;
import com.woniu.mapper.RPMapper;
import com.woniu.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

	private static final boolean Permission = false;
	@Resource
	private PermissionMapper permapper;
	@Resource
	private RPMapper rp;

	@Override
	public List<Permission> selectByID(int uid) throws Exception {
	    return permapper.selectUserOneMenu(uid);
	}

	@Override
	public List<Permission> selectButton(int uid, int pid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("uid", uid);
		map.put("pid", pid);
		return null;
	//	return permapper.selectUserOneSubMenu(map);
		
	}

	@Override
	public List<Permission> list() throws Exception {
		return permapper.selectByExample(null);
	}

	@Override
	public long count() throws Exception {
		return permapper.countByExample(null);
	}

	@Override
	public List<Permission> pagelist(Integer pageIndex,int num) throws Exception {
		Integer start=((pageIndex-1)*num);
		 PermissionExample permissionExample = new PermissionExample();
		 permissionExample.setStart(start);
		 permissionExample.setNum(num);
		return permapper.selectByExample(permissionExample);
	}

	@Override
	public void insert(Permission permission) throws Exception {
	   
		permapper.insert(permission);
	}
	@Transactional
	@Override
	public void delete(Integer pid) throws Exception {
		   Permission pe=permapper.selectByPrimaryKey(pid);
		   //创建一个父级id为删除的id的条件
		   PermissionExample permissionExample = new PermissionExample();
		   //创建条件对象
		   permissionExample.createCriteria().andParentIdEqualTo(pid);
		   //查询该菜单下的所有子菜单或者是按钮
		   List<Permission> list = permapper.selectByExample(permissionExample);
		  //如果该菜单是一级菜单，且其子菜单不为空，则循环遍历二级子菜单下的按钮并将其删除
            if(pe.getLevel()==1&&list!=null) {
            	for(Permission level2:list) {
            		 PermissionExample ex3 = new PermissionExample();
                     ex3.createCriteria().andParentIdEqualTo(level2.getPid());
                     List<Permission> list3= permapper.selectByExample(ex3);
                     if(list3!=null) {
                    //删除权限记录表
                     for(Permission level3:list3) {
                     RPExample rp1 = new RPExample();
                     rp1.createCriteria().andPidEqualTo(level3.getPid());
                     rp.deleteByExample(rp1);
                     }
                     }
                     //删除3级按钮表
                     permapper.deleteByExample(ex3);    
            	}
            }
            // 如果该菜单还有子菜单 则将其删除
            if(list!=null) {
            	 //删除二级菜单下对应的角色权限记录
                for(Permission rp1:list) {
                	 RPExample rp2 = new RPExample();
                     rp2.createCriteria().andPidEqualTo(rp1.getPid());
                     rp.deleteByExample(rp2);
                }
                permapper.deleteByExample(permissionExample );
            }
           
            //删除一级菜单下的角色权限记录
            RPExample rp3 = new RPExample();
            rp3.createCriteria().andPidEqualTo(pe.getPid());
            rp.deleteByExample(rp3);
 		     //删除一级菜单
 		     permapper.deleteByPrimaryKey(pid);	   
	}
	@Override
	public Permission selectById(Integer pid) {
		return permapper.selectByPrimaryKey(pid);
	}
    @Transactional
	@Override
	public void update(Permission permission) {
    	
		permapper.updateByPrimaryKeySelective(permission);
	}

}
