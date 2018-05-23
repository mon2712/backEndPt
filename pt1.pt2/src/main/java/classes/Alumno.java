package classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

public class Alumno {
	public static final String DESEMPEÑO_ALTO= "bueno";
	public static final String DESEMPEÑO_MEDIO = "medio";
	public static final String DESEMPEÑO_BAJO = "malo";
	
	
	public static String getDesempeñoAlto() {
		return DESEMPEÑO_ALTO;
	}
	public static String getDesempeñoMedio() {
		return DESEMPEÑO_MEDIO;
	}
	public static String getDesempeñoBajo() {
		return DESEMPEÑO_BAJO;
	}


	private String idAlumno;
	private String apellidoPaterno;
	private String nombre;
	private String fechaNac;
	private String grado;
	private String tel;
	private String nombreMadre;
	private String apellidoMadre;
	private String emailMadre;
	private String celMadre;
	private String tutorNombre;
	private String celTutor;
	private String telTutor;
	private String trabajoTutor;
	private String faxTutor;
	private String materia;
	private String fecIngreso;
	private String nivelAnterior;
	private String nivelActual;
	private String ultimaAusencia;
	private String statusAnterior;
	private String statusActual;
	private int set;
	private int numHojas;
	private int gradoNum;
	private int fluidez;
	private int fluidezResta;
	private int trabajoB;
	private int concentracion;
	private int fluidezMultiplicacion;
	private int calculoMental;
	private int procedimientosCortos;
	private int divisionConResta;
	private int simplificacion;
	private int cuenta;
	private int anteSuc;
	private int puntajeDesempeño;
	private String desempeño;
	
	
	public String getDesempeño() {
		return desempeño;
	}
	public void setDesempeño(String desempeño) {
		this.desempeño = desempeño;
	}
	public int getPuntajeDesempeño() {
		return puntajeDesempeño;
	}
	public void setPuntajeDesempeño(int puntajeDesempeño) {
		this.puntajeDesempeño = puntajeDesempeño;
	}
	public int getAnteSuc() {
		return anteSuc;
	}
	public void setAnteSuc(int anteSuc) {
		this.anteSuc = anteSuc;
	}
	public int getCuenta() {
		return cuenta;
	}
	public void setCuenta(int cuenta) {
		this.cuenta = cuenta;
	}
	public int getFluidez() {
		return fluidez;
	}
	public void setFluidez(int fluidez) {
		this.fluidez = fluidez;
	}
	public int getDivisionConResta() {
		return divisionConResta;
	}
	public void setDivisionConResta(int divisionConResta) {
		this.divisionConResta = divisionConResta;
	}
	public int getSimplificacion() {
		return simplificacion;
	}
	public void setSimplificacion(int simplificacion) {
		this.simplificacion = simplificacion;
	}
	public int getTrabajoB() {
		return trabajoB;
	}
	public int getFluidezMultiplicacion() {
		return fluidezMultiplicacion;
	}
	public void setFluidezMultiplicacion(int fluidezMultiplicacion) {
		this.fluidezMultiplicacion = fluidezMultiplicacion;
	}
	public int getCalculoMental() {
		return calculoMental;
	}
	public void setCalculoMental(int calculoMental) {
		this.calculoMental = calculoMental;
	}
	public int getProcedimientosCortos() {
		return procedimientosCortos;
	}
	public void setProcedimientosCortos(int procedimientosCortos) {
		this.procedimientosCortos = procedimientosCortos;
	}
	public void setFecIngreso(String fecIngreso) {
		this.fecIngreso = fecIngreso;
	}
	public void setTrabajoB(int trabajoB) {
		this.trabajoB = trabajoB;
	}

	
	public int getFluidezResta() {
		return fluidezResta;
	}
	public void setFluidezResta(int fluidezResta) {
		this.fluidezResta = fluidezResta;
	}
	public int getConcentracion() {
		return concentracion;
	}
	public void setConcentracion(int concentración) {
		this.concentracion = concentración;
	}
	public int getGradoNum() {
		return gradoNum;
	}
	public void setGradoNum(int gradoNum) {
		this.gradoNum = gradoNum;
	}
	public String getIdAlumno() {
		return idAlumno;
	}
	public void setIdAlumno(String idAlumno) {
		this.idAlumno = idAlumno;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getGrado() {
		return grado;
	}
	public void setGrado(String grado) {
		this.grado = grado;
		switch (grado) {
			case "pk1":
				this.gradoNum=-1;
			break;
			case "pk2":
				this.gradoNum=-2;
			break;
			case "pk3":
				this.gradoNum=-3;
			break;
			default:
				this.gradoNum=Integer.parseInt(this.grado);
			break;
		}
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getNombreMadre() {
		return nombreMadre;
	}
	public void setNombreMadre(String nombreMadre) {
		this.nombreMadre = nombreMadre;
	}
	public String getApellidoMadre() {
		return apellidoMadre;
	}
	public void setApellidoMadre(String apellidoMadre) {
		this.apellidoMadre = apellidoMadre;
	}
	public String getEmailMadre() {
		return emailMadre;
	}
	public void setEmailMadre(String emailMadre) {
		this.emailMadre = emailMadre;
	}
	public String getCelMadre() {
		return celMadre;
	}
	public void setCelMadre(String celMadre) {
		this.celMadre = celMadre;
	}
	public String getTutorNombre() {
		return tutorNombre;
	}
	public void setTutorNombre(String tutorNombre) {
		this.tutorNombre = tutorNombre;
	}
	public String getCelTutor() {
		return celTutor;
	}
	public void setCelTutor(String celTutor) {
		this.celTutor = celTutor;
	}
	public String getTelTutor() {
		return telTutor;
	}
	public void setTelTutor(String telTutor) {
		this.telTutor = telTutor;
	}
	public String getTrabajoTutor() {
		return trabajoTutor;
	}
	public void setTrabajoTutor(String trabajoTutor) {
		this.trabajoTutor = trabajoTutor;
	}
	public String getFaxTutor() {
		return faxTutor;
	}
	public void setFaxTutor(String faxTutor) {
		this.faxTutor = faxTutor;
	}
	public String getMateria() {
		return materia;
	}
	public void setMateria(String materia) {
		this.materia = materia;
	}
	public String getNivelAnterior() {
		return nivelAnterior;
	}
	public void setNivelAnterior(String nivelAnterior) {
		this.nivelAnterior = nivelAnterior;
	}
	public String getNivelActual() {
		return nivelActual;
	}
	public void setNivelActual(String nivelActual) {
		this.nivelActual = nivelActual;
	}
	public String getStatusAnterior() {
		return statusAnterior;
	}
	public void setStatusAnterior(String statusAnterior) {
		this.statusAnterior = statusAnterior;
	}
	public String getStatusActual() {
		return statusActual;
	}
	public void setStatusActual(String statusActual) {
		this.statusActual = statusActual;
	}
	public int getSet() {
		return set;
	}
	public void setSet(int set) {
		this.set = set;
	}
	public int getNumHojas() {
		return numHojas;
	}
	public void setNumHojas(int numHojas) {
		this.numHojas = numHojas;
	}

	public String getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(java.util.Date fechaNac) {
		SimpleDateFormat formatF = new SimpleDateFormat("yyyy-MM-dd");
		this.fechaNac = formatF.format(fechaNac);	
	}
	public String getFecIngreso() {
		return fecIngreso;
	}
	public void setFecIngreso(java.util.Date fecIngreso) {
		SimpleDateFormat formatF = new SimpleDateFormat("yyyy-MM-dd");
		this.fecIngreso = formatF.format(fecIngreso);
	}
	public String getUltimaAusencia() {
		return ultimaAusencia;
	}
	public void setUltimaAusencia(String  ultimaAusencia) {
		if(ultimaAusencia == null || ultimaAusencia.equals("")) {
			this.ultimaAusencia=ultimaAusencia;
		}	
		else {
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("MM/yyyy");
			Date fecha = null;
			try {
				fecha = formatoDelTexto.parse(ultimaAusencia);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
			SimpleDateFormat formatF = new SimpleDateFormat("yyyy-MM-dd");
			this.ultimaAusencia = formatF.format(fecha);
			
		}
	}


	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
    
    public static String obtenerFichaAlumno(int idAlumno) {
    		
    		StringWriter swriter = new StringWriter();
    		try {
    		    CallableStatement cStmt = conn.prepareCall("{call getStudentFile(?,?,?,?,?,?,?,?,?)}");
	
    		    cStmt.setInt(1, idAlumno);
    		    cStmt.registerOutParameter(2, Types.INTEGER);
    		    cStmt.registerOutParameter(3, Types.INTEGER);
    		    cStmt.registerOutParameter(4, Types.VARCHAR);
    		    cStmt.registerOutParameter(5, Types.VARCHAR);
    		    cStmt.registerOutParameter(6, Types.VARCHAR);
    		    cStmt.registerOutParameter(7, Types.VARCHAR);
    		    cStmt.registerOutParameter(8, Types.VARCHAR);
    		    cStmt.registerOutParameter(9, Types.INTEGER);
    		    cStmt.execute();    
    		    
    		    int err, debeColegiatura, asistencias;
    		    String nombre, tutor, telefono, apellido, nivel,debe;
    		    err=cStmt.getInt(9);
    		    debeColegiatura=cStmt.getInt(2);
    		    asistencias=cStmt.getInt(3);
    		    nombre = cStmt.getString(5);
    		    apellido = cStmt.getString(6);
    		    nivel = cStmt.getString(7);
    		    telefono = cStmt.getString(8);
    		    tutor=cStmt.getString(4);
    		    
    		    System.out.println("usuario: "+nombre + " " + apellido + " " + nivel+"telefono"+telefono);
    		    if (debeColegiatura == 1) debe="true"; else debe="false";
    		    if (telefono == null) telefono="";
    		    
    		    if(err == 1) {
    		    		System.out.println("no existe alumno");
    		    		try (JsonGenerator gen = Json.createGenerator(swriter)) {
    	    	            gen.writeStartObject();
    	    	            gen.writeStartObject("student");
    	    	            gen.write("code", 1);
    	    	            gen.write("type", "No existen alumnos con ese id");
    	    	            gen.writeEnd();
    	    	            gen.writeEnd();
    	    	        }
    		    }else if(err == 0) {
    		    		System.out.println("existe un alumno"+nombre);
	    	        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	    	            gen.writeStartObject();
	    	            gen.writeStartObject("student");
	    	            gen.write("code", 0);
	    	            gen.write("name", nombre);
	    	            gen.write("lastName", apellido);
	    	            gen.write("level", nivel);
	    	            gen.write("tutor", tutor);
	    	            gen.write("phone", telefono);
	    	            gen.write("cellphone", "5564555656");
	    	            gen.write("missingPayment", debe);
	    	            gen.write("assistances", asistencias);
	    	            gen.writeEnd();
	    	            gen.writeEnd();
	    	        }
    		    }
    	        
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		
    		return swriter.toString();
    }
    
    public static String getAlumnos(String filter) {
    		System.out.println("filter en funcion"+filter);
    		StringWriter swriter = new StringWriter();
        try {
            String getQueryStatement = "SELECT * FROM alumno WHERE CONCAT(nombre, ' ',apellido) LIKE '%"+filter+"%';";

            prepareStat = conn.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = prepareStat.executeQuery();

            try (JsonGenerator gen = Json.createGenerator(swriter)) {
            	gen.writeStartObject();
	            gen.writeStartArray("allStudents");
	            while(rs.next()) {
	            		System.out.println(rs.getString(1) + " " + rs.getString(3)+" "+rs.getString(2));
	                gen.writeStartObject();
		                gen.write("name", rs.getString(3)+ " " + rs.getString(2));
		                gen.write("idStudent", rs.getString(1));
		                gen.write("level", ""+rs.getString(9));
		                gen.write("startDate", ""+rs.getString(7));
		                gen.write("grade", ""+rs.getString(4));
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
    
    public static String getAlumnosSinProyeccion() {
		StringWriter swriter = new StringWriter();
	    try {
	        String getQueryStatement = "SELECT * FROM Alumno AS al\r\n" + 
	        		"WHERE NOT EXISTS (\r\n" + 
	        		"  SELECT * FROM ProyeccionAnual AS pa WHERE al.idAlumno=pa.Alumno_idAlumno\r\n" + 
	        		") AND al.estatus='N' AND al.nivel in ('3A','2A','A','B','C','D');";
	
	        prepareStat = conn.prepareStatement(getQueryStatement);
	
	        // Execute the Query, and get a java ResultSet
	        ResultSet rs = prepareStat.executeQuery();
	
	        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	        	gen.writeStartObject();
	            gen.writeStartArray("allStudents");
	            while(rs.next()) {
	            		System.out.println(rs.getString(1) + " " + rs.getString(3)+" "+rs.getString(2));
	                gen.writeStartObject();
		                gen.write("name", rs.getString(3)+ " " + rs.getString(2));
		                gen.write("idStudent", rs.getString(1));
		                gen.write("level", ""+rs.getString(9));
		                gen.write("startDate", ""+rs.getString(7));
		                gen.write("grade", ""+rs.getString(4));
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
    
    public static String getAlumnosConProyeccion() {
		StringWriter swriter = new StringWriter();
	    try {
	        String getQueryStatement = "SELECT * FROM Alumno as al RIGHT JOIN ProyeccionAnual as pa ON al.idAlumno=pa.Alumno_idAlumno WHERE al.nivel in ('3A','2A','A','B','C','D');";
	
	        prepareStat = conn.prepareStatement(getQueryStatement);
	
	        // Execute the Query, and get a java ResultSet
	        ResultSet rs = prepareStat.executeQuery();
	
	        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	        	gen.writeStartObject();
	            gen.writeStartArray("allStudents");
	            while(rs.next()) {
	            		System.out.println(rs.getString(1) + " " + rs.getString(3)+" "+rs.getString(2));
	                gen.writeStartObject();
		                gen.write("name", rs.getString(3)+ " " + rs.getString(2));
		                gen.write("idStudent", rs.getString(1));
		                gen.write("level", ""+rs.getString(9));
		                gen.write("startDate", ""+rs.getString(7));
		                gen.write("grade", ""+rs.getString(4));
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
    
}

