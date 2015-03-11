package com.SOR2.SOAP;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

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

		/*
		 * String JSONString = "{'document': {'content': " +
		 * document.getContent().toString() + "}}";
		 * 
		 * String test = defaultMethods.excutePost(url, JSONString);
		 */
		buildSOAPRequest();

	}

	public void buildSOAPRequest() {
		try {
			// SOAP connection factory
			SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = sfc.createConnection();

			// Message factory
			MessageFactory mf = MessageFactory.newInstance();
			// SOAP message
			SOAPMessage sm = mf.createMessage();

			// SOAP header
			SOAPHeader sh = sm.getSOAPHeader();
			// SOAP body
			SOAPBody sb = sm.getSOAPBody();
			sh.detachNode();
			// namespace QName
			QName bodyName = new QName(
					"http://localhost:8080/testservices/DocumentReceiver",
					"sendDocument");
			/*
			 * !OLD QName bodyName = new
			 * QName("http://localhost:8080/testservices/DocumentReceiver",
			 * "sendDocument", "d");
			 */

			// add the bodyname (namespace) to the body
			SOAPBodyElement bodyElement = sb.addBodyElement(bodyName);

			// create a new namespace
			QName qn = new QName("aName");
			// add the namespace
			SOAPElement quotation = bodyElement.addChildElement(qn);

			// add TextMode
			quotation.addTextNode("TextMode");

			//
			System.out.println("\n Soap Request:\n");
			sm.writeTo(System.out);
			System.out.println();
			/*
			 * URL endpoint = new URL("http://yourServer.com"); SOAPMessage
			 * response = connection.call(sm, endpoint);
			 * System.out.println(response.getContentDescription());
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
