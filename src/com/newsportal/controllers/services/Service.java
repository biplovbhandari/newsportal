package com.newsportal.controllers.services;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.newsportal.utils.LoadResult;
import com.newsportal.utils.MapWhereQuery;
import com.newsportal.controllers.dao.DAO;
import com.newsportal.utils.SessionBuilder;


public abstract class Service<T> {
	
	protected DAO<T> dao;
	protected Logger log;
	
	protected Service (DAO<T> dao, Logger log) {
		this.dao = dao;
		this.log = log;
	}
	
	public T getByParam(String columnName, Object columnValue) {

		T bean = null;
		Session session = SessionBuilder.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			tx.begin();
			bean = dao.findByParam(session, columnName, columnValue, true);
			//if (bean != null) {
			//	prepareTransientData(session, bean);
			//}
			tx.commit();
			tx = null;
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			log.severe(e.getMessage());
		} finally {
			session.close();
		}
		return bean;
		
	}
	
	
	/**
	 * This method either saves or update the bean class of generic type T
	 * @author Biplov's
	 *
	 * @param <T> the class of type T
	 */
	public boolean addOrRetrieve(T bean) {
		
		boolean success = false;
		Session session = SessionBuilder.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			tx.begin();
			dao.saveOrUpdate(session, bean);
			tx.commit();
			success = true;
		} catch (RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			log.severe(e.getMessage());
		} finally {
			session.close();
		}
		
		return success;
	}

	public LoadResult<List<T>> loadData(int start, int limit,
			String order, boolean desc, String where, Integer deepLvl, List<MapWhereQuery> listMapQuery) {
		
		Session session = SessionBuilder.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		try{
			tx.begin();			
			List<T> list = dao.list(session, start, limit, order, desc, where, deepLvl, listMapQuery);
			//prepareTransientData(session, list);
			LoadResult<List<T>> loadResult = createLoadResult(list, list.size());
			tx.commit();
			return loadResult;
		} catch (RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			log.severe(e.getMessage());
		} finally {
			session.close();
		}
		return null;
	}

	// Create an instance of the LoadResult
	public LoadResult<List<T>> createLoadResult(List<T> list, int totalLength) {
		LoadResult<List<T>> result = new LoadResult<List<T>>(list);
		result.setTotalRecord(totalLength);
		return result;
	}
}
