package com.newsportal.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectHelper {

	private String fieldName;
	private Field field;
	private Method method;
	private Class<?> cls;
	private Object object;
	private Boolean isId; 
	
	public ReflectHelper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Class<?> getCls() {
		return cls;
	}
	public void setCls(Class<?> cls) {
		this.cls = cls;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public Boolean getIsId() {
		return isId;
	}
	public void setIsId(Boolean isId) {
		this.isId = isId;
	}
	
}
