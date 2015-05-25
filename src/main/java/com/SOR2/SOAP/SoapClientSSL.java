package com.SOR2.SOAP;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;
import com.SOR2.SOAP.XMLObjects.ResponseMessage;

/**
 * De SOAPclient klasse kan op basis van een DocumentInformation, Message, url,
 * namespace, serviceName een SOAP bericht opstellen en verzenden
 * 
 * @author Jesse
 * @version 0.1.0
 *
 */

public class SoapClientSSL {

	private URL url;
	private QName serviceQName;
	private Service service;
	private com.SOR2.ontvanger.DocumentReceiver receiver;
	private DocumentInformation documentInformation;
	private Message message;
	private boolean succesFull;

	/**
	 * Stuk code omdat we met een self-signed sertificate werken
	 */
	static {
		// for localhost testing only
		// anders werkt het verzenden met SSL selfsigned-certificaat niet lekker
		// met de hostname localhost
		javax.net.ssl.HttpsURLConnection
				.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

					public boolean verify(String hostname,
							javax.net.ssl.SSLSession sslSession) {
						if (hostname.equals("localhost")) {
							return true;
						}
						return false;
					}
				});
	}

	/**
	 * Constructor
	 */
	public SoapClientSSL(DocumentInformation documentInformation,
			Message message, String url, String nameSpace, String serviceName) {

		try {

			// false by default
			succesFull = false;
			// save to variable
			this.documentInformation = documentInformation;
			// save to variable
			this.message = message;
			// url "https://localhost:8443/services/testreceiver?wsdl"
			this.url = new URL(url);
			// nameSpace "http://ontvanger.SOR2.com/" , serviceName
			serviceQName = new QName(nameSpace, serviceName);
			// create the service using the QName and URL
			service = Service.create(this.url, serviceQName);
			// We get the interface class so we can use it to see what methods
			// are available
			receiver = service
					.getPort(com.SOR2.ontvanger.DocumentReceiver.class);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * roep de sendDocument methode aan van DocumentReceiver via SOAP
	 */
	public void sendSoapCall() {
		// we call the method
		ResponseMessage response = receiver.sendDocument(message,
				documentInformation);

		// if nothing was returned sending the message failed
		if (response == null) {
			succesFull = false;
		}

		succesFull = response.getSuccess();
	}

	/**
	 * getter van succesfull
	 */
	public boolean successFull() {
		return succesFull;
	}
}
