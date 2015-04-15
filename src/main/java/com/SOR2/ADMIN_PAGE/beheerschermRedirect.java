package com.SOR2.ADMIN_PAGE;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;

import com.SOR2.AccessDenied;
import com.SOR2.SESSION.AuthenticatedWebPage;
import com.SOR2.SESSION.BackendSession;

/**
 * Check user Role en redirect naar juiste beheerpage
 * 
 * @author Jesse
 * @version 0.1.0
 *
 */
public class beheerschermRedirect extends WebPage implements
		AuthenticatedWebPage {

	public beheerschermRedirect() {
		BackendSession session = (BackendSession) getSession();
		String userName = session.getUser();
		String userRole;

		// Check de role van de user via de facade
		userRole = "ADMIN";
		// userRole = HibernateMain.getRole(userName);

		if (userRole.equals("ADMIN")) {
			throw new RestartResponseAtInterceptPageException(
					beheerscherm.class);
		} else {
			throw new RestartResponseAtInterceptPageException(
					AccessDenied.class);
		}
	}
}
