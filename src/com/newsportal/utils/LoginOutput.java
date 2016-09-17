package com.newsportal.utils;

import java.util.List;

import com.newsportal.model.auth.AuthRelation;

public class LoginOutput {

	private boolean loggedIn = false;
	private Long userId;
	private String username;
	private String fullname;
	private String apiKey;
	private String message;
	private List<AuthRelation> role;
	private List<Long> roleId;
	private List<String> permissions;
	/**
	 * @return the roleId
	 */
	public List<Long> getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(List<Long> roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return the loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}
	/**
	 * @param loggedIn the loggedIn to set
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}
	/**
	 * @param fullname the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}
	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the permissions
	 */
	public List<String> getPermissions() {
		return permissions;
	}
	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
	/**
	 * @return the role
	 */
	public List<AuthRelation> getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRelation(List<AuthRelation> role) {
		this.role = role;
	}
	
}
