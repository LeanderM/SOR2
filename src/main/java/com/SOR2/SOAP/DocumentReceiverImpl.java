package com.SOR2.SOAP;

import java.util.UUID;

import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;
import com.SOR2.SOAP.XMLObjects.ResponseMessage;
import com.SOR2.hibernate.HibernateThreadObject;

/**
 * De DocumentReceiverImpl class is een implementatie van de SOAP interface
 * DocumentReceiver Deze classe heeft een methode die d.m.v een SOAPcall kan
 * worden aangeroepen Het hoofdzakelijke doel is het ontvangen van documenten
 *
 * @author Jesse
 * @version 0.1.0
 *
 *          WSDL te vinden op
 *          http://localhost:8443/services/DocumentReceiver?wsdl
 */
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
	public ResponseMessage sendDocument(Message message,
			DocumentInformation documentInformation) {

		// We check if all the parameters are present
		if (documentInformation == null) {
			return new ResponseMessage(false,
					"| [Error]: No 'documentInformation' was found in header");
		} else if (message == null) {
			return new ResponseMessage(false,
					"| [Error]: No 'message' found in body");
		}

		// new HibernateThreadObject that will handle all our db calls
		HibernateThreadObject hibernate = new HibernateThreadObject();

		// Check for sender and password
		if (documentInformation.hasSender()
				&& documentInformation.hasPassword()) {
			// Compare sender password pair to database entries,
			// if there are 0 entries returned it does not exist
			if (hibernate.checkLogin(documentInformation.getSender(),
					documentInformation.getPassword()).size() > 0) {
				// Do nothing username and password are correct
			} else {
				return new ResponseMessage(false,
						"| [Error]: No valid sender and password pair");
			}
		} else {
			return new ResponseMessage(false,
					"| [Error]: No valid sender and password pair");
		}

		// We create a UUID
		UUID uuid = UUID.randomUUID();

		// Status 1000 geeft aan dat een message nog moet worden gevalideerd
		hibernate.addValidationQueItem(uuid, message.getMessage(),
				documentInformation.getSender(),
				documentInformation.getSubject(),
				documentInformation.getReceiver(), 1000);

		// op dit punt in de progress is de message nog niet gevalideerd
		hibernate.addProgress(uuid.toString(),
				"Message succesfully saved and is ready for validation", false);

		return new ResponseMessage(true, uuid);
	}
}
