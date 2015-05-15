package com.SOR2.SOAP.HANDLERS;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {  
    
    @Override  
    public Set<QName> getHeaders() {  
        return null;  
    }  
  
    @Override  
    public void close(MessageContext context) {  
    }  
  
    @Override  
    public boolean handleFault(SOAPMessageContext context) {  
        logToSystemOut(context);  
        return true;  
    }  
  
    @Override  
    public boolean handleMessage(SOAPMessageContext context) {  
        logToSystemOut(context);  
        return true;  
    }  
  
    private void logToSystemOut(SOAPMessageContext smc) {  
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);  
  
        if (outboundProperty.booleanValue()) {  
            System.out.println("\nOutgoing message:");  
        } else {  
            System.out.println("\nIncoming message:");  
        }  
  
        SOAPMessage message = smc.getMessage();  
        try {  
            message.writeTo(System.out);  
        } catch (Exception e) {  
            System.out.println("Exception in handler: " + e);  
        }  
    }  
  
}  