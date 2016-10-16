/**
 * 
 */
package com.newsportal;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.newsportal.controllers.resources.AuthUserResource;
import com.newsportal.controllers.resources.HomeResource;

/**
 * @author Biplov's
 *
 */
public class Newsportal extends Application{

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router();
		router.attach("/", HomeResource.class);
		attach(router, "/auth_user", AuthUserResource.class);
		return router;
	}

	private void attach(Router router, String path, Class<?> clazz) {
		
		router.attach(path, clazz);
		// Crud Operation
		router.attach(path + "/create", clazz);
		router.attach(path + "/{id}/read", clazz);
		router.attach(path + "/{id}", clazz); // same as read
		router.attach(path + "/{id}/update", clazz); // respond to PUT??
		router.attach(path + "/{id}/delete", clazz);
	}
	
	
}

/**
 * public class GICApplication extends Application {
 

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router();
		router.attach("/", HomeResource.class);
		attach(router, "/user", AppUserResource.class);
		attach(router, "/permission", PermissionResource.class);
		attach(router, "/module", ModuleResource.class);
		attach(router, "/role", AppRoleResource.class);
		attach(router, "/role/id/{id}/setModule", AppRoleActionResource.class);
		attach(router, "/general-group", GeneralGroupResource.class);
		
		//transaction
		attach(router, "/emergency-observation", EmergencyObservationResource.class);
		attach(router, "/satellite-source", SatelliteSourceResource.class);
		attach(router, "/satellite-sensor", SatelliteSensorResource.class);
		attach(router, "/satellite-image-data", SatelliteImageDataResource.class);
		attach(router, "/sp-satellite-image-data", SPSatelliteImageDataResource.class);

//		ChallengeAuthenticator guard = new ChallengeAuthenticator(getContext(), ChallengeScheme.HTTP_BASIC, "login required");
//		UserVerifier verifier = new UserVerifier();
//      verifier.setSecurity(AppUserService.get());
//
//      guard.setVerifier(verifier);
//      guard.setNext(router);
        
//      return guard;
		
		return router;
	}

	private void attach(Router router, String path, Class<?> clazz) {
		router.attach(path, clazz);
		router.attach(path + "/id/{id}", clazz);
		router.attach(path + "/cmd/{cmd}", clazz);
	}
	
}
*/
