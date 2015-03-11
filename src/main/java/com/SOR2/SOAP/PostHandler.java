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
		buildSOAPRequest(documentInformation, document, url);
	}

	public void buildSOAPRequest(DocumentInformation documentInformation,
			Document document, String url) {
		try {
			// TODO
			// 1. De code opdelen in losse methodes
			// 2. Het verzend gedeelte testen, afmaken

			// SOAP connection factory
			SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = sfc.createConnection();

			// Message factory
			MessageFactory mf = MessageFactory.newInstance();
			// SOAP message
			SOAPMessage sm = mf.createMessage();

			// SOAP header
			SOAPHeader soapHeader = sm.getSOAPHeader();

			// QName for documentInformatie
			QName QdocumentInformation = new QName("http://SOAP.SOR2.com/",
					"documentInformation");

			// create the following elements: documentInformation, destination,
			// title
			// in the soap header
			SOAPElement XMLdocumentInformation = soapHeader
					.addChildElement(QdocumentInformation);
			// This needs to be there because SOAP expects the namespaces
			// of the child elements to be empty, but still be there
			XMLdocumentInformation.addAttribute(new QName("xmlns"), "");

			SOAPElement XMLdestination = XMLdocumentInformation
					.addChildElement(new QName("", "destination"));
			SOAPElement XMLtitle = XMLdocumentInformation
					.addChildElement(new QName("", "title"));

			// add values from documentInformation to the XML elements
			XMLdestination.addTextNode(documentInformation.getDestination());
			XMLtitle.addTextNode(documentInformation.getTitle());
			// soapHeader.detachNode(); to detach the HEADER if you want to

			// SOAP body
			SOAPBody sb = sm.getSOAPBody();

			// namespace QName of the body element
			QName bodyName = new QName("http://SOAP.SOR2.com/", "sendDocument");

			// add the body element to the body
			SOAPBodyElement bodyElement = sb.addBodyElement(bodyName);

			// This needs to be there because SOAP expects the namespaces
			// of the child elements to be empty, but still be there
			bodyElement.addAttribute(new QName("xmlns"), "");

			// create elements within the element we just created
			SOAPElement XMLdocument = bodyElement.addChildElement(new QName("",
					"document"));

			SOAPElement XMLcontent = XMLdocument.addChildElement("content");

			XMLcontent.addTextNode(document.getContent());

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
