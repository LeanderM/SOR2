package com.SOR2.SOAP;

import java.net.MalformedURLException;
import java.net.URL;
import com.SOR2.ontvanger.*;
import com.SOR2.SOAP.XMLObjects.*;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class SoapClientSSL {
	
	private URL url;
	private QName serviceQName;
	private Service service;
	private com.SOR2.ontvanger.DocumentReceiver receiver;
	private DocumentInformation documentInformation;
	private Message message;
	private boolean succesFull;
	
	static {
	    //for localhost testing only
		// anders werkt het verzenden met SSL selfsigned-certificaat niet lekker met de hostname localhost
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){
 
	        public boolean verify(String hostname,
	                javax.net.ssl.SSLSession sslSession) {
	            if (hostname.equals("localhost")) {
	                return true;
	            }
	            return false;
	        }
	    });
	}
 
	public SoapClientSSL(DocumentInformation documentInformation, Message message, String url, String nameSpace, String serviceName) {
		
		try {
			
			// false by default
			succesFull = false;
			// save to variable
			this.documentInformation = documentInformation;
			// save to variable
			this.message = message;
			// url
			this.url = new URL("https://localhost:8443/services/testreceiver?wsdl");
			// nameSpace, serviceName
			serviceQName = new QName("http://ontvanger.SOR2.com/", "TestReceiver");
			// create the service using the QName and URL
			service = Service.create(this.url, serviceQName);			
			// We get the interface class so we can use it to see what methods are available 
			receiver = service.getPort(com.SOR2.ontvanger.DocumentReceiver.class);
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	
	}
	
	public void sendSoapCall() {
		// we call the method
		ResponseMessage response = receiver.sendDocument(message, documentInformation);
		
		// if nothing was returned sending the message failed
		if(response == null) {
			succesFull = false;
		}
		
		succesFull = response.getSuccess();
	}
	
	public boolean successFull(){
		return succesFull;
	}
}
