package com.SOR2.SOAP;

import java.util.List;
import java.util.UUID;

import com.SOR2.hibernate.HibernateThreadObject;
import com.SOR2.hibernate.InvallidMessage;
import com.SOR2.hibernate.ValidationQueItem;

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
	public String getMessageStatus(String username, String password,
			String messageID) {

		// Regular expression to check if the String resembles the UUID format
		if (messageID.length() > 0
				&& messageID
						.matches("[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89ab][a-f0-9]{3}-[0-9a-f]{12}")) {

			HibernateThreadObject hibernate = new HibernateThreadObject();

			// Check for sender and password
			if ((username != null && username.length() > 0)
					&& (password != null && password.length() > 0)) {
				// Compare sender password pair to database entries,
				// if there are 0 entries returned it does not exist
				if (hibernate.checkLogin(username, password).size() > 0) {
					// Do nothing username and password are correct
				} else {
					return "| [Error]: No valid sender and password pair";
				}
			} else {
				return "| [Error]: No valid username and password pair";
			}

			UUID uuid = UUID.fromString(messageID);

			boolean valid = false;
			boolean invalid = false;
			boolean inValidation = false;

			valid = hibernate.checkUUIDExistsInMessageOrInvallid(uuid, true);
			invalid = hibernate.checkUUIDExistsInMessageOrInvallid(uuid, false);
			inValidation = hibernate.checkUUIDExistsInValidationQue(uuid);

			String status;
			// We check if the user has access to this message
			boolean validAccess = false;
			if (valid) {
				status = hibernate.getStatusByUUID(uuid, true);
				if (status.length() > 0) {
					return status;
				} else {
					return "Problem occured while getting status for valid message";
				}
			} else if (invalid) {
				List valItem = hibernate.getMessageForUUID(uuid, false);

				if (!(valItem.size() > 0)) {
					return "something went wrong retrieving status of existing invalid message please try again";
				}

				InvallidMessage invMessage = (InvallidMessage) valItem.get(0);

				if (invMessage.getSender() != null
						&& invMessage.getSender().length() > 0) {
					if (invMessage.getSender().equals(username)) {
						validAccess = true;
					}
				}
				//
				if (invMessage.getReceiver() != null
						&& invMessage.getReceiver().length() > 0) {
					if (invMessage.getReceiver().equals(username)) {
						validAccess = true;
					}
				}

				if (!validAccess) {
					return "You don't have access to the status of this message";
				}

				status = hibernate.getStatusByUUID(uuid, false);

				if (status.length() > 0) {
					return status;
				} else {
					return "Problem occured while getting status for invalid message";
				}

			} else if (inValidation) {
				ValidationQueItem valItem = hibernate
						.getValidationItemByUUID(uuid);

				if (valItem == null) {
					return "something went wrong retrieving status of existing message please try again";
				}

				// check if the user is the sender
				if (valItem.getSender() != null
						&& valItem.getSender().length() > 0) {
					if (valItem.getSender().equals(username)) {
						validAccess = true;
					}
				}

				// check if the user is the receiver
				if (valItem.getReceiver() != null
						&& valItem.getReceiver().length() > 0) {
					if (valItem.getReceiver().equals(username)) {
						validAccess = true;
					}
				}

				// if the user is either the receiver or send of the message
				// he can see the message
				if (!validAccess) {
					return "You don't have access to the status of this message";

				}

				return "Message is being validated";
			}

			return "No valid status for this id was found, the UUID might be invalid";

		} else {
			return "No valid messageID";
		}

	}
}
