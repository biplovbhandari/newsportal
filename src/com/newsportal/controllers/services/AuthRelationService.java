package com.newsportal.controllers.services;

import java.util.logging.Logger;

import com.newsportal.model.auth.AuthRelation;
import com.newsportal.controllers.dao.AuthRelationDAO;

public class AuthRelationService extends Service<AuthRelation>{

	private static final Logger LOG = Logger.getLogger(AuthRelationService.class.getName());
	
	private static AuthRelationService authRelationService;

	private AuthRelationService() {
		super(AuthRelationDAO.get(), LOG);
	}
	
	public static AuthRelationService get() {
		
		if (authRelationService == null) {
			authRelationService = new AuthRelationService();
		}
		return authRelationService;
	}
}
