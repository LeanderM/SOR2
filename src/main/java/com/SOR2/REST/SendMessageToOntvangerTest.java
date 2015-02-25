package com.SOR2.REST;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class SendMessageToOntvangerTest extends WebPage {

	public SendMessageToOntvangerTest() throws JSONException {
		JSONObject obj = new JSONObject("{" + "id" + ":" + "1" + "}");

		String test = defaultMethods.excutePost("http://localhost:8080/a2/b/c",
				obj.toString());

		add(new Label("testooo", test));
	}

}
