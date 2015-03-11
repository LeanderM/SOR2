package com.SOR2.ontvanger;

import javax.jws.WebService;

import com.SOR2.SOAP.XMLObjects.Document;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.ResponseMessage;

@WebService(endpointInterface = "com.SOR2.ontvanger.DocumentReceiver", serviceName = "DocumentReceiver")
public class DocumentReceiverImpl implements DocumentReceiver {

	@Override
	public ResponseMessage sendDocument(
			DocumentInformation documentInformation, Document document) {

		if (documentInformation == null) {
			return new ResponseMessage(false,
					"| [Error]: No 'documentInformation' was found in header");
		} else if (document == null) {
			return new ResponseMessage(false,
					"| [Error]: No 'document' found in body");
		}

		System.out.println("[Header] Destination ="
				+ documentInformation.getDestination() + ", Information ="
				+ documentInformation.getTitle());
		System.out.println("[Document] Content =" + document.getContent());
		return new ResponseMessage(true, "No Errors");

	}
}
