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
 * @version 0.1.0
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

	private void validateDocumentInformation() {
		if (!documentInformation.hasReceiver()) {
			invalid();
			addError("'documentInformation' Does not contain a 'receiver'");
			initiateStatusCode(11);
		} else if (!destinationList.hasKey(documentInformation.getReceiver())) {
			invalid();
			addError("the given 'receiver' does not match any existing receiver");
			initiateStatusCode(12);
		}
		if (!documentInformation.hasSender()) {
			invalid();
			addError("'documentInformation' Does not contain a 'sender'");
			initiateStatusCode(21);
		} else if (!HibernateMain.checkUsrExists(documentInformation
				.getSender())) {
			invalid();
			addError("the given 'sender' does not match any existing sender");
			initiateStatusCode(22);
		}
		if (!documentInformation.hasSubject()) {
			invalid();
			addError("'documentInformation' does not contain a 'subject'");
			initiateStatusCode(23);
		}
	}

	private void validateDocument() {
		if (!message.hasContent()) {
			invalid();
			addError("'message' does not contain any 'content'");
			initiateStatusCode(31);
		}
	}

	private void invalid() {
		valid = false;
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

	public void initiateStatusCode(int statusCode) {
		// If there is no statusCode add statusCode
		if (this.statusCode != 0) {
			this.statusCode = statusCode;
		}
	}
}
