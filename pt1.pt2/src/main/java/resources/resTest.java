package resources;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import com.google.zxing.WriterException;

import classes.Test;

@Path("/test")
public class resTest {
	
	Test test = new Test();
	
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getTestAnnualPlan")
	public String getTestProyeccion(@QueryParam("infoToGet") String array) throws WriterException, IOException, SQLException {
    		return test.obtenerTestProyeccion(array);
    }
    
}
