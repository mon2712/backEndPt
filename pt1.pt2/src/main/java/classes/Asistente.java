package classes;

import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import org.json.JSONArray;
import org.json.JSONObject;

public class Asistente {
	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
    
    private int idAsistente;
	private String nombre;
	private String apellido;
	private String telefono;
	private String estatus;
	private String horaLlegada;
	private int lunes;
	private int miercoles;
	private int jueves;
	private int sabado;
	private String nivel;
    
	public static Asistente getInfoAsistente(int id) {

		StringWriter swriter = new StringWriter();
	    try {
	        String getQueryStatement = "SELECT * FROM Asistente as asis JOIN Usuario as us "
	        		+ "ON us.idUsuario=asis.Usuario_idUsuario WHERE us.idUsuario=" + Integer.toString(id) + ";";
	
	        prepareStat = conn.prepareStatement(getQueryStatement);
	
	        // Execute the Query, and get a java ResultSet
	        ResultSet rs = prepareStat.executeQuery();
	
	        
	        /*try (JsonGenerator gen = Json.createGenerator(swriter)) {
	        		gen.writeStartObject();
		            while(rs.first()) {
		                gen.writeStartObject();
		                		gen.write("name", ""+rs.getString(4));
		                		gen.write("idAssistant", ""+rs.getString(2));
		                		gen.write("lastName", rs.getString(9));
		                		gen.write("level" + rs.getString(1));
		                		gen.write("arrivingTime", rs.getString(10));
		                		gen.write("monday", rs.getInt(11));
		                		gen.write("wednesday", rs.getInt(12));
		                		gen.write("thursday", rs.getInt(13));
		                		gen.write("saturday", rs.getInt(14));
		                gen.writeEnd();
		            }
	            gen.writeEnd();
	        }*/
	        Asistente asis = new Asistente();
	        
	        if(rs.first()) {
	        		System.out.println("hola");
	        		
	        		asis.setNombre(rs.getString(4));
	        		asis.setEstatus(rs.getString(8));
	        		asis.setApellido(rs.getString(9));
	        		asis.setIdAsistente(rs.getInt(3));
	        		asis.setHoraLlegada(rs.getString(10));
	        		asis.setNivel(rs.getString(1));
	        		asis.setLunes(11);
	        		asis.setMiercoles(12);
	        		asis.setJueves(13);
	        		asis.setSabado(14);
	        }
	        return asis;
	        
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	
	} 
    
    public static String getAsistentes() {
		StringWriter swriter = new StringWriter();
	    try {
	        String getQueryStatement = "SELECT * FROM Asistente as asis JOIN Usuario as us WHERE us.idUsuario=asis.Usuario_idUsuario;";
	
	        prepareStat = conn.prepareStatement(getQueryStatement);
	
	        // Execute the Query, and get a java ResultSet
	        ResultSet rs = prepareStat.executeQuery();
	
	        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	        	gen.writeStartObject();
	            gen.writeStartArray("allAssistants");
	            while(rs.next()) {
	                gen.writeStartObject();
	                		gen.write("name", ""+rs.getString(4)+ " " + rs.getString(9));
	                		gen.write("idAssistant", ""+rs.getString(2));
	                gen.writeEnd();
	            }
	            gen.writeEnd();
	            gen.writeEnd();
	        }
	        return swriter.toString();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
    
    public static String getAsistenteInfo(String array) {
		StringWriter swriter = new StringWriter();
	    try {
	    		JSONObject obj = new JSONObject(array);
			JSONArray selectedPeople = obj.getJSONArray("selectedPeople");
			
			JSONObject asis = selectedPeople.getJSONObject(0);
			
	    		if(asis.has("idAssistant")) {
		        String getQueryStatement = "SELECT * FROM Asistente as asis JOIN Usuario as us WHERE us.idUsuario=asis.Usuario_idUsuario AND us.idUsuario="+asis.getString("idAssistant")+";";
		
		        prepareStat = conn.prepareStatement(getQueryStatement);
		
		        // Execute the Query, and get a java ResultSet
		        ResultSet rs = prepareStat.executeQuery();
		        
		        try (JsonGenerator gen = Json.createGenerator(swriter)) {
		        	gen.writeStartObject();
		            while(rs.next()) {
		            		
		            	gen.writeStartObject("assistantInfo");
		                gen.write("name", ""+rs.getString(4));
		                gen.write("idAssistant", ""+rs.getString(2));
		                gen.write("username", ""+rs.getString(5));
		                gen.write("password", ""+rs.getString(6));
		                gen.write("phone", ""+rs.getString(7));
		                gen.write("lastName", ""+rs.getString(9));
		                gen.write("arriveTime", ""+rs.getString(10));
		                gen.write("status", ""+rs.getString(8));
		                gen.write("level", ""+rs.getString(1));
		                gen.write("lunes", ""+rs.getString(11));
		                gen.write("miercoles", ""+rs.getString(12));
		                gen.write("jueves", ""+rs.getString(13));
		                gen.write("sabado", ""+rs.getString(14));
		                gen.write("type", "asistente");
		                
		                gen.writeEnd();
		            }
		            gen.writeEnd();
		        }
	    		}
	        return swriter.toString();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
    
    public static String setAssistant(String array) {
		StringWriter swriter = new StringWriter();
	    try {
	    		JSONObject obj = new JSONObject(array);
	    		
			//JSONArray selectedPeople = obj.getJSONArray("infoAssistant");
			
			JSONObject asis = obj.getJSONObject("infoAssistant");
			
	    		if(asis.has("idAssistant") ) {
	    			if(asis.getString("idAssistant").length() == 0 ) {
	    				
	    				int lunes=0, miercoles=0, jueves=0, sabado=0;
	    				
	    				if(asis.getString("lunes").length() == 0) lunes=0; else lunes=asis.getInt("lunes");
	    				if(asis.getString("miercoles").length() == 0) miercoles=0; else miercoles=asis.getInt("miercoles");
	    				if(asis.getString("jueves").length() == 0) jueves=0; else jueves=asis.getInt("jueves");
	    				if(asis.getString("sabado").length() == 0) sabado=0; else sabado=asis.getInt("sabado");
	    				
	    				CallableStatement cStmt = conn.prepareCall("{call setUsuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
	    				
	        		    cStmt.setString(1, asis.getString("name"));
	        		    cStmt.setString(2, asis.getString("lastName"));
	        		    cStmt.setString(3, asis.getString("phone"));
	        		    cStmt.setString(4, asis.getString("password"));
	        		    cStmt.setString(5, asis.getString("username"));
	        		    cStmt.setString(6, asis.getString("arriveTime"));
	        		    cStmt.setString(7, asis.getString("level"));
	        		    cStmt.setString(8, asis.getString("type"));
	        		    cStmt.setInt(9, lunes);
	        		    cStmt.setInt(10, miercoles);
	        		    cStmt.setInt(11, jueves);
	        		    cStmt.setInt(12, sabado);
	        		    cStmt.registerOutParameter(13, Types.VARCHAR);
	        		    cStmt.registerOutParameter(14, Types.VARCHAR);
	        		    cStmt.registerOutParameter(15, Types.INTEGER);
	        		    cStmt.registerOutParameter(16, Types.INTEGER);
	        		    cStmt.execute();    
	    				
	    				String messageError, nombre, typeUsuario;
	    				int err;
	    				
	    				messageError=cStmt.getString(16);
	    				nombre=cStmt.getString(13);
	    				typeUsuario=cStmt.getString(14);
	    				err=cStmt.getInt(15);
	    		
	    		        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	    		        		gen.writeStartObject();
	    		        		
	    		        		gen.writeStartObject("assistantInfo");
	    		        		gen.write("name", ""+nombre);
	    		        		gen.write("typeUser", ""+typeUsuario);
	    		        		gen.write("err", ""+err);
	    		        		gen.write("messageError", ""+messageError);
	    		        		gen.writeEnd();
	    		            
	    		        		gen.writeEnd();
	    		        }
	    			}else {
	    				
	    				CallableStatement cStmt = conn.prepareCall("{call updateUsuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
	    				
	    				cStmt.setInt(1, asis.getInt("idAssistant"));
	        		    cStmt.setString(2, asis.getString("name"));
	        		    cStmt.setString(3, asis.getString("lastName"));
	        		    cStmt.setString(4, asis.getString("phone"));
	        		    cStmt.setString(5, asis.getString("password"));
	        		    cStmt.setString(6, asis.getString("username"));
	        		    cStmt.setString(7, asis.getString("arriveTime"));
	        		    cStmt.setString(8, asis.getString("level"));
	        		    cStmt.setString(9, asis.getString("status"));
	        		    cStmt.setInt(10, asis.getInt("lunes"));
	        		    cStmt.setInt(11, asis.getInt("miercoles"));
	        		    cStmt.setInt(12, asis.getInt("jueves"));
	        		    cStmt.setInt(13, asis.getInt("sabado"));
	        		    cStmt.registerOutParameter(14, Types.VARCHAR);
	        		    cStmt.registerOutParameter(15, Types.INTEGER);
	        		    cStmt.registerOutParameter(16, Types.VARCHAR);
	        		    cStmt.execute();    
	    				
	    				String messageError, nombre;
	    				int err;
	    				
	    				messageError=cStmt.getString(16);
	    				nombre=cStmt.getString(14);
	    				err=cStmt.getInt(15);
	    		
	    		        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	    		        		gen.writeStartObject();
	    		        		
	    		        		gen.writeStartObject("assistantInfo");
	    		        		gen.write("name", ""+nombre);
	    		        		gen.write("err", ""+err);
	    		        		gen.write("messageError", ""+messageError);
	    		        		gen.writeEnd();
	    		            
	    		        		gen.writeEnd();
	    		        }
	    				
	    			}
	    			
		       
	    		}
	        return swriter.toString();
	    	} catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
    
    public static String getAssignedStudents(int id) {
    	System.out.println("enra a assigned");
    	
    	StringWriter swriter = new StringWriter();
    	String queryAlumnos = "SELECT a.idAlumno, a.nombre, a.apellido, a.nivel, asis.horaEntrada,subtime(curtime(), horaEntrada) ,asis.Asistente_Usuario_idUsuario, us.nombre, us.apellido, asis.tiempoReducido, asist.nivel, a.nombreMadre, a.apellidoMadre, a.telCasa, a.celMadre \n" + 
    			"FROM Asistencia as asis JOIN Alumno as a JOIN Usuario as us JOIN Asistente as asist ON asis.Alumno_idAlumno=a.idAlumno AND us.idUsuario=asis.Asistente_Usuario_idUsuario \n" + 
    			"AND asist.Usuario_idUsuario=us.idUsuario WHERE asis.fecha=CURDATE() AND asis.horaSalida = '00:00:00' AND us.idUsuario="+ Integer.toString(id)+ ";";
    	
    	Asistente asis = new Asistente();
    	
    	asis = asis.getInfoAsistente(id);
    	
		try {
			prepareStat = conn.prepareStatement(queryAlumnos);
		
			System.out.println("query alumnos " + queryAlumnos);
			ResultSet rsAlumnos = prepareStat.executeQuery();
			
			
				try (JsonGenerator gen = Json.createGenerator(swriter)) {
		            	gen.writeStartObject();
		            		gen.writeStartObject("infoLogin");
		            			gen.write("code", 1);
		            			gen.write("id", id);
		            			gen.write("type", "asistente");
		            			gen.write("name", asis.getNombre());
		            			gen.write("level", asis.getNivel());
		            			gen.writeStartArray("students");
			            			if(rsAlumnos.isBeforeFirst()) {
			            				System.out.println("si hay");
			            				while(rsAlumnos.next()) {
					    		    			gen.writeStartObject();
					    			    			gen.write("id",rsAlumnos.getInt(1));
					    			    			gen.write("name",rsAlumnos.getString(2));
					    			    			gen.write("level",rsAlumnos.getString(4));
					    			    			gen.write("lastName",rsAlumnos.getString(3));
					    			    			gen.write("entranceTime",rsAlumnos.getString(5));
					    			    			gen.write("time",rsAlumnos.getString(6));
					    			    			gen.write("reducedTime",rsAlumnos.getString(10));
					    			    			gen.write("nameMother", rsAlumnos.getString(12));
					    			    			gen.write("lastNameMother", rsAlumnos.getString(13));
					    			    			gen.write("telHome", rsAlumnos.getString(14));
					    			    			gen.write("celMother", rsAlumnos.getString(15));
					    			    		gen.writeEnd();
				    		    			}
			    				    }else {
				    		    			System.out.println("No hay");
				    		    		}
		            			gen.writeEnd();
			            gen.writeEnd();
		            gen.writeEnd();
		        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    		return swriter.toString();
    }

	public int getIdAsistente() {
		return idAsistente;
	}

	public void setIdAsistente(int idAsistente) {
		this.idAsistente = idAsistente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getHoraLlegada() {
		return horaLlegada;
	}

	public void setHoraLlegada(String horaLlegada) {
		this.horaLlegada = horaLlegada;
	}

	public int getLunes() {
		return lunes;
	}

	public void setLunes(int lunes) {
		this.lunes = lunes;
	}

	public int getMiercoles() {
		return miercoles;
	}

	public void setMiercoles(int miercoles) {
		this.miercoles = miercoles;
	}

	public int getJueves() {
		return jueves;
	}

	public void setJueves(int jueves) {
		this.jueves = jueves;
	}

	public int getSabado() {
		return sabado;
	}

	public void setSabado(int sabado) {
		this.sabado = sabado;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
}
