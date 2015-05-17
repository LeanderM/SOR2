package com.SOR2.THREADS;

import java.util.List;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;

import com.SOR2.SOAP.DocumentValidator;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;
import com.SOR2.hibernate.HibernateThreadObject;
import com.SOR2.hibernate.ValidationQueItem;

public class ValidationRunnable implements Runnable {

	private boolean running;
	private boolean cycle;
	private boolean sleep;
	private HibernateThreadObject hibernate;

	public ValidationRunnable() {
		running = false;
		cycle = false;
		sleep = false;
	}

	@Override
	public void run() {
		checkForSleep();
		System.out.println("starting ValidationThread");
		running = true;
		cycle = true;
		// new object of HibernateThreadObject a class especialy made to make
		// hibernate calls without
		// creating problems with resources between threads
		hibernate = new HibernateThreadObject();
		startValidating();
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	private void startValidating() {
		System.out.println("Start validating");
		// Variable that contains all the messages
		List<ValidationQueItem> queItems;

		while (running) {
			checkForSleep();

			cycle = false;
			System.out.println("Starting Validation Cycle");

			// hibernateMethod that gets all the messages from the
			// validationQue the type of casting works but is maybe not the
			// prettiest
			queItems = (List<ValidationQueItem>) (List<?>) hibernate
					.getAllValidationItems();
			// Check if there is at least one message in the Que
			System.out.println("Items to be validated: " + queItems.size());

			if (queItems.size() > 0) {
				// Start validating
				validateItems(queItems);
			}
			// We sleep seconds till the next validation cycle
			try {
				cycle = false;
				System.out.println("Ending Validation Cycle");
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private void validateItems(List<ValidationQueItem> queItems) {

		// Variable we use to temp store objects in
		ValidationQueItem validationQueItem;
		// documentInformation
		DocumentInformation documentInformation;
		// Message
		Message message;

		for (int i = 0; i < queItems.size(); i++) {
			// an Object Array that represent one message
			validationQueItem = queItems.get(i);
			// get the message and documentInformation from the array
			// with Objects
			documentInformation = new DocumentInformation(
					validationQueItem.getSender(),
					validationQueItem.getReceiver(),
					validationQueItem.getSubject());

			// TODO transactionID moet ook in Message komen maar bevindt zich
			// momenteel nog niet in validationQueItem, daarom gebruiken we een
			// test waarde (111)
			message = new Message(validationQueItem.getMessage(), 111);

			// a more thorough validation
			DocumentValidator validator = new DocumentValidator(
					documentInformation, message, hibernate);

			// check if valid
			if (validator.isValid()) {

				// TODO We want an ID earlier because the receiver also wants to
				// update the progress already
				// This could also be solved if we could set the id of the
				// Progress ourselves
				try {

					hibernate.addMessage(
							UUID.fromString(validationQueItem.getUuid()),
							message.getMessage(),
							documentInformation.getSender(),
							documentInformation.getSubject(),
							documentInformation.getReceiver(),
							validator.getStatusCode());

					hibernate.addSendQueItem(
							UUID.fromString(validationQueItem.getUuid()),
							message.getMessage(),
							documentInformation.getSender(),
							documentInformation.getSubject(),
							documentInformation.getReceiver(),
							validator.getStatusCode());

				} catch (ConstraintViolationException e) {
					e.printStackTrace();
				}

				// we add a new progress for this message
				hibernate.addProgress(validationQueItem.getUuid(),
						"Message successfully validated and ready for sending",
						true);

			} else {
				// if the message is invalid we will still keep it in
				// the
				// dataBase
				// There is no point in saving if there is both no valid
				// sender and
				// receiver
				if (validator.receiverExists(documentInformation.getReceiver())
						|| validator.senderExists(documentInformation
								.getSender())) {

					try {
						hibernate.addInvallidMessage(
								UUID.fromString(validationQueItem.getUuid()),
								message.getMessage(),
								documentInformation.getSender(),
								documentInformation.getSubject(),
								documentInformation.getReceiver(),
								validator.getStatusCode());
					} catch (ConstraintViolationException e) {
						e.printStackTrace();
					}
					// TODO method still needs to be made
					// TODO we want to update the status of this message

					// we add a new progress for this message
					hibernate.addProgress(validationQueItem.getUuid(),
							"Message was validated unsuccessfully, stopped",
							false);

				}
			}
			System.out.println("Validated: " + queItems.size() + " items");
			hibernate.deleteItemsFromDB(queItems);
		}
	}

	private void checkForSleep() {
		while (sleep) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isCycle() {
		return cycle;
	}

	public void setCycle(boolean cycle) {
		this.cycle = cycle;
	}

	public boolean isSleep() {
		return sleep;
	}

	public void setSleep(boolean sleep) {
		this.sleep = sleep;
	}
}
