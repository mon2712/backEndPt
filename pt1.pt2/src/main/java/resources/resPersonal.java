package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import classes.Personal;

@Path("/personal")
public class resPersonal {
	
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getLogin")
	public String getLoginSession(@QueryParam("user") String user, @QueryParam("pass") String pass) {
    		System.out.println("hola"+user+pass);
    		Personal personal = new Personal();
    		return personal.iniciarSesion(user,pass);
	}
}
