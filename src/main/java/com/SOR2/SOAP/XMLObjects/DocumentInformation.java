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

	private String destination;
	private String title;

	@XmlElement(name = "destination", required = true, nillable = false)
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@XmlElement(name = "title", required = true, nillable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean hasDestionation() {
		if (destination == null || destination.length() == 0) {
			return false;
		}
		return true;
	}

	public boolean hasTitle() {
		if (title == null || title.length() == 0) {
			return false;
		}
		return true;
	}
}
