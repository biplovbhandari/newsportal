package com.newsportal.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "auth_permission")
public class AuthPermission implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Relationships Column
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="created_by")
	private AuthUser createdBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="approved_by")
	private AuthUser approvedBy;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="modified_by")
	private AuthUser modifiedBy;

	// Fields
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id")
	private AuthRole roleId;

	@Column(name= "module_name", length=16)
	private String moduleName;

	@Column(name="table_name", length=16)
	private String tableName;
	
	// Owner Permission should be something like this:
	// "CREATE, READ, UPDATE, DELETE, APPROVE, PUBLISH"
	@Column(name="owner_permission")
	private String ownerPermission;

	@Column(name="user_permission")
	private String userPermission;
	
	/**
	 * Constructors
	 */
	public AuthPermission() {
		
	}
	
	public AuthPermission(String moduleName, String ownerPermission, String userPermission, AuthRole roleId) {
		
		this.moduleName = moduleName;
		this.ownerPermission = ownerPermission;
		this.userPermission = userPermission;
		this.roleId = roleId;
	}
	
	/**
	 * @return the createdBy
	 */
	public AuthUser getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(AuthUser createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the approvedBy
	 */
	public AuthUser getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(AuthUser approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the modifiedBy
	 */
	public AuthUser getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(AuthUser modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the roleId
	 */
	public AuthRole getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(AuthRole roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the ownerPermission
	 */
	public String getOwnerPermission() {
		return ownerPermission;
	}

	/**
	 * @param ownerPermission the ownerPermission to set
	 */
	public void setOwnerPermission(String ownerPermission) {
		this.ownerPermission = ownerPermission;
	}

	/**
	 * @return the userPermission
	 */
	public String getUserPermission() {
		return userPermission;
	}

	/**
	 * @param userPermission the userPermission to set
	 */
	public void setUserPermission(String userPermission) {
		this.userPermission = userPermission;
	}
	
}