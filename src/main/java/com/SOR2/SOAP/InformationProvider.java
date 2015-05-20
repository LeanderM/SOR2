package com.SOR2.SOAP;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/*	
 * De interface van InformationProvider
 * 	Deze webservice is bedoelt om de status van berichten op te halen doormiddel van een ID
 */

@WebService(serviceName = "InformationProvider", targetNamespace = "https://SOAP.SOR2.com")
public interface InformationProvider {

	/*
	 * Met deze SOAP methode kun je de status van een message ophalen op basis
	 * van een ID
	 */

	@WebMethod(operationName = "getMessageStatus")
	String getMessageStatus(@WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "messageID") String messageID);

}
