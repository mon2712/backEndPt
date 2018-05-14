package classes;

import java.util.List;

public class Nivel {
	public static final int DESEMPEÑO_ALTO = 0;
	public static final int DESEMPEÑO_MEDIO= 1;
	public static final int DESEMPEÑO_BAJO = 2;
	
	
	private int idNivel;
	private int desempeño;
	private String nombreNivel;
	private List<Double> Frecuencias;
	private int puntajeDesempeño;
	public int getPuntajeDesempeño() {
		return puntajeDesempeño;
	}

	public void setPuntajeDesempeño(int puntajeDesempeño) {
		this.puntajeDesempeño = puntajeDesempeño;
	}
	private String desempeñoMessage;
	
	

	public static int getDesempeñoAlto() {
		return DESEMPEÑO_ALTO;
	}

	public static int getDesempeñoMedio() {
		return DESEMPEÑO_MEDIO;
	}

	public static int getDesempeñoBajo() {
		return DESEMPEÑO_BAJO;
	}

	public Nivel(int idNivel, String nombreNivel, List<Double> frecuencias) {
		super();
		this.idNivel = idNivel;
		this.nombreNivel = nombreNivel;
		Frecuencias = frecuencias;
	}
	
	public int getDesempeño() {
		return desempeño;
	}

	public void setDesempeño(int desempeño) {
		this.desempeño = desempeño;
	}

	public List<Double> getFrecuencias() {
		return Frecuencias;
	}

	public void setFrecuencias(List<Double> frecuencias) {
		Frecuencias = frecuencias;
	}
	
	public int getIdNivel() {
		return idNivel;
	}
	public void setIdNivel(int idNivel) {
		this.idNivel = idNivel;
	}
	public String getNombreNivel() {
		return nombreNivel;
	}
	public void setNombreNivel(String nombreNivel) {
		this.nombreNivel = nombreNivel;
	}
	public void setNivelMessage(String desempeño) {
		//System.out.println("El desempeño fue: " + desempeño);
		this.desempeñoMessage="El desempeño fue: "+ desempeño;
	}
	public String getNivelMessage() {
		return desempeñoMessage;
	}
}