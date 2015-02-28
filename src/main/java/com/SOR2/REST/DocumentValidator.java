package com.SOR2.REST;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class DocumentValidator {
	JSONObject jsonDocument;

	public DocumentValidator(JSONObject jsonDocument) {
		this.jsonDocument = jsonDocument;
	}

	public boolean validate() {

		if (jsonDocument == null) {
			return false;
		}

		if (!hasTitle()) {
			return false;
		} else if (!hasDestionation()) {
			return false;
		} else if (!hasContent()) {
			return false;
		} else {
			return true;
		}

	}

	private boolean hasTitle() {
		if (jsonDocument.has("title")) {
			try {
				if (jsonDocument.get("title").toString().isEmpty()) {
					System.out.println("no value in 'title':");
					return false;
				}
			} catch (JSONException e) {
				System.out.println("no valid 'title':");
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	private boolean hasDestionation() {
		if (jsonDocument.has("destination")) {
			try {
				if (jsonDocument.get("destination").toString().isEmpty()) {
					System.out.println("no value in 'destination':");
					return false;
				}
			} catch (JSONException e) {
				System.out.println("no valid 'destination':");
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	private boolean hasContent() {
		if (jsonDocument.has("content")) {
			try {
				if (jsonDocument.get("content").toString().isEmpty()) {
					System.out.println("no value in 'content':");
					return false;
				}
			} catch (JSONException e) {
				System.out.println("no valid 'content:");
				e.printStackTrace();
			}
		} else {
			return false;
		}
		return true;
	}
}
