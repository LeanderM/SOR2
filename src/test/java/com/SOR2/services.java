package com.SOR2;

import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import com.SOR2.SOAP.DocumentReceiverImpl;
import com.SOR2.SOAP.InformationProvider;
import com.SOR2.SOAP.InformationProviderImpl;
import com.SOR2.SOAP.XMLObjects.DocumentInformation;
import com.SOR2.SOAP.XMLObjects.Message;
import com.SOR2.SOAP.XMLObjects.ResponseMessage;

public class services {

	public final static String testUsername = "test";
	public final static String testPassword = "test";

	private static ResponseMessage validMessage;
	private static ResponseMessage invalidCredentialsMessage;
	private static ResponseMessage invalidReceiverMessage;
	private static ResponseMessage noSubjectMessage;

	@BeforeClass
	public static void setUp() {

		// verstuur alle berichten die in deze tests thuis horen, om te
		// voorkomen dat er voor elke test 15 seconden gewacht moet worden.
		services.validMessage = sendMssg("Belastingsdienst", "subject",
				"testValidMessage", 1234);

		services.invalidCredentialsMessage = sendMssg("Belastingsdienst",
				"subject", "testInvalidCredentials", 1234, "SlechtWachtwoord");

		services.invalidReceiverMessage = sendMssg("invalidReceiver",
				"subject", "testInvalidReceiver", 1234);

		services.noSubjectMessage = sendMssg("Belastingsdienst", "",
				"testNoSubject", 1234);

		// wait for processing thread to finish it's queue
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			System.err
					.println("Failed executing sleep thread in services.java");
			e.printStackTrace();
		}
	}

	@Test
	public void testSendValidMessage() {

		// did it go right?
		String status = this.getStatus(validMessage);
		assert (status.equals("OK"));
	}

	@Test
	public void checkMessageWithInvalidCredentials() {
		String status = this.getStatus(invalidCredentialsMessage);
		assert (status.equals("| [Error]: No valid sender and password pair"));
	}

	@Test
	public void testSendMessageWithInvalidReceiver() {
		String status = this.getStatus(invalidReceiverMessage);
		assert (status.equals("The given receiver does not exist"));
	}

	@Test
	public void testSendMessageWithoutSubject() {
		String status = this.getStatus(noSubjectMessage);
		assert (status.equals("Header does not contain a subject"));
	}

	/**
	 * Verstuur een bericht met de gegeven waardes. wachtwoord is default
	 * 
	 * @param ontvanger
	 * @param subject
	 * @param message
	 * @param transactieId
	 * @return
	 */
	private static ResponseMessage sendMssg(String ontvanger, String subject,
			String message, int transactieId) {

		return services.sendMssg(ontvanger, subject, message, transactieId,
				services.testPassword);
	}

	/**
	 * Verstuur een bericht met de gegeven waardes.
	 * 
	 * @param ontvanger
	 * @param subject
	 * @param message
	 * @param transactieId
	 * @param password
	 * @return
	 */
	private static ResponseMessage sendMssg(String ontvanger, String subject,
			String message, int transactieId, String password) {

		DocumentReceiverImpl receiver = new DocumentReceiverImpl();

		// opstellen inhoud bericht
		Message msg = new Message();
		msg.setMessage(message);
		msg.setTransactionID(transactieId);

		// opstellen headers bericht
		DocumentInformation header = new DocumentInformation();
		header.setReceiver(ontvanger);
		header.setPassword(password);
		header.setSender(services.testUsername);
		header.setSubject(subject);

		return receiver.sendDocument(msg, header);
	}

	/**
	 * Haal de status op van een bericht.
	 * 
	 * @param response
	 *            Het ResponseMessage object
	 * @return De status
	 */
	private String getStatus(ResponseMessage response) {

		UUID uuid = response.getUuid();

		if (uuid == null) {
			return response.getErrorMessage();
		}

		// haal info op over dit bericht, op basis van de username/password en
		// UUID.
		InformationProvider messageInformation = new InformationProviderImpl();
		String status = messageInformation.getMessageStatus(
				services.testUsername, services.testPassword, uuid.toString());

		return status;
	}
}
