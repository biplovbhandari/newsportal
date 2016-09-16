package com.newsportal.core.servlet;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.newsportal.utils.LoginInfo;
import com.newsportal.model.bean.AuthEvent;
import com.newsportal.model.bean.AuthRole;
import com.newsportal.model.bean.AuthUser;
import com.newsportal.model.services.AuthEventService;
import com.newsportal.model.services.AuthRoleService;
import com.newsportal.model.services.AuthUserService;
import com.newsportal.utils.LoginRequest;
import com.newsportal.utils.Utils;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int MAX_FAILED_LOGINS = 10;
	private static final Logger LOG = Logger.getLogger(LoginServlet.class.getName());
	private ObjectMapper objectMapper;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		objectMapper = new ObjectMapper();
	}

	@Override
	protected void doPost (HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try{
			
			LoginRequest loginRequest = objectMapper.readValue(req.getInputStream(), LoginRequest.class);
			String email = loginRequest.getEmail();
			@SuppressWarnings("unused")
			String username = loginRequest.getUsername();
			String password = Utils.MD5(loginRequest.getPassword());

			AuthUser user = AuthUserService.get().getUserByEmail(email);

			if (user == null || user.getId().equals("")) {
				throw new IllegalArgumentException("User name is not registered.");
			}
			
			user = AuthUserService.get().getUserById(user.getId());
		
			if (req.getParameter("logout") == null) {
				doLogin(req, resp, user, password);
			}
			
		} catch (IllegalArgumentException e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.setContentType("text/plain");
			resp.getWriter().print(e.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
			LOG.severe(LoginServlet.class.getSimpleName() + " Error: " + e.getMessage());
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.setContentType("text/plain");
			resp.getWriter().print("Server Error: " + e.getMessage());
		}
	}

	private void doLogin(HttpServletRequest req, HttpServletResponse resp, AuthUser user, String password)
			throws IOException,	JsonGenerationException, JsonMappingException {
		if (user.isDisabled()) {
			throw new IllegalArgumentException(
					"Your account has been disabled.\n"
							+ "Please contact the administrator.");

		} else if (user.isLocked()) {
			throw new IllegalArgumentException("Your account"
					+ " is locked. Please contact the administrator.");

		} else if (!user.getPassword().equals(password)) {
			String message = "Invalid username or password";

			user.setFailedLoginCount(user.getFailedLoginCount() + 1);
			if (user.getFailedLoginCount() == MAX_FAILED_LOGINS - 1) {
				message = "\nYou have 1 more attempt before your account is locked.";
			} else if (user.getFailedLoginCount() == MAX_FAILED_LOGINS) {
				user.setLocked(true);
				message = "\nYour account has been locked. Please contact the administrator";
			}
			throw new IllegalArgumentException(message);
		}

		UUID uuid = UUID.randomUUID();
		@SuppressWarnings("unused")
		String userSession = uuid.toString();
		//user.setLastSessionId(userSession);
		
		// User
		Boolean userResult = AuthUserService.get().addOrRetrieve(user);
		if (!userResult) {
			throw new RuntimeException("Failed to save or update the user");
		}
		
		// User Event
		Boolean eventResult = AuthEventService.get().addOrRetrieve(new AuthEvent(user, req.getRemoteAddr(), "logs in"));
		if (!eventResult) {
			throw new RuntimeException("Failed to save or update activity log data");
		}

		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");

		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setLoggedIn(true);
		loginInfo.setApiKey(user.getApiKey());
		loginInfo.setFullname(Utils.getFullName(user));
		loginInfo.setUserId(user.getId());
		loginInfo.setUsername(user.getUsername());
		List<AuthRole> authRole = AuthRoleService.get().retrieveUserRole(user);
		loginInfo.setRole(authRole);
		
		if(loginInfo.getRole() != null) {
			System.out.println("Filter role");
		}
		
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "POST");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setHeader("Access-Control-Max-Age", "true");
		objectMapper.writeValue(resp.getOutputStream(), loginInfo);
	}

	
}
