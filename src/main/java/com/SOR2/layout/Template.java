package com.SOR2.layout;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class Template extends WebPage {

	// id of the stuff that holds the content. this is just a template, so no
	// content is included in this file
	public static final String CONTENT_ID = "contentComponent";

	private Component header;
	private Component menu;

	public Template() {
		super();

		add(header = new Header("header"));
		add(menu = new Menu("menu"));
		add(new Label(CONTENT_ID, "Your content here"));
	}

	public Component getHeader() {
		return header;
	}

	public Component getMenu() {
		return menu;
	}

}
