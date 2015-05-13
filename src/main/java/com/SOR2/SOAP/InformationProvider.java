package com.SOR2.SOAP;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface InformationProvider {

	@WebMethod(operationName = "getMessageStatus")
	String getMessageStatus(@WebParam(name = "messageID") int messageID);

}
