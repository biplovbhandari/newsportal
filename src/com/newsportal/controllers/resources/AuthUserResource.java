package com.newsportal.controllers.resources;

import com.newsportal.controllers.services.AuthUserService;
import com.newsportal.model.auth.AuthUser;

public class AuthUserResource extends Resource<AuthUser> {
	
	public AuthUserResource() {
		
		service = AuthUserService.get();
		clazz = AuthUser.class;
	}

	@Override
	protected void hideSensitiveData(AuthUser authUser) {
		
		super.hideSensitiveData(authUser);
		authUser.setPassword(null);
	} 
}
