package com.newsportal.controllers.resources;

import java.util.ArrayList;
import java.util.List;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

import com.newsportal.controllers.services.Service;
import com.newsportal.utils.LoadResult;
import com.newsportal.utils.MapWhereQuery;

public abstract class Resource<T> extends  FirstRun {

	protected int start = 0;
	protected int limit = -1;
	protected String order = "id";
	protected boolean desc = false;
	protected List<MapWhereQuery> listMapQuery = new ArrayList<MapWhereQuery>();
	protected Integer deepLvl = 1;
	protected Service<T> service;
	protected Class<T> clazz;

	@Override
	protected void doInit() throws ResourceException {
		
		// Call the doInit for FirstRun
		super.doInit();
		
		// Set the response headers
		// See if response headers are set before
		Form responseHeaders = (Form) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
			responseHeaders = new Form();
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add("Access-Control-Allow-Origin", "*");
		responseHeaders.add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
		responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
		responseHeaders.add("Access-Control-Allow-Credentials", "true");
	}

	/* for hiding sensitive data like pwd etc */
	/* override this in subclasses to implement */
	protected void hideSensitiveData(T bean) {
		
	}

	@Get("json")
	public Representation toJson() {

		Representation representation = null;
		LoadResult<List<T>> loadResult = null;
		
		try {
			
			String whereClause = "";
			
			//checkUserPermission();

			loadResult = service.loadData(start, limit, order, desc, whereClause, deepLvl, listMapQuery);
			for (T bean : loadResult.getRecord()) {
				hideSensitiveData(bean);
			}
		} catch (IllegalArgumentException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e.getMessage());
		}
		representation = new JacksonRepresentation<LoadResult<List<T>>>(loadResult);
		return representation;
	}
}
