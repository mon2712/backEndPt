package classes;

import java.io.StringWriter;
//import org.json.simple.JSONObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
    private static Connection conn = conexionDB.conectarBD();
    int idCentro=0;
	
	public static String getStudents() {
		StringWriter swriter = new StringWriter();
		Gson gson = new Gson();
        try {
        	//java.util.Date fecha = new Date();
        	//System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(fecha));
        	//System.out.println(new SimpleDateFormat("HH:mm").format(fecha));
        	String getQueryStatement = "SELECT alumno.nombre, alumno.apellido, alumno.idAlumno, asistencia.horaEntrada FROM alumno INNER JOIN asistencia ON (alumno.idAlumno = asistencia.Alumno_idAlumno) AND (asistencia.fecha=CURDATE())";
            prepareStat = conn.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = prepareStat.executeQuery();

            int totalR = 0, totalC=0;
            while(rs.next()) {
            	totalR++;
            }
            rs.first();
            
            totalC=rs.getMetaData().getColumnCount();
            String general[][] = new String[totalR+1][totalC+1];
            int n=0;
            do {
               for (int x=1;x<totalC+1;x++) {
             	     general[n][x]=rs.getString(x);
            	     System.out.print( general[n][x] + " ");
               }
               System.out.println("");
               n++;
            }while(rs.next());
            try (JsonGenerator gen = Json.createGenerator(swriter)) {
            gen.writeStartObject();
            gen.writeStartArray("s");
            for (int x=1;x<totalC;x++) {
                gen.writeStartObject();
                gen.write("name", general[x][1]);
                gen.write("idStudent", general[x][2]);
                gen.write("apellido", general[x][3] );
                gen.write("hour", general[x][4]);
                gen.writeEnd();
            }
            gen.writeEnd();
            gen.writeEnd();
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }  
        return swriter.toString();    
    }
}



