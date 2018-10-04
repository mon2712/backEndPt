package classes;

import java.io.IOException;
import java.util.ArrayList;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.WorkingMemory;
import org.json.JSONObject;

public class pruebaCombinaciones{
	public static void main(String[] args) throws DroolsParserException, IOException{
		Auxiliar aux= new Auxiliar();
		//int flecha, triangulo, setenta, ochenta,noventa, cien, c1, c2, c3;
		int digitos=6, posibles=11,c1, c2;
		int num[]= {0,1,2,3,4,5,6,7,8,9,10}, combinaciones[][]=new int[digitos][((int) Math.pow(posibles,(digitos-1)))*posibles], combinaciones2[][]=new int[((int) Math.pow(posibles,(digitos-1)))*posibles][digitos];
		int x=0, columna=0,renglon=0;
		
		//System.out.println("holi0");
		for(int n=digitos-1;n>=0;n--) { //potencia de repeticion
			//System.out.println("n=" + n);
			int p=(n-digitos+1)*-1;
			double i=Math.pow(posibles,p)-1;
		//CREANDO TODAS LAS POSIBLES COMBINACIONES DE 6 DIGITOS ENTRE 0 Y 10
			for(c1=0;c1<Math.pow(posibles,n);c1++) { //las veces que se repite la secuencia
				//System.out.println(posibles+"^"+n+" = " + Math.pow(posibles,n));
				//System.out.print((c1+1)+") ");
				for(c2=0;c2<posibles;c2++) { //secuencia
					//System.out.println("holi3");
					for(int j=0;j<=i;j++) {
						//System.out.print("["+renglon+"]["+ columna+"]");
						//System.out.print(num[c2]);
						combinaciones[renglon][columna]=num[c2];
						//System.out.print(combinaciones[renglon][columna]);
						columna++;
					//System.out.println("");
					}
				}
				
			}
			//System.out.println("");
			//cmabiamos de renglon
			renglon++;
			columna=0;
		}
		
		//TRANSPONIENDO LA MATRIZ
		int auxsum=0, ren=0;
		ArrayList<Integer> rr = new ArrayList<Integer>();
		System.out.println("La matriz transpuesta es: ");
		System.out.println("ren: "+ combinaciones[0].length+ " col: " + combinaciones.length);
        for(int j = 0; j < combinaciones[0].length; j++){//renglones
            for(int i = 0; i < combinaciones.length; i++){//columnas
                //System.out.print(" " + combinaciones[i][j] + " ");
            	combinaciones2[j][i]=combinaciones[i][j];
            	auxsum=auxsum+combinaciones2[j][i];
            	
            	//System.out.print(" " + combinaciones2[j][i] + " ");
            }
            //System.out.println("sum = "+ auxsum);
            if(auxsum==10) {
            	//guardando las posiciones de la matriz de donde los digitos suman 10
            	rr.add(j);
            	//ren=ren+1;
            	
            	 //System.out.print("sum = "+ auxsum);
            	 //System.out.println(" POSICION"+rr);
            }
            auxsum=0;
            //System.out.println();
        }
        
        //System.out.println(combinaciones2.length);
        //quitando valores que sumados excedan 10
        int combinaciones3[][]= new int [rr.size()][combinaciones2[0].length];
        int ro=0;
        System.out.println("Posiciones "+ rr);
        System.out.println("COMBINACIONES DE CALIFICACIONES: ");
        //System.out.println("  F  T  70  80 90");
        for(int j = 0; j < rr.size(); j++){//renglones
        	ro=rr.get(j);
        	//System.out.println("Posicion "+ ro);
        	 //System.out.println(ro);
            for(int i = 0; i < combinaciones2[0].length; i++){//columnas
                //System.out.print(" " + combinaciones2[ro][i] + " ");
            	combinaciones3[j][i]=combinaciones2[ro][i];
            	//auxsum=auxsum+combinaciones2[j][i];
            	
            	//System.out.print(" " + combinaciones2[ro][i] + " = " + combinaciones3[j][i]);
          //  	System.out.print(" " + combinaciones3[j][i] + " ");
            	
            	
            }
            
            String arrayJson=aux.crearJsonRegistro2(combinaciones3[j][0], combinaciones3[j][1], combinaciones3[j][2], combinaciones3[j][3], combinaciones3[j][4],combinaciones3[j][5]);
     		JSONObject obj = new JSONObject(arrayJson);
     		WorkingMemory wk=aux.conexionDrools();
     		JSONObject results = obj.getJSONObject("resultsRegistro");
     		aux.executeProgramacion(wk, arrayJson);
          //IMPRESION DE RESULTADO DE EVALUACION
            
           //System.out.println();
            
            
        }
       
	}

}
