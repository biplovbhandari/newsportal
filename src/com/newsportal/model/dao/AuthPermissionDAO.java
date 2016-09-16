package com.newsportal.model.dao;

import com.newsportal.model.bean.auth.AuthPermission;

public class AuthPermissionDAO extends DAO<AuthPermission>{

	private static AuthPermissionDAO instance;

	private AuthPermissionDAO() {
		super(AuthPermission.class);
	}
	
	public static AuthPermissionDAO get() {
		if (instance == null) {
			instance = new AuthPermissionDAO();
		}
		return instance;
	}
}
