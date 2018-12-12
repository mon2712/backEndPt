package resources;

import java.io.IOException;
import java.sql.SQLException;
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
	public int setTimeRed(@PathParam("file1") String file1, @PathParam("file2") String file2) throws IOException, ParseException {
		int error1=0, error2=0, error=0;	
			//file1="C:\\Users\\Vanessa Miranda\\Desktop\\"+ file1;
			//file2="C:\\Users\\Vanessa Miranda\\Desktop\\"+ file2;
		String files1 = System.getProperty("user.home")+"/Desktop/"+file1;
		String files2 = System.getProperty("user.home")+"/Desktop/"+file2;
		String pdfPath = System.getProperty("user.home")+"/Desktop/file1";
		System.out.println("archivo " + "file1:  " + files1 + " files2: " + files2);
		//int error=1;
		try {
    		FileReport fr = new FileReport();
    		error1=fr.getBaseInfo(files1);
    		error2=fr.getInfo(files2);
    		
    		if(error1==1 || error2==1) {
    			System.out.println("Hubo un error");
    			error=1;
    		}else {
    			System.out.println("Si entro a la peticion");
        		System.out.println("archivo " + "file1:  " + files1 + "  files2:  " + files2);	
    		}
		}catch (Exception e) {
			System.out.println("Hubo un error");
			error=1;
			//e.printStackTrace();
		}
    		//System.out.println("Path1: " + file1+ "Path2: " +file2);
    		return error;
	}
}
