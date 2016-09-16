package com.newsportal.controllers.services;

import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.newsportal.model.auth.AuthUser;
import com.newsportal.controllers.dao.AuthUserDAO;
import com.newsportal.utils.SessionBuilder;

public class AuthUserService extends Service<AuthUser>{

	private static final Logger LOG = Logger.getLogger(AuthUserService.class.getName());
	
	private static AuthUserService authUserService;

	private AuthUserService() {
		super(AuthUserDAO.get(), LOG);
	}
	
	public static AuthUserService get() {
		
		if (authUserService == null) {
			authUserService = new AuthUserService();
		}
		return authUserService;
	}
	
	public AuthUser getUserById(Long id) {
		
		AuthUser authUser = null;
		Session session = SessionBuilder.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			tx.begin();
			authUser = dao.findById(session, id);
			//if (authUser != null) {
				
			//	AuthRole role = RoleDAO.get().findById(session, authUser.getAuthRole().getId());
			//	authUser.setAuthRole(role.getRole());
			//}
			
			
		} catch(RuntimeException e) {
			tx.rollback();
			LOG.severe(e.getMessage());
		} finally {
			session.close();
		}
		return authUser;
	}
	
	public AuthUser getUserByEmail(String email) {
		
		return getByParam("email", email);
	}
}
