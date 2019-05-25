package com.woniu.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

public class Role {
	
	@Override
	public String toString() {
		return "Role {rid=" + rid + ", name=" + name + ", permissions=" + permissions + "}";
	}
	 private List<Permission> permissions;
    public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	private Integer rid;
    @NotNull(message="角色名称不能为空")
    private String name;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}