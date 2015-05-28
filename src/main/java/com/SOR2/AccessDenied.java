package com.SOR2;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class AccessDenied extends WebPage {

	public AccessDenied(final PageParameters parameters) {
		super(parameters);

		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));
		add(new Label("name", getApplication().getName()));

	}
	// ConflictCOmment1
}
