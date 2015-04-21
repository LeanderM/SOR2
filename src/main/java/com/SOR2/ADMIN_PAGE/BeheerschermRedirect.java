package com.SOR2.ADMIN_PAGE;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;

import com.SOR2.HomePage;
import com.SOR2.SESSION.AuthenticatedWebPage;
import com.SOR2.SESSION.BackendSession;
import com.SOR2.hibernate.HibernateMain;

/**
 * Check user Role en redirect naar juiste beheerpage op basis van account type
 * 
 * @author Jesse
 * @version 0.1.0
 *
 */
public class BeheerschermRedirect extends WebPage implements
		AuthenticatedWebPage {

	public BeheerschermRedirect() {
		BackendSession session = (BackendSession) getSession();
		String userName = session.getUser();

		// Check de role van de user via de facade
		String userRole = HibernateMain.getUserTypeForAccount(userName);

		if (userRole.equals("Admin")) {
			throw new RestartResponseAtInterceptPageException(
					BeheerschermAdmin.class);
		} else if (userRole.equals("User")) {
			throw new RestartResponseAtInterceptPageException(
					BeheerschermUser.class);
		} else {
			throw new RestartResponseAtInterceptPageException(HomePage.class);
		}
	}
}
