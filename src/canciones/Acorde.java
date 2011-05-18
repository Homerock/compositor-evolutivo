package canciones;


/**
 * Clase que representa un acorde
 * 
 * @author SEBASTIAN PAZOS - YAMIL GOMEZ
 *
 */

public class Acorde implements Cloneable{


	/*################################################################################################################
	 ###################						ATRIBUTOS						###################################### 
	 ################################################################################################################# */
	private String nombre;


	/*################################################################################################################
	 ###################						CONSTRUCTOR						###################################### 
	 ################################################################################################################# */

	public Acorde(){
	}
	
	public Acorde(String nombre){
		this.setNombre(nombre);
	}
	
    public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
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

	@Override
	public String toString() {
		return "Acorde [nombre=" + nombre + "]";
	}
	
	

}
