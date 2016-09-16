package com.newsportal.model.services;

import java.util.logging.Logger;

import com.newsportal.model.bean.AuthEvent;
import com.newsportal.model.dao.AuthEventDAO;

public class AuthEventService extends Service<AuthEvent> {

	private static final Logger LOG = Logger.getLogger(AuthEventService.class.getName());
	
	private static AuthEventService authEventService;

	private AuthEventService() {
		super(AuthEventDAO.get(), LOG);
	}
	
	public static AuthEventService get() {
		
		if (authEventService == null) {
			authEventService = new AuthEventService();
		}
		return authEventService;
	}
}
