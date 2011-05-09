package canciones;

import java.util.ArrayList;
/**
 * Clase que representa una cancion
 * 
 * @author SEBASTIAN PAZOS - YAMIL GOMEZ
 *
 */
public class Cancion {
	

	/*################################################################################################################
	 ###################							ATRIBUTOS					###################################### 
	 ################################################################################################################# */

	private String nombre;
	private String tempo;
	private int duracion;// cantidad de compases de toda la cancion
	private String tonica ;
	private String estiloPrincipal;
	private ArrayList<Estrofa> estrofas;// estrofas de la cancion


	/*################################################################################################################
	 ###################						CONSTRUCTORES				    ###################################### 
	 ################################################################################################################# */

	public Cancion(){
		this.setNombre(null);
		this.setTempo(null);
		this.setDuracion(0);
		this.setTonica(null);
		this.setEstiloPrincipal(null);
		this.setEstrofas(new ArrayList<Estrofa>());
	}
	
	
	public Cancion(String nombre,String tempo,int duracion,String tonica,String estiloPrincipal){
		this.setNombre(nombre);
		this.setTempo(tempo);
		this.setDuracion(duracion);
		this.setTonica(tonica);
		this.setEstiloPrincipal(estiloPrincipal);
		this.setEstrofas(new ArrayList<Estrofa>());
	}
	
	

	/*################################################################################################################
	 ###################						METODOS PUBLICOS				###################################### 
	 ################################################################################################################# */

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getTonica() {
		return tonica;
	}

	public void setTonica(String tonica) {
		this.tonica = tonica;
	}

	public String getEstiloPrincipal() {
		return estiloPrincipal;
	}

	public void setEstiloPrincipal(String estiloPrincipal) {
		this.estiloPrincipal = estiloPrincipal;
	}

	public ArrayList<Estrofa> getEstrofas() {
		return estrofas;
	}

	public void setEstrofas(ArrayList<Estrofa> estrofas) {
		this.estrofas = estrofas;
	}
	
	

}
