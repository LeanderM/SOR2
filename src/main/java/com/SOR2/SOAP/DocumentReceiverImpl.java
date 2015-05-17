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

		// a more thorough validation
		DocumentValidator validator = new DocumentValidator(
				documentInformation, message);

		// We create a UUID
		UUID uuid = UUID.randomUUID();

		HibernateThreadObject hibernate = new HibernateThreadObject();

		// check if valid
		if (validator.isValid()) {

			// we add the message to the dataBase.
			int id = hibernate.addMessage(uuid, message.getMessage(),
					documentInformation.getSender(),
					documentInformation.getSubject(),
					documentInformation.getReceiver(), 1);

			// we add a new progress for this message
			// TODO dit moet gaan werken met UUID
/*			hibernate.addProgress(id,
					"Message successfully validated and ready for sending",
					true);*/

			// build the handler
			DestinationHandler destination = new DestinationHandler(
					documentInformation.getReceiver());

			// Post the document
			/*
			 * PostHandler poster = new PostHandler(documentInformation,
			 * message, destination.getUrl(), destination.getNameSpace(), id);
			 */

			boolean done = false;

			SoapClientSSL client = new SoapClientSSL(documentInformation,
					message, destination.getUrl(), destination.getNameSpace(),
					"");

			for (int i = 0; i < 5 && !done; i++) {
				// poster.executeSOAPRequest();
				client.sendSoapCall();
				// poster.successfull()
				if (client.successFull()) {
					done = true;
				} else {
					// we add a new progress for this message
					// TODO dit moet gaan werken met UUID
/*					hibernate.addProgress(id,
							"Sending was unsuccessful retrying in 15 seconds, "
									+ (5 - i) + " retries left", true);*/
					try {
						Thread.sleep(15000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

			if (done) {
				// we add a new progress for this message
				// TODO dit moet gaan werken met UUID
/*				hibernate.addProgress(id, "Message was succesfully delivered",
						true);*/
				return new ResponseMessage(true, uuid);
			} else {
				// we add a new progress for this message
				// TODO dit moet gaan werken met UUID
/*				hibernate
						.addProgress(
								id,
								"Sending was unsuccessful after 5 tries, stopped",
								true);*/
				validator
						.addError("Sending was unsuccessful after 5 tries, stopped");
				return new ResponseMessage(false, validator.getErrors(), uuid);
			}

		} else {
			// if the message is invalid we will still keep it in the
			// dataBase
			// There is no point in saving if there is both no valid sender and
			// receiver
			if (validator.receiverExists(documentInformation.getReceiver())
					|| validator.senderExists(documentInformation.getSender())) {
				int id = hibernate.addInvallidMessage(uuid,
						message.getMessage(), documentInformation.getSender(),
						documentInformation.getSubject(),
						documentInformation.getReceiver(),
						validator.getStatusCode());
				// we add a new progress for this message
				// TODO dit moet gaan werken met UUID
/*				hibernate.addProgress(id,
						"Message was validated unsuccessfully, stopped", false);*/
			}
			// in case the message was not valid we return all the errors
			return new ResponseMessage(false, validator.getErrors(), uuid);
		}
	}
}
