package com.SOR2.SOAP;

import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.NodeList;

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
	private SOAPConnectionFactory soapConnectionFactory;
	private MessageFactory messageFactory;

	private SOAPConnection connection;
	private SOAPMessage soapMessage;
	private SOAPMessage soapResponse;

	public PostHandler(DocumentInformation documentInformation,
			Document document, String url) {

		// We instantiate the needed objects
		try {
			soapConnectionFactory = SOAPConnectionFactory.newInstance();
			connection = soapConnectionFactory.createConnection();
			messageFactory = MessageFactory.newInstance();
			soapMessage = messageFactory.createMessage();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (SOAPException e) {
			e.printStackTrace();
		}

		executeSOAPRequest(documentInformation, document, url);

	}

	private void executeSOAPRequest(DocumentInformation documentInformation,
			Document document, String url) {
		try {
			// TODO
			// Het verzend deel werkt, het response opvangen gaat nog niet
			// helemaal goed

			// Build the body and header of the SOAPMessage
			buildSOAPHeader(documentInformation);
			buildSOAPBody(document);

			// Print the soap message to see what it looks like
			System.out.println("\n Soap Request:\n");
			soapMessage.writeTo(System.out);
			System.out.println();

			// Send the SOAPMessage
			sendSOAPMessage(url);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void buildSOAPHeader(DocumentInformation documentInformation)
			throws SOAPException {
		// SOAP header
		SOAPHeader soapHeader = soapMessage.getSOAPHeader();

		// QName for documentInformatie
		QName QdocumentInformation = new QName("http://ontvanger.SOR2.com/",
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
				.addChildElement("destination");
		SOAPElement XMLtitle = XMLdocumentInformation.addChildElement("title");

		// add values from documentInformation to the XML elements
		XMLdestination.addTextNode(documentInformation.getDestination());
		XMLtitle.addTextNode(documentInformation.getTitle());
	}

	private void buildSOAPBody(Document document) throws SOAPException {
		// SOAP body
		SOAPBody soapBody = soapMessage.getSOAPBody();

		// namespace QName of the body element
		QName bodyName = new QName("http://ontvanger.SOR2.com/", "sendDocument");

		// add the body element to the body
		SOAPBodyElement bodyElement = soapBody.addBodyElement(bodyName);

		// This needs to be there because SOAP expects the namespaces
		// of the child elements to be empty, but still be there
		bodyElement.addAttribute(new QName("xmlns"), "");

		// create elements within the element we just created
		SOAPElement XMLdocument = bodyElement.addChildElement(new QName("",
				"document"));

		// and another element inside the document element
		SOAPElement XMLcontent = XMLdocument.addChildElement("content");
		XMLcontent.addTextNode(document.getContent());
	}

	private void sendSOAPMessage(String url) throws MalformedURLException,
			SOAPException {
		// create a new url with the url String
		URL endpoint = new URL(url);

		// Send SOAP message to the endpoint, the response will be returned in
		// this variable
		soapResponse = connection.call(soapMessage, endpoint);

		// turn the response into String for printing
		final StringWriter sw = new StringWriter();
		try {
			TransformerFactory
					.newInstance()
					.newTransformer()
					.transform(new DOMSource(soapResponse.getSOAPPart()),
							new StreamResult(sw));
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}

		// Now you have the XML as a String:
		// We can easily print this
		System.out.println("The SOAP reponse that was returned:");
		System.out.println(sw.toString());

		// We get the elements with the tagName success
		NodeList elementList = soapResponse.getSOAPBody().getElementsByTagName(
				"success");

		// We get te text value from success
		boolean success = Boolean.parseBoolean(elementList.item(0)
				.getTextContent());
		if (success) {
			System.out.println("Everything went successful!");

		} else {
			System.out
					.println("Sorry, the receiver did not accept our message.");
		}

	}
}
