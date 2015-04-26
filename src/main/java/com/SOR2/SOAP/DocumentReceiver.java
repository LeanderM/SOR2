package com.SOR2.SOAP;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;
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
	 * Een SOAP methode die DocumentInformation en message als parameters heeft.
	 */
	@WebMethod(operationName = "sendDocument")
	ResponseMessage sendDocument(
			// DocumentInformation moet in de header
			// @Valid geeft aan dat aan alle validatieregels van de
			// DocumentInformation class moet worden voldaan
			/*@NotNull(message = "documentInformation can not be null") @Valid */@WebParam(name = "documentInformation", header = true) @XmlElement(name = "documentInformation", required = true, nillable = false) DocumentInformation documentInformation,
			/*@NotNull */ @WebParam(name = "message") Message message);
}
