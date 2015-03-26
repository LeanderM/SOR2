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
	HashMap<String, String> nameSpaceLijst;

	/**
	 * de constructor is protected omdat hij niet van buiten af mag worden
	 * aangeroepen
	 */
	protected DestinationList() {
		// De url van een destination
		lijst = new HashMap<String, String>();
		lijst.put("Belastingsdienst",
				"http://localhost:8080/testservices/DocumentReceiver");
		// De namespace van een destination
		nameSpaceLijst = new HashMap<String, String>();
		nameSpaceLijst.put("Belastingsdienst", "http://ontvanger.SOR2.com/");

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
	public String getURL(String key) {
		return lijst.get(key);
	}

	/**
	 * Returned true als de opgegeven String voorkomt in de HashMap Hiermee kun
	 * je controleren of een destination valide is
	 */
	public boolean hasKey(String key) {
		return lijst.containsKey(key);
	}
	
	/**
	 * getter van nameSpacelijst
	 */
	public HashMap<String, String> getNameSpaceLijst() {
		return nameSpaceLijst;
	}
	
	/**
	 * setter van nameSpacelijst
	 */
	public void setNameSpaceLijst(HashMap<String, String> nameSpaceLijst) {
		this.nameSpaceLijst = nameSpaceLijst;
	}
	
	/**
	 * methode die een value uit de nameSpacelijst ophaald op basis van een key
	 */
	public String getNameSpace(String key) {
		return nameSpaceLijst.get(key);
	}

}
