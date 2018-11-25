package classes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgramacionDiaria {
	private int idProgramaciónDiaria;
	private float frecuencia;
	private int puntoInicio;
	private int tipo;
	
	public int getIdProgramaciónDiaria() {
		return idProgramaciónDiaria;
	}
	public void setIdProgramaciónDiaria(int idProgramaciónDiaria) {
		this.idProgramaciónDiaria = idProgramaciónDiaria;
	}
	public float getFrecuencia() {
		return frecuencia;
	}
	public void setFrecuencia(float frecuencia) {
		this.frecuencia = frecuencia;
	}
	public int getPuntoInicio() {
		return puntoInicio;
	}
	public void setPuntoInicio(int puntoInicio) {
		this.puntoInicio = puntoInicio;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	
	/*public static String getProgramacionDiaria() {
		
	}*/
	

}
