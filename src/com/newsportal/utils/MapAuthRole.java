package com.newsportal.utils;

public class MapAuthRole {

	private Long roleId;
	private String roleName;
	
	public MapAuthRole() {
		
	}
	
	public MapAuthRole(Long roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}
	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
