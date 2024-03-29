package estructura;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Esta clase representa un estilo 
 * 
 * @author Yamil Gomez ,Sebastian Pazos
 *
 */
public class EstilosFila {

	//propios del estilo
	private String nombreEstilo;	
	private int cantUnCompas;
	private int cantDosCompases;
	private int cantCuatroCompases;
	private int cantOchoCompases;
	//contadores de acordes por compas
	private int unAcordeEnCompas;
	private int dosAcordesEnCompas;
	private int tresAcordesEnCompas;
	private int cuatroAcordesEnCompas;
	
	// para optimizacion de la base de datos	
	private boolean modificado ;
	
	// para contabilizar las apariciones de los estilos secundarios
	private Map <String,ValorEstilos> mapEstilos;
	private int contador; //se setea en calcular acumulados
	
	 

	/**---------------------------------------------------------------------------
	  * Constructor de Estilos Fila 
	  * con el contador en cero
	  * @param nombre
	  *---------------------------------------------------------------------------*/
	public EstilosFila(String nombre,boolean modificado){
		this.setNombreEstilo(nombre);
		this.mapEstilos=new HashMap<String, ValorEstilos> ();
		this.setContador(0);
		this.setCantUnCompas(0);
	 	this.setCantDosCompases(0);
	 	this.setCantCuatroCompases(0);
	 	this.setCantOchoCompases(0);
	 	
	 	this.unAcordeEnCompas = 0;
		this.dosAcordesEnCompas = 0;
		this.tresAcordesEnCompas = 0;
		this.cuatroAcordesEnCompas = 0;
		this.setModificado(modificado);
	}

	/**
	 * Constructor.
	 * 
	 * @param nombre
	 * @param cantUnCompas
	 * @param cantDosCompases
	 * @param cantCuatroCompases
	 * @param cantOchoCompases
	 * @param unAcordeEnCompas
	 * @param dosAcordesEnCompas
	 * @param tresAcordesEnCompas
	 * @param cuatroAcordesEnCompas
	 * @param modificado
	 */
	public EstilosFila(
			String nombre,
			int cantUnCompas,
			int cantDosCompases,
			int cantCuatroCompases,
			int cantOchoCompases,
			int unAcordeEnCompas ,
			int dosAcordesEnCompas ,
			int tresAcordesEnCompas,
			int cuatroAcordesEnCompas,
			boolean modificado
			){
		
		this.setContador(0);
		this.mapEstilos=new HashMap<String, ValorEstilos> ();
		
		this.setNombreEstilo(nombre);
		this.setCantUnCompas(cantUnCompas);
	 	this.setCantDosCompases(cantDosCompases);
	 	this.setCantCuatroCompases(cantCuatroCompases);
	 	this.setCantOchoCompases(cantOchoCompases);
	 	
	 	this.setUnAcordeEnCompas(unAcordeEnCompas);
		this.setDosAcordesEnCompas(dosAcordesEnCompas);
		this.setTresAcordesEnCompas(tresAcordesEnCompas);
		this.setCuatroAcordesEnCompas(cuatroAcordesEnCompas);
		this.setModificado(modificado);
	}
	/**
	 * constructructor.
	 * 
	 * @param Estilo
	 * @param modificado
	 */
	public void agregarEstilo(String Estilo,boolean modificado){
		
		Map<String, ValorEstilos> mapEstilos = this.getMapEstilos();
		Integer cant=0;
		ValorEstilos miValEstilo;
		
		if (mapEstilos.containsKey(Estilo)){
			cant= new Integer(mapEstilos.get(Estilo).getValor());
			cant=cant+1;
			
			mapEstilos.get(Estilo).setValor(cant, modificado);
			
		}else{
			miValEstilo = new ValorEstilos(1,modificado);
			mapEstilos.put(Estilo, miValEstilo);//se sobreescribe con el valor nuevo
		}
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * Agrego un Estilo secundario y la cantidad de ocurrencias
	  * @param Estilo y cantidad
	  *---------------------------------------------------------------------------*/
	public void agregarEstilo(String Estilo, int cantidad,boolean modificado){
		
		Map<String, ValorEstilos> mapEstilos = this.getMapEstilos();
		ValorEstilos miValEstilo;
		
		if (mapEstilos.containsKey(Estilo)){
			mapEstilos.get(Estilo).setValor(cantidad,modificado);
		}else{
			miValEstilo = new ValorEstilos(cantidad,modificado);
			mapEstilos.put(Estilo, miValEstilo);//se sobreescribe con el valor nuevo
		}
		return;
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
		String estilo= null;

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			miValEstilo = (ValorEstilos)e.getValue(); 
			if (miValEstilo.getValorAcumulado()>=valor){
				estilo= (String)e.getKey();
				break;
			}
		}
		return estilo;
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
	  * Devuelve el contador total
	  *  
	  * @return contador
	  *---------------------------------------------------------------------------*/
	public int getContador() {
		return contador;
	}
	
	/**---------------------------------------------------------------------------
	  * Devuelve el contador total de compases
	  *  
	  * @return contador
	  *---------------------------------------------------------------------------*/
	public int getContadorCompases() {
		
		int contador =  this.getCantUnCompas()+
						this.getCantDosCompases()+
						this.getCantCuatroCompases()+
						this.getCantOchoCompases();
	
		return contador;
	}
	
	
	/**---------------------------------------------------------------------------
	  *	Devuelve el nombre de un estilo
	  * 
	  * @return nombreEstilo
	  *---------------------------------------------------------------------------*/
	public String getNombreEstilo() {
		return nombreEstilo;
	}
	
	/**---------------------------------------------------------------------------
	  * Setea el nombre de un estilo
	  * 
	  * @param nombreEstilo
	  *---------------------------------------------------------------------------*/
	public void setNombreEstilo(String nombreEstilo) {
		this.nombreEstilo = nombreEstilo;
	}
	public int getCantUnCompas() {
		return cantUnCompas;
	}
	public void setCantUnCompas(int cantUnCompas) {
		this.cantUnCompas = cantUnCompas;
	}
	public int getCantDosCompases() {
		return cantDosCompases;
	}
	public void setCantDosCompases(int cantDosCompases) {
		this.cantDosCompases = cantDosCompases;
	}
	
	public int getCantCuatroCompases() {
		return cantCuatroCompases;
	}
	public void setCantCuatroCompases(int cantCuatroCompases) {
		this.cantCuatroCompases = cantCuatroCompases;
	}
	
	public int getCantOchoCompases() {
		return cantOchoCompases;
	}
	public void setCantOchoCompases(int cantOchoCompases) {
		this.cantOchoCompases = cantOchoCompases;
	}
	
	public void incCantUnCompas(boolean modificado){
		int cant = this.getCantUnCompas();
		cant++;
		this.setCantUnCompas(cant);
		this.setModificado(modificado);
		return;
	}
	
	public void incCantUnCompas(int incremento,boolean modificado){
		int cant = this.getCantUnCompas();
		cant = cant + incremento;
		this.setCantUnCompas(cant);
		this.setModificado(modificado);
		return;
	}
	
	public void incCantDosCompases(int incremento,boolean modificado){
		int cant = this.getCantDosCompases();
		cant = cant + incremento;
		this.setCantDosCompases(cant);
		this.setModificado(modificado);
		return;
	}
	
	public void incCantCuatroCompases(int incremento,boolean modificado){
		int cant = this.getCantCuatroCompases();
		cant = cant + incremento;
		this.setCantCuatroCompases(cant);
		this.setModificado(modificado);
		return;
	}
	
	public void incCantOchoCompases(int incremento,boolean modificado){
		int cant = this.getCantOchoCompases();
		cant = cant + incremento;
		this.setCantOchoCompases(cant);
		return;
	}

	public int getUnAcordeEnCompas() {
		return unAcordeEnCompas;
	}

	public void setUnAcordeEnCompas(int unAcordeEnCompas) {
		this.unAcordeEnCompas = unAcordeEnCompas;
	}

	public void incrementarUnAcordeEnCompas(boolean modificado) {
		this.unAcordeEnCompas++;
		this.setModificado(modificado);
	}
	
	public int getDosAcordesEnCompas() {
		return dosAcordesEnCompas;
	}

	public void setDosAcordesEnCompas(int dosAcordesEnCompas) {
		this.dosAcordesEnCompas = dosAcordesEnCompas;
	}
	
	public void incrementarDosAcordeEnCompas(boolean modificado) {
		this.dosAcordesEnCompas++;
		this.setModificado(modificado);
	}

	public int getTresAcordesEnCompas() {
		return tresAcordesEnCompas;
	}

	public void setTresAcordesEnCompas(int tresAcordesEnCompas) {
		this.tresAcordesEnCompas = tresAcordesEnCompas;
	}

	public void incrementarTresAcordeEnCompas(boolean modificado) {
		this.tresAcordesEnCompas++;
		this.setModificado(modificado);
	}
	
	public int getCuatroAcordesEnCompas() {
		return cuatroAcordesEnCompas;
	}

	public void setCuatroAcordesEnCompas(int cuatroAcordesEnCompas) {
		this.cuatroAcordesEnCompas = cuatroAcordesEnCompas;
	}

	public void incrementarCuatroAcordeEnCompas(boolean modificado) {
		this.cuatroAcordesEnCompas++;
		this.setModificado(modificado);
	}
	
	public boolean isModificado() {
		return modificado;
	}
	public void setModificado(boolean modificado) {
		this.modificado = modificado;
	}
	
	public String toString(){
		
		String salida =  " compas:cant =" +
						"{ 1:"+this.getCantUnCompas() +
						" | 2:"+this.getCantDosCompases()+
						" | 4:"+this.getCantCuatroCompases()+
						" | 8:"+ this.getCantOchoCompases()+" }" +
						" acordes x compas =" +
						"{ 1:"+this.getUnAcordeEnCompas() +
						" | 2:"+this.getDosAcordesEnCompas()+
						" | 3:"+this.getTresAcordesEnCompas()+
						" | 4:"+ this.getCuatroAcordesEnCompas()+" }" ;
				
		return salida;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String toStringEstilosConModificado(){
		
		Map<String, ValorEstilos> mapEstilosFILA = this.getMapEstilos();
		Iterator it2 = mapEstilosFILA.entrySet().iterator();
		
		String salida ="";
		
		while (it2.hasNext()) {
			Map.Entry e2 = (Map.Entry)it2.next();
			
			if(((ValorEstilos) e2.getValue()).isModificado()){
				salida +=" < MODIFICADO >  ";	
			}else{
				salida +=" [NO modificado] ";
			}
			salida += e2.getKey() + " " + e2.getValue().toString()+"\n";
		
		}
		return salida;
	}
	/**
	 * 
	 * @return
	 */
	public String toStringEstilos(){
		
		Map<String, ValorEstilos> mapEstilosFILA = this.getMapEstilos();
		Iterator it2 = mapEstilosFILA.entrySet().iterator();
		
		String salida ="";
		
		while (it2.hasNext()) {
			Map.Entry e2 = (Map.Entry)it2.next();
			
			salida += e2.getKey() + " " + e2.getValue().toString()+"\n";
		
		}
		return salida;
	}
	

}
