package com.SOR2;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import com.SOR2.AJAX_EXAMPLE.TestPage;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see com.SOR2.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		// hier ziet u de routing van de webpaginas
		mountPage("homepage", HomePage.class);
		mountPage("testpage", TestPage.class);
		super.init();

		// add your configuration here
	}
}
