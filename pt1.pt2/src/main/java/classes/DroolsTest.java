package classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.compiler.PackageBuilder;
import org.drools.core.RuleBase;
import org.drools.core.RuleBaseFactory;
import org.drools.core.WorkingMemory;


public class DroolsTest {

		public static void main(String[] args) throws DroolsParserException,
				IOException {
			DroolsTest droolsTest = new DroolsTest();
			droolsTest.executeDrools();
		}

		public void executeDrools() throws DroolsParserException, IOException {

			PackageBuilder packageBuilder = new PackageBuilder();

			String ruleFile = "../rules/proyeccionNivel.drl";
			InputStream resourceAsStream = getClass().getResourceAsStream(ruleFile);

			Reader reader = new InputStreamReader(resourceAsStream);
			packageBuilder.addPackageFromDrl(reader);
			org.drools.core.rule.Package rulesPackage = packageBuilder.getPackage();
			RuleBase ruleBase = RuleBaseFactory.newRuleBase();
			ruleBase.addPackage(rulesPackage);

			WorkingMemory workingMemory = ruleBase.newStatefulSession();
			
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
				niveles[x-nivelUbicacion] = new Nivel(x,n,Frec);
				niveles[x-nivelUbicacion].setPuntajeDesempeño(puntaje[x-nivelUbicacion]);
				
				workingMemory.insert(niveles[x-nivelUbicacion]);
				workingMemory.fireAllRules();
				System.out.println(niveles[x-nivelUbicacion].getNivelMessage());
			}
			
			// residuo=0, rango=0, frecCompartidas=0;
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
			
			//Alumno alumno = new Alumno();
			//alumno.setNivelActual("3A");
			//alumno.setIdAlumno("1");
			//List <Nivel> niv = new ArrayList <Nivel> ();
			//niv = Arrays.asList (niveles);
			//ProyeccionNivel proyeccionNivel = new ProyeccionNivel(alumno, niv);
			
			for(int x=0; x<6; x++) {
				System.out.println("Frecuencia " + (x+1) + " : " + frecProy[x]);
			}

	}
	
}
