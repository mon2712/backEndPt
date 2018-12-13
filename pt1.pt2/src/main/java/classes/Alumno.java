package classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
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

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.WorkingMemory;
import org.json.JSONArray;
import org.json.JSONObject;

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
	private int adeudo;
	private int asistencias;
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
	private int lunes;
	private int miercoles;
	private int jueves;
	private int sabado;
	private String desempeño;
	
	
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
			case "PK1":
				this.gradoNum=-1;
			break;
			case "PK2":
				this.gradoNum=-2;
			break;
			case "PK3":
				this.gradoNum=-3;
			break;
			case "K":
				this.gradoNum=-4;
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
	public int getAdeudo() {
		return adeudo;
	}
	public void setAdeudo(int adeudo) {
		this.adeudo = adeudo;
	}
	public int getAsistencias() {
		return asistencias;
	}
	public void setAsistencias(int asistencias) {
		this.asistencias = asistencias;
	}


	static PreparedStatement prepareStat = null;
    //private static Connection conn = BaseDatos.conectarBD();
    
    public static String obtenerFichaAlumno(int idAlumno) throws SQLException {
    		Connection conn = BaseDatos.conectarBD();
    		
    		StringWriter swriter = new StringWriter();
    		try {
    		    CallableStatement cStmt = conn.prepareCall("{call getFichaAlumno(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
	
    		    cStmt.setInt(1, idAlumno);
    		    cStmt.registerOutParameter(2, Types.INTEGER);
    		    cStmt.registerOutParameter(3, Types.INTEGER);
    		    cStmt.registerOutParameter(4, Types.VARCHAR);
    		    cStmt.registerOutParameter(5, Types.VARCHAR);
    		    cStmt.registerOutParameter(6, Types.VARCHAR);
    		    cStmt.registerOutParameter(7, Types.VARCHAR);
    		    cStmt.registerOutParameter(8, Types.VARCHAR);
    		    cStmt.registerOutParameter(9, Types.INTEGER);
    		    cStmt.registerOutParameter(10, Types.VARCHAR);
    		    cStmt.registerOutParameter(11, Types.VARCHAR);
    		    cStmt.registerOutParameter(12, Types.VARCHAR);
    		    cStmt.registerOutParameter(13, Types.VARCHAR);
    		    cStmt.registerOutParameter(14, Types.INTEGER);
    		    cStmt.registerOutParameter(15, Types.INTEGER);
    		    cStmt.execute();    
    		    
    		    int err, debeColegiatura;
    		    String telefono="",debe;
    		    
    		    err=cStmt.getInt(15);
    		    debeColegiatura=cStmt.getInt(2);
    		    
    		    Alumno alumno = new Alumno();
    		    
    		    
    		    alumno.setNombre(cStmt.getString(11));
    		    alumno.setApellidoPaterno(cStmt.getString(12));
    		    alumno.setNivelActual(cStmt.getString(13));
    		    alumno.setTel(cStmt.getString(8));
    		    alumno.setNombreMadre(cStmt.getString(6));
    		    alumno.setApellidoMadre(cStmt.getString(7));
    		    alumno.setCelMadre(cStmt.getString(9));
    		    alumno.setAdeudo(cStmt.getInt(10));
    		    alumno.setAsistencias(cStmt.getInt(3));
    		    alumno.setTutorNombre(cStmt.getString(4));
    		    alumno.setCelTutor(cStmt.getString(5));
    		    
    		    if (alumno.getAdeudo() == 1) debe="true"; else debe="false";
    		    if (alumno.getTel() == null) telefono=""; else telefono=alumno.getTel();
    		    
    		    if(err == 1) {
    		    		try (JsonGenerator gen = Json.createGenerator(swriter)) {
    	    	            gen.writeStartObject();
    	    	            gen.writeStartObject("student");
    	    	            		gen.write("code", 1);
    	    	            		gen.write("type", "No existe alumno con ese id");
    	    	            gen.writeEnd();
    	    	            gen.writeEnd();
    	    	        }
    		    }else if(err == 0) {
	    	        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	    	            gen.writeStartObject();
	    	            gen.writeStartObject("student");
	    	            		gen.write("code", 0);
	    	            		gen.write("type","student");
	    	            		gen.write("name", alumno.getNombre());
	    	            		gen.write("lastName", alumno.getApellidoPaterno());
	    	            		gen.write("level", alumno.getNivelActual());
	    	            		gen.write("tutor", alumno.getTutorNombre());
	    	            		gen.write("cellTutor", alumno.getCelTutor());
	    	            		gen.write("missingPayment", debe);
	    	            		gen.write("assistances", alumno.getAsistencias());
	    	            		gen.write("nameMom", ""+alumno.getNombreMadre());
	    	            		gen.write("lastNameMom", ""+alumno.getApellidoMadre());
	    	            		gen.write("phoneHouse", telefono);
	    	            		gen.write("cellMom", alumno.getCelMadre());
	    	            gen.writeEnd();
	    	            gen.writeEnd();
	    	        }
    		    }
    	        
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		
    		if(!conn.isClosed()) {
    			conn.close();
        }
        
    		
    		return swriter.toString();
    }
    
    public static String getAlumnos(String filter) {
    		Connection conn = BaseDatos.conectarBD();
    		StringWriter swriter = new StringWriter();
        try {
            String getQueryStatement = "SELECT * FROM Alumno WHERE CONCAT(nombre, ' ',apellido) LIKE '%"+filter+"%';";

            prepareStat = conn.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = prepareStat.executeQuery();

            try (JsonGenerator gen = Json.createGenerator(swriter)) {
            	gen.writeStartObject();
	            gen.writeStartArray("allStudents");
	            while(rs.next()) {
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
            
            if(!conn.isClosed()) {
	    			conn.close();
	        }
            
            return swriter.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String getAlumnosSinProyeccion() {
    		Connection conn = BaseDatos.conectarBD();
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
	        
	        if(!conn.isClosed()) {
	    			conn.close();
	        }
	        
	        return swriter.toString();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
    }
    
    public static String getAlumnosConProyeccion() {
    		Connection conn = BaseDatos.conectarBD();
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
	        
	        if(!conn.isClosed()) {
	    			conn.close();
	        }
	        
	        return swriter.toString();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
    }
    
    public static int getDiaInmediato(String condicion,int lunes, int miercoles, int jueves, int sabado, int today) throws SQLException{
    	Connection conn = BaseDatos.conectarBD();
    	int dia=0;
    	int[] arrayDays = new int[4];
    	List<Integer> diasQueViene = new ArrayList<Integer> ();
    	int todayNew=0;
    	
		if(today==2) todayNew=0;
		if(today==4) todayNew=1;
		if(today==5) todayNew=2;
		if(today==7) todayNew=3;
		
		arrayDays[0] = lunes; //lunes
		arrayDays[1] = miercoles; //miercoles
		arrayDays[2] = jueves; //jueves
		arrayDays[3] = sabado; //sabado
		
		int count=0;
		//guardando en orden de ascendente los dias que viene que no sean hoy
		for(int i=0; i<4; i++) {
			if(todayNew != i && arrayDays[i] == 1) {
						if(i == 0) diasQueViene.add(2);
						if(i == 1) diasQueViene.add(4); 
						if(i == 2) diasQueViene.add(5); 
						if(i == 3) diasQueViene.add(7); 
			}
			
		}
		int size=diasQueViene.size();
		//si solo viene un dia (hoy), el dia anterior o siguiente sera el mismo
		if(size == 0) {
				dia=today;
		}
		else {
			
			int auxiliar=0;
			
			for(int j=0;j<size;j++) {
				if(condicion=="anterior"){
					//guardando en auxiliar mientras el dia sea menor que hoy
					if(diasQueViene.get(j)<today) {
						auxiliar=diasQueViene.get(j);
					}
				}
				else {//dia siguiente
					//guardando en auxiliar mientras el dia sea mayor que hoy
					if(diasQueViene.get(j)>today) {
						auxiliar=diasQueViene.get(j);
						break;
					}
				}
			}
			//Primer dia menor(anterior), mayor(siguiente) que hoy
			if(auxiliar!=0) {
				dia=auxiliar;
			}
			//si no hay dias menores(anterior), mayores(siguiente) que hoy
			else {
				if(condicion=="anterior"){
					dia=diasQueViene.get(size-1);
				}
				else { //dia siguiente
					dia=diasQueViene.get(0);
				}
			}
			
		}
		
		if(!conn.isClosed()) {
				conn.close();
	    }
		
		return dia;
    }
    
    public static String getBoleta(String alumno) {
    		Connection conn = BaseDatos.conectarBD();
    		System.out.println(alumno);
    		//JSONObject obj2 = new JSONObject(alumno);
    		
		StringWriter swriter = new StringWriter();
	    try {
	        String getQueryStatement = "Select re.idRegistro, DAY(re.fecha) as dia, MONTH(re.fecha) as mes, YEAR(re.fecha) as año, us.nombre, se.`set`, re.tipo, re.tiempo, re.`1`, re.`2`,re.`3`,re.`4`,re.`5`,re.`6`,re.`7`,re.`8`,re.`9`,re.`10`, niv.nombre \r\n" + 
	        		"from Registro as re JOIN Usuario as us JOIN `Set` as se JOIN Nivel as niv\r\n" + 
	        		"ON re.Asistente_Usuario_idUsuario=us.idUsuario AND se.idSet=re.Set_idSet AND se.Nivel_idNivel=niv.idNivel\r\n" + 
	        		"WHERE Alumno_idAlumno="+alumno+ " ORDER BY fecha ASC;";
	
	        prepareStat = conn.prepareStatement(getQueryStatement);
	
	        // Execute the Query, and get a java ResultSet
	        ResultSet rs = prepareStat.executeQuery();
	        
	        int i=0;
	        List<String> grades=new ArrayList<>();
	        List<String> mes=new ArrayList<>();
	        
	        try (JsonGenerator gen = Json.createGenerator(swriter)) {
	        		gen.writeStartObject();
	        		gen.writeStartArray("gradesStudent"); //Contiene el array de calificaciones
		        int bandera=0, bandera2=0;
		            if (!rs.isBeforeFirst()){
		            		//ResultSet is empty
		            		
		            		gen.writeStartObject();
				        		gen.write("err", 1);
				        		gen.write("messageError", "No tiene calificaciones registrados");
			        		gen.writeEnd();
			        		
			        		gen.writeEnd();
						gen.writeEnd();
		    				//gen.writeEnd();
		            	}else {
				        while(rs.next()) {
					        	if(grades.isEmpty()) {
					        		grades.add(rs.getString(4));
					        		
					        		gen.writeStartObject(); //Empieza el objeto de un año
					        		gen.write("year", rs.getString(4));
					        		
					        		if(mes.isEmpty()) {
					        			mes.add(rs.getString(3));
					        			
					        			gen.writeStartArray("months"); //Empieza el array que contiene los meses
						        			gen.writeStartObject(); //Empieza el objeto de 1 mes
							        		gen.write("month", rs.getString(3));
						        			gen.writeStartArray("days"); //Empieza el array de los días
						        			
						        				gen.writeStartObject(); //Empieza el objeto de 1 día 
						        					gen.write("idRegister", rs.getInt(1));
								        			gen.write("day", rs.getInt(2));
								        			gen.write("set", rs.getInt(6));
								        			gen.write("typeSet", ""+rs.getString(7));
								        			gen.write("time", rs.getInt(8));
								        			gen.write("assistant", rs.getString(5));
							        				gen.write("1", rs.getInt(9));
							        				gen.write("2", rs.getInt(10));
							        				gen.write("3", rs.getInt(11));
							        				gen.write("4", rs.getInt(12));
							        				gen.write("5", rs.getInt(13));
							        				gen.write("6", rs.getInt(14));
							        				gen.write("7", rs.getInt(15));
							        				gen.write("8", rs.getInt(16));
							        				gen.write("9", rs.getInt(17));
							        				gen.write("10", rs.getInt(18));
							        				gen.write("nivel", rs.getString(19));
							        			gen.writeEnd(); //Cierra el objeto de 1 día
					        		}else {
					        			System.out.println("si no entra a a empty");
					        		}
					        	}else {
					        		
				        			for(i=0; i<grades.size(); i++) {
				        				
					        			if(grades.get(i).equals(rs.getString(4))) { //donde el año sea igual 
					        				
					        				//System.out.println("año en for " + rs.getString(4));
					        				
					        				for(int j=0; j<mes.size(); j++) { //buscar meses 
						        				
					        					if(mes.get(j).equals(rs.getString(3))) { // si mes ya existe agrego un día
					        						//System.out.println("mes en for " + rs.getString(3));

					        						//System.out.println("el mes es igual en el if");
					        						
					        						gen.writeStartObject();
						        						gen.write("idRegister", rs.getInt(1));
									        			gen.write("day", rs.getInt(2));
									        			gen.write("set", rs.getInt(6));
									        			gen.write("typeSet", ""+rs.getString(7));
									        			gen.write("time", rs.getInt(8));
									        			gen.write("assistant", rs.getString(5));
								        				gen.write("1", rs.getInt(9));
								        				gen.write("2", rs.getInt(10));
								        				gen.write("3", rs.getInt(11));
								        				gen.write("4", rs.getInt(12));
								        				gen.write("5", rs.getInt(13));
								        				gen.write("6", rs.getInt(14));
								        				gen.write("7", rs.getInt(15));
								        				gen.write("8", rs.getInt(16));
								        				gen.write("9", rs.getInt(17));
								        				gen.write("10", rs.getInt(18));
								        				gen.write("nivel", rs.getString(19));
								        			gen.writeEnd();
								        			
								        			bandera2=0;
						        				}else {
						        					//System.out.println("mes en for cuando bandera2 se vuelve 1 " + rs.getString(3));
						        					bandera2=1;
						        					//System.out.println("entra al else año: " + rs.getString(4) + "mes " + rs.getString(3));
						        				}
					        					
					        										        					
					        				}
					        				
					        				
					        				bandera=0;
					        				
					        				

					        			}else {
					        				bandera=1;
					        			}
				        			}
				        			
				        			if(bandera2==1) { 
			        					System.out.println("si entra al bandera2 a mes diferente de if");
			        					System.out.println("entra al else año: " + rs.getString(4) + "mes " + rs.getString(3));
			        					gen.writeEnd();
				        				gen.writeEnd();

				        				//gen.writeEnd();
				        				
				        				
				        				gen.writeStartObject();
			        					gen.write("month", rs.getString(3));
				        				gen.writeStartArray("days");
					        				gen.writeStartObject();
						        				gen.write("idRegister", rs.getInt(1));
							        			gen.write("day", rs.getInt(2));
							        			gen.write("set", rs.getInt(6));
							        			gen.write("typeSet", ""+rs.getString(7));
							        			gen.write("time", rs.getInt(8));
							        			gen.write("assistant", rs.getString(5));
						        				gen.write("1", rs.getInt(9));
						        				gen.write("2", rs.getInt(10));
						        				gen.write("3", rs.getInt(11));
						        				gen.write("4", rs.getInt(12));
						        				gen.write("5", rs.getInt(13));
						        				gen.write("6", rs.getInt(14));
						        				gen.write("7", rs.getInt(15));
						        				gen.write("8", rs.getInt(16));
						        				gen.write("9", rs.getInt(17));
						        				gen.write("10", rs.getInt(18));
						        				gen.write("nivel", rs.getString(19));

						        			gen.writeEnd();
						        			
						        		mes.add(rs.getString(3));
				        			}

				        							        			
				        			if(bandera==1) {
				        				System.out.println("entra a bandera 1");
				        				gen.writeEnd();
				        				gen.writeEnd();
				        				gen.writeEnd();

				        				
				        				
				        				gen.writeStartObject();
				        					gen.write("year", rs.getString(4));
				        					gen.writeStartArray("months");
				        					gen.writeStartObject();
					        					gen.write("month", rs.getString(3));
						        				gen.writeStartArray("days");
							        				gen.writeStartObject();
								        				gen.write("idRegister", rs.getInt(1));
									        			gen.write("day", rs.getInt(2));
									        			gen.write("set", rs.getInt(6));
									        			gen.write("typeSet", ""+rs.getString(7));
									        			gen.write("time", rs.getInt(8));
									        			gen.write("assistant", rs.getString(5));
								        				gen.write("1", rs.getInt(9));
								        				gen.write("2", rs.getInt(10));
								        				gen.write("3", rs.getInt(11));
								        				gen.write("4", rs.getInt(12));
								        				gen.write("5", rs.getInt(13));
								        				gen.write("6", rs.getInt(14));
								        				gen.write("7", rs.getInt(15));
								        				gen.write("8", rs.getInt(16));
								        				gen.write("9", rs.getInt(17));
								        				gen.write("10", rs.getInt(18));
								        				gen.write("nivel", rs.getString(19));
								        			gen.writeEnd(); //Cierra el objeto de 1 alumno
		
					        			
				        				grades.add(rs.getString(4));
				        				mes.add(rs.getString(3));
				        			}
					        	}
				        	}

				        //gen.writeEnd();
						//gen.writeEnd();
				        gen.writeEnd();
						gen.writeEnd();
						gen.writeEnd();
						gen.writeEnd();
						gen.writeEnd();
						gen.writeEnd();
						/*gen.writeEnd();*/
						//gen.writeEnd();
						
						
						
		            	}
	        }
	        
	        if(!conn.isClosed()) {
	    			conn.close();
	        }
	        
	        return swriter.toString();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
    }

    public static String setRegistro(String infoRegistration) throws SQLException, DroolsParserException, IOException {
    		Connection conn = BaseDatos.conectarBD();
    		
    		System.out.println("info " +  infoRegistration);
    		JSONObject info = new JSONObject(infoRegistration);
    		JSONObject calificaciones = info.getJSONObject("grades");
    		StringWriter swriter = new StringWriter();
    		
    		int idStudent = info.getInt("idStudent");
    		int idRegistro = info.getInt("idRegistro");
    		int idAssistant = info.getInt("idAssistant");
    		
    		String updateQuery = "UPDATE Registro SET Asistente_Usuario_idUsuario=?, tiempo=?, "
    				+ "`1`="+calificaciones.getString("0") + ", `2`="+calificaciones.getString("1") + ", `3`="+calificaciones.getString("2")+", "
    				+ "`4`="+calificaciones.getString("3") + ", `5`="+calificaciones.getString("4") + ", `6`="+calificaciones.getString("5")+", "
    				+ "`7`="+calificaciones.getString("6") + ", `8`="+calificaciones.getString("7") + ", `9`="+calificaciones.getString("8")+", "
    				+ "`10`="+calificaciones.getString("9") +" WHERE idRegistro=? AND Alumno_idAlumno=?";
    		
    		prepareStat = conn.prepareStatement(updateQuery);
    		
    		prepareStat.setInt(1, idAssistant);
    		prepareStat.setInt(2, info.getInt("time"));
    		prepareStat.setInt(3,idRegistro);
    		prepareStat.setInt(4,idStudent);
	
    		int count = prepareStat.executeUpdate();
    		prepareStat.close();
    		
    		if(count>0) {
    			System.out.println("Succesful");
    			try (JsonGenerator gen = Json.createGenerator(swriter)) {
    	            gen.writeStartObject();
	    	            gen.writeStartObject("response");
		    	            gen.write("success", 1);
		    	            gen.write("idStudent", info.getInt("idStudent"));
		    	            gen.write("idRegistro", info.getInt("idRegistro"));
		    	            gen.write("idAssistant", info.getInt("idAssistant"));
		    	            gen.write("tipo", info.getString("tipo"));
	    	            gen.writeEnd();
    	            gen.writeEnd();
    	        }
    	    
    		}else {
    			System.out.println("Error");
    			try (JsonGenerator gen = Json.createGenerator(swriter)) {
    	            gen.writeStartObject();
	    	            gen.writeStartObject("response");
		    	            gen.write("success", 0);
		    	            gen.write("idStudent", info.getInt("idStudent"));
		    	            gen.write("idRegistro", info.getInt("idRegistro"));
		    	            gen.write("idAssistant", info.getInt("idAssistant"));
		    	            gen.write("tipo", info.getString("tipo"));
	    	            gen.writeEnd();
    	            gen.writeEnd();
    	        }
    		}
    		
    		//System.out.println("obj " + swriter.toString());
    		String finalResult;
    		
    		if(info.getString("tipo").compareTo("T") == 0) {
    			System.out.println("Es tarea");
    			finalResult=swriter.toString();
    		}else {
    			System.out.println("Es centro");
    			JSONObject finalS = new JSONObject(swriter.toString());
    			JSONArray testResults = info.getJSONArray("testResultados");
    			
    			String insertQuery = "INSERT INTO Registro_has_Nivel_has_PreguntaTest_has_Respuesta (Registro_idRegistro, Registro_Asistente_Usuario_idUsuario, Registro_Alumno_idAlumno, "
    					+ "Nivel_has_P_has_R_Nivel_idNivel, Nivel_has_P_has_R_idpreguntaTest, Nivel_has_P_has_R_IdRespuesta, Nivel_has_P_has_R_puntoInicio)" +
    			        "VALUES (?, ?, ?, ?, ?, ?, ?)";
    			
    			System.out.println("test size "+testResults.length());
    			for(int i=0; i<testResults.length(); i++) {
    				prepareStat = conn.prepareStatement(insertQuery);
    	    		
	    	    		prepareStat.setInt(1, info.getInt("idRegistro"));
	    	    		prepareStat.setInt(2, info.getInt("idAssistant"));
	    	    		prepareStat.setInt(3,info.getInt("idStudent"));
	    	    		prepareStat.setInt(4,testResults.getJSONObject(i).getInt("idNivel"));
	    	    		prepareStat.setInt(5, testResults.getJSONObject(i).getInt("id"));
	    	    		prepareStat.setInt(6, testResults.getJSONObject(i).getInt("selected"));
	    	    		prepareStat.setInt(7, 1);
	    		
	    	    		int count2 = prepareStat.executeUpdate();
	    	    		prepareStat.close();
    				
    			}
    			Auxiliar aux= new Auxiliar();
    			
    			String fileRules1="../rules/registro_progDiaria.drl";
    			String fileRules2="../rules/accion.drl";
    			//int time, int id, String nombre, String nivel, int grado,  int cal1
    			String arrayJson=aux.crearJsonRegistro(Integer.toString(idStudent),info.getString("nameStudent"), info.getString("level"), "1",info.getInt("time"),calificaciones.getInt("0"),calificaciones.getInt("1"),calificaciones.getInt("2"),calificaciones.getInt("3"),calificaciones.getInt("4"),calificaciones.getInt("5"), calificaciones.getInt("6"), calificaciones.getInt("7"), calificaciones.getInt("8"), calificaciones.getInt("9"));
    			//String arrayJson=aux.crearJsonRegistro("3","Itzel Aguilar", "2A", "1",15,100,100,100,100,100,100, 80, 70, 110, 120);
    			JSONObject obj = new JSONObject(arrayJson);
    			WorkingMemory wk1=aux.conexionDrools(fileRules1);
    			WorkingMemory wk2=aux.conexionDrools(fileRules2);
    			JSONObject results = obj.getJSONObject("resultsRegistro");
    			aux.executeProg(wk1,wk2,arrayJson);
    			
    			finalResult = finalS.toString();
    			
    			
    		}
    		
    		if(!conn.isClosed()) {
    			conn.close();
        }
    		
    		return finalResult;	
    }

    public static String getProgramacionDiaria(String id) {
    		Connection conn = BaseDatos.conectarBD();
    		StringWriter swriter = new StringWriter();
    		
	    try {
	        String getQueryStatement = "Select re.idRegistro, DAY(re.fecha) as dia, MONTH(re.fecha) as mes, YEAR(re.fecha) as año, se.`set`, re.tipo, re.tiempo, niv.nombre \r\n" + 
	        		"	        		from Registro as re JOIN Usuario as us JOIN `Set` as se JOIN Nivel as niv\r\n" + 
	        		"	        		ON re.Asistente_Usuario_idUsuario=us.idUsuario AND se.idSet=re.Set_idSet AND se.Nivel_idNivel=niv.idNivel\r\n" + 
	        		"	        		WHERE Alumno_idAlumno=1 ORDER BY fecha ASC;";
	
	        prepareStat = conn.prepareStatement(getQueryStatement);
	
	        ResultSet rs = prepareStat.executeQuery();
	        
	        List<String> nivel=new ArrayList<>();
	        
	        JSONObject programacionDiaria = new JSONObject();
	        int bandera=0, existe=0, bandera3=0;
	        
	        if(!rs.isBeforeFirst()) {
	        		programacionDiaria.put("success", 0);
	        }else {
	        		programacionDiaria.put("success", 1);
	        		 while(rs.next()) {
	        			 if(nivel.isEmpty()) {
	        				 
	        				 JSONObject level = new JSONObject();
	        			     JSONArray secuencias = new JSONArray();
	        			     JSONArray niveles = new JSONArray();
	        			     
	        				 nivel.add(rs.getString(8));
	        				 
	        				 
	        				 level.put("nivel", rs.getString(8));
	        				 
	        				 JSONArray secuencia = new JSONArray();
	        				 secuencia.put(rs.getInt(5));
	        				 
	        				 secuencias.put(secuencia);
	        				 
	        				 level.put("secuencias", secuencias);
	        				 
	        				 niveles.put(level);
	        				 programacionDiaria.put("niveles", niveles);
	        				 
	        			 }else { //No esta vacio nivel
	        				 for(int i=0; i<nivel.size(); i++) {
	        					 //System.out.println("niv "+ nivel.get(i) + " niv 2 " + rs.getString(8) + " id "+rs.getString(1));
	        					 
	        					 if(nivel.get(i).equals(rs.getString(8))) {  //si el nivel ya existe en el array de nivel
	        						 
	        						 JSONArray nivs = programacionDiaria.getJSONArray("niveles");
	        						
	        						 for(int j=0; j<nivs.length(); j++) {
	        							 
	        							 if(nivs.getJSONObject(j).getString("nivel").equals(rs.getString(8))) {
	        								JSONObject level = nivs.getJSONObject(j);		        								
		        							JSONArray secuencias = level.getJSONArray("secuencias");
		        							
		        							int lastSeq = secuencias.length();
		        							
		        							System.out.println("ultima secuencia "+ lastSeq);
		        							
		        							JSONArray secuencia = secuencias.getJSONArray(lastSeq-1);
		        							
		        							for(int l=0; l<secuencia.length(); l++) {
		        								System.out.println("s "+secuencia.getInt(l));
		        								if(secuencia.getInt(l) == rs.getInt(5)) {
		        									existe=1;
		        								}
		        							}
		        							
		        							if(existe==1) {
		        								JSONArray newSecuencia = new JSONArray();
	        									newSecuencia.put(rs.getInt(5));
	        										      
	        									secuencias.put(newSecuencia);
	        									existe=0;
		        							}else {
		        								//No existe
		        								secuencia.put(rs.getInt(5));
		        							}
		        									        							
	        							 }
	        						 }
	        						
	        						 bandera=0;
	        					 }else { //Si el nivel no existe en el array de nivel
	        						 bandera=1;
	        					 }
	        					 
	        				 }
	        				 
	        				 if(bandera == 1) {
	        					 nivel.add(rs.getString(8)); //Se agrega nuevo nivel al array
        						 System.out.println("no es nivel");
        						 
        						 
        						 JSONObject level = new JSONObject();
        						 JSONArray secuencias = new JSONArray();
        						 
         						 
        						 level.put("nivel", rs.getString(8));
        						 
        						 JSONArray secuencia = new JSONArray();
    	        				 	 secuencia.put(rs.getInt(5));
    	        				 
	    	        				 secuencias.put(secuencia);
	    	        				 
	    	        				 level.put("secuencias", secuencias);
	    	        				 
	    	        				 JSONArray niveles = programacionDiaria.getJSONArray("niveles");
	    	        				 
	    	        				 niveles.put(level);
	        				 }
	        			 }
	        		 }
	        }
	        
	        System.out.println(programacionDiaria.toString());
	        //System.out.println(level.toString());
	        
	        if(!conn.isClosed()) {
	    			conn.close();
	        }
	        
	        return programacionDiaria.toString();
	       
	        
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	    
	    
    	
    	
    }
    
    public static String getCenterHw(String idAlumno, String tipo) throws SQLException {
    		Connection connc = BaseDatos.conectarBD();
    		String getQueryStatement = "SELECT re.idRegistro, re.fecha, re.tipo, se.`set`, nv.nombre  FROM Registro as re JOIN `Set` as se JOIN Nivel as nv \r\n" + 
    				"ON se.idSet=re.Set_idSet AND nv.idNivel=se.Nivel_idNivel\r\n" + 
    				"WHERE re.Alumno_idAlumno=" +idAlumno +" AND re.fecha<=CURDATE() AND re.`1`=0 AND re.tipo='" + tipo +"';";
	
    		//System.out.println(getQueryStatement);
    		
        prepareStat = connc.prepareStatement(getQueryStatement);

        ResultSet rs = prepareStat.executeQuery();
        
        JSONObject setstograde = new JSONObject();
        
        if(!rs.isBeforeFirst()) {
        		setstograde.put("success", 0);
	    }else {
	    		setstograde.put("success", 1);
	    		JSONArray sets = new JSONArray();
	    		while(rs.next()) {
	    			//System.out.println("fecha "+rs.getString(5));
	    			JSONObject set = new JSONObject();
	    			
	    			set.put("idRegistro", rs.getInt(1));
	    			set.put("fecha", rs.getString(2));
	    			set.put("tipo", rs.getString(3));
	    			set.put("set", rs.getString(4));
	    			set.put("nombre", rs.getString(5));
	    			
	    			sets.put(set);
	    		}
	    		setstograde.put("sets", sets);
	    		//System.out.println("sets "+ setstograde.toString());
	    	}
        
        
        if(!connc.isClosed()) {
    			connc.close();
        }
        
        return setstograde.toString();
    }
}

