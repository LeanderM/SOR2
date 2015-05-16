package com.SOR2.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "validationQue")
public class ValidationQueItem {

	// Kolom message_ID in database
	@Id
	@GeneratedValue
	@Column(name = "validationQue_id")
	// Veld voor message_ID
	private int validationQue_id;

	// veld voor uuid
	@Column(name = "uuid")
	private String uuid;

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

	@Column(name = "status")
	private int status;

	public ValidationQueItem() {
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getValidationQue_id() {
		return validationQue_id;
	}

	public void setValidationQue_id(int validationQue_id) {
		this.validationQue_id = validationQue_id;
	}

}
