package com.SOR2.REST;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
// Routing /services/rest/document
@Path("/document")
public class SendDocument {
	

	// testurl 			http://localhost:8080/services/rest/document/send
	// content type 	application/json
	// testdocument		{"title":"sometitle","destination":"somedestination","content":"somecontent"}
	
	
	@POST
	// Routing /services/rest/document/send
	@Path("/send")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean send(String document) {

		JSONObject jsonDocument = null;

		// stop de json String in een JSONObject
		try {
			jsonDocument = new JSONObject(document);
		} catch (JSONException e) {
			System.out.println("something went wrong while converting to JSON object"+ document);
			e.printStackTrace();
		}

		// maak een validator die controleerd of alle benodigde waarden aanwezig zijn
		DocumentValidator validator = new DocumentValidator(jsonDocument);

		// Zo ja verzend het document
		if (validator.validate()) {
			try {
				PostHandler poster = new PostHandler(jsonDocument);
			} catch (JSONException e) {
				System.out.println("something went wrong while instantiating PostHandler");
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}
}
