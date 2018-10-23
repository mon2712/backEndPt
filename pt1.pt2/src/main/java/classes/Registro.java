package classes;

import java.text.SimpleDateFormat;

import org.drools.core.WorkingMemory;

public class Registro {
	private int idRegistro;
	private int idUsuario;
	private int idAlumno;
	private int idSet;
	private String fecha;
	private String tipo;
	private int tiempo;
	private int calificaciones[];
	private String evaluacion;
	private String accion;
	private int numCien;
	private int numNoventa;
	private int numOchenta;
	private int numSetenta;
	private int numFlecha;
	private int numTriangulo;
	private Nivel nivel;
	private float frec;
	
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getAccion() {
		return accion;
	}
	public float getFrec() {
		return frec;
	}
	public void setFrec(float frec) {
		this.frec = frec;
	}
	public Nivel getNivel() {
		return nivel;
	}
	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
	public int getNumCien() {
		return numCien;
	}
	public void setNumCien(int numCien) {
		this.numCien = numCien;
	}
	public int getNumNoventa() {
		return numNoventa;
	}
	public void setNumNoventa(int numNoventa) {
		this.numNoventa = numNoventa;
	}
	public int getNumOchenta() {
		return numOchenta;
	}
	public void setNumOchenta(int numOchenta) {
		this.numOchenta = numOchenta;
	}
	public int getNumSetenta() {
		return numSetenta;
	}
	public void setNumSetenta(int numSetenta) {
		this.numSetenta = numSetenta;
	}
	public int getNumFlecha() {
		return numFlecha;
	}
	public void setNumFlecha(int numFlecha) {
		this.numFlecha = numFlecha;
	}
	public int getNumTriangulo() {
		return numTriangulo;
	}
	public void setNumTriangulo(int numTriangulo) {
		this.numTriangulo = numTriangulo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(java.util.Date fecha) {
		SimpleDateFormat formatF = new SimpleDateFormat("yyyy-MM-dd");
		this.fecha = formatF.format(fecha);	
	}
	public int getIdRegistro() {
		return idRegistro;
	}
	public void setIdRegistro(int idRegistro) {
		this.idRegistro = idRegistro;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdAlumno() {
		return idAlumno;
	}
	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
	}
	public int getIdSet() {
		return idSet;
	}
	public void setIdSet(int idSet) {
		this.idSet = idSet;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getTiempo() {
		return tiempo;
	}
	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
	/*public int getUno() {
		return uno;
	}
	public void setUno(int uno) {
		this.uno = uno;
	}
	public int getDos() {
		return dos;
	}
	public void setDos(int dos) {
		this.dos = dos;
	}
	public int getTres() {
		return tres;
	}
	public void setTres(int tres) {
		this.tres = tres;
	}
	public int getCuatro() {
		return cuatro;
	}
	public void setCuatro(int cuatro) {
		this.cuatro = cuatro;
	}
	public int getCinco() {
		return cinco;
	}
	public void setCinco(int cinco) {
		this.cinco = cinco;
	}
	public int getSeis() {
		return seis;
	}
	public void setSeis(int seis) {
		this.seis = seis;
	}
	public int getSiete() {
		return siete;
	}
	public void setSiete(int siete) {
		this.siete = siete;
	}
	public int getOcho() {
		return ocho;
	}
	public void setOcho(int ocho) {
		this.ocho = ocho;
	}
	public int getNueve() {
		return nueve;
	}
	public void setNueve(int nueve) {
		this.nueve = nueve;
	}
	public int getDiez() {
		return diez;
	}
	public void setDiez(int diez) {
		this.diez = diez;
	}*/
	public String getEvaluacion() {
		return evaluacion;
	}
	public int[] getCalificaciones() {
		return calificaciones;
	}
	public void setCalificaciones(int[] calificaciones) {
		this.calificaciones = calificaciones;
	}
	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
	}
	//public int[] getCalificaciones() {
		//return calificaciones;
	//}
	//public void setCalificaciones() {
		//this.calificaciones = new int[]{this.uno, this.dos, this.tres, this.cuatro, this.cinco, this.seis, this.siete, this.ocho, this.nueve, this.diez};
	//}
	
	
	 public void setCantidadCalificaciones() {
		 this.numCien=0;
		 this.numNoventa=0;
		 this.numOchenta=0;
		 this.numSetenta=0;
		 this.numFlecha=0;
		 this.numTriangulo=0;
		 //int[] calificaciones = new int[]{rs.uno, rs.dos, rs.tres, rs.cuatro, rs.cinco, rs.seis, rs.siete, rs.ocho, rs.nueve, rs.diez};
		 for(int x=0; x<10;x++) {
			 switch (this.calificaciones[x]){
			 case 70:
				 this.numSetenta=this.numSetenta+1;
			 break;
			 case 80:
				 this.numOchenta=this.numOchenta+1;
				 //System.out.println("numOchenta: " + r.numOchenta);
			 break;
			 case 90:
				 this.numNoventa=this.numNoventa+1;
			 break;
			 case 100:
				 this.numCien=this.numCien+1;
			 break;
			 case 110:
				 this.numTriangulo=this.numTriangulo+1;
			 break;
			 case 120:
				 this.numFlecha=this.numFlecha+1;
			 break;
			 
				 
			 }
		 }
		 //wm.insert(r);
		 //wm.fireAllRules();
		 //System.out.println(r.getEvaluacion());
	 }
	
}
