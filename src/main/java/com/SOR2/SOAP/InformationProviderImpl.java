package com.SOR2.SOAP;

import java.util.UUID;

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
		
		// Regular expression to check if the String resembles the UUID format
		if (messageID.length() > 0 && messageID.matches("[a-f0-9]{8}-[a-f0-9]{4}-4[0-9]{3}-[89ab][a-f0-9]{3}-[0-9a-f]{12}")) {

			UUID uuid = UUID.fromString(messageID);
	
			boolean valid = false; 
			boolean invalid = false;
			
			// TODO dit moet worden herzien aangezien de structuur van de db verandert is
			// de methode moet gaan zoeken in alle 3 de tabellen
			valid = HibernateMain.checkUUIDExists(uuid, true);
			invalid = HibernateMain.checkUUIDExists(uuid, false);
			
			String status;
			if(valid){
				System.out.println("valid message");
				status = HibernateMain.getStatusByUUID(uuid, true);
				if (status.length() > 0) {
					return status;
				} 
			} else if(invalid) {
				System.out.println("invalid message");
				status = HibernateMain.getStatusByUUID(uuid, false);
				if(status.length() > 0)	{
					return status;
				}
			} else {
				System.out.println("message does not exist");
			}
			
			return "No valid status for this id was found, the UUID might be invalid";
			
		} else {
			return "No valid messageID";
		}

	}
}
