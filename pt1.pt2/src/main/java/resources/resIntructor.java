package resources;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.zxing.WriterException;

import classes.Instructor;

@Path("/instructor")
public class resIntructor {
	
	Instructor instructor = new Instructor();
	
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createIds")
	public void createIDs(String array) throws WriterException, IOException {
    		instructor.obtenerCredenciales(array);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createStampsStudents")
	public void crearEtiquetasAlumnos(String array) throws WriterException, IOException {
    		instructor.obtenerEtiquetas(array);
    }

}