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
import org.json.JSONObject;

import com.google.zxing.WriterException;

import classes.Alumno;

@Path("/alumno") //Etiqueta que identifica las funciones pertenecientes a la clase Alumno
public class resAlumno {
	Alumno alumno = new Alumno();
	
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getStudentFile") //Etiqueta que invoca el procedimiento de inicio de sesión
	public String getLoginSession(@QueryParam("id") int idAlumno) {
    		return alumno.obtenerFichaAlumno(idAlumno);
	}
	
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllStudents")
	public String getAllStudents(@QueryParam("filter") String filter) {
		System.out.println("hola getallstudents"+filter);
    		return alumno.getAlumnos(filter);
	}
	
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getStudentsWithoutAnnualPlan")
	public String getAllStudentsWAnnualP() {
    		return alumno.getAlumnosSinProyeccion();
	}
	
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getStudentsWithAnnualPlan")
	public String getStudentsWithAnnualPlan() {
    		return alumno.getAlumnosConProyeccion();
	}
	
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getFile")
	public String getFileStudent(@QueryParam("idAlumno") String idAlumno) {
    		return alumno.getBoleta(idAlumno);
	}
	
	 @PUT
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/setRegister")
	 public String setRegistration(String infoRegistro) throws WriterException, IOException, JSONException, SQLException, DroolsParserException {
	    	return alumno.setRegistro(infoRegistro);
	 }
}
