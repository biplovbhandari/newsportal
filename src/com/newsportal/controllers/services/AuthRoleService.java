package com.newsportal.controllers.services;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.newsportal.model.auth.AuthRole;
import com.newsportal.model.auth.AuthUser;
import com.newsportal.controllers.dao.AuthRoleDAO;
import com.newsportal.utils.SessionBuilder;

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
	
	@SuppressWarnings("unchecked")
	public List<AuthRole> retrieveUserRole(AuthUser authUser) {
		
		Long userId = authUser.getId();
		List<AuthRole> authRole = null;
		Session session = SessionBuilder.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			tx.begin();
			authRole = (List<AuthRole>) dao.findByParam(session, "id", userId, false);
		} catch(RuntimeException e) {
			tx.rollback();
			LOG.severe(e.getMessage());
		} finally {
			session.close();
		}
		return authRole;
	}
}
