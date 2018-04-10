package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import classes.Centro;
import classes.Personal;

@Path("/centro")
public class resCentro {
	
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getStateAtCenter")
	public String getStateOfCenter(@QueryParam("filter") String filter) {
    		Centro center = new Centro();
    		String Students=center.getStudents(filter);
    		return Students;
	}
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idStudent}/{timeRed}")
	public void setTimeRed(@PathParam("idStudent") String idStudent, @PathParam("timeRed") String timeRed) {
    		int idS=Integer.parseInt(idStudent);
    		Centro center = new Centro();
    		center.updateTimeRed(timeRed, idS);
    		
	}
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getStatusOfCenter")
	public String getEstatusOfCenter() {
    		Centro center = new Centro();
    		String Students=center.getEstatusCentro();
    		return Students;
	}
    
}
