package com.SOR2;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.SOR2.SOAP.PostHandler;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;

/**
 * JUnit test voor user story voor 1a, 1b en 2
 * 
 * @author Mark
 * @version 0.1.0
 *
 */
public class UserStoryTest1a1b2 {

	private PostHandler handler;
	private DocumentInformation documentInformation;
	private Message document;
	private String url;
	private String nameSpace;

	@Before
	public void init() {
		url = "http://localhost:8080/services/DocumentReceiver";
		documentInformation = new DocumentInformation();
		documentInformation.setReceiver("Belastingsdienst");
		documentInformation.setSubject("testTitle");
		documentInformation.setSender("mark");
		document = new Message();
		document.setContent("testcontent");
		nameSpace = "http://SOAP.SOR2.com/";

	}

	// <soap:Envelope xmlns:soap='http://www.w3.org/2001/12/soap-envelope'
	// soap:encodingStyle='http://www.w3.org/2001/12/soap-encoding'> <soap:Body>
	// <m:GetPrice
	// xmlns:m='http://www.w3schools.com/prices'> <m:Item>Apples</m:Item>
	// </m:GetPrice> </soap:Body> </soap:Envelope>
	// "<soap:Header><m:Trans xmlns:m='http://www.w3schools.com/transaction/' soap:mustUnderstand='1'>234 </m:Trans></soap:Header>"

	@Test
	public void test() {
		handler = new PostHandler(documentInformation, document, url, nameSpace);
		Assert.assertEquals(true, handler.successfull());

		// handler = new PostHandler(documentInformation, document, url);

	}
}
// einde