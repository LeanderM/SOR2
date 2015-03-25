package com.SOR2.SOAP;

import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;

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
	DocumentInformation documentInformation;
	Message message;
	Boolean valid;
	String errors;
	DestinationList destinationList;

	public DocumentValidator(DocumentInformation documentInformation,
			Message document) {
		this.documentInformation = documentInformation;
		this.message = document;
		valid = true;
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
		} else if (!destinationList.hasKey(documentInformation.getReceiver())) {
			invalid();
			addError("the given 'destination' does not match any existing receiver");
		}
		if (!documentInformation.hasSubject()) {
			invalid();
			addError("'documentInformation' does not contain a 'subject'");
		}

	}

	private void validateDocument() {
		if (!message.hasContent()) {
			invalid();
			addError("'message' does not contain any 'content'");
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
}
