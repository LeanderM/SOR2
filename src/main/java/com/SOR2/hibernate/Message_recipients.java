package com.SOR2.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
// Tabelnaam voor de database
@Table(name = "message_recipients")
public class Message_recipients {

	@Column(name = "recipient_id")
	// String voor recipient_id.
	private String recipient_id;

	// Kolom message_id met een int als id.
	@Id
	@Column(name = "message_id")
	private int message_id;

	/**
	 * Getter voor recipient_id
	 * 
	 * @return recipient_id
	 */
	public String getRecipient_id() {
		return recipient_id;
	}

	/**
	 * Setter voor recipient_id
	 * 
	 * @param recipient_id
	 */
	public void setRecipient_id(String recipient_id) {
		this.recipient_id = recipient_id;
	}

	/**
	 * Getter voor message_id.
	 * 
	 * @return message_id
	 */
	public int getMessage_id() {
		return message_id;
	}

	/**
	 * Setter voor message_id.
	 * 
	 * @param message_id
	 */
	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

}
