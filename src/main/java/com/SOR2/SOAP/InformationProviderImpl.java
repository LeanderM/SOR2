package com.SOR2.SOAP;

import java.util.UUID;

import com.SOR2.hibernate.HibernateMain;
import com.SOR2.hibernate.HibernateThreadObject;

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
		if (messageID.length() > 0
				&& messageID
						.matches("[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89ab][a-f0-9]{3}-[0-9a-f]{12}")) {

			HibernateThreadObject hibernate = new HibernateThreadObject();
			UUID uuid = UUID.fromString(messageID);

			boolean valid = false;
			boolean invalid = false;
			boolean inValidation = false;

			valid = hibernate.checkUUIDExistsInMessageOrInvallid(uuid, true);
			invalid = hibernate.checkUUIDExistsInMessageOrInvallid(uuid, false);
			inValidation = hibernate.checkUUIDExistsInValidationQue(uuid);

			String status;
			if (valid) {
				status = HibernateMain.getStatusByUUID(uuid, true);
				if (status.length() > 0) {
					return status;
				} else {
					return "Problem occured while getting status for valid message";
				}
			} else if (invalid) {
				status = HibernateMain.getStatusByUUID(uuid, false);
				if (status.length() > 0) {
					return status;
				} else {
					return "Problem occured while getting status for invalid message";
				}
			} else if (inValidation) {
				return "Message is being validated";
			}

			return "No valid status for this id was found, the UUID might be invalid";

		} else {
			return "No valid messageID";
		}

	}
}
