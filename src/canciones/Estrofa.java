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
	private ArrayList<Compas> listaDeCompases;
	private boolean esEstrofaGemela;
	private int nroEstrofaGemela ;//dice si es copia de otra estrofa, -1 si no es .
	

	/*################################################################################################################
	 ###################						CONSTRUCTOR						###################################### 
	 ################################################################################################################# */

	public Estrofa(){
	}
	
	/**
	 * 
	 * @param numeroEstrofa
	 * @param estilo
	 * @param cantidadCompases
	 */
	public Estrofa(int numeroEstrofa, String estilo, int cantidadCompases) {
		
		this.numeroEstrofa = numeroEstrofa;
		this.estilo = estilo;
		this.cantidadCompases = cantidadCompases;
		this.esEstrofaGemela = false;
		this.listaDeCompases = new ArrayList<Compas>();
	}

	/*################################################################################################################
	 ###################						METODOS PUBLICOS				###################################### 
	 ################################################################################################################# */

	
	@Override
	public String toString() {
		return "\n\nEstrofa [numeroEstrofa = " + numeroEstrofa + ", estilo = " + estilo 
				+ ", cantidadCompases = " + cantidadCompases+ "\n" 
				+ "listaDeCompases = " + listaDeCompases + "\n" 
				+ "esEstrofaGemela = " + esEstrofaGemela + ", nroEstrofaGemela = " + nroEstrofaGemela + "]";
	}

	public void agregarCompas(Compas miCompas) {
		this.getListaDeCompases().add(miCompas);
	}


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
	public ArrayList<Compas> getListaDeCompases() {
		return listaDeCompases;
	}
	public void setListaDeCompases(ArrayList<Compas> compases) {
		this.listaDeCompases = compases;
	}

	public int getNumeroEstrofa() {
		return numeroEstrofa;
	}

	public void setNumeroEstrofa(int numeroEstrofa) {
		this.numeroEstrofa = numeroEstrofa;
	}

	public boolean isEsEstrofaGemela() {
		return esEstrofaGemela;
	}

	public void setEsEstrofaGemela(boolean esEstrofaGemela) {
		
		this.esEstrofaGemela = esEstrofaGemela;
	}

	public int getNroEstrofaGemela() {
		return nroEstrofaGemela;
	}

	public void setNroEstrofaGemela(int nroEstrofaGemela) {
		this.nroEstrofaGemela = nroEstrofaGemela;
	}


	
	
	
	
	

}
