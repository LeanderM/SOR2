package com.SOR2.REST;

import java.util.HashMap;

public class DestinationList {
	private static DestinationList singleTon;
	HashMap<String, String> lijst;

	protected DestinationList() {
		lijst = new HashMap<String, String>();
		lijst.put("Belastingsdienst",
				"http://localhost:8080/testservices/external/ontvanger/receive");
	}

	public static DestinationList getInstance() {
		if (singleTon == null) {
			singleTon = new DestinationList();
		}

		return singleTon;

	}

	public HashMap<String, String> getLijst() {
		return lijst;
	}

	public void setLijst(HashMap<String, String> lijst) {
		this.lijst = lijst;
	}

	public String getValue(String key) {
		return lijst.get(key);
	}

	public boolean hasKey(String key) {
		return lijst.containsKey(key);
	}

}
