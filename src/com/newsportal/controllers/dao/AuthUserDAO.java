package com.newsportal.controllers.dao;

import com.newsportal.model.auth.AuthUser;

public class AuthUserDAO extends DAO<AuthUser>{

	private static AuthUserDAO instance;

	private AuthUserDAO() {
		super(AuthUser.class);
	}
	
	public static AuthUserDAO get() {
		if (instance == null) {
			instance = new AuthUserDAO();
		}
		return instance;
	}
}
