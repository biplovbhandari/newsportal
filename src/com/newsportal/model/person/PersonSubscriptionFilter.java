package com.newsportal.model.person;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.newsportal.model.auth.AuthUser;
import com.newsportal.model.person.PersonSubscription;

@Entity
@Table(name = "person_subscription_filter")
public class PersonSubscriptionFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Relationships Column
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private AuthUser userId;

	@OneToOne(fetch=FetchType.LAZY, mappedBy="filterId")
	private PersonSubscription userSubscription;
	
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

	@Column(name="query", columnDefinition="TEXT")
	private String query;
	
	@Column(name="description", columnDefinition="TEXT")
	private String description;
	
	@Column(name="comments", columnDefinition="TEXT")
	private String comments;
	
	@Column(name = "uuid", unique = true)
	private String uuid = UUID.randomUUID().toString();
	
	@Column(name="deleted")
	private Boolean deleted = false;
	
	@Column(name="deleted_fk", length=512) // deleted foreign keys
	private String deletedFk;
	
	@Column(name="created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@Column(name="modified_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedOn = new Date();

	/**
	 * @return the userId
	 */
	public AuthUser getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(AuthUser userId) {
		this.userId = userId;
	}

	/**
	 * @return the userSubscription
	 */
	public PersonSubscription getUserSubscription() {
		return userSubscription;
	}

	/**
	 * @param userSubscription the userSubscription to set
	 */
	public void setUserSubscription(PersonSubscription userSubscription) {
		this.userSubscription = userSubscription;
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
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the deletedFk
	 */
	public String getDeletedFk() {
		return deletedFk;
	}

	/**
	 * @param deletedFk the deletedFk to set
	 */
	public void setDeletedFk(String deletedFk) {
		this.deletedFk = deletedFk;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the modifiedOn
	 */
	public Date getModifiedOn() {
		return modifiedOn;
	}

	/**
	 * @param modifiedOn the modifiedOn to set
	 */
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
}
