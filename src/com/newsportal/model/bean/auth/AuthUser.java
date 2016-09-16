package com.newsportal.model.bean.auth;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.newsportal.model.bean.auth.AuthUser;
import com.newsportal.model.bean.doc.DocImage;
import com.newsportal.model.bean.news.NewsComment;
import com.newsportal.model.bean.news.NewsPost;
import com.newsportal.model.bean.person.PersonProfile;
import com.newsportal.model.bean.person.PersonSubscription;
import com.newsportal.model.bean.person.PersonSubscriptionFilter;

@Entity
@Table(name = "auth_user")
public class AuthUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Relationships Columns
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="profile_id")
	private PersonProfile profileId;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_post",
			   joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) },
			   inverseJoinColumns = { @JoinColumn(name = "post_id", nullable = false, updatable = false) })
	@OrderBy("id")
	private Set<NewsPost> newsPost = new LinkedHashSet<NewsPost>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="userId")
	private Set<AuthEvent> authEvent = new LinkedHashSet<AuthEvent>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="userId")
	private Set<NewsComment> newsComment = new LinkedHashSet<NewsComment>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="userId")
	private Set<DocImage> docImage = new LinkedHashSet<DocImage>();
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="userId")
	private Set<PersonSubscription> userSubscription = new LinkedHashSet<PersonSubscription>();

	@OneToMany(fetch=FetchType.EAGER, mappedBy="userId")
	private Set<PersonSubscriptionFilter> userSubscriptionFilter = new LinkedHashSet<PersonSubscriptionFilter>();
	
	/*************************************************/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="created_by")
	private AuthUser createdBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="approved_by")
	private AuthUser approvedBy;

	@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name="modified_by")
	private AuthUser modifiedBy;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="first_name", length=128, nullable=false)
	private String firstName;

	@Column(name="middle_name", length=128)
	private String middleName;

	@Column(name="last_name", length=128)
	private String lastName;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="password", length=512, nullable=false)
	private String password;
	
	@Column(name="username", nullable=false)
	private String username;
	
	@Column(name="language", length=16)
	private String language;

	@Column(name="utc_offset", length=16)
	private String utcOffset;

	@Column(name="registration_key")
	private String registrationKey;
	
	@Column(name="password_reset_key", length=512)
	private String passwordResetKey;
	
	@Column(name="timestmp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestmp;

	@Column(name="failed_login_count")
	private Integer failedLoginCount = 0;

	@Column(name="locked")
	private Boolean locked = false;

	@Column(name="disabled")
	private Boolean disabled = false;
	
	@Column(name="api_key")
	private String apiKey;

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
	 * Constructors
	 */
	
	public AuthUser() {
	}
	
	public AuthUser(Long id) {
		this.id = id;
	}
	
	public AuthUser(String id) {
		this.id = Long.valueOf(id);
	}

	public AuthUser(String firstName, String lastName,
			String userName, String password, String email) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = userName;
		this.password = password;
		this.email = email;
	}

	/**
	 * @return the newsPost
	 */
	public Set<NewsPost> getNewsPost() {
		return newsPost;
	}

	/**
	 * @param newsPost the newsPost to set
	 */
	public void setNewsPost(Set<NewsPost> newsPost) {
		this.newsPost = newsPost;
	}

	/**
	 * @return the authEvent
	 */
	public Set<AuthEvent> getAuthEvent() {
		return authEvent;
	}

	/**
	 * @param authEvent the authEvent to set
	 */
	public void setAuthEvent(Set<AuthEvent> authEvent) {
		this.authEvent = authEvent;
	}

	/**
	 * @return the newsComment
	 */
	public Set<NewsComment> getNewsComment() {
		return newsComment;
	}

	/**
	 * @param newsComment the newsComment to set
	 */
	public void setNewsComment(Set<NewsComment> newsComment) {
		this.newsComment = newsComment;
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
	 * @return the profileId
	 */
	public PersonProfile getProfileId() {
		return profileId;
	}

	/**
	 * @param profileId the profileId to set
	 */
	public void setProfileId(PersonProfile profileId) {
		this.profileId = profileId;
	}

	/**
	 * @return the userSubscription
	 */
	public Set<PersonSubscription> getUserSubscription() {
		return userSubscription;
	}

	/**
	 * @param userSubscription the userSubscription to set
	 */
	public void setUserSubscription(Set<PersonSubscription> userSubscription) {
		this.userSubscription = userSubscription;
	}

	/**
	 * @return the userSubscriptionFilter
	 */
	public Set<PersonSubscriptionFilter> getUserSubscriptionFilter() {
		return userSubscriptionFilter;
	}

	/**
	 * @param userSubscriptionFilter the userSubscriptionFilter to set
	 */
	public void setUserSubscriptionFilter(Set<PersonSubscriptionFilter> userSubscriptionFilter) {
		this.userSubscriptionFilter = userSubscriptionFilter;
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the utcOffset
	 */
	public String getUtcOffset() {
		return utcOffset;
	}

	/**
	 * @param utcOffset the utcOffset to set
	 */
	public void setUtcOffset(String utcOffset) {
		this.utcOffset = utcOffset;
	}

	/**
	 * @return the registrationKey
	 */
	public String getRegistrationKey() {
		return registrationKey;
	}

	/**
	 * @param registrationKey the registrationKey to set
	 */
	public void setRegistrationKey(String registrationKey) {
		this.registrationKey = registrationKey;
	}

	/**
	 * @return the passwordResetKey
	 */
	public String getPasswordResetKey() {
		return passwordResetKey;
	}

	/**
	 * @param passwordResetKey the passwordResetKey to set
	 */
	public void setPasswordResetKey(String passwordResetKey) {
		this.passwordResetKey = passwordResetKey;
	}

	/**
	 * @return the timestmp
	 */
	public Date getTimestmp() {
		return timestmp;
	}

	/**
	 * @param timestmp the timestmp to set
	 */
	public void setTimestmp(Date timestmp) {
		this.timestmp = timestmp;
	}

	/**
	 * @return the failedLoginCount
	 */
	public Integer getFailedLoginCount() {
		return failedLoginCount;
	}

	/**
	 * @param failedLoginCount the failedLoginCount to set
	 */
	public void setFailedLoginCount(Integer failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}

	/**
	 * @return the locked
	 */
	public Boolean isLocked() {
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	/**
	 * @return the disabled
	 */
	public Boolean isDisabled() {
		return disabled;
	}

	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
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

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
