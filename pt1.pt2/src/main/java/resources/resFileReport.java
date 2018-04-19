package resources;

import java.io.IOException;
import java.text.ParseException;

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
import classes.FileReport;
import classes.Personal;

@Path("/documento")
public class resFileReport {

	@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	//@Path("/{file1}")
	@Path("/{file1}/{file2}")
	public void setTimeRed(@PathParam("file1") String file1, @PathParam("file2") String file2) throws IOException, ParseException {
			file1="C:\\Users\\Vanessa Miranda\\Desktop\\"+ file1;
			file2="C:\\Users\\Vanessa Miranda\\Desktop\\"+ file2;
    		FileReport fr = new FileReport();
    		fr.getBaseInfo(file1);
    		fr.getInfo(file2);
    		System.out.println("Si entro a la peticion");
    		System.out.println("Path1: " + file1+ "Path2: " +file2);
    		
	}
}
