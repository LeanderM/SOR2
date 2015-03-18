package com.SOR2.SOAP;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

public class SimpleCXFNonSpringServlet extends CXFNonSpringServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void loadBus(ServletConfig servletConfig) {
		try {
			super.loadBus(servletConfig);
		} catch (ServletException e) {
			System.out
					.println("Something went wrong while loading servlet bus");
			e.printStackTrace();
		}
		Bus bus = getBus();
		BusFactory.setDefaultBus(bus);
		Endpoint.publish("/DocumentReceiver", new DocumentReceiverImpl());
	}
}
