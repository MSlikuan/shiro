package com.woniu.service;

import java.util.List;

import com.woniu.entity.RP;

public  interface  RPSerivece {
	public List<RP>  rpList(int rid) throws Exception;
	public void save(int rid,Integer[] pids) throws Exception;
}
