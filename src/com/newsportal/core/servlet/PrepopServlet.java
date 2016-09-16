package com.newsportal.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newsportal.model.bean.AuthPermission;
import com.newsportal.model.bean.AuthRelation;
import com.newsportal.model.bean.AuthRole;
import com.newsportal.model.bean.AuthUser;
import com.newsportal.model.services.AuthPermissionService;
import com.newsportal.model.services.AuthRelationService;
import com.newsportal.model.services.AuthRoleService;
import com.newsportal.model.services.AuthUserService;
import com.newsportal.utils.Utils;

public class PrepopServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static AuthRole authRole1;
	private static AuthRole authRole2;
	private static AuthRole authRole3;
	private static AuthRole authRole4;
	private static AuthUser user1;
	private static AuthUser user2;
	private static AuthUser user3;
	private static AuthPermission permission1;
	private static AuthPermission permission2;
	private static AuthPermission permission3;
	private static AuthPermission permission4;
	private static AuthPermission permission5;
	private static AuthPermission permission6;
	private static AuthPermission permission7;
	private static AuthPermission permission8;
	private static AuthPermission permission9;
	private static AuthRelation authRelation1;
	private static AuthRelation authRelation2;
	private static AuthRelation authRelation3;
	private static AuthRelation authRelation4;
	//private static Package packages;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.setStatus(HttpServletResponse.SC_OK);
		PrintWriter printWriter = resp.getWriter();
		try {
			createAuthRole();
			createAuthPermission();
			createAuthUser();
			createAuthRelation();
			printWriter.write("init Data Successfully");
		} catch (Exception e) {
			e.printStackTrace(printWriter);
			printWriter.write(e.getMessage());
			// TODO: handle exception
		}
		printWriter.close();
	}

	private void createAuthRole() {

		System.out.println("*********************************************");
		System.out.println("CREATING AUTH ROLES");
		System.out.println("*********************************************");

		authRole1 = new AuthRole("EDITOR", "Content Creator");
		authRole2 = new AuthRole("APPROVER", "Content Approver");
		authRole3 = new AuthRole("ADMINISTRATOR", "Administrator");
		authRole4 = new AuthRole("PUBLISHER", "Content Publisher");

		AuthRoleService.get().addOrRetrieve(authRole1);
		AuthRoleService.get().addOrRetrieve(authRole2);
		AuthRoleService.get().addOrRetrieve(authRole3);
		AuthRoleService.get().addOrRetrieve(authRole4);
	}

	private void createAuthPermission() {
		
		System.out.println("*********************************************");
		System.out.println("CREATING AUTH PERMISSION");
		System.out.println("*********************************************");

		// "CREATE, READ, UPDATE, DELETE, APPROVE, PUBLISH"
		// EDITOR
		permission1 = new AuthPermission("auth", "READ", "READ", authRole1);
		permission2 = new AuthPermission("doc", "CREATE|READ|UPDATE|DELETE", "READ",  authRole1);
		permission3 = new AuthPermission("news", "CREATE|READ|UPDATE|DELETE", "READ", authRole1);
		
		// APPROVER
		permission4 = new AuthPermission("auth", "READ", "READ", authRole2);
		permission5 = new AuthPermission("doc", "CREATE|READ|UPDATE|DELETE|APPROVE", "READ|UPDATE|DELETE", authRole2);
		permission6 = new AuthPermission("news", "CREATE|READ|UPDATE|DELETE|APPROVE", "READ|UPDATE|DELETE", authRole2);

		// PUBLISHER
		permission7 = new AuthPermission("auth", "READ", "READ", authRole4);
		permission8 = new AuthPermission("doc", "CREATE|READ|UPDATE|DELETE|PUBLISH", "READ", authRole4);
		permission9 = new AuthPermission("news", "CREATE|READ|UPDATE|DELETE|PUBLISH", "READ", authRole4);
		
		AuthPermissionService.get().addOrRetrieve(permission1);
		AuthPermissionService.get().addOrRetrieve(permission2);
		AuthPermissionService.get().addOrRetrieve(permission3);
		AuthPermissionService.get().addOrRetrieve(permission4);
		AuthPermissionService.get().addOrRetrieve(permission5);
		AuthPermissionService.get().addOrRetrieve(permission6);
		AuthPermissionService.get().addOrRetrieve(permission7);
		AuthPermissionService.get().addOrRetrieve(permission8);
		AuthPermissionService.get().addOrRetrieve(permission9);
	}

	private void createAuthUser() throws NoSuchAlgorithmException {
		
		System.out.println("*********************************************");
		System.out.println("CREATING AUTH USERS");
		System.out.println("*********************************************");

		user1 = new AuthUser("User", "1", "user1", Utils.MD5("user1"), "user1@example.com");
		user2 = new AuthUser("User", "2", "user2", Utils.MD5("user2"), "user2@example.com");
		user3 = new AuthUser("Admin", "User", "adminuser", Utils.MD5("adminuser"), "admin@example.com");

		String apiKey1 = UUID.randomUUID().toString();
		String apiKey2 = UUID.randomUUID().toString();
		String apiKey3 = UUID.randomUUID().toString();
		
		user1.setApiKey(apiKey1);
		user2.setApiKey(apiKey2);
		user3.setApiKey(apiKey3);
		AuthUserService.get().addOrRetrieve(user1);
		AuthUserService.get().addOrRetrieve(user2);
		AuthUserService.get().addOrRetrieve(user3);
	}

	private void createAuthRelation() {
		
		System.out.println("*********************************************");
		System.out.println("ASSIGNING ROLES TO USERS");
		System.out.println("*********************************************");

		authRelation3 = new AuthRelation(user3, authRole3);
		authRelation1 = new AuthRelation(user1, authRole1);
		authRelation2 = new AuthRelation(user2, authRole2);
		authRelation4 = new AuthRelation(user2, authRole1);
		
		AuthRelationService.get().addOrRetrieve(authRelation1);
		AuthRelationService.get().addOrRetrieve(authRelation2);
		AuthRelationService.get().addOrRetrieve(authRelation3);
		AuthRelationService.get().addOrRetrieve(authRelation4);
	}
}
