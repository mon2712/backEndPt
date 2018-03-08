package TrabajoTerminalBack.pt1.pt2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
    public String getLoginInfo() {
        return personal.iniciarSesion("mon", "mon");
    }
}
