package com.SOR2.ontvanger;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

import com.SOR2.SOAP.XMLObjects.Document;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.ResponseMessage;

@WebService
public interface DocumentReceiver {
	/**
	 * Een classe die dient als implementatie voor een tweede SOAPontvanger Deze
	 * classe is een directie kopie van de main DocumentReceiver uit de
	 * namespace: com.SOR2.SOAP
	 * 
	 * @author Jesse
	 * @version 0.1.0
	 *
	 */
	// controlleren hoe je @WebParam kan afdwingen
	@WebMethod(operationName = "sendDocument")
	ResponseMessage sendDocument(
			@WebParam(name = "documentInformation", header = true) DocumentInformation documentInformation,
			@WebParam(name = "document") @XmlElement(required = false, nillable = false) Document document);
}
