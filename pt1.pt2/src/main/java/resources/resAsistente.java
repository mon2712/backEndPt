package resources;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import classes.Alumno;
import classes.Asistente;
import classes.Centro;

@Path("/asistente")
public class resAsistente {
	
	Asistente asistente = new Asistente();
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllAssistants")
	public String getAllAssistants() {
    		return asistente.getAsistentes();
	}
            
}
