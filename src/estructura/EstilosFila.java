package estructura;

import java.util.*;

/**
 * @author Admin
 *
 */
public class EstilosFila {
	
	private String nombreEstilo;
	private Map <String,ValorEstilos> mapEstilos;
	//es una "lista" donde guardamos los pares Estilo y la cantidad de veces que aparece 
	// (clave, valor) : la clave es el nombre de la Estilo y el valor es la cantidad de ocurrencias
	private int contador ; 
	private int cantPromedio;//numero promedio de cuantas veces cambia de estilo una cancion =D



	/**---------------------------------------------------------------------------
	  * @param nombre
	  *---------------------------------------------------------------------------*/
	public EstilosFila(String nombre){
		this.setNombreEstilo(nombre);
		this.mapEstilos=new HashMap<String, ValorEstilos> ();
		this.setContador(0);
	
	}
	
	public EstilosFila(String nombre, int cantidad){
		this.setNombreEstilo(nombre);
		this.mapEstilos=new HashMap<String, ValorEstilos> ();
		this.setContador(cantidad);
	
	}
	
	public EstilosFila(String nombre, int cantidad,int cantPromedio){
		this.setNombreEstilo(nombre);
		this.mapEstilos=new HashMap<String, ValorEstilos> ();
		this.setContador(cantidad);
		this.cantPromedio=cantPromedio;
	
	}
	
	
	/**---------------------------------------------------------------------------
	  * @param Estilo
	  *---------------------------------------------------------------------------*/
	public void agregarEstilo(String Estilo){
		
		Map<String, ValorEstilos> mapEstilos = this.getMapEstilos();
		Integer cant=0;
		ValorEstilos miValEstilo;
		
		if (mapEstilos.containsKey(Estilo)){
			cant= new Integer(mapEstilos.get(Estilo).getValor());
			cant=cant+1;
			mapEstilos.get(Estilo).setValor(cant);
		}else{
			miValEstilo = new ValorEstilos(1);
			mapEstilos.put(Estilo, miValEstilo);//se sobreescribe con el valor nuevo
		}
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * Agrego la Estilo secundaria y el valor de cantidad de ocurrencias
	  * @param Estilo
	  *---------------------------------------------------------------------------*/
	public void agregarEstilo(String Estilo, int valor){
		
		Map<String, ValorEstilos> mapEstilos = this.getMapEstilos();
		ValorEstilos miValEstilo;
		
		if (mapEstilos.containsKey(Estilo)){
			mapEstilos.get(Estilo).setValor(valor);
		}else{
			miValEstilo = new ValorEstilos(valor);
			mapEstilos.put(Estilo, miValEstilo);//se sobreescribe con el valor nuevo
		}
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void listarEstilos(){
		
		Map<String, ValorEstilos> mapEstilos = this.getMapEstilos();
		Iterator it = mapEstilos.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			System.out.println(e.getKey() + " " + e.getValue().toString());
		}
	}
	
	/**---------------------------------------------------------------------------
	  * devuelve una Estilo segun el valor pasado por parametro
	  * nos fijamos en los valores acumulados de cada Estilo
	  * y devolvemos el mayor mas cercano
	  * @param valor
	  * @return
	  *---------------------------------------------------------------------------*/
	public String buscarEstilo(int valor){
		
		Map<String, ValorEstilos> mapEstilos = this.getMapEstilos();
		Iterator it = mapEstilos.entrySet().iterator();
		ValorEstilos miValEstilo;
		String Estilo="";

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			miValEstilo = (ValorEstilos)e.getValue(); 
			if (miValEstilo.getValorAcumulado()>=valor){
				Estilo= (String)e.getKey();
				break;
			}
		}
		return Estilo;
	}
	
	/**---------------------------------------------------------------------------
	  * @return
	  *---------------------------------------------------------------------------*/
	public Map<String, ValorEstilos> getMapEstilos() {
		return mapEstilos;
	}

	/**---------------------------------------------------------------------------
	  * @param mapEstilos
	  *---------------------------------------------------------------------------*/
	public void setMapEstilos(Map<String, ValorEstilos> mapEstilos) {
		this.mapEstilos = mapEstilos;
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
		ValorEstilos miValEstilos;
		Map<String, ValorEstilos> mapEstilos = this.getMapEstilos();
		Iterator it = mapEstilos.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			miValEstilos=(ValorEstilos)e.getValue();
			acumulador=acumulador+miValEstilos.getValor();
			miValEstilos.setValorAcumulado(acumulador);	
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
	public String getNombreEstilo() {
		return nombreEstilo;
	}
	
	/**---------------------------------------------------------------------------
	  * @param nombreEstilo
	  *---------------------------------------------------------------------------*/
	public void setNombreEstilo(String nombreEstilo) {
		this.nombreEstilo = nombreEstilo;
	}
	
	
	public int getCantPromedio() {
		return cantPromedio;
	}

	public void setCantPromedio(int cantPromedio) {
		this.cantPromedio = cantPromedio;
	}
}
