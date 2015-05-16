package com.SOR2.THREADS;

import java.util.List;
import java.util.UUID;

import com.SOR2.SOAP.DocumentValidator;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;
import com.SOR2.hibernate.HibernateMain;
import com.SOR2.hibernate.HibernateThreadObject;
import com.SOR2.hibernate.ValidationQueItem;

public class ValidationRunnable implements Runnable {

	private boolean running;
	private HibernateThreadObject hibernate;

	@Override
	public void run() {
		System.out.println("starting ValidationThread");
		running = true;
		// new object of HibernateThreadObject a class especialy made to make
		// hibernate calls without
		// creating problems with resources between threads
		hibernate = new HibernateThreadObject();
		int i = 0;
		while (running) {
			System.out.println("ValidationThread is running Cycle:" + i++);
			try {
				System.out.println("ValidationThread going to sleep");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	private void startValidating() {
		// boolean that tell if there are messages in the que
		boolean messagesInQue;
		// Variable that contains all the messages
		List<ValidationQueItem> queItems;
		// Variable we use to temp store object arrays in
		ValidationQueItem validationQueItem;
		// documentInformation
		DocumentInformation documentInformation;
		// Message
		Message message;

		while (running) {
			// Check if there is at least one message in the Que
			if (hibernate.getFirstValidationQueItem() != null) {

				// hibernateMethod that gets all the messages from the
				// validationQue the type of casting works but is maybe not the
				// pretiest
				queItems = (List<ValidationQueItem>) (List<?>) hibernate
						.getAllValidationItems();

				for (int i = 0; i < queItems.size(); i++) {
					// an Object Array that represent one message
					validationQueItem = queItems.get(0);
					// get the message and documentInformation from the array
					// with Objects
					documentInformation = new DocumentInformation(
							validationQueItem.getSender(),
							validationQueItem.getReceiver(),
							validationQueItem.getSubject());
					
					// TODO transactionID moet ook in Message komen maar bevindt zich momenteel nog niet in validationQueItem
					message = new Message(validationQueItem.getMessage());

					// a more thorough validation
					DocumentValidator validator = new DocumentValidator(
							documentInformation, message);

					// check if valid
					if (validator.isValid()) {

						// TODO We want an ID earlier because the receiver also wants to update the progress already
						// This could also be solved if we could set the id of the Progress ourselves
						int id = hibernate.addMessage(UUID.fromString(validationQueItem.getUuid()), message.getMessage(), documentInformation.getSender(), documentInformation.getSubject(), documentInformation.getReceiver(), 0);
						
						
						hibernate.addSendQueItem(UUID.fromString(validationQueItem.getUuid()), message.getMessage(), documentInformation.getSender(), documentInformation.getSubject(), documentInformation.getReceiver(), 5);
						
						// TODO method still needs to be made
						// we add a new progress for this message
						HibernateMain
								.addProgress(
										validationQueItem.getMessageID(),
										"Message successfully validated and ready for sending",
										true);

					} else {
						// if the message is invalid we will still keep it in
						// the
						// dataBase
						// There is no point in saving if there is both no valid
						// sender and
						// receiver
						if (validator.receiverExists(documentInformation
								.getReceiver())
								|| validator.senderExists(documentInformation
										.getSender())) {
							
							// TODO We want an ID earlier because the receiver also wants to update the progress already
							int id = hibernate.addInvallidMessage(UUID.fromString(validationQueItem.getUuid()), message.getMessage(), documentInformation.getSender(), documentInformation.getSubject(), documentInformation.getReceiver(), 0);
							// TODO method still needs to be made
							// TODO we want to update the status of this message
							// we add a new progress for this message
							HibernateMain
									.addProgress(
											validationQueItem.getMessageID(),
											"Message was validated unsuccessfully, stopped",
											false);
						}
					}
				}
			}
			Thread.sleep(10000);
		}
	}
}
