package classes;

import java.io.StringWriter;
import java.sql.CallableStatement;
//import org.json.simple.JSONObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import TrabajoTerminalBack.pt1.pt2.conexionDB;

public class Centro {
	
	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
    int idCentro=0;
	
	public static String getStudents(String filter) {
		StringWriter swriter = new StringWriter();
        try {
        	int totalR = 0, totalC=0, n=0;
        	String getQueryStatement = "CALL jsonStudentsAtCenter('"+filter+"')";
        	
            prepareStat = conn.prepareStatement(getQueryStatement);
            ResultSet rs = prepareStat.executeQuery();

            while(rs.next()){
            	totalR++;
            }
            rs.first();
            
            totalC=rs.getMetaData().getColumnCount();
            String general[][] = new String[totalR+1][totalC+1];
            do{
            	for(int x=1;x<totalC+1;x++){
             	     general[n][x]=rs.getString(x);
            	}
                n++;
            }while(rs.next());
            
            try (JsonGenerator gen = Json.createGenerator(swriter)) {
            	gen.writeStartObject();
	            gen.writeStartArray("studentsInCenter");
	            for (int x=0;x<totalR;x++) {
	                gen.writeStartObject();
	                gen.write("name", general[x][1]+ " " + general[x][2]);
	                gen.write("idStudent", general[x][3] );
	                gen.write("hourIn", general[x][4]);
	                gen.write("timeRed", general[x][5]);
	                gen.writeEnd();
	            }
	            gen.writeEnd();
	            gen.writeEnd();
	        }
        }catch (SQLException e) {
        	
            e.printStackTrace();
        }  
        
        return swriter.toString();    
    }
	
	public static void updateTimeRed(String timeRed, int idStudent){
		try {
		    CallableStatement cS = conn.prepareCall("{call updateTimeRed(?, ?)}");
		    cS.setString(1, timeRed);
		    cS.setInt(2, idStudent);
		    cS.execute();  
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}



