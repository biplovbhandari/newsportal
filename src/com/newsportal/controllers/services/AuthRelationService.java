package com.newsportal.controllers.services;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.newsportal.model.auth.AuthRelation;
import com.newsportal.model.auth.AuthUser;
import com.newsportal.utils.SessionBuilder;
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
	
	@SuppressWarnings("unchecked")
	public List<AuthRelation> retrieveUserRole(AuthUser authUser) {
		
		Long userId = authUser.getId();
		List<AuthRelation> authRole = null;
		Session session = SessionBuilder.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			tx.begin();
			authRole = (List<AuthRelation>) dao.findByParam(session, "userId", userId, false);
		} catch(RuntimeException e) {
			tx.rollback();
			LOG.severe(e.getMessage());
		} finally {
			session.close();
		}
		return authRole;
	}
}
