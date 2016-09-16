package com.newsportal.model.dao;

import java.util.logging.Logger;

import com.newsportal.model.bean.auth.AuthRole;

public class AuthRoleDAO extends DAO<AuthRole>{

	private static AuthRoleDAO instance;
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(AuthRelationDAO.class.getName());

	private AuthRoleDAO() {
		super(AuthRole.class);
	}

	public static AuthRoleDAO get() {
		if (instance == null) {
			instance = new AuthRoleDAO();
		}
		return instance;
	}

}
