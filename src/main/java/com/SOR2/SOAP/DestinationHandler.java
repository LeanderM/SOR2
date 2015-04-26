package com.SOR2.SOAP;

import java.util.List;

import com.SOR2.hibernate.HibernateMain;
import com.SOR2.hibernate.UserConnectData;

public class DestinationHandler {

	private String nameSpace;
	private String url;
	private boolean valid;

	public DestinationHandler(String userName) {
		retrieveDestinationInformation(userName);
	}

	/* De data ophalen */
	public void retrieveDestinationInformation(String userName) {
		List resultList = HibernateMain.getUserNamespaceAndUrl(userName);
		UserConnectData row = (UserConnectData) resultList.get(0);

		if (row.getNamespace() != null) {
			nameSpace = row.getNamespace();
		}

		if (row.getUrl() != null) {
			url = row.getUrl();
		}

		valid = checkIfValid();
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public String getUrl() {
		return url;
	}

	public boolean valid() {
		return valid;
	}

	/* check of alle waardes correct aanwezig zijn */
	private boolean checkIfValid() {
		if (nameSpace == null || url == null) {
			return false;
		} else if (!(url.length() > 0)) {
			return false;
		}
		return true;
	}
}
