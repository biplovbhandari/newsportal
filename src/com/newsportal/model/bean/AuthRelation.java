package com.newsportal.model.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "auth_relation")
public class AuthRelation implements Serializable {

	private static final long serialVersionUID = 1L;

	// Fields
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private AuthUser userId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id")
	private AuthRole roleId;
	
	//@ToDo: Add person id
	
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
	
	/**
	 * Constructors
	 */
	public AuthRelation() {
		
	}
	
	public AuthRelation(AuthUser authUser, AuthRole authRole) {
		
		this.userId = authUser;
		this.roleId = authRole;
	}
	
	/**
	 * Getters and Setters
	 */

	/**
	 * @return the userId
	 */
	public AuthUser getUserId() {
		return userId;
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
	 * @param userId the userId to set
	 */
	public void setUserId(AuthUser userId) {
		this.userId = userId;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
