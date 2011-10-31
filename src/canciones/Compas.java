package canciones;

import java.util.ArrayList;

/**
 * Clase que representa un compas
 * 
 * @author SEBASTIAN PAZOS - YAMIL GOMEZ
 *
 */

public class Compas implements Cloneable{

	/*################################################################################################################
	 ###################						ATRIBUTOS						###################################### 
	 ################################################################################################################# */
	private int cantidadAcordes;
	private ArrayList<Acorde> acordes;
	
	private boolean modificarCompas=false;//para la parte ver/modificar

	/*################################################################################################################
	 ###################						CONSTRUCTOR						###################################### 
	 ################################################################################################################# */

	public Compas(){
		this.setAcordes(new ArrayList<Acorde>());
	}
	
	public Compas(int cantidadAcordes) {
		this.cantidadAcordes = cantidadAcordes;
		this.setAcordes(new ArrayList<Acorde>());
	}

	public Object clone(){
        Compas obj=null;
        try{
            obj=(Compas)super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        
      //  ((Compas)obj).setAcordes((ArrayList)acordes.clone());
        
        ArrayList<Acorde> nuevaLista = new ArrayList<Acorde>(obj.getAcordes().size());
        for (Acorde nuevoAcorde : obj.getAcordes()) {
        	nuevaLista.add((Acorde) nuevoAcorde.clone());
        }
        obj.acordes = nuevaLista;
        
        return obj;
    }
	
	/*################################################################################################################
	 ###################						METODOS PUBLICOS				###################################### 
	 ################################################################################################################# */

	public Acorde getUltimoAcorde(){
		int ultimo = this.getAcordes().size()-1;
		return this.getAcordes().get(ultimo);
	}
	
	
	
	/**
	 * metodo para no llamar a get  de la lista
	 * @param numero entre 0 y el larga de la lista
	 * @return
	 */
	public Acorde getAcordePorNumero(int numero){
		return this.getAcordes().get(numero-1);
	}
	
	
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

	public void agregarAcorde (Acorde acorde){
		this.acordes.add(acorde);
		
	}
	
	@Override
	public String toString() {
		return "Compas [cantidadAcordes=" + cantidadAcordes + ", acordes="
				+ acordes + "]";
	}

	public boolean isModificarCompas() {
		return modificarCompas;
	}

	public void setModificarCompas(boolean modificarCompas) {
		this.modificarCompas = modificarCompas;
	}

	public void actualizarContadores() {
		
		cantidadAcordes = acordes.size();
		
	}

	
	
}
