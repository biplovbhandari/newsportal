package com.newsportal.model.news;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.newsportal.model.auth.AuthUser;
import com.newsportal.model.doc.DocDocument;
import com.newsportal.model.doc.DocImage;
import com.newsportal.model.news.NewsCategory;
import com.newsportal.model.news.NewsTag;

@Entity
@Table(name = "news_post")
public class NewsPost implements Serializable {

	private static final long serialVersionUID = 1L;

	// Relationships Column
	@ManyToOne
	@JoinColumn(name="category_id")
	private NewsCategory categoryId;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name = "news_post_tag",
			   joinColumns = { @JoinColumn(name = "post_id", nullable = false) },
			   inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	@OrderBy("id")
	private Set<NewsTag> newsTag = new LinkedHashSet<NewsTag>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "news_post_image",
			   joinColumns = { @JoinColumn(name = "post_id", nullable = false, updatable = false) },
			   inverseJoinColumns = { @JoinColumn(name = "image_id", nullable = false, updatable = false) })
	@OrderBy("id")
	private Set<DocImage> docImage = new LinkedHashSet<DocImage>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "news_post_document",
			   joinColumns = { @JoinColumn(name = "post_id", nullable = false, updatable = false) },
			   inverseJoinColumns = { @JoinColumn(name = "document_id", nullable = false, updatable = false) })
	@OrderBy("id")
	private Set<DocDocument> docDocument = new LinkedHashSet<DocDocument>();
	
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
	
	@Column(name="title", length=512)
	private String title;
	
	// can contain html tags as well
	@Column(name="body", columnDefinition="TEXT")
	private String body;
	
	@Column(name="date")
	private Date date = new Date();
	
	@Column(name="expired")
	private Boolean expired;
	
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
	 * @return the categoryId
	 */
	public NewsCategory getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(NewsCategory categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the newsTag
	 */
	public Set<NewsTag> getNewsTag() {
		return newsTag;
	}

	/**
	 * @param newsTag the newsTag to set
	 */
	public void setNewsTag(Set<NewsTag> newsTag) {
		this.newsTag = newsTag;
	}

	/**
	 * @return the docImage
	 */
	public Set<DocImage> getDocImage() {
		return docImage;
	}

	/**
	 * @param docImage the docImage to set
	 */
	public void setDocImage(Set<DocImage> docImage) {
		this.docImage = docImage;
	}

	/**
	 * @return the docDocument
	 */
	public Set<DocDocument> getDocDocument() {
		return docDocument;
	}

	/**
	 * @param docDocument the docDocument to set
	 */
	public void setDocDocument(Set<DocDocument> docDocument) {
		this.docDocument = docDocument;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the expired
	 */
	public Boolean getExpired() {
		return expired;
	}

	/**
	 * @param expired the expired to set
	 */
	public void setExpired(Boolean expired) {
		this.expired = expired;
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
