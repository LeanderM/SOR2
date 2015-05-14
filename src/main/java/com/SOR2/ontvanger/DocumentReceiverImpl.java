package com.SOR2.ontvanger;

import javax.jws.WebService;

import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;
import com.SOR2.SOAP.XMLObjects.ResponseMessage;

/**
 * Een classe die dient als implementatie voor een tweede SOAPontvanger Deze
 * classe is een directie kopie van de main DocumentReceiverImpl uit de
 * namespace: com.SOR2.SOAP
 * 
 * @author Jesse
 * @version 0.1.0
 * 
 *          WSDL te vinden op
 *          http://localhost:8080/testservices/DocumentReceiver?wsdl
 */
@WebService(endpointInterface = "com.SOR2.ontvanger.DocumentReceiver")
public class DocumentReceiverImpl implements DocumentReceiver {

	@Override
	public ResponseMessage sendDocument(
			DocumentInformation documentInformation, Message message) {

		if (documentInformation == null) {
			return new ResponseMessage(false,
					"| [Error]: No 'documentInformation' was found in header");
		} else if (message == null) {
			return new ResponseMessage(false,
					"| [Error]: No 'message' found in body");
		}
		System.out.println("New SOAP message received!..");
		System.out.println("[Header] Receiver = "
				+ documentInformation.getReceiver() + ", Subject = "
				+ documentInformation.getSubject());
		System.out.println("[Document] Content = " + message.getMessage());
		return new ResponseMessage(true, "No Errors");

	}
}
