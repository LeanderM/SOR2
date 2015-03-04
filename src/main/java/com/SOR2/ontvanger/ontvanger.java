package com.SOR2.ontvanger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Een basis implementatie van een ontvanger.
 * 
 * @author Leander
 * @version 0.1.0
 */
@Consumes(MediaType.APPLICATION_JSON)
@Path("/ontvanger")
public class ontvanger {

	public ontvanger() {
	}

	/**
	 * Methode die aangeroepen wordt door de webserver wanneer er een nieuw
	 * bericht voor de ontvanger is. Deze echoed het bericht in de console
	 * 
	 * @param message
	 *            Het bericht voor deze gebruiker
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/receive")
	public void receive(String message) {
		System.out.println("hoi! " + data);
	}
}
