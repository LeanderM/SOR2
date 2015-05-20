package com.SOR2;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.junit.Before;
import org.junit.Test;

import com.SOR2.SOAP.SoapClientSSL;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;

public class services {
	private SOAPConnectionFactory soapConnectionFactory;
	private MessageFactory messageFactory;
	private SOAPMessage soapMessage;
	private SOAPPart soapPart;

	@Before
	public void setUp() {

	}

	@Test
	public void testSendValidMessage() {

		assert (sendMssg("Belastingsdienst", "Belastingsdienst", "subject",
				"mssg", 1234) == true);
	}

	@Test
	public void testSendMessageWithInvalidReceiver() {
		assert (sendMssg("Invalid!", "Belastingsdienst", "subject", "mssg",
				1234) == true);
	}

	@Test
	public void testSendMessageWithoutSubject() {
		assert (sendMssg("Belastingsdienst", "Belastingsdienst", null, "mssg",
				1234) == true);
	}

	private boolean sendMssg(String ontvanger, String sender, String subject,
			String message, int transactieId) {
		DocumentInformation docInfo = new DocumentInformation();
		docInfo.setReceiver(ontvanger);
		docInfo.setSender(sender);
		docInfo.setSubject(subject);

		Message messageContent = new Message();
		messageContent.setMessage(message);
		messageContent.setTransactionID(transactieId);

		SoapClientSSL client = new SoapClientSSL(docInfo, messageContent,
				"https://localhost:8443/services/documentreceiver?wsdl",
				"soap", "DocumentReceiver");

		client.sendSoapCall();
		return client.successFull();
	}
}
