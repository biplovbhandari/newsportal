package com.newsportal.utils;

import java.lang.reflect.Field;

public class MapWhereQuery {

	private String fieldName;
	private Object value;
	private Field field;
	
	public MapWhereQuery() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
}
