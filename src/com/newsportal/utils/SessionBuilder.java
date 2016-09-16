package com.newsportal.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionBuilder {
	
	private static final Configuration configuration = configuration();
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
        	// Create the SessionFactory from hibernate.cfg.xml
            //return new AnnotationConfiguration().configure().buildSessionFactory();
        	
        	// load from different directory
			SessionFactory sessionFactory = configuration.buildSessionFactory();
			return sessionFactory;
			
		} catch(Throwable e) {
			System.err.println("build SessionFactory Error: " + e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	private static Configuration configuration() {
		try {
			return new Configuration().configure("hibernate.cfg.xml");
					
		} catch(Throwable e) {
			System.err.println("Configuration creation failed: " + e);
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static Configuration getConfiguration() {
		return configuration;
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static void closeConnection() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
}
