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
	        
	        
	        //System.out.println(" nivel: "+ n + " Freceuncias: " + Frec);
			//System.out.println(" x: "+ x + " nivelUbicacion: " + nivelUbicacion+ " indice: " + (x-nivelUbicacion));
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
		 	cS.setString(2, String.valueOf(frecProy[x]));
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
			 	cS2.setString(2, String.valueOf(frecProy[x]));
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
		//Nivel niv = new Nivel();
		//String array=this.crearJson();
		JSONObject obj = new JSONObject(array);
		
		JSONObject results = obj.getJSONObject("resultsTest");
		
			JSONObject infoStudent = results.getJSONObject("infoStudent");
		
			int desempeñoGeneral = results.getInt("finalScore");
		
			JSONArray exams = results.getJSONArray("exams");
			JSONArray frecuenciaIncial = results.getJSONArray("startPoint");
			String n;
			System.out.println("puntajeDesempeño " + desempeñoGeneral);
			System.out.println("examenes " + exams);
			System.out.println("infoStudent" +infoStudent.toString());
			System.out.println("para frecuencia inicial" + frecuenciaIncial.toString());
			System.out.println("nivel" + infoStudent.getString("level"));
			
			n=infoStudent.getString("level");
			String g=infoStudent.getString("grade");
			String idStudent=infoStudent.getString("idStudent");
			Alumno alumn = new Alumno();
			alumn.setPuntajeDesempeño(desempeñoGeneral);
			workingMemory.insert(alumn);
			workingMemory.fireAllRules();
			//System.out.println("Desempeño general: " +  alumn.getDesempeño());
			alumn.setNivelActual(n);
			alumn.setGrado(g);
			alumn.setIdAlumno(idStudent);
			String id;
			//String desempeñosNivel[] = new String [exams.length()];
			String desNivel[] = new String [exams.length()];
			String level[] = new String [exams.length()];
			//List<Double> Frec=new ArrayList<Double>();
			//System.out.println("Longitud de examenes: "+exams.length());
			for(int i=0; i<exams.length();i++) {
				JSONObject res = (JSONObject) exams.get(i);
				desNivel[i]=res.getString("desempeño");
				level[i] = res.getString("level");
			}
			
			int newIndice=Arrays.binarySearch(level, n);
			//System.out.println("New indice: " + newIndice);
			//String desempeñosNivel[] = new String [exams.length()- newIndice];
			//String nivel[] = new String [exams.length()- newIndice];
			String desempeñosNivel[]=Arrays.copyOf(desNivel, newIndice);
			String nivel[]=Arrays.copyOfRange(level, newIndice, exams.length());
			//System.out.println("Examenes desempeños:  "+ Arrays.asList(desempeñosNivel) + " Niveles: " + Arrays.asList(nivel));
			
			
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
	                gen.write("finalScore", "76");
	                gen.writeStartObject("infoStudent");
	                    //gen.writeStartObject();
		                gen.write("level", "B");
		                gen.write("grade", "1");
		                gen.write("name", "Ximena Aguilar");
		                gen.write("idStudent", "7");
		                gen.write("startDate", "2017-03-02");
		            gen.writeEnd();
		            gen.writeStartArray("exams");
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
				             gen.writeStartObject();
			                 gen.write("desempeño", "malo");
			                 gen.write("level", "B");
			             gen.writeEnd();
			             gen.writeStartObject();
			                 gen.write("desempeño", "malo");
			                 gen.write("level", "C");
			             gen.writeEnd();
			             gen.writeStartObject();
			                 gen.write("desempeño", "malo");
			                 gen.write("level", "D");
			             gen.writeEnd();
		            gen.writeEnd();
		            gen.writeStartArray("startPoint");
		             gen.writeStartObject();
		                 gen.write("identificador", "fluidez");
		                 gen.write("answer", "0");
		                 gen.write("answerLbl", "Si");
		             gen.writeEnd();
		             gen.writeStartObject();
			             gen.write("identificador", "concentracion");
		                 gen.write("answer", "1");
		                 gen.write("answerLbl", "Si");
		             gen.writeEnd();
		             gen.writeStartObject();
			             gen.write("identificador", "trabajoB");
		                 gen.write("answer", "1");
		                 gen.write("answerLbl", "Si");
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
	
}