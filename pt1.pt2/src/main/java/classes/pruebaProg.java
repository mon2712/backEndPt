package classes;

import java.io.IOException;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.WorkingMemory;
import org.json.JSONObject;

public class pruebaProg {
	public static void main(String[] args) throws DroolsParserException, IOException{
		Auxiliar aux= new Auxiliar();
		
		//String fileRules1="../rules/proyeccionNivel.drl";
		//String fileRules2="../rules/desempeño.drl";
		String arrayJson=aux.crearJsonRegistro();
		JSONObject obj = new JSONObject(arrayJson);
		WorkingMemory wk=aux.conexionDrools();
		JSONObject results = obj.getJSONObject("resultsRegistro");
		aux.executeProg(wk, arrayJson);
		//aux.executeFrecuencias(wk);
		//aux.executeFrecInicial(wk, arrayJson);
		//aux.exFrecuencias(wk,2);

	}
}

