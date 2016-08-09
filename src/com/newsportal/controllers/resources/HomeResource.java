/**
 * 
 */
package com.newsportal.controllers.resources;

import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * @author Biplov's
 *
 */
public class HomeResource extends ServerResource {
	
	// Responds to GET request
	@Get
	public String index() throws ResourceException {
		return "Hello! Welcome to Online News Portal";
	}
	
}
