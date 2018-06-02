package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import classes.Centro;
import classes.Recepcion;


@Path("/recepcion")
public class resRecepcion {
	@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getStudentsCalls")
	public String getStudentsCall() {
		Recepcion recepcion = new Recepcion();
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
    		Recepcion recepcion = new Recepcion();
    		recepcion.NotaLlamada(idS, nota, fec);
    		
	}
	
}
