package com.SOR2.SESSION;

import java.util.List;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import com.SOR2.hibernate.HibernateMain;

/**
 * Session manager
 * 
 * @author Leander
 * @version 1.0.0
 *
 */
public final class BackendSession extends AuthenticatedWebSession {
	/**
	 * basis informatie over de aangemelde user, of NULL
	 */
	private String user;

	public BackendSession(Request request) {
		super(request);

	}

	/**
	 * Inloggen/authenticeren
	 * 
	 * @param username
	 *            De gebruikersnaam
	 * @param password
	 *            Het wachtwoord
	 * @returns FALSE als de gebruiker niet bestaat, ander wordt zijn
	 *          gebruikersnaam in het veld user gezet, en TRUE terug gegeven.
	 */
	@Override
	public final boolean authenticate(final String username,
			final String password) {

		// @TODO tijdelijke login informatie vervangen door hetgene uit de
		// database.
		/**
		 * if (username.equalsIgnoreCase("root") &&
		 * password.equalsIgnoreCase("root")) { user = username; }
		 */

		List usersFromDb = HibernateMain.checkLogin(username, password);

		// als de gebruiker gevonden is...
		if (usersFromDb.size() != 0) {
			user = username;
		}

		return user != null;
	}

	/**
	 * Haal de huidige gebruiker op.
	 * 
	 * @return
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Zet de huidige gebruikersnaam
	 * 
	 * @param user
	 */
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
