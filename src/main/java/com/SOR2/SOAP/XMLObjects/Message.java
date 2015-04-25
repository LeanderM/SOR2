package com.SOR2.SOAP.XMLObjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Een classe voor documenten die met de SOAPcalls binnenkomen
 * 
 * @author Jesse
 * @version 0.1.0
 *
 */
@XmlType(name = "message")
public class Message implements Serializable {

	private static final long serialVersionUID = 8836226163119733084L;
	private String message;
	private Integer transactionID;

	@XmlElement(name = "message", required = true)
	public String getMessage() {
		return message;
	}

	/**
	 * get the ID of the current transaction
	 * 
	 * @return
	 */
	@XmlElement(name = "transactionID", required = true)
	public Integer getTransactionID() {
		return this.transactionID;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}

	public boolean hasContent() {
		if (message == null || message.length() == 0) {
			return false;
		}
		return true;
	}
}
