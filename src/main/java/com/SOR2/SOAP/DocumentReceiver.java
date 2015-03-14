package com.SOR2.SOAP;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.SOR2.SOAP.XMLObjects.Document;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.ResponseMessage;

/**
 * De DocumentReceiver interface, geeft aan hoe de SOAP service er uit ziet
 * 
 * @author Jesse
 * @version 0.1.0
 *
 */
@WebService
public interface DocumentReceiver {

	/**
	 * Een methode die DocumentInformation en Document als parameters heeft.
	 * 
	 */
	@WebMethod(operationName = "sendDocument")
	ResponseMessage sendDocument(
			// DocumentInformation moet in de header
			@WebParam(name = "documentInformation", header = true) DocumentInformation documentInformation,
			@WebParam(name = "document") Document document);
}
