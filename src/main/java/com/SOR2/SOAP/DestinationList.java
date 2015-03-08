package com.SOR2.SOAP;

import java.util.HashMap;

/**
 * Dit is een singleton klasse die een lijst met destination-namen en
 * destination-urls bijhoud
 * 
 * @author Jesse
 * @version 0.1.0
 *
 */
public class DestinationList {

	/**
	 * De singleton variabele
	 */
	private static DestinationList singleTon;

	/**
	 * Een hashmap met een String als key en een String als value Hier komen de
	 * destination namen met de destination urls in te staan
	 */
	HashMap<String, String> lijst;

	/**
	 * de constructor is protected omdat hij niet van buiten af mag worden
	 * aangeroepen
	 */
	protected DestinationList() {
		lijst = new HashMap<String, String>();
		lijst.put("Belastingsdienst",
				"http://localhost:8080/testservices/external/ontvanger/receive");
	}

	/**
	 * De singleTon methode getInstance zorgt er voor dat er geen meerdere
	 * instanties van DestinationList tegelijk kunnen bestaand
	 */
	public static DestinationList getInstance() {
		if (singleTon == null) {
			singleTon = new DestinationList();
		}
		return singleTon;
	}

	/**
	 * Returend de HashMap
	 */
	public HashMap<String, String> getLijst() {
		return lijst;
	}

	/**
	 * Set de HashMap
	 */
	public void setLijst(HashMap<String, String> lijst) {
		this.lijst = lijst;
	}

	/**
	 * Haald een url value op, op basis van de destination naam (key)
	 */
	public String getValue(String key) {
		return lijst.get(key);
	}

	/**
	 * Returned true als de opgegeven String voorkomt in de HashMap Hiermee kun
	 * je controleren of een destination valide is
	 */
	public boolean hasKey(String key) {
		return lijst.containsKey(key);
	}
}
