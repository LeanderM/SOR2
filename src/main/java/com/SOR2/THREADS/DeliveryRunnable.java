package com.SOR2.THREADS;

import java.util.List;

import com.SOR2.SOAP.DestinationHandler;
import com.SOR2.SOAP.SoapClientSSL;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;
import com.SOR2.hibernate.HibernateMain;
import com.SOR2.hibernate.HibernateThreadObject;
import com.SOR2.hibernate.SendQueItem;

/**
 * Runnable klasse die berichten uit de DeliveryQue verzend
 * 
 * @author Jesse
 * @version 0.1.0
 *
 */

public class DeliveryRunnable implements Runnable {

	private boolean running;
	private boolean cycle;
	private boolean sleep;
	private HibernateThreadObject hibernate;

	/**
	 * Constructor
	 *
	 */
	public DeliveryRunnable() {
		running = false;
		cycle = false;
		sleep = false;
	}

	/**
	 * Run method that gets called when starting the thread
	 *
	 */
	@Override
	public void run() {
		checkForSleep();
		System.out.println("starting DeliveryThread");
		running = true;
		setCycle(true);
		// new object of HibernateThreadObject a class especialy made to make
		// hibernate calls without
		// creating problems with resources between threads
		hibernate = new HibernateThreadObject();
		startDelivering();
	}

	/**
	 * getter for running
	 *
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * setter for running
	 *
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * method with the while loop, that will be looping until running is set to
	 * false
	 *
	 */
	private void startDelivering() {
		// List with queItems
		List<SendQueItem> queItems;

		// Variable we use to temp store object arrays in
		Object[] messageArray;
		// documentInformation
		DocumentInformation documentInformation;
		// Message
		Message message;

		while (running) {
			checkForSleep();
			cycle = true;
			System.out.println("Start delivery cycle");
			queItems = (List<SendQueItem>) (List<?>) hibernate
					.getAllSendQueItems();

			System.out.println("Items to be send: " + queItems.size());
			if (queItems.size() > 0) {
				deliverItems(queItems);
			}
			try {
				System.out.println("Ending delivery Cycle");
				cycle = false;
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * method that delivers the queItems
	 *
	 */
	private void deliverItems(List<SendQueItem> queItems) {

		// Variable we use to temp store objects in
		SendQueItem sendQueItem;
		// documentInformation
		DocumentInformation documentInformation;
		// Message
		Message message;

		for (int i = 0; i < queItems.size(); i++) {
			// an Object Array that represent one message
			sendQueItem = queItems.get(i);
			// get the message and documentInformation from the array
			// with Objects
			documentInformation = new DocumentInformation(
					sendQueItem.getSender(), sendQueItem.getReceiver(),
					sendQueItem.getSubject());
			message = new Message(sendQueItem.getMessage(), 111);

			// build the handler
			DestinationHandler destination = new DestinationHandler(
					documentInformation.getReceiver());
			// boolean to check if sending was successfull
			boolean done = false;

			SoapClientSSL client = new SoapClientSSL(documentInformation,
					message, destination.getUrl(), destination.getNameSpace(),
					destination.getServiceName());

			// for now we will use the loop for retries
			// TODO improvement would be to keep the retries in the
			// sendQue to keep more time between retries
			for (int ii = 0; ii < 5 && !done; ii++) {
				// poster.executeSOAPRequest();
				client.sendSoapCall();
				// poster.successfull()
				if (client.successFull()) {
					done = true;
				} else {
					// we add a new progress for this message
					HibernateMain.addProgress(sendQueItem.getUuid(),
							"Sending was unsuccessful retrying in 15 seconds, "
									+ (5 - i) + " retries left", true);

					try {
						Thread.sleep(15000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if (done) {
				// we add a new progress for this message
				HibernateMain.addProgress(sendQueItem.getUuid(),
						"Message was succesfully delivered", true);

			} else {
				// we add a new progress for this message
				HibernateMain
						.addProgress(
								sendQueItem.getUuid(),
								"Sending was unsuccessful after 5 tries, stopped",
								true);
			}
		}
		System.out.println("Delivered: " + queItems.size() + " items");
		hibernate.deleteItemsFromDB(queItems);
	}

	/**
	 * Check if sleep is true if so then sleep
	 *
	 */
	private void checkForSleep() {
		while (sleep) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * returns the boolean that is set to true when the thread is in a cycle and
	 * not sleeping
	 */
	public boolean isCycle() {
		return cycle;
	}

	/**
	 * setter for cycle
	 * 
	 */
	public void setCycle(boolean cycle) {
		this.cycle = cycle;
	}

	/**
	 * getter for sleep
	 */
	public boolean isSleep() {
		return sleep;
	}

	/**
	 * setter for sleep
	 * 
	 */
	public void setSleep(boolean sleep) {
		this.sleep = sleep;
	}

}
