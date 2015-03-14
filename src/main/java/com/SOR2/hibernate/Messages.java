package com.SOR2.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
public class Messages {

	@Id
	@GeneratedValue
	@Column(name = "message_ID")
	private int message_ID;

	@Column(name = "message")
	private String message;

	@Column(name = "subject")
	private String subject;

	@Column(name = "sender")
	private String sender;

	@GeneratedValue
	@Column(name = "date")
	private String date;

	public Messages() {

	}

	public int getMessage_ID() {
		return message_ID;
	}

	public void setMessage_ID(int message_ID) {
		this.message_ID = message_ID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

}
