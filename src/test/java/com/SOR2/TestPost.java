package com.SOR2;

import org.junit.Before;
import org.junit.Test;

import com.SOR2.SOAP.PostHandler;
import com.SOR2.SOAP.XMLObjects.Document;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;

/**
 * JUnit test voor user story voor 1a, 1b en 2
 * 
 * @author Mark
 * @version 0.1.0
 *
 */
public class TestPost {

	private PostHandler handler;
	private DocumentInformation documentInformation;
	private Document document;
	private String url;

	@Before
	public void init() {
		url = "http://localhost:8080/services/DocumentReceiver";
		documentInformation = new DocumentInformation();
		documentInformation.setDestination("http://localhost:8080/testservices/DocumentReceiver");
		documentInformation.setTitle("testTitle");
		document = new Document();
		document.setContent("testcontent");
		
	}

	// <soap:Envelope xmlns:soap='http://www.w3.org/2001/12/soap-envelope'
	// soap:encodingStyle='http://www.w3.org/2001/12/soap-encoding'> <soap:Body>
	// <m:GetPrice
	// xmlns:m='http://www.w3schools.com/prices'> <m:Item>Apples</m:Item>
	// </m:GetPrice> </soap:Body> </soap:Envelope>
	// "<soap:Header><m:Trans xmlns:m='http://www.w3schools.com/transaction/' soap:mustUnderstand='1'>234 </m:Trans></soap:Header>"

	@Test
	public void test() {
		handler = new PostHandler(documentInformation, document, url);

	}

}
// einde