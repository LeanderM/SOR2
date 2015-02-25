package com.SOR2.ontvanger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Path("/b")
public class ontvanger {

	public ontvanger() {
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/c")
	public void receive(String data) {
		System.out.println("hoi! " + data);
	}
}
