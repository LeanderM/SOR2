package com.SOR2.SOAP.XMLObjects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ResponseMessage")
public class ResponseMessage {
	private boolean success;
	private String errorMessage;

	public ResponseMessage() {
	}

	public ResponseMessage(boolean success) {
		this.success = success;
		errorMessage = "no Errors";
	}

	public ResponseMessage(boolean success, String errorMessage) {
		this.success = success;
		this.errorMessage = errorMessage;
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
}
