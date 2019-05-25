package com.woniu.entity;

import java.util.ArrayList;
import java.util.List;

public class Permission {
	@Override
	public String toString() {
		return "Permission {pid=" + pid + ", name=" + name + ", resource=" + resource + ", parentId=" + parentId
				+ ", level=" + level + ", type=" + type + "}";
	}

	private List<Permission> childs=new ArrayList<Permission>();
	
    public List<Permission> getChilds() {
		return childs;
	}

	public void setChilds(List<Permission> childs) {
		this.childs = childs;
	}

	private Integer pid;

    private String name;

    private String resource;

    private Integer parentId;

    private Integer level;

    private Integer type;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource == null ? null : resource.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}