package com.SOR2.SESSION;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public final class BackendSession extends AuthenticatedWebSession {
	private String user;

	public BackendSession(Request request) {
		super(request);

	}

	@Override
	public final boolean authenticate(final String username,
			final String password) {
		final String WICKET = "wicket";

		if (user == null) {
			if (WICKET.equalsIgnoreCase(username)
					&& WICKET.equalsIgnoreCase(password)) {
				user = username;
			}
		}

		return user != null;
	}

	public String getUser() {
		return user;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	/**
	 * @see org.apache.wicket.authentication.AuthenticatedWebSession#getRoles()
	 */
	@Override
	public Roles getRoles() {
		return null;
	}
}
