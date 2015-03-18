package com.SOR2;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));

		add(new Link("login") {
			@Override
			public void onClick() {
				setResponsePage(com.SOR2.LOGIN.LoginPage.class);
			}
		});
		// TODO Add your page's components here

	}
}
