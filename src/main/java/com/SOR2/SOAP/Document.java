package com.SOR2.SOAP;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Document")
public class Document implements Serializable {

	private String content;

	@XmlElement(name = "content", required = true)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean hasContent() {
		if (content == null || content.length() == 0) {
			return false;
		}
		return true;
	}
}
