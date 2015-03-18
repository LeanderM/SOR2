package com.SOR2;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.SOR2.SESSION.PageThatNeedsAuthentication;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		// defineer de elementen die in de pagina moeten.
		Label version = new Label("version", getApplication()
				.getFrameworkSettings().getVersion());

		Link demoLink = new Link("login") {
			public void onClick() {
				setResponsePage(PageThatNeedsAuthentication.class);
			}
		};

		// add stuff
		add(version);
		add(demoLink);

		// TODO Add your page's components here

	}
}
