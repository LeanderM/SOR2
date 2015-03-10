package com.SOR2.REST;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Dit is de Klasse van de CXF REST API Deze klasse is het aanspreekpunt voor de
 * verzende partijen Deze klasse focust op het verzenden van documenten
 * 
 * @author Jesse
 * @version 0.1.0
 *
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/document")
public class SendDocument {

	/**
	 * De methode voor het verzenden van documenten De ontvangen JSON String
	 * wordt in een JSONobject opgeslagen Er wordt gecontrolleerd of alle
	 * verwachte waarden compleet en valide zijn Het JSONobject wordt gepost
	 * naar de gespecificeerde destination
	 * 
	 * @param document
	 *            Deze parameter vangt een JSON String op Die mee wordt gezonden
	 *            met een POST bericht
	 *
	 * @returns true/false Als het document voeldoet aan de minimum eisen (heeft
	 *          een title, content & een valide destination)
	 */

	@POST
	@Path("/send")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean send(String document) {
		// leeg json object
		JSONObject jsonDocument = null;
		try {
			// stop de json String in een JSONObject
			jsonDocument = new JSONObject(document);
		} catch (JSONException e) {
			System.out
					.println("something went wrong while converting to JSON object"
							+ document);
			e.printStackTrace();
		}
		// maak een validator die controleerd of alle benodigde waarden aanwezig
		// zijn
		DocumentValidator validator = new DocumentValidator(jsonDocument);
		// Zo ja verzend het document
		if (validator.validate()) {
			try {
				// De lijst met bestemmingen ophalen
				DestinationList list = DestinationList.getInstance();
				PostHandler poster = new PostHandler(jsonDocument,
						list.getValue(jsonDocument.get("destination")
								.toString()));
			} catch (JSONException e) {
				System.out
						.println("something went wrong while instantiating PostHandler");
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}
}
