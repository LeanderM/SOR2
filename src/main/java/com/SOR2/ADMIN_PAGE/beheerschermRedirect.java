package com.SOR2.ADMIN_PAGE;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;

import com.SOR2.SESSION.AuthenticatedWebPage;

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
		throw new RestartResponseAtInterceptPageException(beheerscherm.class);
	}
}
