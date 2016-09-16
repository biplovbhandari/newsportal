package com.newsportal;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.newsportal.model.bean.AuthEvent;
import com.newsportal.model.bean.AuthPermission;
import com.newsportal.model.bean.AuthRole;
import com.newsportal.model.bean.AuthUser;
import com.newsportal.model.bean.DocDocument;
import com.newsportal.model.bean.DocImage;
import com.newsportal.model.bean.NewsCategory;
import com.newsportal.model.bean.NewsComment;
import com.newsportal.model.bean.NewsPost;
import com.newsportal.model.bean.NewsTag;
import com.newsportal.model.bean.UserProfile;
import com.newsportal.model.bean.UserSubscription;
import com.newsportal.model.bean.UserSubscriptionFilter;
import com.newsportal.model.bean.UserSubscriptionResource;

public class MainClass {

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		AuthUser authUser1 = new AuthUser();
		authUser1.setFirstName("Biplov");
		authUser1.setLastName("Bhandari");
		authUser1.setEmail("bionicbiplov45@gmail.com");
		authUser1.setPassword("biplov");
		authUser1.setUsername("bionicbiplov45@gmail.com");
		
		AuthUser authUser2 = new AuthUser();
		authUser2.setFirstName("Saru");
		authUser2.setLastName("Acharya");
		authUser2.setEmail("saru.archarya@gmail.com");
		authUser2.setPassword("saru");
		authUser2.setUsername("saru.archarya@gmail.com");
		
		AuthEvent authEvent1 = new AuthEvent();
		authEvent1.setDescription("Biplov Bhandari logs in");
		authEvent1.setUserId(authUser1);
		
		AuthEvent authEvent3 = new AuthEvent();
		authEvent3.setDescription("Biplov Bhandari logged in");
		authEvent3.setUserId(authUser1);

		AuthEvent authEvent2 = new AuthEvent();
		authEvent2.setDescription("Saru Archarya logs in");
		authEvent2.setUserId(authUser2);

		UserProfile userProfile1 = new UserProfile();
		userProfile1.setGender("Male");
		UserProfile userProfile2 = new UserProfile();
		userProfile2.setGender("Female");

		authUser1.setProfileId(userProfile1);
		authUser2.setProfileId(userProfile2);
		
		AuthRole authRole1 = new AuthRole();
		AuthRole authRole2 = new AuthRole();
		authRole1.setUuid("ADMINSTRATOR");
		authRole1.setRole("Administrator");
		authRole2.setUuid("EDITOR");
		authRole2.setRole("Editor");

		Set<AuthRole> roleSet1 = new LinkedHashSet<AuthRole>();
		roleSet1.add(authRole1);
		roleSet1.add(authRole2);
		Set<AuthRole> roleSet2 = new LinkedHashSet<AuthRole>();
		roleSet2.add(authRole2);

		//authUser1.setAuthRole(roleSet1);
		//authUser2.setAuthRole(roleSet2);
		
		@SuppressWarnings("unused")
		AuthPermission authPermission1 = new AuthPermission();
		//authPermission1.setResource("news_post");
		//authPermission1.setCreatePermission(true);
		//authPermission1.setReadPermission(true);
		//authPermission1.setUpdatePermission(true);
		//authPermission1.setDeletePermission(true);

		@SuppressWarnings("unused")
		AuthPermission authPermission2 = new AuthPermission();
		//authPermission2.setResource("news_post");
		//authPermission2.setCreatePermission(true);
		//authPermission2.setReadPermission(true);
		//authPermission2.setUpdatePermission(true);
		//authPermission2.setDeletePermission(true);
		
		//authRole1.setAuthPermission(authPermission1);
		//authRole2.setAuthPermission(authPermission2);
		
		NewsCategory newsCategory = new NewsCategory();
		newsCategory.setName("Sports");
		
		NewsPost newsPost = new NewsPost();
		newsPost.setCategoryId(newsCategory);
		newsPost.setBody("This is the body of the news");
		newsPost.setTitle("Title");
		
		NewsTag newsTag = new NewsTag();
		newsTag.setName("WC2020");

		Set<NewsTag> newsTagSet = new LinkedHashSet<NewsTag>();
		newsTagSet.add(newsTag);
		newsPost.setNewsTag(newsTagSet);

		Set<NewsPost> newsPostSet = new LinkedHashSet<NewsPost>();
		newsPostSet.add(newsPost);
		authUser1.setNewsPost(newsPostSet);
		
		NewsComment newsComment = new NewsComment();
		newsComment.setUserId(authUser2);
		newsComment.setPostId(newsPost);
		newsComment.setBody("This is the first Comment");
		
		DocImage docImage1 = new DocImage();
		docImage1.setName("DOC_IMAGE_NAME");
		docImage1.setFile("DOC_IMAGE_FILE");
		docImage1.setUserId(authUser1);

		DocImage docImage2 = new DocImage();
		docImage2.setName("DOC_IMAGE_NAME_2");
		docImage2.setFile("DOC_IMAGE_FILE_2");
		docImage2.setUserId(authUser2);		
		
		Set<DocImage> docImageSet = new LinkedHashSet<DocImage>();
		docImageSet.add(docImage1);
		docImageSet.add(docImage2);
		newsPost.setDocImage(docImageSet);
		
		DocDocument docDocument1 = new DocDocument();
		docDocument1.setName("DOC_DOCUMENT_NAME");
		docDocument1.setFile("DOC_DOCUMENT_FILE");
		docDocument1.setUserId(authUser1);

		DocDocument docDocument2 = new DocDocument();
		docDocument2.setName("DOC_DOCUMENT_NAME_2");
		docDocument2.setFile("DOC_DOCUMENT_FILE_2");
		
		Set<DocDocument> docDocumentSet = new LinkedHashSet<DocDocument>();
		docDocumentSet.add(docDocument1);
		docDocumentSet.add(docDocument2);
		newsPost.setDocDocument(docDocumentSet);
		
		UserSubscriptionFilter userSubscriptionFilter = new UserSubscriptionFilter();
		userSubscriptionFilter.setQuery("SELECT * FROM SOMETHING");
		userSubscriptionFilter.setUserId(authUser1);

		UserSubscriptionResource userSubscriptionResource = new UserSubscriptionResource();
		userSubscriptionResource.setResource("news_post");
		userSubscriptionResource.setLastCheckTime(new Date());
		userSubscriptionResource.setNextCheckTime(new Date());
		
		UserSubscription userSubscription = new UserSubscription();
		userSubscription.setUserId(authUser1);
		userSubscription.setFilterId(userSubscriptionFilter);
		userSubscription.setResourceId(userSubscriptionResource);

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		//session.save(authUser);
		session.persist(authUser1);
		session.persist(authUser2);
		
		session.save(authRole1);
		session.save(authRole2);
		
		session.save(authEvent1);
		session.save(authEvent2);
		session.save(authEvent3);
		
		session.save(newsCategory);
		session.save(newsPost);
		session.save(newsComment);
		
		session.save(docImage1);
		session.save(docImage2);
		
		session.save(docDocument1);
		session.save(docDocument2);

		session.persist(userSubscription);
		
		session.getTransaction().commit();
		session.close();

	}

}
