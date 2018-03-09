package TrabajoTerminalBack.pt1.pt2;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	
	Personal personal = new Personal();
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/inicioSesion")
    public String getLoginInfo() {
        return personal.iniciarSesion("vane", "vanessita");
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/post")
	public String getLoginSession(@QueryParam("user") String user, @QueryParam("pass") String pass) {
    		Personal personal = new Personal();
    		System.out.println("hola"+user+pass);
    		return personal.iniciarSesion(user,pass);
	}
    

}
