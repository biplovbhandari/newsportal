package com.newsportal.model.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AuthPermission {
	// Fields
	@Column(name="resource", length=16)
	private String resource;
	
	@Column(name="create_permission")
	private boolean createPermission;
	
	@Column(name="read_permission")
	private boolean readPermission;
	
	@Column(name="update_permission")
	private boolean updatePermission;
	
	@Column(name="delete_permission")
	private boolean deletePermission;

	/**
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

	/**
	 * @return the createPermission
	 */
	public boolean isCreatePermission() {
		return createPermission;
	}

	/**
	 * @param createPermission the createPermission to set
	 */
	public void setCreatePermission(boolean createPermission) {
		this.createPermission = createPermission;
	}

	/**
	 * @return the readPermission
	 */
	public boolean isReadPermission() {
		return readPermission;
	}

	/**
	 * @param readPermission the readPermission to set
	 */
	public void setReadPermission(boolean readPermission) {
		this.readPermission = readPermission;
	}

	/**
	 * @return the updatePermission
	 */
	public boolean isUpdatePermission() {
		return updatePermission;
	}

	/**
	 * @param updatePermission the updatePermission to set
	 */
	public void setUpdatePermission(boolean updatePermission) {
		this.updatePermission = updatePermission;
	}

	/**
	 * @return the deletePermission
	 */
	public boolean isDeletePermission() {
		return deletePermission;
	}

	/**
	 * @param deletePermission the deletePermission to set
	 */
	public void setDeletePermission(boolean deletePermission) {
		this.deletePermission = deletePermission;
	}
}