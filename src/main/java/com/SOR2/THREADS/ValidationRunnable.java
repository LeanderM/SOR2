package com.SOR2.THREADS;

import java.util.ArrayList;
import java.util.UUID;

import com.SOR2.SOAP.DestinationHandler;
import com.SOR2.SOAP.DocumentValidator;
import com.SOR2.SOAP.SoapClientSSL;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;
import com.SOR2.SOAP.XMLObjects.ResponseMessage;
import com.SOR2.hibernate.HibernateMain;

public class ValidationRunnable implements Runnable {

	private boolean running;

	@Override
	public void run() {
		System.out.println("starting ValidationThread");
		running = true;
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

	private void startValidating(){
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
			// TODO new hibernateMethod that checks if there are messages in validationQue
			messagesInQue = HibernateMain.messagesInValidationQue();
			
			if(messagesInQue) {
				
				// TODO new hibernateMethod that gets all the messages from the validationQue
				messages = HibernateMain.getMessagesInValidationQue();
				
				for(int i = 0; i < messages.size(); i++) {
					// an Object Array that represent one message
					messageArray = messages.get(0);
					// get the message and documentInformation from the array with Objects
					documentInformation = (DocumentInformation) messageArray[0];
					message = (Message) messageArray[1];
					
					// a more thorough validation
					DocumentValidator validator = new DocumentValidator(documentInformation, message);
					
					// check if valid
					if (validator.isValid()) {

						// TODO we want to update the status of this message to validated (validMessage)
						int id = HibernateMain.addMessage(uuid, message.getMessage(),
								documentInformation.getSender(),
								documentInformation.getSubject(),
								documentInformation.getReceiver(), 1);

						// we add a new progress for this message
						HibernateMain.addProgress(id,
								"Message successfully validated and ready for sending",
								true);

					} else {
						// if the message is invalid we will still keep it in the
						// dataBase
						// There is no point in saving if there is both no valid sender and
						// receiver
						if (validator.receiverExists(documentInformation.getReceiver()) || validator.senderExists(documentInformation.getSender())) {
							// TODO we want to update the status of this message to validated (invalidMessage)
							int id = HibernateMain.addInvallidMessage(uuid,
									message.getMessage(), documentInformation.getSender(),
									documentInformation.getSubject(),
									documentInformation.getReceiver(),
									validator.getStatusCode());
							// we add a new progress for this message
							HibernateMain.addProgress(id,"Message was validated unsuccessfully, stopped", false);
						}
					}
				}
			}		
			Thread.sleep(10000);
		}
	}
	
}
