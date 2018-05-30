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
import org.json.JSONObject;
import org.kie.internal.KnowledgeBaseFactory;

public class Auxiliar {
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
	
	static PreparedStatement prepareStat = null;
    private static Connection conn = BaseDatos.conectarBD();
		
	public WorkingMemory conexionDrools() throws DroolsParserException, IOException {

		PackageBuilder packageBuilder = new PackageBuilder();

		String ruleFile = "../rules/proyeccionNivel.drl";
		//String ruleFile = "../rules/proyNivel.drl";
		InputStream resourceAsStream = getClass().getResourceAsStream(ruleFile);

		Reader reader = new InputStreamReader(resourceAsStream);
		packageBuilder.addPackageFromDrl(reader);
		org.drools.core.rule.Package rulesPackage = packageBuilder.getPackage();
		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		ruleBase.addPackage(rulesPackage);

		WorkingMemory workingMemory = ruleBase.newStatefulSession();
		
		return workingMemory;
		
	}
	
	public void executeFrecuencias(WorkingMemory workingMemory) {
		int [] puntaje = {8,8,8,8,8,8};  //obtenidos del front
		int nivelUbicacion=1; //Obtenido del front
		double frecInicial = 3.4;
		int dimension = 6 - nivelUbicacion+1;
		System.out.println("Dimension: " + dimension);
		Nivel[] niveles = new Nivel[dimension];
		List<Double> Frec=new ArrayList<Double>();
		
		for(int x =nivelUbicacion; x<=6; x++) {
			String n="";
			switch (x){
				case 1: 
					n= "3A";
					Frec=Arrays.asList(1.0, 1.1, 1.2, 1.4, 1.5, 1.6, 1.7, 1.8, 2.0, 2.2, 2.3, 3.0, 3.3, 3.4);
				break;
				case 2: 
					n= "2A";
					Frec=Arrays.asList(1.0,1.2,1.7,2.0, 2.5, 2.6, 3.2, 3.3, 3.7, 4.3, 4.4);
				break;
				case 3: 
					n= "A";
					Frec=Arrays.asList(1.0, 1.2, 1.3, 1.5, 2.0, 2.1, 2.4, 2.8, 3.1, 3.3, 3.5, 3.8, 3.9, 4.3, 4.5);
				break;
				case 4: 
					n= "B";
					Frec=Arrays.asList(1.0, 1.2, 1.3, 1.4, 1.5, 1.7, 1.9, 2.0, 2.5, 2.8, 2.9, 3.1, 3.2, 3.5, 3.6, 3.9, 4.1, 4.2);
				break;
				case 5: 
					n= "C";
					Frec=Arrays.asList(1.0, 1.2, 1.3, 1.4, 1.5,1.6, 1.7,2.0, 2.3, 2.8, 3.0, 3.3, 3.4, 3.5, 3.6, 3.7, 3.8, 3.9, 4.0);
				break;
				case 6: 
					n= "D";
					Frec=Arrays.asList(1.0, 1.1, 1.3, 1.4, 1.7, 1.8, 2.0, 2.1, 2.5, 2.6, 2.7, 3.0, 3.1, 3.2, 3.4, 3.5, 3.6, 3.7, 3.8, 3.9, 4.0);
				break;
				}
			System.out.println(" nivel: "+ n + " Freceuncias: " + Frec);
			//System.out.println(" x: "+ x + " nivelUbicacion: " + nivelUbicacion+ " indice: " + (x-nivelUbicacion));
			niveles[x-nivelUbicacion] = new Nivel();
			niveles[x-nivelUbicacion].setIdNivel(x);
			niveles[x-nivelUbicacion].setNombreNivel(n);
			niveles[x-nivelUbicacion].setFrecuencias(Frec);
			niveles[x-nivelUbicacion].setPuntajeDesempeño(puntaje[x-nivelUbicacion]);
		
			workingMemory.insert(niveles[x-nivelUbicacion]);
			workingMemory.fireAllRules();
			System.out.println(niveles[x-nivelUbicacion].getNivelMessage());
		}
		double []frecProy = new double [6-nivelUbicacion+1];
		int size, frecOriginal, s=0, indiceActual=0, mov=0, totalF=0;
	
		for(int x=0;x<=(6-nivelUbicacion);x++) { //nivel
			size=niveles[x].getFrecuencias().size();
			s=0;
			while(s<size && niveles[x].getFrecuencias().get(s)!=frecInicial) {
				//System.out.println("Freceuncia " +  (x+1) + ": " + niveles[x].getFrecuencias().get(s));
				s++;
			}
			if(x==0) {
				indiceActual=niveles[x].getFrecuencias().indexOf(frecInicial);
				if(indiceActual == -1) {
					System.out.println("La frecuencia no existe");
					break;
				}
				else {
					System.out.println("Está en el indice: " + indiceActual);
				}
				//System.out.println("Avanzaré " + (niveles[x].getDesempeño()) + " Indices");
	
			}
			else {
				
				indiceActual = indiceActual+ niveles[x].getDesempeño();
				System.out.println("Está en el indice: " + indiceActual);
				System.out.println("Avanzaré " + (niveles[x].getDesempeño()) + " Indices");
				totalF = niveles[x].getFrecuencias().size()-1;
				System.out.println("El numero de frecuencias de este nivel es: " + totalF);
				if(indiceActual > totalF){
					indiceActual = totalF;
					System.out.println("No se puede avanzar mas, nos vamos ala ultima freceuncia: " + indiceActual);
				}
				//frecProy[x] = niveles[x].getFrecuencias().get(indiceActual);
			}
			//System.out.println("Frecuencia: "+niveles[x].getFrecuencias().get(indiceActual) + " x: " + x);
			frecProy[x]= niveles[x].getFrecuencias().get(indiceActual);
		  }
		for(int x=0; x<6; x++) {
			System.out.println("Frecuencia " + (x+1) + " : " + frecProy[x]);
		}
	}
	
	
	public void executeFrecInicial(WorkingMemory workingMemory) {
		//Nivel niv = new Nivel();
		String json=this.crearJson();
		System.out.println();
		Alumno alumn = new Alumno();
		
		alumn.setNivelActual("3A");
		alumn.setGrado("6");
		alumn.setFluidez(1);
		alumn.setConcentracion(0);
		alumn.setAnteSuc(1);
		alumn.setCuenta(0);
		
		
		/*alumn.setNivelActual("2A");
		alumn.setGrado("6");
		alumn.setFluidez(0);
		alumn.setConcentracion(0);
		alumn.setCuenta(0);*/
		
		/*alumn.setNivelActual("A");
		alumn.setGrado("7");
		alumn.setFluidezResta(0);
		alumn.setTrabajoB(0);*/
		
		/*alumn.setNivelActual("B");
		alumn.setGrado("7");
		alumn.setFluidez(0);
		alumn.setTrabajoB(0);
		alumn.setConcentracion(0);*/
		
		/*alumn.setNivelActual("C");
		alumn.setFluidezMultiplicacion(0);
		alumn.setCalculoMental(1);
		alumn.setProcedimientosCortos(0);*/
		
		/*alumn.setNivelActual("D");
		alumn.setDivisionConResta(1);
		alumn.setSimplificacion(1);*/
		
		
		ProyeccionNivel pn = new ProyeccionNivel();
		pn.setAlumno(alumn);
		workingMemory.insert(pn);
		workingMemory.fireAllRules();
		
	}
	public String crearJson() {                                                       
		StringWriter swriter = new StringWriter();
		String alumno[], testInicial[], desempeñoGral[], puntajeNivel[];
            try (JsonGenerator gen = Json.createGenerator(swriter)) {
                gen.writeStartObject();
	                gen.write("puntajeDesempeño", "70");
	                	gen.writeStartObject("infoStudnet");
	                    //gen.writeStartObject();
		                    gen.write("level", "2A");
		                    gen.write("grade", "1");
		                    gen.write("name", "Itzel Aguilar");
		                    gen.write("idStudent", "3");
		                    gen.write("startDate", "2017-04-03");
		                gen.writeEnd();
		                gen.writeStartArray("para freceucnia inicial");
		                gen.writeStartObject();
		                    gen.write("desempeño", "malo");
		                    gen.write("level", "3A");
		                gen.writeEnd();
		                gen.writeStartObject();
		                	gen.write("desempeño", "malo");
		                	gen.write("level", "2A");
		                gen.writeEnd();
		                gen.writeStartObject();
		                	gen.write("desempeño", "malo");
		                	gen.write("level", "A");
		                gen.writeEnd();
		            gen.writeEnd();
                gen.writeEnd();
            }
        return swriter.toString(); 
	}
	
    public static void asignarAsistente(String alumno) throws SQLException {
    		String[] niveles = {"3A","2A","A","B","C","D","E","F","G","H","I","J"}; 
    		
    		JSONObject obj2 = new JSONObject(alumno);
		JSONObject alumnoIn = obj2.getJSONObject("student");
		
		String nivel="";
		nivel=alumnoIn.getString("level");
		System.out.println("alumno " + alumnoIn.getString("level") + niveles.length);
		
		int index=-1;
		for(int i=0; i<niveles.length; i++) {
			if(niveles[i].equals(nivel)) {
				System.out.println("en el if " + niveles[i] + " " + nivel);
				index=i;
			}else {
				System.out.println("no esta en el array");
			}
		}
		System.out.println("index "+ index);
		
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
		
		
		System.out.println("cadena final " + nivelesToCheck);
		
		String getQueryStatement = "SELECT pas.Asistente_Usuario_idUsuario, users.nombre, users.apellido, COUNT(pas.Alumno_idAlumno) FROM asistencia as pas JOIN usuario as users\r\n" + 
				"ON pas.Asistente_Usuario_idUsuario=users.idUsuario\r\n" + 
				"WHERE fecha=CURDATE() AND Asistente_Usuario_idUsuario IN (\r\n" + 
				"	SELECT asist.Usuario_idUsuario FROM asistencia as lista JOIN asistente as asist ON lista.Asistente_Usuario_idUsuario=asist.Usuario_idUsuario \r\n" + 
				"	WHERE fecha=CURDATE() AND asist.nivel IN ("+nivelesToCheck+") )\r\n" + 
				"GROUP BY pas.Asistente_Usuario_idUsuario ORDER BY COUNT(pas.Alumno_idAlumno);";

        prepareStat = conn.prepareStatement(getQueryStatement);

        ResultSet rs = prepareStat.executeQuery();

        if (!rs.isBeforeFirst()){
        		System.out.println("no hay alumnos asignados");
        		
        		String queryAsistentes = "SELECT asist.Usuario_idUsuario, users.nombre, users.apellido FROM asistencia as lista JOIN asistente as asist JOIN usuario as users\r\n" + 
        				"ON lista.Asistente_Usuario_idUsuario=asist.Usuario_idUsuario  AND asist.usuario_idUsuario=users.idUsuario\r\n" + 
        				"WHERE fecha=CURDATE() AND asist.nivel IN ("+nivelesToCheck+") GROUP BY asist.Usuario_idUsuario;";

        		prepareStat = conn.prepareStatement(queryAsistentes);

            ResultSet rsAsistentes = prepareStat.executeQuery();
            
            if (!rsAsistentes.isBeforeFirst()){
            		System.out.println("no hay asistentes calificadas");
            }else {
            		if(rsAsistentes.first()) {
            			System.out.println("asistente nuevo " + rs.getString(2));
            		}
            }
        		
        }else {
        		System.out.println("hay resultado");
        		if(rs.first()) {
        			System.out.println("asistente " + rs.getString(2));
        		}
        		
        }
		

    }
}
