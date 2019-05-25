package com.woniu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woniu.entity.RP;
import com.woniu.entity.RPExample;
import com.woniu.mapper.RPMapper;
import com.woniu.service.RPSerivece;

@Service
public class RPServiceImpl implements RPSerivece {
	@Resource
	private RPMapper rpmapper;
	@Override
	public List<RP> rpList(int rid) throws Exception {
		RPExample example = new RPExample();
		example.createCriteria().andRidEqualTo(rid);
		List<RP> rplist = rpmapper.selectByExample(example);
		return rplist;
	}

	@Transactional
	@Override
	public void save(int rid, Integer[] pids) throws Exception {
		RPExample example = new RPExample();
		example.createCriteria().andRidEqualTo(rid);
		rpmapper.deleteByExample(example);
		for (int pid : pids) {
			RP rp = new RP();
			rp.setPid(pid);
			rp.setRid(rid);
			rpmapper.insert(rp);
		}
	}
}
