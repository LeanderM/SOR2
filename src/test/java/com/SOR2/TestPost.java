package com.SOR2;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.SOR2.REST.SendDocument;

/**
 * JUnit test voor user story voor 1a, 1b en 2
 * 
 * @author Mark
 * @version 0.1.0
 *
 */
public class TestPost {

	// bevat een instantie van de SendDocument klasse
	private SendDocument send;
	// bevat een instantie van een String dat een JSON object moet voorstellen.
	// Er staat een voorbeeld in de init() methode
	private String document;

	/**
	 * Initieerd de benodigde objecten om een test mee uit te kunnen voeren
	 * 
	 */
	@Before
	public void init() {
		send = new SendDocument();
		document = "{'title':'sometitle','destination':'Belastingsdienst','content':'somecontent'}";
	}

	/**
	 * De werkelijke test om te kijken of wij voldoen aan de eisen van user
	 * story 1a b en 2 Het verwacht dat er true teruggegeven wordt. Terwijl de
	 * test draait wordt het meegegeven JSON object uitgeprint in de consoles
	 *
	 */
	@Test
	public void test() {
		assertTrue(send.send(document.toString()));

	}

}
