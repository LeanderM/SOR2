package com.SOR2.LOGIN;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public class CustomAuthenticatedWebSession extends AuthenticatedWebSession {

	private static final long serialVersionUID = 3003280399762836676L;

	public CustomAuthenticatedWebSession(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean authenticate(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Roles getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

}
