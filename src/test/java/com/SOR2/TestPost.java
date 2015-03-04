package com.SOR2;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.SOR2.REST.SendDocument;

public class TestPost {

	SendDocument send;
	String document;

	@Before
	public void init() {
		send = new SendDocument();
		document = "{'title':'sometitle','destination':'Belastingsdienst','content':'somecontent'}";
	}

	@Test
	public void test() {
		assertTrue(send.send(document.toString()));

	}

}
