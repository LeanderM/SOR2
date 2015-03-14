package com.SOR2.hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ID")
public class ID {

	// @Column(name = "messages")
	private String message;

	// @Column(name = "account_type")
	private String accountType;

	// @Column(name = "users")
	private String firstName;

	// @Column(name = "message_recipients")
	private String message_recipients;

	public ID() {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMessage_recipients() {
		return message_recipients;
	}

	public void setMessage_recipients(String message_recipients) {
		this.message_recipients = message_recipients;
	}

}
