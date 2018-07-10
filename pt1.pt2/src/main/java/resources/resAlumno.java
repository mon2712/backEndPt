package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import classes.Alumno;

@Path("/alumno")
public class resAlumno {
	Alumno alumno = new Alumno();
	
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getStudentFile")
	public String getLoginSession(@QueryParam("id") int idAlumno) {
    		System.out.println("hola"+idAlumno);
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
	public String getFileStudent() {
    		return alumno.getBoleta();
	}
}
