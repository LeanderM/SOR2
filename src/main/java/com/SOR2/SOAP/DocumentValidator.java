package com.SOR2.SOAP;

import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;
import com.SOR2.hibernate.HibernateMain;

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
	private DestinationList destinationList;
	private int statusCode;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public DocumentValidator(DocumentInformation documentInformation,
			Message document) {
		this.documentInformation = documentInformation;
		this.message = document;
		valid = true;
		statusCode = 0;
		destinationList = DestinationList.getInstance();

		validate();
	}

	private void validate() {
		validateDocumentInformation();
		validateDocument();
	}

	public boolean getReceiverexists(String usr) {
		return HibernateMain.checkUsrExists(usr);

	}

	private void validateDocumentInformation() {
		if (!documentInformation.hasReceiver()) {
			invalidate(2);
		} else if (!getReceiverexists(documentInformation.getReceiver())) {
			invalidate(3);
		}
		if (!documentInformation.hasSender()) {
			invalidate(4);
		} else if (!HibernateMain.checkUsrExists(documentInformation
				.getSender())) {
			invalidate(5);
		}
		if (!documentInformation.hasSubject()) {
			invalidate(6);
		}
	}

	private void validateDocument() {
		if (!message.hasContent() || message.getMessage().length() == 0) {
			invalidate(7);
		} else if (message.getTransactionID() == 0) {
			invalidate(8);
		} else if (message.getMessage().length() > 1000) {
			invalidate(9);
		}

	}

	private void invalidate(int errorCode) {
		valid = false;
		this.addError(HibernateMain.getStatusWithStatus_ID(errorCode));
		this.statusCode = errorCode;
	}

	public boolean isValid() {
		return valid;
	}

	private void addError(String error) {
		if (errors == null) {
			errors = "";
		}
		errors += "| [Error]: " + error;
	}

	public String getErrors() {
		return errors;
	}
}
