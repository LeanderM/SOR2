package com.SOR2.SOAP;

import javax.jws.WebService;

import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;
import com.SOR2.SOAP.XMLObjects.ResponseMessage;
import com.SOR2.hibernate.HibernateMain;

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
	 * Verwacht de volgende objecten: documentInformation, message
	 */
	@Override
	public ResponseMessage sendDocument(
			DocumentInformation documentInformation, Message message) {

		// We check if all the parameters are present
		if (documentInformation == null) {
			return new ResponseMessage(false,
					"| [Error]: No 'documentInformation' was found in header");
		} else if (message == null) {
			return new ResponseMessage(false,
					"| [Error]: No 'message' found in body");
		}

		// a more thorough validation
		DocumentValidator validator = new DocumentValidator(
				documentInformation, message);

		// check if valid
		if (validator.isValid()) {
			// we add the message to the dataBase.
			HibernateMain.addMessage(message.getMessage(),
					documentInformation.getSender(),
					documentInformation.getSubject(),
					documentInformation.getReceiver(), 1);

			// get the list of destinations
			DestinationHandler destination = new DestinationHandler(documentInformation.getReceiver());
	
			System.out.println(" TESTING " + destination.getUrl() + "  " + destination.getNameSpace());
			
			// Post the document
			PostHandler poster = new PostHandler(documentInformation,
					message,
					destination.getUrl(),
					destination.getNameSpace());
			return new ResponseMessage(true);

		} else {
			// if the message is invalid we will still keep it in the
			// dataBase
			// There is no point in saving if there is both no valid sender and receiver
			if(validator.receiverExists(documentInformation.getReceiver()) || validator.senderExists(documentInformation.getSender())) {
				HibernateMain.addInvallidMessage(message.getMessage(),
						documentInformation.getSender(),
						documentInformation.getSubject(),
						documentInformation.getReceiver(),
						validator.getStatusCode());
			}
			// in case the message was not valid we return all the errors
			return new ResponseMessage(false, validator.getErrors());
		}
	}
}
