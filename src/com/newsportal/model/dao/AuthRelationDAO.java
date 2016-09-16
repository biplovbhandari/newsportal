package com.newsportal.model.dao;

import java.util.logging.Logger;

import com.newsportal.model.bean.AuthRelation;

public class AuthRelationDAO extends DAO<AuthRelation>{

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(AuthRelationDAO.class.getName());
	private static AuthRelationDAO instance;

	private AuthRelationDAO() {
		super(AuthRelation.class);
	}
	
	public static AuthRelationDAO get() {
		if (instance == null) {
			instance = new AuthRelationDAO();
		}
		return instance;
	}
}