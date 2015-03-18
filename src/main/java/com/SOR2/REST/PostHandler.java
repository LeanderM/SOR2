package com.SOR2.REST;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * De PostHandler klasse kan op basis van een JSONObject en een URL een post
 * verzenden naar de meegegeven url
 * 
 * @author Mark
 * @version 0.1.0
 *
 */
public class PostHandler {

	/**
	 * De contructor voert ook gelijk de post call uit Hij maakt hierbij gebruik
	 * van de excutePost methode uit abstracte klasse defaultMethods de klasse
	 * defaultMethods bevat een aantal statische methodes
	 * 
	 */
	public PostHandler(JSONObject jsonDocument, String url)
			throws JSONException {

		String test = defaultMethods.excutePost(url, jsonDocument.toString());

	}

}
