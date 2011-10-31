package canciones;

import java.util.ArrayList;

public class CancionAprendida extends Cancion {

	/*################################################################################################################
	 ###################							ATRIBUTOS					###################################### 
	 ################################################################################################################# */

	private ArrayList<String> cancionSinRepeats ;
	private ArrayList<String> listaAcordes;
	
	
	

	/*################################################################################################################
	 ###################						CONSTRUCTORES				    ###################################### 
	 ################################################################################################################# */

	public CancionAprendida() {
		super();
		this.cancionSinRepeats = new ArrayList<String>();
		this.listaAcordes = new ArrayList<String>();
	}

	public CancionAprendida(
				String nombre, 
				String tempo, 
				int duracion,
				Acorde tonica, 
				String estiloPrincipal) {
		
		super(nombre, tempo, duracion, tonica, estiloPrincipal);
		this.cancionSinRepeats = new ArrayList<String>();
		this.listaAcordes = new ArrayList<String>();
	}

	public CancionAprendida(
			String nombre, 
			String tempo, 
			int duracion,
			Acorde tonica, 
			String estiloPrincipal,
			ArrayList<String> cancionSinRepeats,
			ArrayList<String> listaAcordes) {
	
		super(nombre, tempo, duracion, tonica, estiloPrincipal);
	
		this.setCancionSinRepeats(cancionSinRepeats);
		this.setListaAcordes(listaAcordes);
	
	}
	
	

	/*################################################################################################################
	 ###################						METODOS PUBLICOS				###################################### 
	 ################################################################################################################# */

	
	public ArrayList<String> getCancionSinRepeats() {
		return cancionSinRepeats;
	}

	public void setCancionSinRepeats(ArrayList<String> cancionSinRepeats) {
		this.cancionSinRepeats = cancionSinRepeats;
	}

	public ArrayList<String> getListaAcordes() {
		return listaAcordes;
	}

	public void setListaAcordes(ArrayList<String> listaAcordes) {
		this.listaAcordes = listaAcordes;
	}

		
}
