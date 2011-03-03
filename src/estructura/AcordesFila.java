package estructura;

import java.util.*;

/**
 * @author Admin
 *
 */
public class AcordesFila {
	
	private String nombreAcorde;
	private Map <String,ValorAcordes> mapAcordes;
	//es una "lista" donde guardamos los pares Acorde y la cantidad de veces que aparece 
	// (clave, valor) : la clave es el nombre de la Acorde y el valor es la cantidad de ocurrencias
	private int contador ; 

	/**---------------------------------------------------------------------------
	  * @param nombre
	  *---------------------------------------------------------------------------*/
	public AcordesFila(String nombre){
		this.setNombreAcorde(nombre);
		//ValorAcordes miValorAcorde= new ValorAcordes();
		this.mapAcordes=new HashMap<String, ValorAcordes> ();
		this.setContador(0);
	
	}
	
	public AcordesFila(String nombre, int cantidad){
		this.setNombreAcorde(nombre);
		//ValorAcordes miValorAcorde= new ValorAcordes();
		this.mapAcordes=new HashMap<String, ValorAcordes> ();
		this.setContador(cantidad);
	
	}
	
	/**---------------------------------------------------------------------------
	  * @param Acorde
	  *---------------------------------------------------------------------------*/
	public void agregarAcorde(String Acorde){
		
		Map<String, ValorAcordes> mapAcordes = this.getMapAcordes();
		Integer cant=0;
		ValorAcordes miValAcorde;
		
		if (mapAcordes.containsKey(Acorde)){
			cant= new Integer(mapAcordes.get(Acorde).getValor());
			cant=cant+1;
			mapAcordes.get(Acorde).setValor(cant);
		}else{
			miValAcorde = new ValorAcordes(1);
			mapAcordes.put(Acorde, miValAcorde);//se sobreescribe con el valor nuevo
		}
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * Agrego la Acorde secundaria y el valor de cantidad de ocurrencias
	  * @param Acorde
	  *---------------------------------------------------------------------------*/
	public void agregarAcorde(String Acorde, int valor){
		
		Map<String, ValorAcordes> mapAcordes = this.getMapAcordes();
		ValorAcordes miValAcorde;
		
		if (mapAcordes.containsKey(Acorde)){
			mapAcordes.get(Acorde).setValor(valor);
		}else{
			miValAcorde = new ValorAcordes(valor);
			mapAcordes.put(Acorde, miValAcorde);//se sobreescribe con el valor nuevo
		}
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void listarAcordes(){
		
		Map<String, ValorAcordes> mapAcordes = this.getMapAcordes();
		Iterator it = mapAcordes.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			System.out.println(e.getKey() + " " + e.getValue().toString());
		}
	}
	
	/**---------------------------------------------------------------------------
	  * devuelve una Acorde segun el valor pasado por parametro
	  * nos fijamos en los valores acumulados de cada Acorde
	  * y devolvemos el mayor mas cercano
	  * @param valor
	  * @return
	  *---------------------------------------------------------------------------*/
	public String buscarAcorde(int valor){
		
		Map<String, ValorAcordes> mapAcordes = this.getMapAcordes();
		Iterator it = mapAcordes.entrySet().iterator();
		ValorAcordes miValAcorde;
		String Acorde="";

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			miValAcorde = (ValorAcordes)e.getValue(); 
			if (miValAcorde.getValorAcumulado()>=valor){
				Acorde= (String)e.getKey();
				break;
			}
		}
		return Acorde;
	}
	
	/**---------------------------------------------------------------------------
	  * @return
	  *---------------------------------------------------------------------------*/
	public Map<String, ValorAcordes> getMapAcordes() {
		return mapAcordes;
	}

	/**---------------------------------------------------------------------------
	  * @param mapAcordes
	  *---------------------------------------------------------------------------*/
	public void setMapAcordes(Map<String, ValorAcordes> mapAcordes) {
		this.mapAcordes = mapAcordes;
	}
	
	/**---------------------------------------------------------------------------
	  * @param contador
	  *---------------------------------------------------------------------------*/
	public void setContador(int contador) {
		this.contador = contador;
	}
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void calcularAcumulados(){
		
		int acumulador=0;
		ValorAcordes miValAcordes;
		Map<String, ValorAcordes> mapAcordes = this.getMapAcordes();
		Iterator it = mapAcordes.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			miValAcordes=(ValorAcordes)e.getValue();
			acumulador=acumulador+miValAcordes.getValor();
			miValAcordes.setValorAcumulado(acumulador);	
		}
		this.setContador(acumulador) ;
	}
	
	/**---------------------------------------------------------------------------
	  * devuelve el acumulaodor total 
	  * @return
	  *---------------------------------------------------------------------------*/
	public int getContador() {
		return contador;
	}
	
	/**---------------------------------------------------------------------------
	  * @return
	  *---------------------------------------------------------------------------*/
	public String getNombreAcorde() {
		return nombreAcorde;
	}
	
	/**---------------------------------------------------------------------------
	  * @param nombreAcorde
	  *---------------------------------------------------------------------------*/
	public void setNombreAcorde(String nombreAcorde) {
		this.nombreAcorde = nombreAcorde;
	}
}
