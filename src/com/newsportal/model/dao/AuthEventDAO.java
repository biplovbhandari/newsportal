package com.newsportal.model.dao;

import com.newsportal.model.bean.AuthEvent;

public class AuthEventDAO extends DAO<AuthEvent> {

	private static AuthEventDAO instance;

	private AuthEventDAO() {
		super(AuthEvent.class);
	}
	
	public static AuthEventDAO get() {
		if (instance == null) {
			instance = new AuthEventDAO();
		}
		return instance;
	}
	 
}
