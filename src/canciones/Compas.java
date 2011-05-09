package canciones;

import java.util.ArrayList;

/**
 * Clase que representa un compas
 * 
 * @author SEBASTIAN PAZOS - YAMIL GOMEZ
 *
 */

public class Compas {

	/*################################################################################################################
	 ###################						ATRIBUTOS						###################################### 
	 ################################################################################################################# */
	private int cantidadAcordes;
	private ArrayList<Acorde> acordes;
	

	/*################################################################################################################
	 ###################						CONSTRUCTOR						###################################### 
	 ################################################################################################################# */

	public Compas(){
		this.setAcordes(new ArrayList<Acorde>());
	}
	
	

	/*################################################################################################################
	 ###################						METODOS PUBLICOS				###################################### 
	 ################################################################################################################# */

	
	public int getCantidadAcordes() {
		return cantidadAcordes;
	}
	public void setCantidadAcordes(int cantidadAcordes) {
		this.cantidadAcordes = cantidadAcordes;
	}
	public ArrayList<Acorde> getAcordes() {
		return acordes;
	}
	public void setAcordes(ArrayList<Acorde> acordes) {
		this.acordes = acordes;
	}
	

	
}
