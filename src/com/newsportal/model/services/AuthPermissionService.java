package com.newsportal.model.services;

import java.util.logging.Logger;

import com.newsportal.model.bean.AuthPermission;
import com.newsportal.model.dao.AuthPermissionDAO;

public class AuthPermissionService extends Service<AuthPermission> {

	private static final Logger LOG = Logger.getLogger(AuthPermissionService.class.getName());
	
	private static AuthPermissionService authPermissionService;

	private AuthPermissionService() {
		super(AuthPermissionDAO.get(), LOG);
	}
	
	public static AuthPermissionService get() {
		
		if (authPermissionService == null) {
			authPermissionService = new AuthPermissionService();
		}
		return authPermissionService;
	}
}
