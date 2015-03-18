package com.SOR2.SOAP;

import javax.jws.WebService;

import com.SOR2.SOAP.XMLObjects.Document;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.ResponseMessage;

/**
 * De DocumentReceiverImpl class is een implementatie van de SOAP interface
 * DocumentReceiver Deze classe heeft een methode die d.m.v een SOAPcall kan
 * worden aangeroepen Het hoofdzakelijke doel is het ontvangen van documenten
 * 
 * @author Jesse
 * @version 0.1.0
 *
 *          WSDL te vinden op
 *          http://localhost:8080/services/DocumentReceiver?wsdl
 */
@WebService(endpointInterface = "com.SOR2.SOAP.DocumentReceiver", serviceName = "DocumentReceiver")
public class DocumentReceiverImpl implements DocumentReceiver {
	/**
	 * Deze methode neemt SOAPcalls aan met DocumentInformation en Document
	 * Objecten De methode zorgt er voor dat ontvangen berichten gecontrolleerd
	 * worden De methode stuurt een ResponseMessage terug waarin te lezen is of
	 * het bericht aan de eisen voldoet, met mogelijk errors
	 * 
	 * Verwacht de volgende objecten: documentInformation, document
	 */
	@Override
	public ResponseMessage sendDocument(
			DocumentInformation documentInformation, Document document) {
		// We check if all the parameters are present
		if (documentInformation == null) {
			return new ResponseMessage(false,
					"| [Error]: No 'documentInformation' was found in header");
		} else if (document == null) {
			return new ResponseMessage(false,
					"| [Error]: No 'document' found in body");
		}

		// a more thorough validation
		DocumentValidator validator = new DocumentValidator(
				documentInformation, document);

		if (validator.isValid()) {
			DestinationList list = DestinationList.getInstance();
			PostHandler poster = new PostHandler(documentInformation, document,
					list.getURL(documentInformation.getDestination()),
					list.getNameSpace(documentInformation.getDestination()));
			return new ResponseMessage(true);
		}

		// in case the message was not valid we return all the errors
		return new ResponseMessage(false, validator.getErrors());

	}
}
