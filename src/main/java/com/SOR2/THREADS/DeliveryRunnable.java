package com.SOR2.THREADS;

import java.util.ArrayList;

import com.SOR2.SOAP.DestinationHandler;
import com.SOR2.SOAP.DocumentValidator;
import com.SOR2.SOAP.SoapClientSSL;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;
import com.SOR2.SOAP.XMLObjects.ResponseMessage;
import com.SOR2.hibernate.HibernateMain;

public class DeliveryRunnable implements Runnable {

	private boolean running;

	@Override
	public void run() {
		System.out.println("starting DeliveryThread");
		running = true;
		int i = 0;
		while (running) {
			System.out.println("DeliveryThread is running Cycle:" + i++);
			try {
				System.out.println("DeliveryThread going to sleep");
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

	private void startDelivering() {
		// boolean that tell if there are messages in the que
		boolean messagesInQue;
		// Variable that contains all the messages
		ArrayList<Object[]> messages;
		// Variable we use to temp store object arrays in
		Object[] messageArray;
		// documentInformation
		DocumentInformation documentInformation;
		// Message
		Message message;
		
		while(running) {
			// TODO new hibernateMethod that checks if there are messages in sendQue
			messagesInQue = HibernateMain.messagesInSendQue();
			
			if(messagesInQue) {
				
				// TODO new hibernateMethod that gets all the messages from the sendQue
				messages = HibernateMain.getMessagesInSendQue();
				
				for(int i = 0; i < messages.size(); i++) {
					// an Object Array that represent one message
					messageArray = messages.get(0);
					// get the message and documentInformation from the array with Objects
					documentInformation = (DocumentInformation) messageArray[0];
					message = (Message) messageArray[1];
					
					// build the handler
					DestinationHandler destination = new DestinationHandler(documentInformation.getReceiver());
					// boolean to check if sending was successfull
					boolean done = false;
					
					SoapClientSSL client = new SoapClientSSL(documentInformation, message, destination.getUrl(), destination.getNameSpace(), "");
					
					// for now we will use the loop for retries
					// TODO improvement would be to keep the retries in the sendQue to keep more time between retries
					for (int ii = 0; ii < 5 && !done; ii++) {
						//poster.executeSOAPRequest();
						client.sendSoapCall();
						//poster.successfull()
						if (client.successFull()) {
							done = true;
						} else {
							// we add a new progress for this message
							// TODO the que needs an id to be able to add statusUpdates
							HibernateMain.addProgress(id,
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
						HibernateMain.addProgress(id,
								"Message was succesfully delivered", true);
					} else {
						// we add a new progress for this message
						HibernateMain.addProgress(id, "Sending was unsuccessful after 5 tries, stopped", true);
					}
				}		
			}
			Thread.sleep(10000);
		}	
	}
	
}
