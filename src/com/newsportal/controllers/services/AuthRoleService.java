package com.newsportal.controllers.services;

import java.util.logging.Logger;

import com.newsportal.model.auth.AuthRole;
import com.newsportal.controllers.dao.AuthRoleDAO;

public class AuthRoleService extends Service<AuthRole>{

	private static final Logger LOG = Logger.getLogger(AuthRoleService.class.getName());
	
	private static AuthRoleService authRoleService;

	private AuthRoleService() {
		super(AuthRoleDAO.get(), LOG);
	}
	
	public static AuthRoleService get() {
		
		if (authRoleService == null) {
			authRoleService = new AuthRoleService();
		}
		return authRoleService;
	}
}
