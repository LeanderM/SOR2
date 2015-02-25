package com.SOR2.layout;

//note how it extends template, not WebPage!
public class Login extends Template {

	public Login() {
		super();
		replace(new LoginPanel(CONTENT_ID));

	}

}
