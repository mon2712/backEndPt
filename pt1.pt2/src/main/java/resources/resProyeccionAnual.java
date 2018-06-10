package resources;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.drools.compiler.compiler.DroolsParserException;
import org.json.JSONException;

import com.google.zxing.WriterException;

import classes.ProyeccionAnual;

@Path("/proyeccionAnual")
public class resProyeccionAnual {
	
	ProyeccionAnual proyeccionAnual = new ProyeccionAnual();
	
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAnnualPlan")
	public String getProyeccionAnual(@QueryParam("idAlumno") int idAlumno) throws WriterException, IOException, SQLException {
    		return proyeccionAnual.obtenerProyeccionAnual(idAlumno);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/setAnnualPlan")
	public String setProyeccionAnual(String array) throws WriterException, IOException, JSONException, SQLException, DroolsParserException {
    		return proyeccionAnual.crearProyeccionAnual(array);
    }
}
