package classes;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.compiler.PackageBuilder;
import org.drools.core.RuleBase;
import org.drools.core.RuleBaseFactory;
import org.drools.core.WorkingMemory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kie.internal.KnowledgeBaseFactory;

public class Auxiliar {
	
	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
    
	public float frecuenciaInicial;
	public String nivelInicial;
	public String[] respuestasUbicacion;
	public int desempeñoGral;
	//public String[] respuestasRegistro;
	//public int[] calificaciones;
	//public String[] nivelUbicacion;
	//public int[]calificacionUbicacion;
	//public String[] nivelesProyeccion;
	//public float[] frecProyeccion;
	/*public static void conexionDrools()throws DroolsParserException,
		IOException {
		Auxiliar droolsTest = new Auxiliar();
		droolsTest.executeDrools();
	}*/
		
	public WorkingMemory conexionDrools() throws DroolsParserException, IOException {

		PackageBuilder packageBuilder = new PackageBuilder();
		String ruleFile = "../rules/proyeccionNive.drl";
		InputStream resourceAsStream = getClass().getResourceAsStream(ruleFile);
		Reader reader = new InputStreamReader(resourceAsStream);
		packageBuilder.addPackageFromDrl(reader);
		org.drools.core.rule.Package rulesPackage = packageBuilder.getPackage();
		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		ruleBase.addPackage(rulesPackage);

		WorkingMemory workingMemory = ruleBase.newStatefulSession();
		
		return workingMemory;
		
	}
	
	public void exFrecuencias(WorkingMemory workingMemory,ProyeccionNivel pn, String desempeñosNivel[], String n[]) throws SQLException {
		String getQueryStatementN ="Select idNivel from Nivel where nombre='"+ pn.getAlumno().getNivelActual()+"';";
        prepareStat = conn.prepareStatement(getQueryStatementN);
        ResultSet rsN = prepareStat.executeQuery();
       // System.out.println(rsN.getString(0));
        //System.out.println("La frecuencia del nivel: " + pn.getAlumno().getNivelActual() + " es: " + pn.getFrecuenciaEstudio() + " " + pn.getFrecuenciaInciso() );
        int nivelUbicacion = 0;
        while(rsN.next()) {
        	nivelUbicacion = rsN.getInt(1);
        }
        System.out.println();
        System.out.println("______________________________________________________________");
		int dimension = 6 - nivelUbicacion+1;

		//String [] desempeñoNivel = {"malo","malo","malo","malo","malo","malo"};  //obtenidos del front
		//System.out.println("Examenes desempeños:  "+ Arrays.asList(desempeñosNivel));
		//System.out.println("Dimension: " + dimension);

		Nivel[] niveles = new Nivel[dimension];
		
		//alu.setDesempeño("bueno");
		double frecInicial = pn.getFrecuenciaEstudio();
		String inciso = pn.getFrecuenciaInciso();
		//System.out.println("Nivel inicial: "+ nivelUbicacion);
		//System.out.println("Frecuencia inicial: " + frecInicial+ inciso );
		
		//List<Double> Frec=new ArrayList<Double>();
		//Obtencion de todas las frecuencias y tipos por nivel
		for(int x =nivelUbicacion; x<=6; x++) {
			List<Double> Frec=new ArrayList<Double>();
			List<String> Incisos=new ArrayList<String>();
			String getQueryStatement = " ";
			ResultSet rs;
			getQueryStatement = "SELECT frecuenciaEstudio,tipo FROM ProyeccionNivel where Nivel_idNivel="+Integer.toString(x)+" order by frecuenciaEstudio;";
	        prepareStat = conn.prepareStatement(getQueryStatement);
	        rs = prepareStat.executeQuery();
			int i=0;
			
	        while(rs.next()) {
	        	Frec.add(rs.getDouble(1));
	        	Incisos.add(rs.getString(2));
	        	i++;
	        }
	        
	        //Estableciendo los atributos de cada nivel
	        //System.out.println(" nivel: "+ n + " Freceuncias: " + Frec);
			System.out.println(" nivel lengt : "+ n.length + "x:" + x + " nivelUbicacion: " + nivelUbicacion+ " indice: " + (x-nivelUbicacion));
			niveles[x-nivelUbicacion] = new Nivel();
			niveles[x-nivelUbicacion].setIdNivel(x);
			//System.out.println("Niveles en la segunda: " + Arrays.asList(n));
			niveles[x-nivelUbicacion].setNombreNivel(n[x-nivelUbicacion]);
			niveles[x-nivelUbicacion].setFrecuencias(Frec);
			niveles[x-nivelUbicacion].setTipos(Incisos);
			niveles[x-nivelUbicacion].setDesempeñoGeneral(pn.getAlumno().getDesempeño());
			niveles[x-nivelUbicacion].setDesempeñoNivel(desempeñosNivel[x-nivelUbicacion]);
			//System.out.println(" x: "+ niveles[x-nivelUbicacion].getIdNivel() + " nivelUbicacion: " + nivelUbicacion+ " indice: " + (x-nivelUbicacion));
			
			//System.out.println("Nombre nivel: "+ niveles[x-nivelUbicacion].getNombreNivel());
			//System.out.println(" Freceuncias: " + niveles[x-nivelUbicacion].getFrecuencias());
			//System.out.println("Desempeño del nivel: " + niveles[x-nivelUbicacion].getDesempeñoNivel() + " Desempeño general: " + pn.getAlumno().getDesempeño());
			
			//para obtener la frecuencia por nivel
			workingMemory.insert(niveles[x-nivelUbicacion]);
			workingMemory.fireAllRules();
			
			
			//System.out.println("Desempeño del nivel: "+ niveles[x-nivelUbicacion].getDesempeñoNivel()+", Desempeño general: "+ niveles[x-nivelUbicacion].getDesempeñoGeneral() + ", Desempeño total: "+ niveles[x-nivelUbicacion].getDesempeño());
			//System.out.println("Mensaje: "+niveles[x-nivelUbicacion].getNivelMessage());
			//niveles[x-nivelUbicacion].setPuntajeDesempeño(puntaje[x-nivelUbicacion]);
	        //int size= Frec.size();
	        //System.out.println("size: "+ size);
	        
		}
		double []frecProy = new double [6-nivelUbicacion+1];
		String []frecProyTipo = new String [6-nivelUbicacion+1];
		int size, frecOriginal, s=0, indiceActual=0, mov=0, totalF=0, err;
		String msn;
		
		for(int x=0;x<=(6-nivelUbicacion);x++) { //nivel
			size=niveles[x].getFrecuencias().size();
			//System.out.println("Numero de frecuenicas del nivel actual: " + size);
			//s=0;
			/*while(s<size && niveles[x].getFrecuencias().get(s)!=frecInicial) {
				//System.out.println("Freceuncia " +  (x+1) + ": " + niveles[x].getFrecuencias().get(s));
				s++;
			}*/
			if(x==0) {
				indiceActual=niveles[x].getFrecuencias().indexOf(frecInicial);
				if(indiceActual == -1) {
					//System.out.println("La frecuencia no existe");
					break;
				}
				else {
					//System.out.println("Está en el indice: " + indiceActual);
				}
				//System.out.println("Avanzaré " + (niveles[x].getDesempeño()) + " Indices");
	
			}
			else {
				
				indiceActual = indiceActual+ niveles[x].getDesempeño();
				//System.out.println("Está en el indice: " + indiceActual);
				//System.out.println("Avanzaré " + (niveles[x].getDesempeño()) + " Indices");
				totalF = niveles[x].getFrecuencias().size()-1;
				//System.out.println("El numero de frecuencias de este nivel es: " + totalF);
				if(indiceActual > totalF){
					indiceActual = totalF;
					//System.out.println("No se puede avanzar mas, nos vamos ala ultima freceuncia: " + indiceActual);
				}
				//frecProy[x] = niveles[x].getFrecuencias().get(indiceActual);
			}
			//System.out.println("Frecuencia: "+niveles[x].getFrecuencias().get(indiceActual) + " x: " + x);
			frecProy[x]= niveles[x].getFrecuencias().get(indiceActual);
			frecProyTipo[x]= niveles[x].getTipos().get(indiceActual);
		  }
		int idAl=Integer.parseInt(pn.getAlumno().getIdAlumno());
		for(int x=0; x<6-nivelUbicacion+1; x++) {
			System.out.println("Frecuencia " + (x+1) + " : " + frecProy[x] + " " + frecProyTipo[x] + " " + niveles[x].getNombreNivel());
				CallableStatement cS = conn.prepareCall("{call setProyeccionAnual(?, ?, ?, ?, ?, ?)}");
				
			 	
				cS.setInt(1, idAl);
			 	cS.setDouble(2, frecProy[x]);
			 	cS.setString(3, niveles[x].getNombreNivel());
			 	cS.setString(4, frecProyTipo[x] );
			 	cS.registerOutParameter(5, Types.INTEGER);
			 	cS.registerOutParameter(6, Types.VARCHAR);
			 	cS.execute();
			 	
		 	if(cS.getInt(5)==1 && x==0) {
		 		System.out.println("Error: " + cS.getInt(5) + " " + cS.getString(6));
		 		break;
		 	}
		 	else {
		 		//System.out.println("Frecuencia " + (x+1) + " : " + frecProy[x] + " " + frecProyTipo[x] + " " + niveles[x].getNombreNivel());
				CallableStatement cS2 = conn.prepareCall("{call setProyeccionNivelInProyeccionAnual(?, ?, ?, ?, ?, ?)}");
				cS2.setInt(1, idAl);
			 	cS2.setDouble(2,frecProy[x]);
			 	//(2, String.valueOf(frecProy[x]));
			 	cS2.setString(3, niveles[x].getNombreNivel());
			 	cS2.setString(4, frecProyTipo[x] );
			 	cS2.registerOutParameter(5, Types.INTEGER);
			 	cS2.registerOutParameter(6, Types.VARCHAR);
			 	cS2.execute();
			 	System.out.println("Error: " + cS2.getInt(5) + " " + cS2.getString(6));
		 	}
		 	
			
			//String insertProyeccionPS="call setProyeccionAnual(4,"+ frecProy[x] +"," + niveles[x].getNombreNivel() + "," + frecProyTipo[x] + ", @mensaje, @err);";
		}
	}

	
	public void executeFrecInicial(WorkingMemory workingMemory, String array) {
		
		JSONObject obj = new JSONObject(array);
		
		JSONObject results = obj.getJSONObject("resultsTest");
		
			JSONObject infoStudent = results.getJSONObject("infoStudent");
			JSONArray exams = results.getJSONArray("exams");
			JSONArray frecuenciaIncial = results.getJSONArray("startPoint");
			String n;
			
			//---------------Impresion de datos obtenidos del Json-------------
			//System.out.println("puntajeDesempeño " + desempeñoGeneral);
			System.out.println("examenes " + exams);
			System.out.println("infoStudent" +infoStudent.toString());
			System.out.println("para frecuencia inicial" + frecuenciaIncial.toString());
			System.out.println("nivel" + infoStudent.getString("level"));
			//-----------------------------------------------------------------
			
			n=infoStudent.getString("level"); //obtención del nivel
			String g=infoStudent.getString("grade"); //obtención del grado
			String idStudent=infoStudent.getString("idStudent");  //obtención del id
			int desempeñoGeneral = results.getInt("finalScore");  //obtención del desempeño general
			Alumno alumn = new Alumno();
			//dando atributos al alumno
			alumn.setPuntajeDesempeño(desempeñoGeneral);
			//workingMemory.insert(alumn);
			//workingMemory.fireAllRules();
			//System.out.println("Desempeño general: " +  alumn.getDesempeño());
			alumn.setNivelActual(n);
			alumn.setGrado(g);
			alumn.setIdAlumno(idStudent);
			String id;
			//String desempeñosNivel[] = new String [exams.length()];
			String desNivel[] = new String [exams.length()];
			String [] level = new String [exams.length()];
			//List<Double> Frec=new ArrayList<Double>();
			//System.out.println("Longitud de examenes: "+exams.length());
			
			//obtencion de desempeño por nivel
			System.out.println("_______________________________________________");
			System.out.println("Exámenes que realizó: ");
			for(int i=0; i<exams.length();i++) {
				JSONObject res = (JSONObject) exams.get(i);
				if(res.getString("level").compareTo("E")!=0 && res.getString("level").compareTo("F")!=0 ) {
					desNivel[i]=res.getString("desempeño");
					level[i] = res.getString("level");
					System.out.println("Nivel: "+ level[i]);
				}
			}
			System.out.println("_______________________________________________");
			//buscando el indice del nivel de ubicación
			int newIndice=indexOfIntArray(level,n);
			
			//buscando indie de nivel final (D)
			String fin="D";
			int newIndice2=indexOfIntArray(level,fin);
			
			
			//System.out .println("Tamaño del array de desempeños: "+ desNivel.length + " New indice: " + newIndice + " tamaño de array examenes" + exams.length());
			
			//acotando los desempeños de nivel a partir del nivel deubicación
			System.out.println("Nivel ubicacion: " + n + " Indice de incicio: "+ newIndice);
			System.out.println("Nivel final: " + fin + " Indice de fin: "+ newIndice2);
			String desempeñosNivel[]=Arrays.copyOfRange(desNivel, newIndice, newIndice2+1);
			String nivel[]=Arrays.copyOfRange(level, newIndice, newIndice2+1);
			System.out.println("Examenes desempeños:  "+ Arrays.asList(desempeñosNivel) + " Niveles: " + Arrays.asList(nivel));
			
			//Obtención de variables a tomar en cuenta ne el árbol de decisión de la freceuncia incial
			for(int i=0; i<frecuenciaIncial.length();i++) {
				JSONObject res = (JSONObject) frecuenciaIncial.get(i);
				id=res.getString("identificador");
			
				switch (id) {
					case "fluidez":
						alumn.setFluidez(res.getInt("answer"));
					break;
					case "concentracion":
						alumn.setConcentracion(res.getInt("answer"));
					break;
					case "antysuc":
						alumn.setAnteSuc(res.getInt("answer"));
					break;
					case "cuenta":
						alumn.setCuenta(res.getInt("answer"));
					break;
					case "trabajoB":
						alumn.setTrabajoB(res.getInt("answer"));
					break;
					case "procedimiento":
						alumn.setProcedimientosCortos(res.getInt("answer"));
					break;
					case "calculo":
						alumn.setCalculoMental(res.getInt("answer"));
					break;
					case "division":
						alumn.setDivisionConResta(res.getInt("answer"));
					break;
					case "simplifica":
						alumn.setSimplificacion(res.getInt("answer"));
					break;
				}
				System.out.println("Id: " + id + " Answer: " + res.getInt("answer"));
			}
		
		ProyeccionNivel pn = new ProyeccionNivel();
		pn.setAlumno(alumn);
		workingMemory.insert(pn);
		workingMemory.insert(alumn);
		workingMemory.fireAllRules();
		try {
			this.exFrecuencias(workingMemory,pn, desempeñosNivel, nivel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public String crearJson() {                                                       
		StringWriter swriter = new StringWriter();
		String alumno[], testInicial[], desempeñoGral[], puntajeNivel[];
            try (JsonGenerator gen = Json.createGenerator(swriter)) {
            	gen.writeStartObject();
                gen.writeStartObject("resultsTest");
	                gen.write("finalScore", "72");
	                gen.writeStartObject("infoStudent");
	                    //gen.writeStartObject();
		                gen.write("level", "2A");
		                gen.write("grade", "1");
		                gen.write("name", "Itzel  Aguilar ");
		                gen.write("idStudent", "3");
		                gen.write("startDate", "2017-04-03");
		            gen.writeEnd();
		            gen.writeStartArray("exams");
			            gen.writeStartObject();
			                 gen.write("desempeño", "medio");
			                 gen.write("level", "2A");
			             gen.writeEnd();
			             gen.writeStartObject();
			                 gen.write("desempeño", "medio");
			                 gen.write("level", "A");
			             gen.writeEnd();
			             gen.writeStartObject();
			                 gen.write("desempeño", "medio");
			                 gen.write("level", "B");
			             gen.writeEnd();
				             gen.writeStartObject();
			                 gen.write("desempeño", "bueno");
			                 gen.write("level", "C");
			             gen.writeEnd();
			             gen.writeStartObject();
			                 gen.write("desempeño", "medio");
			                 gen.write("level", "D");
			             gen.writeEnd();
			             /*gen.writeStartObject();
			                 gen.write("desempeño", "malo");
			                 gen.write("level", "D");
			             gen.writeEnd();*/
			             gen.writeStartObject();
			                 gen.write("desempeño", "medio");
			                 gen.write("level", "E");
			             gen.writeEnd();
		            gen.writeEnd();
		            gen.writeStartArray("startPoint");
		             gen.writeStartObject();
		                 gen.write("identificador", "fluidez");
		                 gen.write("answer", "1");
		                 gen.write("answerLbl", "Si");
		             gen.writeEnd();
		             gen.writeStartObject();
			             gen.write("identificador", "calculo");
		                 gen.write("answer", "1");
		                 gen.write("answerLbl", "Si");
		             gen.writeEnd();
		             gen.writeStartObject();
			             gen.write("identificador", "procedimeinto");
		                 gen.write("answer", "1");
		                 gen.write("answerLbl", "Cortos");
		                 gen.writeEnd();
		            /* gen.writeStartObject();
			             gen.write("identificador", "cuenta");
		                 gen.write("answer", "0");
		                 gen.write("answerLbl", "No");
		             gen.writeEnd();*/
	            gen.writeEnd();
                gen.writeEnd();
                gen.writeEnd();
            }
        return swriter.toString(); 
	}
	
	
	public static int indexOfIntArray(String[] array, String key) {
	    int returnvalue = -1;
	    for (int i = 0; i < array.length; ++i) {
	        if (key.equals(array[i])) {
	            returnvalue = i;
	            break;
	        }
	    }
	    return returnvalue;
	}

    public static String asignarAsistente(String alumno) throws SQLException {
    		String[] niveles = {"7A","6A","5A","4A","3A","2A","A","B","C","D","E","F","G","H","I","J"}; 
    		String asistente = "";
    		JSONObject obj2 = new JSONObject(alumno);
		JSONObject alumnoIn = obj2.getJSONObject("student");
		StringWriter swriter = new StringWriter();
		
		String nivel="";
		nivel=alumnoIn.getString("level");
		
		int index=-1;
		for(int i=0; i<niveles.length; i++) {
			if(niveles[i].equals(nivel)) {
				index=i;
			}else {
				System.out.println("no esta en el array");
			}
		}
		
		String nivelesToCheck="";
		if(index==-1) {
			nivelesToCheck="'J'";
		}else {
			for(int i=0; i<niveles.length; i++) {
				if(i >= index) {
					if(nivelesToCheck == "") {
						nivelesToCheck=nivelesToCheck + "'" + niveles[i] +"'";
					}else if(niveles.length > 1) {
						nivelesToCheck=nivelesToCheck +",'"+niveles[i] + "'";
					}
				}
			}
		}
			
			String getQueryStatement = "SELECT pas.Asistente_Usuario_idUsuario, users.nombre, users.apellido, users.telefono, COUNT(pas.Alumno_idAlumno) FROM asistencia as pas JOIN usuario as users JOIN Asistente as asiste\r\n" + 
					"ON pas.Asistente_Usuario_idUsuario=users.idUsuario AND asiste.Usuario_idUsuario=users.idUsuario \r\n" + 
					"WHERE fecha=CURDATE() AND Asistente_Usuario_idUsuario IN (\r\n" + 
					"	SELECT asist.Usuario_idUsuario FROM asistencia as lista JOIN asistente as asist ON lista.Asistente_Usuario_idUsuario=asist.Usuario_idUsuario \r\n" + 
					"	WHERE fecha=CURDATE() AND horaSalida='00:00:00' AND asist.nivel IN ("+nivelesToCheck+") )\r\n" + 
					"GROUP BY pas.Asistente_Usuario_idUsuario ORDER BY COUNT(pas.Alumno_idAlumno);";
	
	        prepareStat = conn.prepareStatement(getQueryStatement);
	
	        ResultSet rs = prepareStat.executeQuery();
	
	        if (!rs.isBeforeFirst()){
	        		
	        		String queryAsistentes = "SELECT asist.Usuario_idUsuario, users.nombre, users.apellido, users.telefono, asist.nivel FROM asistencia as lista JOIN asistente as asist JOIN usuario as users\r\n" + 
	        				"ON lista.Asistente_Usuario_idUsuario=asist.Usuario_idUsuario  AND asist.usuario_idUsuario=users.idUsuario\r\n" + 
	        				"WHERE fecha=CURDATE() AND horaSalida='00:00:00' AND asist.nivel IN ("+nivelesToCheck+") GROUP BY asist.Usuario_idUsuario;";
	
	        		prepareStat = conn.prepareStatement(queryAsistentes);
	
	            ResultSet rsAsistentes = prepareStat.executeQuery();
	            
	            if (!rsAsistentes.isBeforeFirst()){
	            		
	            		String queryNoCalificadas = "SELECT pas.Asistente_Usuario_idUsuario, users.nombre, users.apellido, users.telefono, asist.nivel, COUNT(pas.Alumno_idAlumno) FROM asistencia as pas JOIN usuario as users JOIN Asistente as asist\r\n" + 
	            				"ON pas.Asistente_Usuario_idUsuario=users.idUsuario AND asist.Usuario_idUsuario=users.idUsuario\r\n" + 
	            				"WHERE  fecha=CURDATE() AND horaSalida='00:00:00' AND Asistente_Usuario_idUsuario IN (\r\n" + 
	            				"	SELECT asist.Usuario_idUsuario FROM asistencia as lista JOIN asistente as asist ON lista.Asistente_Usuario_idUsuario=asist.Usuario_idUsuario \r\n" + 
	            				"	WHERE fecha=CURDATE()  )\r\n" + 
	            				"GROUP BY pas.Asistente_Usuario_idUsuario ORDER BY asist.nivel DESC;";
	
	            		prepareStat = conn.prepareStatement(queryNoCalificadas);
	
	                ResultSet rsNoCualificados = prepareStat.executeQuery();
	                
	                if (!rsNoCualificados.isBeforeFirst()){
	                		
	              		try (JsonGenerator gen = Json.createGenerator(swriter)) {
                				gen.writeStartObject();
                				gen.writeStartObject("infoAssistant");
                					gen.write("error", 1);
            		                gen.write("message", "No hay asistentes en el centro.");
            		            gen.writeEnd();
                    	        gen.writeEnd();
                			}
	              		
	                }else {
	                		//esta vacio
	                		if(rsNoCualificados.first()) {
	                			asistente=rsNoCualificados.getString(2) + " " + rsNoCualificados.getString(3);
	                			
	                			try (JsonGenerator gen = Json.createGenerator(swriter)) {
	                				gen.writeStartObject();
	                				gen.writeStartObject("infoAssistant");
	                					gen.write("error", 0);
	            		                	gen.write("id", rsNoCualificados.getInt(1));
	            		                	gen.write("name", rsNoCualificados.getString(2));
	            		                	gen.write("lastName", ""+rsNoCualificados.getString(3));
		        	    	            		gen.write("level", rsNoCualificados.getString(4));
		        	    	            		gen.write("tutor", "");
		        	    	            		gen.write("cellTutor", "");
		        	    	            		gen.write("missingPayment", alumnoIn.getString("missingPayment"));
		        	    	            		gen.write("assistances", "");
		        	    	            		gen.write("nameMom", "");
		        	    	            		gen.write("lastNameMom", "");
		        	    	            		gen.write("phoneHouse","");
		        	    	            		gen.write("cellMom", "");
	            		            gen.writeEnd();
	                    	        gen.writeEnd();
	                			} catch (SQLException e) {
	                		        e.printStackTrace();
	                		        return null;
	                		    }
	                			
	                		}

	                }
	                
	                
	            		
	            }else {
	            		if(rsAsistentes.first()) {
	            			asistente=rsAsistentes.getString(2) + " " + rsAsistentes.getString(3);
	            			try (JsonGenerator gen = Json.createGenerator(swriter)) {
	            				gen.writeStartObject();
	            				gen.writeStartObject("infoAssistant");
	            					gen.write("error", 0);
            		                gen.write("id", rsAsistentes.getInt(1));
            		                gen.write("name", rsAsistentes.getString(2));
            		                gen.write("lastName", ""+rsAsistentes.getString(3));
		    	    	            		gen.write("level", rsAsistentes.getString(4));
		    	    	            		gen.write("tutor", "");
		    	    	            		gen.write("cellTutor", "");
		    	    	            		gen.write("missingPayment", alumnoIn.getString("missingPayment"));
		    	    	            		gen.write("assistances", "");
		    	    	            		gen.write("nameMom", "");
		    	    	            		gen.write("lastNameMom", "");
		    	    	            		gen.write("phoneHouse","");
		    	    	            		gen.write("cellMom", "");
            		            gen.writeEnd();
            		            gen.writeEnd();
	            			} catch (SQLException e) {
                		        e.printStackTrace();
                		        return null;
                		    }
	            		}
	            }
	        		
	        }else {
	        		if(rs.first()) {
	        			asistente=rs.getString(2) + " " + rs.getString(3);
	        			try (JsonGenerator gen = Json.createGenerator(swriter)) {
	            			
		        			gen.writeStartObject();
	            			gen.writeStartObject("infoAssistant");
	            				gen.write("error", 0);
	        		            	gen.write("id", rs.getInt(1));
	        		            	gen.write("name", rs.getString(2));
	        		            	gen.write("lastName", ""+rs.getString(3));
	    	    	            		gen.write("level", rs.getString(4));
	    	    	            		gen.write("tutor", "");
	    	    	            		gen.write("cellTutor", "");
	    	    	            		gen.write("missingPayment", alumnoIn.getString("missingPayment"));
	    	    	            		gen.write("assistances", "");
	    	    	            		gen.write("nameMom", "");
	    	    	            		gen.write("lastNameMom", "");
	    	    	            		gen.write("phoneHouse","");
	    	    	            		gen.write("cellMom", "");
	        	            gen.writeEnd();
	        	            gen.writeEnd();
		        		} catch (SQLException e) {
	        		        e.printStackTrace();
	        		        return null;
	        		    }
	        		}
	        		
	        }
	        
	        return swriter.toString();

    }
}
