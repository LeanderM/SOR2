package com.SOR2.ADMIN_PAGE;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;

import com.SOR2.HomePage;
import com.SOR2.SESSION.AuthenticatedWebPage;
import com.SOR2.SESSION.BackendSession;
import com.SOR2.hibernate.HibernateMain;

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
		// userRole = "ADMIN";
		userRole = HibernateMain.getUserTypeForAccount(userName);

		System.out.println(userRole);

		if (userRole.equals("Admin")) {
			throw new RestartResponseAtInterceptPageException(
					beheerscherm.class);
		} else {
			throw new RestartResponseAtInterceptPageException(HomePage.class);
		}
	}
}
