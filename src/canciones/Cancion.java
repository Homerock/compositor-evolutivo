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
	private Acorde tonica ;
	private String estiloPrincipal;
	private ArrayList<Estrofa> estrofas;// estrofas de la cancion


	/*################################################################################################################
	 ###################						CONSTRUCTORES				    ###################################### 
	 ################################################################################################################# */

	public Cancion(){
		this.setNombre(null);
		this.setTempo(null);
		this.setDuracion(0);
		this.setTonica(new Acorde());
		this.setEstiloPrincipal(null);
		this.setEstrofas(new ArrayList<Estrofa>());
	}
	
	/**
	 * Crea un nuevo objeto cancion.
	 * 
	 * @param nombre
	 * @param tempo
	 * @param duracion
	 * @param tonica
	 * @param estiloPrincipal
	 */
	public Cancion(String nombre,String tempo,int duracion,Acorde tonica,String estiloPrincipal){
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

	public void agregarEstrofa(Estrofa miEstrofa) {
		
		this.getEstrofas().add(miEstrofa);
		
	}
	
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

	public Acorde getTonica() {
		return tonica;
	}

	public void setTonica(Acorde tonica) {
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
	/**
	 * verifica en la lista de estilos de la cancion existe alguna estrofa con un estilo determinado. 
	 * @param estilo que queremos aviriguar si existe
	 * @return true si existe el estilo en alguna de las estrofas de la cancion , false si no.
	 */
	public boolean existeEstrofaEstilo(String estilo){
		
		boolean existe=false;
		ArrayList<Estrofa> estrofas = this.getEstrofas();
	
		for (Estrofa e : estrofas){
			if(e.getEstilo().equals(estilo)){
				existe =true;
				break;
			}
		}
		return existe;
	}
	
	
	public Estrofa buscarEstrofaEstilo(String estilo){
		
		ArrayList<Estrofa> estrofas = this.getEstrofas();
		Estrofa miEstrofa = null;
		
		for (Estrofa e : estrofas){
			if(e.getEstilo().equals(estilo)){
				miEstrofa = e;
				break;
			}
		}
		return miEstrofa;
	}

	@Override
	public String toString() {
		return "Cancion [nombre = " + nombre + ", tempo = " + tempo + ", duracion = " + duracion + ", tonica = " 
				+ tonica + ", estiloPrincipal = " + estiloPrincipal + "\n"
				+ "estrofas = " + estrofas + "]\n";
	}
	

}
