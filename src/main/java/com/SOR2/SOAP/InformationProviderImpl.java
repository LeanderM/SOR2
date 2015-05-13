package com.SOR2.SOAP;

import com.SOR2.hibernate.HibernateMain;

/*	
 * De implementatie van InformationProvider
 * 	Deze webservice is bedoelt om de status van berichten op te halen doormiddel van een ID
 */
public class InformationProviderImpl implements InformationProvider {

	/*
	 * Met deze SOAP methode kun je de status van een message ophalen op basis
	 * van een ID
	 */
	@Override
	public String getMessageStatus(String messageID) {

		// We get the status from HibernateMain
		String status = HibernateMain.getStatusByUUID(messageID);

		if (status.length() > 0) {
			return status;
		} else {
			return "";
		}

	}

}
