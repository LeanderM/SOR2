package com.SOR2.SOAP;

import java.util.UUID;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/*	
 * De interface van InformationProvider
 * 	Deze webservice is bedoelt om de status van berichten op te halen doormiddel van een ID
 */

@WebService
public interface InformationProvider {

	/*
	 * Met deze SOAP methode kun je de status van een message ophalen op basis
	 * van een ID
	 */
	@WebMethod(operationName = "getMessageStatus")
	String getMessageStatus(@WebParam(name = "messageID") String messageID);

}
