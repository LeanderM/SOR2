package com.SOR2;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.SOR2.SOAP.PostHandler;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;
import com.SOR2.hibernate.HibernateMain;
import com.SOR2.hibernate.Messages;

/**
 * JUnit test voor user story voor 1a, 1b en 2
 * 
 * @author Mark
 * @version 0.1.0
 *
 */
public class UserStoryTesting {

	private PostHandler handler;
	private DocumentInformation documentInformation;
	private Message document;
	private String url;
	private String nameSpace;
	private String nameToCheck;

	@Before
	public void init() {
		BeBelastingsdienstBelastingsdienstBelastingsdienstlastingsdienst nameToCheck = "test";
		url = "http://localhost:8080/services/DocumentReceiver";
		documentInformation = new DocumentInformation();
		documentInformation.setReceiver("jesse");
		documentInformation.setSubject("testTitle");
		documentInformation.setSender(nameToCheck);
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
	public void test1a1b() {
		handler = new PostHandler(documentInformation, document, url, nameSpace);
		Assert.assertEquals(true, handler.successfull());

	}

	@Test
	public void test2a() {
		handler = new PostHandler(documentInformation, document, url, nameSpace);
		Assert.assertEquals(true, HibernateMain.checkUsrExists(nameToCheck));
		List toCheck = HibernateMain.getMailForAdmin(nameToCheck);

		boolean doesTheNameExistInAResult = false;

		for (Object obj : toCheck) {
			Messages looped = (Messages) obj;
			System.out.println(looped.getReceiver() + " " + nameToCheck);
			if (looped.getReceiver().equals(nameToCheck)) {
				doesTheNameExistInAResult = true;
			} else {
				doesTheNameExistInAResult = false;
			}
		}

		Assert.assertEquals(true, doesTheNameExistInAResult);

	}
}

// einde