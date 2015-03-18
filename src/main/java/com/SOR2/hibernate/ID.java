package com.SOR2.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//Tabel ID in database
@Entity
@Table(name = "ID")
public class ID {

	// Veld voor id
	// Kolomnaam id in database
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	// Veld voor messages
	// Kolomnaam message in database
	@Column(name = "messages")
	private String message;

	// Veld voor account_type
	// Kolomnaam account_type
	@Column(name = "account_type")
	private String accountType;

	// Veld voor firstName
	// Kolomnaam users in database.
	@Column(name = "users")
	private String firstName;

	// Veld voor message_recipient
	// Kolomnaam message_recipient in database
	@Column(name = "message_recipients")
	private String message_recipients;

	/**
	 * Constructor moet leeg zijn
	 */
	public ID() {

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
	 * Getter voor accountType
	 * 
	 * @return accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * Setter voor accountType
	 * 
	 * @param accountType
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * Getter voor firstName
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter voor firstName
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter voor message_recipients
	 * 
	 * @return message_recipients
	 */
	public String getMessage_recipients() {
		return message_recipients;
	}

	/**
	 * Setter voor message_recipients
	 * 
	 * @param message_recipients
	 */
	public void setMessage_recipients(String message_recipients) {
		this.message_recipients = message_recipients;
	}

}
