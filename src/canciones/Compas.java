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

	@Override
	public String toString() {
		return "Compas [cantidadAcordes=" + cantidadAcordes + ", acordes="
				+ acordes + "]";
	}
	

	
}
