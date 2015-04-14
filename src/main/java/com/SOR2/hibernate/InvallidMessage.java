package com.SOR2.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// Tabel in de database.
@Entity
@Table(name = "invallidMessage")
public class InvallidMessage {

	// Kolom message_ID in database
	@Id
	@GeneratedValue
	@Column(name = "invallidMessage_ID")
	// Veld voor message_ID
	private int invallidMessage_ID;

	// Veld voor de message
	// Kolom message in database
	@Column(name = "message")
	private String message;

	// Veld voor het subject
	// Kolom subject in database
	@Column(name = "subject")
	private String subject;

	// Veld voor de sender
	// Kolom sender in database
	@Column(name = "sender")
	private String sender;

	// Veld voor de date
	// Kolom date in database
	@GeneratedValue
	@Column(name = "date")
	private String date;

	@Column(name = "receiver")
	private String receiver;

	/**
	 * Constructor moet leeg zijn
	 */
	public InvallidMessage() {

	}

	/**
	 * Getter voor message_ID
	 * 
	 * @return message_ID
	 */
	public int getInvallidMessage_ID() {
		return invallidMessage_ID;
	}

	/**
	 * Getter voor message
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter voor message
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter voor subject
	 * 
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Setter voor subject
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Getter voor sender
	 * 
	 * @return sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Setter voor sender
	 * 
	 * @param sender
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
