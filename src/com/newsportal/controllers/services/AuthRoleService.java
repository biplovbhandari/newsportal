package com.newsportal.controllers.services;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.hibernate.Session;

import com.newsportal.model.auth.AuthRole;
import com.newsportal.utils.MapWhereQuery;
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
	
	public String getRoleName(Session session, Long roleId) {
		String where = "where bean.id='" + roleId.toString() + "'";
		AuthRole authRole = dao.list(session, 0, 1, null, false, where, 1, new ArrayList<MapWhereQuery>()).get(0);
		return authRole.getName();
	}
}
