package com.SOR2.REST;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class PostHandler {

	public PostHandler(JSONObject jsonDocument, String url)
			throws JSONException {
		// JSONObject obj = new JSONObject("{" + "id" + ":" + "1" + "}");

		String test = defaultMethods.excutePost(url, jsonDocument.toString());

	}

}
