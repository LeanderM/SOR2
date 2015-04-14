package com.SOR2;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.component.IRequestableComponent;

import com.SOR2.ADMIN_PAGE.beheerschermRedirect;
import com.SOR2.AJAX_EXAMPLE.TestPage;
import com.SOR2.SESSION.AuthenticatedWebPage;
import com.SOR2.SESSION.BackendSession;
import com.SOR2.SESSION.LoginPage;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see com.SOR2.Start#main(String[])
 * @version 1.2.0
 */
public final class WicketApplication extends WebApplication {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.protocol.http.WebApplication#newSession(Request,
	 *      Response)
	 */
	@Override
	public Session newSession(Request request, Response response) {
		return new BackendSession(request);
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
		mountPage("beheerscherm", beheerschermRedirect.class);

		// Bootstrap settings
		BootstrapSettings settings = new BootstrapSettings();
		// Bootstrap.getSettings(this).getActiveThemeProvider().setActiveTheme();
		Bootstrap.install(this, settings);

		// zet de authorisatie strategie
		getSecuritySettings().setAuthorizationStrategy(
				new IAuthorizationStrategy() {
					/**
					 * Controleer of de gebruiker geauthorizeerd is om een
					 * bepaalde actie uit te voeren.
					 * 
					 * @param component
					 *            Component waar de actie over uitgevoerd wordt
					 * @param action
					 *            Actie die uitgevoerd wordt
					 * @return boolean of er toestemming is
					 */
					public boolean isActionAuthorized(Component component,
							Action action) {
						// Alleen paginas worden geauthenticeerd, acties mogen
						// altijd.
						return true;
					}

					/**
					 * Controleer of een gebruiker geauthorizeerd is om een
					 * pagina te instantïeren.
					 */
					public <T extends IRequestableComponent> boolean isInstantiationAuthorized(
							Class<T> componentClass) {

						// Controleer of de pagina authenticatie vereist.
						// Klassen die authenticatie nodig hebben, erven over
						// van AuthenticatedWebPage.
						if (AuthenticatedWebPage.class
								.isAssignableFrom(componentClass)) {

							// Is de gebruiker al ingelogd? zoja, ga door.
							// er worden geen verschillende gebruikersniveaus
							// toegepast.
							if (((BackendSession) Session.get()).isSignedIn()) {
								return true;
							}

							// Gebruiker is niet geauthenticeerd, wordt
							// geredirect naar de login pagina.
							// De gewenste pagina wordt opgeslagen en kan naar
							// gebrowsed worden met
							// Component.continueToOriginalDestionation()
							throw new RestartResponseAtInterceptPageException(
									LoginPage.class);
						}

						// webpagina heeft geen authenticatie nodig.
						return true;
					}
				});
	}

}
