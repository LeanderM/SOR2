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
			
		// We create a UUID
		UUID uuid = UUID.randomUUID();

		HibernateThreadObject hibernate = new HibernateThreadObject();
		
		//TODO er moet iets aan status gedaan worden validatie levert namelijk een status op, maar dat gebeurd nog niet hier
		hibernate.addValidationQueItem(uuid, message.getMessage(), documentInformation.getSender(), documentInformation.getSubject(), documentInformation.getReceiver(), 1);
		
		//TODO op dit punt in de progress is de message nog niet gevalideerd
		hibernate.addProgress(uuid.toString(), "Message succesfully saved and is ready for validation", false);
		
		return new ResponseMessage(true, uuid);
	}
}
