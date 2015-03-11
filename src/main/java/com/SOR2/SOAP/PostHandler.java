package com.SOR2.SOAP;

import com.SOR2.SOAP.XMLObjects.Document;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;

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
	public PostHandler(DocumentInformation documentInformation,
			Document document, String url) {
		// System.out.println(documentInformation + "  " + document);

		String JSONString = "{'document': {'content': "
				+ document.getContent().toString() + "}}";

		String test = defaultMethods.excutePost(url, JSONString);

	}
}
