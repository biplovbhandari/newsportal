package com.newsportal.controllers.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.hibernatespatial.GeometryUserType;

import com.newsportal.utils.MapWhereQuery;
import com.newsportal.utils.ReflectHelper;
import com.newsportal.utils.Utils;
import com.vividsolutions.jts.geom.Geometry;

public abstract class DAO<T> {
	
	HashMap<Integer,ReflectHelper> mapClass = null;
	HashMap<Class<?>,ReflectHelper> mapObjClass = null;
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(DAO.class.getName());
	
	protected Class<T> clazz;
	
	protected DAO(Class<T> clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public T findById(final Session session, final Long id) {
		String bodyQuery = "";
		String leftJoin = "";
		mapClass = new HashMap<Integer, ReflectHelper>();
		
		Integer deepLvl = 1;
		
		
		String[] strQueryAll = getQueriesFromClass(clazz,"bean.",deepLvl);
		bodyQuery = strQueryAll[0];
		leftJoin = strQueryAll[1];

		String qStr = "select " + bodyQuery.substring(1);
		qStr += " from " + clazz.getName() + " as bean " + leftJoin + " where bean.id= :id "; //bean.role.id as id0,
		
		System.out.println(qStr);
		Query query = session.createQuery(qStr);
		query.setParameter("id", id);
			
		List<T> list = new ArrayList<T>();
		List<Object[]> rows = query.list();
		T beanGlobal = null;
		for (Object[] row : rows) {
			Integer i = 0;
			T bean = null;
			try {
				bean = clazz.newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			mapObjClass = new HashMap<Class<?>,ReflectHelper>();
			for(Object column : row){
				setClassFromList(column,i);
				
				i++;
			}
			
			bean = (T) setBeanFromObj(clazz);
			list.add(bean);
			beanGlobal = bean;
		}
		
		return beanGlobal;
		//return (T) session.get(clazz, id);
	}
	
	@SuppressWarnings("unused")
	public String[] getQueriesFromClass(Class<?> clz,String className,Integer deepLvl){
		String bodyQuery = "";
		String leftJoin = "";
		String classNameWithoutDot = className.substring(0,className.length()-1);
		for(Method m : clz.getDeclaredMethods()){
			// Ignore setters method which has return type void
			if(!m.getReturnType().getName().equals("void")){
				String getterNameMethod = m.getName();
				String fieldName = "";
				if(getterNameMethod.substring(0,2).equals("is")){
					fieldName = getterNameMethod.substring(2,3).toLowerCase()+getterNameMethod.substring(3);
				}
				else{
					fieldName = getterNameMethod.substring(3,4).toLowerCase()+getterNameMethod.substring(4);
				}
				Field field = null;
				try {
					field = clz.getDeclaredField(fieldName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				String typeStr = Utils.getFieldType(field);

				Boolean isId = false;
				if (typeStr == "Long" || fieldName == "id") {
					isId = true;
				}
				String columnJoinName = "";
				Boolean includeInQuery = true;
				for(Annotation annotation : field.getAnnotations()){
					String annoName = annotation.toString();
					
					CharSequence cs1 = "JoinColumn";
					if(annoName.contains(cs1)){
						String[] splitColumn = annoName.split(",");
						for(String StrSplitColumn : splitColumn){
							cs1 = " name=";
							if(StrSplitColumn.contains(cs1)){
								String[] splitColumn1 = StrSplitColumn.split("=");
								columnJoinName = splitColumn1[1];
								break;
							}
						}
					}
					
					if(annoName.equals("@javax.persistence.Transient()")){
						includeInQuery = false;
					}
				}
				
				if(typeStr.equals("Set")){
					includeInQuery = false;
				}
				
				if(includeInQuery){
					ReflectHelper rh = new ReflectHelper();
					Boolean skipSelectTbl = false;
					if(!typeStr.equals("none")){
						String alias = classNameWithoutDot.replaceAll("\\.", "_");
						bodyQuery += ","+alias+"."+field.getName()+" ";
					}
					else{
						Class<?> c = null;
						try {
							c = Class.forName(m.getReturnType().getName());
							Constructor<?>[] ctors = c.getConstructors();
							for(Constructor<?> ctor: ctors) {
								
							    Object object = null;
							    Long valTemp = (long) 1;
						    	try{
						    		object = ctor.newInstance(new Object[] {valTemp });
						    		break;
						    	}
						    	catch(Exception e){
						    		try {
										object = ctor.newInstance(new Object[] {valTemp.toString() });
										break;
									} catch (Exception e1) {
										try {
											object = ctor.newInstance(new Object[] {});
											break;
										} catch (Exception e2) {
											// TODO Auto-generated catch block
											//e2.printStackTrace();
										} 
									
									} 
						    	}
							}
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			
						Integer currLvl = className.split("\\.").length;
						System.out.println("currLvl = "+currLvl);
//						String alias = classNameWithoutDot.replaceAll("\\.", "_");
//						alias = alias+"_"+field.getName();
//						leftJoin += "left join "+className+field.getName()+" "+alias+" ";
						if(currLvl-1 == deepLvl){	
							skipSelectTbl = true;
							//bodyQuery += ","+alias+".id";
							//isId = true;
						}
						else{
							String alias = classNameWithoutDot.replaceAll("\\.", "_");
							alias = alias+"_"+field.getName();
							leftJoin += "left join "+className+field.getName()+" "+alias+" ";
							
							String[] queryStrArr = getQueriesFromClass(c,className+field.getName()+".",deepLvl);
							bodyQuery += queryStrArr[0];
							leftJoin += queryStrArr[1];
							skipSelectTbl = true;
						}
						System.out.println("join = "+leftJoin);
					}
					if(!skipSelectTbl){
						rh.setFieldName(field.getName());
						rh.setField(field);
						rh.setMethod(m);
						rh.setCls(clz);
						rh.setIsId(isId);
						mapClass.put(mapClass.size(),rh );
					}
				}
			}
		}
		return new String[]{bodyQuery,leftJoin};
	}

	@SuppressWarnings("unchecked")
	public T findByParam(final Session session, final String columnName, final Object columnValue, Boolean filter) {
		List<T> list = getListByParam(session, columnName, columnValue);

		if(filter && (list.size() > 0)) {
			return list.get(0);
		} else {
			return (T) list;
		}
	}

	public List<T> getListByParam(final Session session, final String columnName, final Object columnValue) {
		String where = "where bean." + columnName + "='" + columnValue.toString() + "'";
		return list(session, 0, -1, "id", false, where, 1, new ArrayList<MapWhereQuery>());
	}

	@SuppressWarnings("unchecked")
	public List<T> list(final Session session, int start, int limit,
			String order, boolean desc, String where,Integer deepLvl,List<MapWhereQuery> listMapQuery) {

		String bodyQuery = "";
		String leftJoin = "";
		mapClass = new HashMap<Integer, ReflectHelper>();
		
		//deep lvl
		//lvl 0 , there is no heritance
		//lvl 1 , heritance deep lvl 1
		//lvl -1, all heritance
		String[] strQueryAll = getQueriesFromClass(clazz,"bean.",deepLvl);
		bodyQuery = strQueryAll[0];
		leftJoin = strQueryAll[1];

		String qStr = "select " + bodyQuery.substring(1);
		qStr += " from " + clazz.getName() + " as bean " + leftJoin + " "; //bean.role.id as id0,
		qStr += where;
		if (order != null) {
			qStr += " order by bean." + order;
			if (desc) {
				qStr += " desc";
			}
		} else {
			qStr += " order by bean.id";
		}
		System.out.println(qStr);
		Query query = session.createQuery(qStr);
		for(MapWhereQuery mapWhereQuery : listMapQuery){
			String typeStr = Utils.getFieldType(mapWhereQuery.getField());
			if(typeStr.equals("String")){
				query.setString(mapWhereQuery.getFieldName(), mapWhereQuery.getValue().toString());
			}
			else if(typeStr.equals("Long")){
				query.setParameter(mapWhereQuery.getFieldName(), Long.valueOf(mapWhereQuery.getValue().toString()));
			}
			else if(typeStr.equals("Int")){
				query.setParameter(mapWhereQuery.getFieldName(), Integer.valueOf(mapWhereQuery.getValue().toString()));
			}
			else if(typeStr.equals("Float")){
				query.setParameter(mapWhereQuery.getFieldName(), Float.valueOf(mapWhereQuery.getValue().toString()));
			}
			else if(typeStr.equals("Bool")){
				query.setParameter(mapWhereQuery.getFieldName(), Boolean.valueOf(mapWhereQuery.getValue().toString()));
			}
			else if(typeStr.equals("Geom")){
				Type geometryType = GeometryUserType.TYPE;
				query.setParameter(mapWhereQuery.getFieldName(), (Geometry)mapWhereQuery.getValue(),geometryType );
			}
			else{
				query.setParameter(mapWhereQuery.getFieldName(), Long.valueOf(mapWhereQuery.getValue().toString()));
			}		
		}
		
		if (start > 0) {
			query.setFirstResult(start);
		}
		if (limit > 0) {
			query.setMaxResults(limit);
		}
		
		List<T> list = new ArrayList<T>();
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			Integer i = 0;
			T bean = null;
			try {
				bean = clazz.newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			mapObjClass = new HashMap<Class<?>,ReflectHelper>();
			for(Object column : row){
				setClassFromList(column,i);
				
				i++;
			}
			
			bean = (T) setBeanFromObj(clazz);
			list.add(bean);
		}

		// list.size();
		return list;
	}

	@SuppressWarnings("rawtypes")
	public void setClassFromList(Object column,Integer i){
		ReflectHelper rh = mapClass.get(i);
		
		Boolean isId = rh.getIsId();
		Object objectBean = null;
		Class<?> clz = rh.getCls();
		Class[] paramGnrl = new Class[1];	
		paramGnrl[0] = rh.getMethod().getReturnType();

		if(clz.equals(clazz)){
			if(!mapObjClass.containsKey(clz)){
				try {
					ReflectHelper rhT = new ReflectHelper();
					rhT.setCls(clz);
					objectBean = clazz.newInstance();
					rhT.setObject(objectBean);
					mapObjClass.put(rh.getCls(), rhT);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			}
			else{
				ReflectHelper rhT = mapObjClass.get(clz);
				objectBean = rhT.getObject();
			}
		}
		else{
			if(!mapObjClass.containsKey(clz)){
				Object object = null;
				Constructor<?> ctor = clz.getConstructors()[0];
				
				Long valTemp = (long) 1;
				try{
					object = ctor.newInstance(new Object[] {valTemp });
				}
				catch(Exception e){
					try {
						object = ctor.newInstance(new Object[] {valTemp.toString() });
					} catch (Exception e1) {
						try {
							object = ctor.newInstance(new Object[] {});
						} catch (Exception e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} 
					} 
				}
				
				ReflectHelper rhT = new ReflectHelper();
				rhT.setCls(clz);
				objectBean = object;
				rhT.setObject(objectBean);
				mapObjClass.put(clz, rhT);
			}
			else{
				ReflectHelper rhT = mapObjClass.get(clz);
				objectBean = rhT.getObject();
			}
		}
		
		Method method = null;
		try {
			if(column != null){
				if(rh.getMethod().getReturnType().getName().equals("java.util.Date")){
					Boolean changeType = false;
					for(Annotation annotation : rh.getField().getAnnotations()){
						if(annotation.toString().equals("@javax.persistence.Temporal(value=DATE)")){
							changeType = true;
						}
					}
					if(changeType){
						paramGnrl[0] = String.class;
						column = column.toString();
					}
				}
				try{
					if(isId){
						Class[] paramLong = new Class[1];	
						paramLong[0] = Long.class;
						
						method = clz.getMethod(Utils.getSetterMethodName("Id"), paramLong);
						method.invoke(objectBean, Long.valueOf(column.toString()));
					}
					else{
						method = clz.getMethod(Utils.getSetterMethodName(rh.getFieldName()), paramGnrl);
						method.invoke(objectBean, column);
					}
				}
				catch(Exception E){
					E.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	public Object setBeanFromObj(Class<?> clz){
		Object obj = null;
		if(mapObjClass.containsKey(clz)){
			ReflectHelper rh = mapObjClass.get(clz);
			obj = rh.getObject();
			for(Method m : clz.getDeclaredMethods()){
				if(!m.getReturnType().getName().equals("void")){
					String getterNameMethod = m.getName();
					String fieldName = "";
					if(getterNameMethod.substring(0,2).equals("is")){
						fieldName = getterNameMethod.substring(2,3).toLowerCase()+getterNameMethod.substring(3);
					}
					else{
						fieldName = getterNameMethod.substring(3,4).toLowerCase()+getterNameMethod.substring(4);
					}
					Field field = null;
					try {
						field = clz.getDeclaredField(fieldName);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
					String typeStr = Utils.getFieldType(field);
					
					if(typeStr.equals("none")){
						Class[] paramGnrl = new Class[1];	
						paramGnrl[0] = m.getReturnType();
						
						Class<?> c = null;
						Object object = null;

						try {
							c = Class.forName(paramGnrl[0].getName());
							Constructor<?> ctor = c.getConstructors()[0];
						    
						    Long valTemp = (long) 1;
					    	try{
					    		object = ctor.newInstance(new Object[] {valTemp });
					    	}
					    	catch(Exception e){
					    		try {
									object = ctor.newInstance(new Object[] {valTemp.toString() });
								} catch (Exception e1) {
									try {
										object = ctor.newInstance(new Object[] {});
									} catch (Exception e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									} 
								
								} 
					    	}
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Method method = null;
						try {
							method = clz.getMethod(Utils.getSetterMethodName(field.getName()), paramGnrl);
							//method.invoke(obj, setBeanFromObj(c));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		return obj;
	}
	
	/**
	 * Either saves or updates the record
	 * 
	 * @param session session object
	 * @param bean
	 */
	public void saveOrUpdate(Session session, T bean) {
		
		if (bean != null) {
			Long id = null;
			try {
				// Retrieve the Id method
				Method m = clazz.getMethod("getId");
				if (m.getReturnType() == Integer.class) {
					Integer tempIdInt = (Integer) m.invoke(bean);
					id = tempIdInt.longValue();
				} else {
					id = (Long) m.invoke(bean);
				}
			} catch (Exception e) {
			}

			if (id == null) {
				//System.out.println("System process SAVE");
				session.save(bean);
			} else {

				//System.out.println("System process UPDATE");
				session.update(bean);
			}
		}
	}
}
