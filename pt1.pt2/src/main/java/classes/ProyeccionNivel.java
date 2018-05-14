package classes;

import java.util.List;

public class ProyeccionNivel {
	
	private Alumno alumno;
	private List<Nivel> niveles;
	
	
	
	//private int idProyeccionN;
	private float fecuenciaEstudio;
	private String frecuenciaInciso;
	private String nivel;
	private int hojasTotales;
	private int hojasDiarias;
	private int hojasMes;
	private int cantidadMeses;
	
	public ProyeccionNivel(Alumno alumno, List<Nivel> niveles) {
		super();
		this.alumno = alumno;
		this.niveles = niveles;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public List<Nivel> getNiveles() {
		return niveles;
	}

	public void setNiveles(List<Nivel> niveles) {
		this.niveles = niveles;
	}

	/*public int getIdProyeccionN() {
		return idProyeccionN;
	}

	public void setIdProyeccionN(int idProyeccionN) {
		this.idProyeccionN = idProyeccionN;
	}*/

	public float getFecuenciaEstudio() {
		return fecuenciaEstudio;
	}

	public void setFecuenciaEstudio(float fecuenciaEstudio) {
		this.fecuenciaEstudio = fecuenciaEstudio;
	}

	public String getFrecuenciaInciso() {
		return frecuenciaInciso;
	}

	public void setFrecuenciaInciso(String frecuenciaInciso) {
		this.frecuenciaInciso = frecuenciaInciso;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public int getHojasTotales() {
		return hojasTotales;
	}

	public void setHojasTotales(int hojasTotales) {
		this.hojasTotales = hojasTotales;
	}

	public int getHojasDiarias() {
		return hojasDiarias;
	}

	public void setHojasDiarias(int hojasDiarias) {
		this.hojasDiarias = hojasDiarias;
	}

	public int getHojasMes() {
		return hojasMes;
	}

	public void setHojasMes(int hojasMes) {
		this.hojasMes = hojasMes;
	}

	public int getCantidadMeses() {
		return cantidadMeses;
	}

	public void setCantidadMeses(int cantidadMeses) {
		this.cantidadMeses = cantidadMeses;
	}
 
	// getters y setters
	
	
}
