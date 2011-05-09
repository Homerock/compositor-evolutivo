package canciones;

import java.util.ArrayList;
/**
 * Clase que representa una estrofa
 * 
 * @author SEBASTIAN PAZOS - YAMIL GOMEZ
 *
 */

public class Estrofa {
	

	/*################################################################################################################
	 ###################						ATRIBUTOS		 				###################################### 
	 ################################################################################################################# */
	private int numeroEstrofa ;//numero correlativo que representa a la estrofa
	private String estilo;//estilo de la estrofa 
	private int cantidadCompases;
	private ArrayList<Compas> compases;
	

	/*################################################################################################################
	 ###################						CONSTRUCTOR						###################################### 
	 ################################################################################################################# */

	public Estrofa(){
	}
	
	

	/*################################################################################################################
	 ###################						METODOS PUBLICOS				###################################### 
	 ################################################################################################################# */

	public String getEstilo() {
		return estilo;
	}
	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}
	public int getCantidadCompases() {
		return cantidadCompases;
	}
	public void setCantidadCompases(int cantidadCompases) {
		this.cantidadCompases = cantidadCompases;
	}
	public ArrayList<Compas> getCompases() {
		return compases;
	}
	public void setCompases(ArrayList<Compas> compases) {
		this.compases = compases;
	}

	public int getNumeroEstrofa() {
		return numeroEstrofa;
	}

	public void setNumeroEstrofa(int numeroEstrofa) {
		this.numeroEstrofa = numeroEstrofa;
	}
	
	
	
	
	
	

}
