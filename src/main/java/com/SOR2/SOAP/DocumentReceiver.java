package com.SOR2.SOAP;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

@WebService
public interface DocumentReceiver {

	// controlleren hoe je @WebParam kan afdwingen
	@WebMethod(operationName = "sendDocument")
	ResponseMessage sendDocument(
			@WebParam(name = "documentInformation", header = true) DocumentInformation documentInformation,
			@WebParam(name = "document") @XmlElement(required = false, nillable = false) Document document);
}
