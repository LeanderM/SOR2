package com.SOR2;

import static org.junit.Assert.assertTrue;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.SOR2.REST.SendDocument;

public class TestPost {

	SendDocument send;

	@Before
	public void init() {
		send = new SendDocument();
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
		JSONObject document = null;
		try {
			document = new JSONObject(
					"{'title':'sometitle','destination':'somedestination','content':'somecontent'}");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(send.send(document.toString()));
		// System.out.println("ayy lmao");
	}

}
