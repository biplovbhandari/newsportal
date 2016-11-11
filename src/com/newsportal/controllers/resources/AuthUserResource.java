package com.newsportal.controllers.resources;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;

import com.newsportal.utils.ResponseFormat;
import com.newsportal.utils.Utils;
import com.newsportal.controllers.services.AuthEventService;
import com.newsportal.controllers.services.AuthUserService;
import com.newsportal.model.auth.AuthEvent;
import com.newsportal.model.auth.AuthUser;

@SuppressWarnings("unused")
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

	@Post("json")
	public Representation create(Representation formData) throws NoSuchAlgorithmException {
		// TODO implement different hooks for the database transaction

		Representation response = null;
		try {

			AuthUser bean = mapper.readValue(formData.getText(), clazz);

			AuthUser availableUser = AuthUserService.get().getUserByEmail(bean.getEmail());
			if (availableUser != null) {
				throw new IllegalArgumentException("Username already registered");
			}

			// Prepare for creating the user
			if (!StringUtils.isNotBlank(bean.getUsername())) {
				String username = bean.getEmail().replace("@", "_");
				bean.setUsername(username);
			}
			
			bean.setPassword(Utils.MD5(bean.getPassword()));
			UUID uuid = UUID.randomUUID();
			String apiKey = uuid.toString();
			bean.setApiKey(apiKey);
			addMetaDataCreate(bean);
			
			// SAVE in the database
			if (!service.addOrRetrieve(bean)) {
				ResponseFormat responseFormat = new ResponseFormat(500, "Failed to create user!");
				setStatus(Status.SERVER_ERROR_INTERNAL);
				response = new JacksonRepresentation<ResponseFormat>(responseFormat);
				return response;
				//throw new ResourceException(Status.SERVER_ERROR_INTERNAL, "Failed to create user!");
			}
			
			// Update user activity table
			Boolean eventResult = AuthEventService.get().addOrRetrieve(
									new AuthEvent(currentUser,
											getClientInfo().getAddress(),
											"User " + currentUser.getUsername() +
											" creates a new user " + bean.getUsername()));
			if (!eventResult) {
				ResponseFormat responseFormat = new ResponseFormat(500, "Failed to create user!");
				setStatus(Status.SERVER_ERROR_INTERNAL);
				response = new JacksonRepresentation<ResponseFormat>(responseFormat);
				return response;
				//throw new ResourceException(Status.SERVER_ERROR_INTERNAL, "Failed to save or update activity log data");
			}
			
			Method m;
			Long newCreatedId = null;
			try {
				m = clazz.getMethod("getId");
				newCreatedId = (Long) m.invoke(bean);
				ResponseFormat responseFormat = new ResponseFormat(200, "New User has been created", newCreatedId);
				setStatus(Status.SUCCESS_OK);
				response = new JacksonRepresentation<ResponseFormat>(responseFormat);
				return response;
			} catch (Exception e) {
				System.out.println("Failed to get the id method in the class " + clazz.getSimpleName());
				ResponseFormat responseFormat = new ResponseFormat(500, "Failed to create user!");
				setStatus(Status.SERVER_ERROR_INTERNAL);
				response = new JacksonRepresentation<ResponseFormat>(responseFormat);
				return response;
			}

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
