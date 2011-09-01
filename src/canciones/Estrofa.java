package canciones;

import java.util.ArrayList;

/**
 * Clase que representa una estrofa
 * 
 * @author SEBASTIAN PAZOS - YAMIL GOMEZ
 *
 */

public class Estrofa implements Cloneable{
	

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

	public Estrofa(int numeroEstrofa, String estilo) {
		
		this.numeroEstrofa = numeroEstrofa;
		this.estilo = estilo;
		this.cantidadCompases = 0;
		this.esEstrofaGemela = false;
		this.listaDeCompases = new ArrayList<Compas>();
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

	 public Object clone(){
	        Estrofa obj=null;
	        try{
	            obj=(Estrofa)super.clone();
	        }catch(CloneNotSupportedException ex){
	            System.out.println(" no se puede duplicar");
	        }
	        //((Estrofa)obj).setListaDeCompases((ArrayList)listaDeCompases.clone());
	         
	        ArrayList<Compas> nuevaLista = new ArrayList<Compas>(obj.getListaDeCompases().size());
	        for (Compas nuevoCompas : obj.getListaDeCompases()) {
	        	nuevaLista.add((Compas) nuevoCompas.clone());
	        }
	      
	        obj.listaDeCompases = nuevaLista;
	        
	        
	        return obj;
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

	public Compas getUltimoCompas() {
		int ultimo = this.getListaDeCompases().size() -1;
		return this.getListaDeCompases().get(ultimo);
	}
	
	/**
	 * retona el numero de compas de la lista de compases
	 * 
	 * @param num
	 * @return
	 */
	public Compas getCompasPorNumero (int num) {
		return this.getListaDeCompases().get(num-1);
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
