package com.SOR2;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import com.SOR2.ADMIN_PAGE.beheerscherm;
import com.SOR2.AJAX_EXAMPLE.TestPage;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;

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

		super.init();

		// hier ziet u de routing van de webpaginas
		mountPage("homepage", HomePage.class);
		mountPage("testpage", TestPage.class);
		mountPage("beheerscherm", beheerscherm.class);

		// add your configuration here
		BootstrapSettings settings = new BootstrapSettings();
		// settings.minify(true); // use minimized version of all bootstrap
		// references

		// Bootstrap.getSettings(this).getActiveThemeProvider().setActiveTheme();

		Bootstrap.install(this, settings);
	}

}
