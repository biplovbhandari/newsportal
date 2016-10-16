package com.newsportal.controllers.resources;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.newsportal.controllers.services.AuthUserService;
import com.newsportal.model.auth.AuthUser;

public abstract class FirstRun extends ServerResource {

	private String tokenId;
	protected AuthUser currentUser;
	private Form form;

	@Override
	protected void doInit() throws ResourceException{

		// Get data from the request
		form = getQuery();
		tokenId = form.getFirstValue("tokenId");

		// Check for Token Id
		if (tokenId == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_FORBIDDEN, "No Token ID");
		}
		
		// Get current user
		currentUser = AuthUserService.get().getUserByTokenId(tokenId);

		// Check whether the user is valid
		if (currentUser == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Token ID");
		}
		
	}
}
