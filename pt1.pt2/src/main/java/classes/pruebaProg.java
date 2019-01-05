package classes;

import java.io.IOException;
import java.sql.SQLException;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.WorkingMemory;
import org.json.JSONObject;

public class pruebaProg {
	public static void main(String[] args) throws DroolsParserException, IOException, SQLException{
		Auxiliar aux= new Auxiliar();
		
		//String fileRules1="../rules/proyeccionNivel.drl";
		//String fileRules2="../rules/desempeño.drl";
		String fileRules1="../rules/registro_progDiaria.drl";
		String fileRules2="../rules/accion.drl";
		//int time, int id, String nombre, String nivel, int grado,  int cal1
		String arrayJson=aux.crearJsonRegistro("3","Itzel Aguilar", "2A", "1",24,90,100,100,90,90,100, 100, 110, 110, 110);
		JSONObject obj = new JSONObject(arrayJson);
		WorkingMemory wk1=aux.conexionDrools(fileRules1);
		WorkingMemory wk2=aux.conexionDrools(fileRules2);
		JSONObject results = obj.getJSONObject("resultsRegistro");
		aux.executeProg(wk1,wk2,arrayJson);
		//aux.executeProg(wk1, wk2, arrayJson);
		//aux.executeFrecuencias(wk);
		//aux.executeFrecInicial(wk, arrayJson);
		
		//aux.exFrecuencias(wk,2);

	}
}

