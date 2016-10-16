package com.newsportal.utils;

import java.io.Serializable;

public class LoadResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	// if the load was successful
	private boolean success = true;

	// total number of records returned
	private int totalRecord;

	// returned records
	private T record;

	// Constructors
	public LoadResult() {
		
	}

	public LoadResult(T record) {
		this.record = record;
		this.success = true;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the totalRecord
	 */
	public int getTotalRecord() {
		return totalRecord;
	}

	/**
	 * @param totalRecord the totalRecord to set
	 */
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	/**
	 * @return the record
	 */
	public T getRecord() {
		return record;
	}

	/**
	 * @param record the record to set
	 */
	public void setRecord(T record) {
		this.record = record;
	}
	
}
