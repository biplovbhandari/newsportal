package com.newsportal.model.news;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.newsportal.model.auth.AuthUser;

@Entity
@Table(name = "news_tag")
public class NewsTag implements Serializable {


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
	
	@Column(name="name", length=512)
	private String name;
	
	@Column(name="comments", columnDefinition="TEXT")
	private String comments;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="createdBy")
	private Set<AuthUser> createdByUsers = new LinkedHashSet<AuthUser>();

	@OneToMany(fetch=FetchType.LAZY, mappedBy="approvedBy")
	private Set<AuthUser> approvedByUsers = new LinkedHashSet<AuthUser>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="modifiedBy")
	private Set<AuthUser> modifiedByUsers = new LinkedHashSet<AuthUser>();
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the createdByUsers
	 */
	public Set<AuthUser> getCreatedByUsers() {
		return createdByUsers;
	}

	/**
	 * @param createdByUsers the createdByUsers to set
	 */
	public void setCreatedByUsers(Set<AuthUser> createdByUsers) {
		this.createdByUsers = createdByUsers;
	}

	/**
	 * @return the approvedByUsers
	 */
	public Set<AuthUser> getApprovedByUsers() {
		return approvedByUsers;
	}

	/**
	 * @param approvedByUsers the approvedByUsers to set
	 */
	public void setApprovedByUsers(Set<AuthUser> approvedByUsers) {
		this.approvedByUsers = approvedByUsers;
	}

	/**
	 * @return the modifiedByUsers
	 */
	public Set<AuthUser> getModifiedByUsers() {
		return modifiedByUsers;
	}

	/**
	 * @param modifiedByUsers the modifiedByUsers to set
	 */
	public void setModifiedByUsers(Set<AuthUser> modifiedByUsers) {
		this.modifiedByUsers = modifiedByUsers;
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
