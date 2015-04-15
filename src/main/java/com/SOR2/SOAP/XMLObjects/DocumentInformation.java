package com.SOR2.SOAP.XMLObjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Een classe voor document-informatie Objecten die met de SOAPcalls binnenkomen
 * 
 * @author Jesse
 * @version 0.1.0
 */
@XmlRootElement(name = "DocumentInformation")
public class DocumentInformation implements Serializable {

	private String sender;
	private String receiver;
	private String subject;

	@XmlElement(name = "receiver", required = true, nillable = false)
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@XmlElement(name = "subject", required = true, nillable = false)
	public String getSubject() {
		return subject;
	}

	public void setSubject(String title) {
		this.subject = title;
	}

	@XmlElement(name = "sender", required = true, nillable = false)
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public boolean hasReceiver() {
		System.out.println(receiver);
		if (receiver == null || receiver.length() == 0) {
			return false;
		}
		return true;
	}

	public boolean hasSubject() {
		if (subject == null || subject.length() == 0) {
			return false;
		}
		return true;
	}

	public boolean hasSender() {
		if (sender == null || sender.length() == 0) {
			return false;
		}
		return true;
	}

}