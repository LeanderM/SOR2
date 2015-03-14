package com.SOR2.SOAP;

import com.SOR2.SOAP.XMLObjects.Document;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;

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
	Document document;
	Boolean valid;
	String errors;
	DestinationList destinationList;

	public DocumentValidator(DocumentInformation documentInformation,
			Document document) {
		this.documentInformation = documentInformation;
		this.document = document;
		valid = true;
		destinationList = DestinationList.getInstance();

		validate();
	}

	private void validate() {
		validateDocumentInformation();
		validateDocument();
	}

	private void validateDocumentInformation() {
		if (!documentInformation.hasDestionation()) {
			invalid();
			addError("'documentInformation' Does not contain a 'destination'");
		} else if (!destinationList
				.hasKey(documentInformation.getDestination())) {
			invalid();
			addError("the given 'destination' does not match any existing destination");
		}
		if (!documentInformation.hasTitle()) {
			invalid();
			addError("'documentInformation' does not contain a 'title'");
		}

	}

	private void validateDocument() {
		if (!document.hasContent()) {
			invalid();
			addError("'document' does not contain any 'content'");
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
