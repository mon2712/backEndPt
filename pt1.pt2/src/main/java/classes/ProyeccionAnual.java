package classes;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.WorkingMemory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProyeccionAnual {
	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
    
    private String level;
	private double frequency;
	private String type;
	private int totalPages;
	private int totalPagesPerMonth;
	private float months;
	private int dailyPages;
	private String timeLevel;
	private String dailyTime;
	
	
	
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public double getFrequency() {
		return frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getTotalPagesPerMonth() {
		return totalPagesPerMonth;
	}
	public void setTotalPagesPerMonth(int totalPagesPerMonth) {
		this.totalPagesPerMonth = totalPagesPerMonth;
	}
	public float getMonths() {
		return months;
	}
	public void setMonths(float months) {
		this.months = months;
	}
	public int getDailyPages() {
		return dailyPages;
	}
	public void setDailyPages(int dailyPages) {
		this.dailyPages = dailyPages;
	}
	public String getTimeLevel() {
		return timeLevel;
	}
	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}
	public String getDailyTime() {
		return dailyTime;
	}
	public void setDailyTime(String dailyTime) {
		this.dailyTime = dailyTime;
	}

	
	public static String obtenerProyeccionAnual(int idAlumno) throws SQLException {
		System.out.println("id "+idAlumno);
		StringWriter swriter = new StringWriter();
		
		String getQueryStatement = "SELECT pa.idProyeccionAnual, niv.nombre, pn.frecuenciaEstudio, pn.tipo, pn.hojasTotales, pn.hojasMes, pn.cantidadMeses, pn.hojasDiarias, niv.minTime, niv.maxTime, (niv.minTime*pn.hojasDiarias) as tiemMin, (niv.maxTime*pn.hojasDiarias) as tiemMax FROM ProyeccionAnual as pa JOIN ProyeccionAnual_has_ProyeccionNivel as pa_h_pn JOIN ProyeccionNivel as pn JOIN Nivel as niv\n" + 
				"ON pa.Alumno_idAlumno=pa_h_pn.ProyeccionAnual_Alumno_idAlumno AND pa.idProyeccionAnual=pa_h_pn.ProyeccionAnual_idProyeccionAnual \n" + 
				"AND pn.idProyeccionNivel=pa_h_pn.ProyeccionNivel_idProyeccionNivel AND niv.idNivel=pn.Nivel_idNivel\n" + 
				"WHERE pa.Alumno_idAlumno="+Integer.toString(idAlumno)+";";
		
        prepareStat = conn.prepareStatement(getQueryStatement);

        ResultSet rs = prepareStat.executeQuery();
        
        ProyeccionAnual proyeccion = new ProyeccionAnual();

		try (JsonGenerator gen = Json.createGenerator(swriter)) {
		gen.writeStartObject();
        	gen.writeStartObject("annualPlan");
        		
		        if (!rs.isBeforeFirst()){
		        		gen.write("err", 1);
		        		gen.write("messageError", "No existe proyeccion anual");
		        		
		        	}else {
		        		gen.writeStartArray("info");
			        while(rs.next()) {
			        	
			        		proyeccion.setLevel(rs.getString(2));
			        		proyeccion.setFrequency(rs.getDouble(3));
			        		proyeccion.setType(rs.getString(4));
			        		proyeccion.setTotalPages(rs.getInt(5));
			        		proyeccion.setTotalPagesPerMonth(rs.getInt(6));
			        		proyeccion.setMonths(rs.getFloat(7));
			        		proyeccion.setDailyPages(rs.getInt(8));
			        		proyeccion.setTimeLevel(rs.getString(9)+" - "+rs.getString(10));
			        		proyeccion.setDailyTime(rs.getString(11)+" - "+rs.getString(12));
			       
			        	
				        gen.writeStartObject();
				        		gen.write("level", proyeccion.getLevel());
				        		gen.write("frequency", String.valueOf(proyeccion.getFrequency()));
				        		gen.write("type", proyeccion.getType());
				        		gen.write("totalPages", proyeccion.getTotalPages());
				        		gen.write("totalPagesPerMonth", proyeccion.getTotalPagesPerMonth());
				        		gen.write("months", String.valueOf(proyeccion.getMonths()));
				        		gen.write("dailyPages", proyeccion.getDailyPages());
				        		gen.write("timeLevel", proyeccion.getTimeLevel());
				        		gen.write("dailyTime", proyeccion.getDailyTime());
				        	gen.writeEnd();
			        	}
			        gen.writeEnd();
			    
		        	}
		   	
		gen.writeEnd();
		gen.writeEnd();
		}
		return swriter.toString();
	}
	
	public static String crearProyeccionAnual(String array) throws JSONException, SQLException, IOException {
		
		Auxiliar aux= new Auxiliar();
				WorkingMemory wk;
				try {
					wk = aux.conexionDrools();
					aux.executeFrecInicial(wk, array);
				} catch (DroolsParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      JSONObject obj = new JSONObject(array);
		
		JSONObject results = obj.getJSONObject("resultsTest");
		
			JSONObject infoStudent = results.getJSONObject("infoStudent");
			String idStudent=infoStudent.getString("idStudent");
			int desempeñoGeneral = results.getInt("finalScore");
		
			JSONArray exams = results.getJSONArray("exams");
			JSONArray frecuenciaIncial = results.getJSONArray("startPoint");
		
		System.out.println("puntajeDesempeño " + desempeñoGeneral);
		System.out.println("infoStudent" +infoStudent.toString());
		System.out.println("para frecuencia inicial" + frecuenciaIncial.toString());
		
		//String proyeccion = obtenerProyeccionAnual(infoStudent.getInt("idStudent")); //Obtiene la proyeccion despues de la insercion
		String proyeccion = obtenerProyeccionAnual(Integer.parseInt(idStudent)); //Obtiene la proyeccion despues de la insercion
		return proyeccion;
	}

}
