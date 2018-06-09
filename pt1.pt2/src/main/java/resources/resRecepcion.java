package resources;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.zxing.WriterException;

import classes.Centro;
import classes.Recepcion;


@Path("/recepcion")
public class resRecepcion {
	Recepcion recepcion = new Recepcion();
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getStudentsCalls")
	public String getStudentsCall() {
		
		String alumnosLlamada= recepcion.getAlumnosLlamadas();
		//System.out.print(alumnosLlamada);
		return alumnosLlamada;
	}
	
	@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idStudent}/{nota}/{fecha}")
	public void setNotaLlamada(@PathParam("idStudent") String idStudent, @PathParam("nota") String nota,  @PathParam("fecha") String fec) {
    		int idS=Integer.parseInt(idStudent);
    		//String fech=fec.replaceAll("-","/");
    		//System.out.println("fecha: " + fech);
    		recepcion.NotaLlamada(idS, nota, fec);
    		
	}
	
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/setAssistance")
	public String setAsistenciaUsuarios(String array) throws WriterException, IOException, SQLException {
    		return recepcion.setAsistencia(array);
    }
	
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getNotifications")
	public String getNotificaciones() throws SQLException {
    		return recepcion.getNotificacion();
	}
    
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idStudent}")
	public String delNotificacion(@PathParam("idStudent") int id) throws SQLException {
    		System.out.println("id Student " + id);
		return recepcion.deleteNotificacion(id);
	}
}
