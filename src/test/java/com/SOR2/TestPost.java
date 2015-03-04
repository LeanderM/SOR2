package com.SOR2;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.SOR2.REST.SendDocument;

public class TestPost {

	SendDocument send;
	String document;

	// init
	@Before
	public void init() {
		send = new SendDocument();
		document = "{'title':'sometitle','destination':'somedestination','content':'somecontent'}";
		/*
		 * try { Start.main(null); } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * 
		 * try { wait(15000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

	}

	@Test
	public void test() {
		assertTrue(send.send(document.toString()));
		// System.out.println("ayy lmao");
	}

}
