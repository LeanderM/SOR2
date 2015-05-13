package com.SOR2.SOAP.XMLObjects;

import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Een classe voor reponseMessages die worden teruggestuurd naar de verzenders
 * van SOAP messages Deze classe beschrijft een boolean met success die aangeeft
 * of het bericht is geaccepteerd Verder is er een String waar eventuele error
 * berichten in worden gezet
 * 
 * @author Jesse
 * @version 0.1.0
 *
 */
@XmlRootElement(name = "ResponseMessage")
public class ResponseMessage {
	private boolean success;
	private String errorMessage;
	private UUID uuid;

	public ResponseMessage() {
	}

	public ResponseMessage(boolean success, UUID uuid) {
		this.success = success;
		errorMessage = "no Errors";
		this.uuid = uuid;
	}

	public ResponseMessage(boolean success, String errorMessage) {
		this.success = success;
		this.errorMessage = errorMessage;
	}

	public ResponseMessage(boolean success, String errorMessage, UUID uuid) {
		this.success = success;
		this.errorMessage = errorMessage;
		this.uuid = uuid;
	}

	@XmlElement(name = "success")
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	@XmlElement(name = "errorMessage")
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
