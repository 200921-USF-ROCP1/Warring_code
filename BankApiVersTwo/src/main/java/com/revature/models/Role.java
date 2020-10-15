package com.revature.models;

public class Role {
	private int roleId;
	private String role;
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != Role.class) return false;
		Role role = (Role) obj;
		return this.roleId == role.getRoleId() && this.role.equals(role.getRole());
	}
	
	public String toString() {
		return role;
	}
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}