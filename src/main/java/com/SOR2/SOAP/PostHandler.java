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

import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;

/**
 * De PostHandler klasse kan op basis van een DocumentInformation, Document en
 * een url variabele een SOAP bericht opstellen en verzenden
 * 
 * @author Jesse
 * @version 0.1.0
 *
 */
public class PostHandler {

	/**
	 * De contructor instantieert alle belangrijke objecten Aan het einde van de
	 * constructor wordt een methode aangeroepen Deze methode zet het bouwen en
	 * verzenden van een SOAPcall in werking
	 */
	private SOAPConnectionFactory soapConnectionFactory;
	private MessageFactory messageFactory;

	private SOAPConnection connection;
	private SOAPMessage soapMessage;
	private SOAPMessage soapResponse;
	private String nameSpace;
	private boolean success;

	// Verwacht: documentInformation, document, url voor de bestemming van de
	// SOAP call,
	// nameSpace van de SOAP ontvanger bv. http://ontvanger.SOR2.com/
	public PostHandler(DocumentInformation documentInformation,
			Message document, String url, String nameSpace) {

		// We instantiate the needed objects
		try {
			soapConnectionFactory = SOAPConnectionFactory.newInstance();
			connection = soapConnectionFactory.createConnection();
			messageFactory = MessageFactory.newInstance();
			soapMessage = messageFactory.createMessage();
			this.nameSpace = nameSpace;
			success = false;
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (SOAPException e) {
			e.printStackTrace();
		}

		// Start the process of sending a SOAPcall
		executeSOAPRequest(documentInformation, document, url);
	}

	/**
	 * Deze methode roept de methodes aan die de SOAPmessage opbouwen en
	 * verzenden, met de juiste parameters verder luisterd hij voor exceptions
	 */
	private void executeSOAPRequest(DocumentInformation documentInformation,
			Message document, String url) {
		try {
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

	/**
	 * Deze methode bouwt de Header van de SOAP message
	 */
	private void buildSOAPHeader(DocumentInformation documentInformation)
			throws SOAPException {
		// SOAP header
		SOAPHeader soapHeader = soapMessage.getSOAPHeader();

		// QName for documentInformatie
		QName QdocumentInformation = new QName(nameSpace, "documentInformation");

		// create the following elements: documentInformation, destination,
		// title
		// in the soap header
		SOAPElement XMLdocumentInformation = soapHeader
				.addChildElement(QdocumentInformation);

		// This needs to be there because SOAP expects the namespaces
		// of the child elements to be empty, but still be there
		XMLdocumentInformation.addAttribute(new QName("xmlns"), "");
		SOAPElement XMLsender = XMLdocumentInformation
				.addChildElement("sender");
		SOAPElement XMLdestination = XMLdocumentInformation
				.addChildElement("receiver");
		SOAPElement XMLsubject = XMLdocumentInformation
				.addChildElement("subject");

		// add values from documentInformation to the XML elements
		XMLdestination.addTextNode(documentInformation.getReceiver());
		XMLsubject.addTextNode(documentInformation.getSubject());
		XMLsender.addTextNode(documentInformation.getSender());
	}

	/**
	 * Deze methode bouwt de Body voor de SOAP message
	 */
	private void buildSOAPBody(Message document) throws SOAPException {
		// SOAP body
		SOAPBody soapBody = soapMessage.getSOAPBody();

		// namespace QName of the body element
		QName bodyName = new QName(nameSpace, "sendDocument");

		// add the body element to the body
		SOAPBodyElement bodyElement = soapBody.addBodyElement(bodyName);

		// This needs to be there because SOAP expects the namespaces
		// of the child elements to be empty, but still be there
		bodyElement.addAttribute(new QName("xmlns"), "");

		// create elements within the element we just created
		SOAPElement XMLdocument = bodyElement.addChildElement(new QName("",
				"message"));

		// and another element inside the document element
		SOAPElement XMLcontent = XMLdocument.addChildElement("content");
		XMLcontent.addTextNode(document.getContent());
	}

	/**
	 * Deze methode verzend de SOAPmessage en controlleerd het resultaat
	 */
	private void sendSOAPMessage(String url) throws MalformedURLException,
			SOAPException {
		// create a new url with the url String
		URL endpoint = new URL(url);

		// Send SOAP message to the endpoint, the response will be returned in
		// this variable
		soapResponse = connection.call(soapMessage, endpoint);

		// turn the response into String for printing
		final StringWriter stringWriter = new StringWriter();
		try {
			TransformerFactory
					.newInstance()
					.newTransformer()
					.transform(new DOMSource(soapResponse.getSOAPPart()),
							new StreamResult(stringWriter));
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}

		// Now you have the XML as a String:
		// We can easily print this
		System.out.println("The SOAP reponse that was returned:");
		System.out.println(stringWriter.toString());

		// We get the elements with the tagName success
		NodeList elementList = soapResponse.getSOAPBody().getElementsByTagName(
				"success");

		// We get te text value from success
		success = Boolean.parseBoolean(elementList.item(0).getTextContent());

		// We check if the receiver deemed our message acceptable
		if (success) {
			System.out.println("Everything went successful!");

		} else {
			System.out
					.println("Sorry, the receiver did not accept our message.");
		}

	}

	public boolean successfull() {
		return success;
	}
}
