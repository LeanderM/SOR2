package com.SOR2.SOAP;

import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;
import com.SOR2.hibernate.HibernateMain;
import com.SOR2.hibernate.HibernateThreadObject;

/**
 * De DocumentValidator kan controlleerd Document en DocumentInformation
 * objecten op een aantal criteria Tijdens deze controle houd hij een errors
 * String bij waar alle errors in komen te staan
 * 
 * @author Jesse
 * @version 1.0.0
 *
 */
public class DocumentValidator {
	private DocumentInformation documentInformation;
	private Message message;
	private Boolean valid;
	private String errors;
	private int statusCode;
	HibernateThreadObject hibernate;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public DocumentValidator(DocumentInformation documentInformation,
			Message document, HibernateThreadObject hibernate) {
		this.documentInformation = documentInformation;
		this.message = document;
		valid = true;
		statusCode = 1;
		this.hibernate = hibernate;

		validate();
	}

	private void validate() {
		validateDocumentInformation();
		validateDocument();
	}

	public boolean receiverExists(String usr) {
		return HibernateMain.checkUsrExists(usr);

	}

	public boolean senderExists(String usr) {
		return HibernateMain.checkUsrExists(usr);
	}

	private void validateDocumentInformation() {
		if (!documentInformation.hasReceiver()) {
			invalidate(2);

		} else if (!receiverExists(documentInformation.getReceiver())) {
			invalidate(3);
		} else {
			checkDestinationInfo();
		}
		if (!documentInformation.hasSender()) {
			invalidate(4);
		} else if (!senderExists(documentInformation.getSender())) {
			invalidate(5);
		}
		if (!documentInformation.hasSubject()) {
			invalidate(6);
		}
	}

	private void validateDocument() {
		if (!message.hasContent()) {
			invalidate(7);
		} else if (message.getTransactionID() == 0) {
			invalidate(8);
		} else if (message.getMessage().length() > 1000) {
			invalidate(9);
		}

	}

	private void invalidate(int errorCode) {
		valid = false;
		this.addError(hibernate.getStatusWithStatus_ID(errorCode));

		// Er is nu maar support voor één statusCode per bericht
		if (statusCode == 1) {
			this.statusCode = errorCode;
		}
	}

	private void checkDestinationInfo() {
		DestinationHandler destination = new DestinationHandler(
				documentInformation.getReceiver());
		if (!destination.valid()) {
			invalidate(10);
		}
	}

	public boolean isValid() {
		return valid;
	}

	public void addError(String error) {
		if (errors == null) {
			errors = "";
		}
		errors += "| [Error]: " + error + "\n";
	}

	public String getErrors() {
		return errors;
	}

}
