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
		
		// TODO geen goede manier om te checken of message bestaat als valid of invalid	
		boolean test1 = false; 
		boolean test2 = false;
/*		test1 = HibernateMain.checkMessage_idExists(Integer.parseInt(messageID), true);
		test2 = HibernateMain.checkMessage_idExists(Integer.parseInt(messageID), false);*/
		
		if(test1){
			System.out.println("valid message");
		} else if(test2) {
			System.out.println("invalid message");
		} else {
			System.out.println("message does not exist");
		}
				
		if (messageID.length() > 0 ) {
			UUID uuid = UUID.fromString(messageID);
			

			
			// We get the status from HibernateMain
			String status = HibernateMain.getStatusByUUID(uuid, true);

			if (status.length() > 0) {
				return status;
			} 
			
			status = HibernateMain.getStatusByUUID(uuid, false);
			
			if(status.length() > 0)	{
				return status;
			}
			
			return "No valid status for this id was found, the UUID might be invalid";
			
		} else {
			return "No valid messageID";
		}

	}
}
