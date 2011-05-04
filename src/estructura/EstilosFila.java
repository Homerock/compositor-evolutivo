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
	
	private String nombreEstilo;
	private Map <String,ValorEstilos> mapEstilos;
	private int contador; 
	
	private int cantUnCompas;
	private int cantDosCompases;
	private int cantCuatroCompases;
	private int cantOchoCompases;
	
	
	//contadores de acordes por compas
	private int unAcordeEnCompas;
	private int dosAcordesEnCompas;
	private int tresAcordesEnCompas;
	private int cuatroAcordesEnCompas;


	/**---------------------------------------------------------------------------
	  * Constructor de Estilos Fila 
	  * con el contador en cero
	  * @param nombre
	  *---------------------------------------------------------------------------*/
	public EstilosFila(String nombre){
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
	}
	/**------------------------------------------------------------------------
	 * Constructor de Estilos Fila
	 * 
	 * @param nombre
	 * @param cantidad
	 ------------------------------------------------------------------------*/
	public EstilosFila(String nombre, int cantidad){
		this.setNombreEstilo(nombre);
		this.mapEstilos=new HashMap<String, ValorEstilos> ();
		this.setContador(cantidad);
		this.setCantUnCompas(0);
	 	this.setCantDosCompases(0);
	 	this.setCantCuatroCompases(0);
	 	this.setCantOchoCompases(0);
	}
	

	/**
	 *  Agrega en estilo al map con valor 1
	 *  
	 * @param Estilo
	 */
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
	  * Agrego un Estilo secundario y la cantidad de ocurrencias
	  * @param Estilo y cantidad
	  *---------------------------------------------------------------------------*/
	public void agregarEstilo(String Estilo, int cantidad){
		
		Map<String, ValorEstilos> mapEstilos = this.getMapEstilos();
		ValorEstilos miValEstilo;
		
		if (mapEstilos.containsKey(Estilo)){
			mapEstilos.get(Estilo).setValor(cantidad);
		}else{
			miValEstilo = new ValorEstilos(cantidad);
			mapEstilos.put(Estilo, miValEstilo);//se sobreescribe con el valor nuevo
		}
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * Lista todos los estilos existentes
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
	
	public void incCantUnCompas(){
		int cant = this.getCantUnCompas();
		cant++;
		this.setCantUnCompas(cant);
		return;
	}
	
	public void incCantDosCompases(){
		int cant = this.getCantDosCompases();
		cant++;
		this.setCantDosCompases(cant);
		return;
	}
	
	public void incCantCuatroCompases(){
		int cant = this.getCantCuatroCompases();
		cant++;
		this.setCantCuatroCompases(cant);
		return;
	}

	public void incCantOchoCompases(){
		int cant = this.getCantOchoCompases();
		cant++;
		this.setCantOchoCompases(cant);
		return;
	}

	public void incCantUnCompas(int incremento){
		int cant = this.getCantUnCompas();
		cant = cant + incremento;
		this.setCantUnCompas(cant);
		return;
	}
	
	public void incCantDosCompases(int incremento){
		int cant = this.getCantDosCompases();
		cant = cant + incremento;
		this.setCantDosCompases(cant);
		return;
	}
	
	public void incCantCuatroCompases(int incremento){
		int cant = this.getCantCuatroCompases();
		cant = cant + incremento;
		this.setCantCuatroCompases(cant);
		return;
	}
	
	public void incCantOchoCompases(int incremento){
		int cant = this.getCantOchoCompases();
		cant = cant + incremento;
		this.setCantOchoCompases(cant);
		return;
	}
	
	public int cantidadCompases() {
		
		Random rnd= new Random();
		int miRandom;
		Map<Integer, Integer> mapCompases = new HashMap<Integer, Integer>();
		int acumulado = 0;
		
		if (this.getCantUnCompas() > 0) {
			acumulado += this.getCantUnCompas();
			mapCompases.put(1, acumulado);
		}
		if (this.getCantDosCompases() > 0) {
			acumulado += this.getCantDosCompases();
			mapCompases.put(2, acumulado);
		}
		if (this.getCantCuatroCompases() > 0) {
			acumulado += this.getCantCuatroCompases();
			mapCompases.put(4, acumulado);
		}
		if (this.getCantOchoCompases() > 0) {
			acumulado += this.getCantOchoCompases();
			mapCompases.put(8, acumulado);
		}
		miRandom = rnd.nextInt(acumulado);
		
		Iterator it = mapCompases.entrySet().iterator();
		int valor = 0;
		
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			if ((Integer)e.getValue() >= miRandom){
				valor = (Integer)e.getKey();
				break;
			}
		}
		return valor;
		
	}
	


	public int getUnAcordeEnCompas() {
		return unAcordeEnCompas;
	}

	public void setUnAcordeEnCompas(int unAcordeEnCompas) {
		this.unAcordeEnCompas = unAcordeEnCompas;
	}

	public void incrementarUnAcordeEnCompas() {
		this.unAcordeEnCompas++;
	}
	
	public int getDosAcordesEnCompas() {
		return dosAcordesEnCompas;
	}

	public void setDosAcordesEnCompas(int dosAcordesEnCompas) {
		this.dosAcordesEnCompas = dosAcordesEnCompas;
	}
	
	public void incrementarDosAcordeEnCompas() {
		this.dosAcordesEnCompas++;
	}

	public int getTresAcordesEnCompas() {
		return tresAcordesEnCompas;
	}

	public void setTresAcordesEnCompas(int tresAcordesEnCompas) {
		this.tresAcordesEnCompas = tresAcordesEnCompas;
	}

	public void incrementarTresAcordeEnCompas() {
		this.tresAcordesEnCompas++;
	}
	
	public int getCuatroAcordesEnCompas() {
		return cuatroAcordesEnCompas;
	}

	public void setCuatroAcordesEnCompas(int cuatroAcordesEnCompas) {
		this.cuatroAcordesEnCompas = cuatroAcordesEnCompas;
	}

	public void incrementarCuatroAcordeEnCompas() {
		this.cuatroAcordesEnCompas++;
	}
	
	
	
	public String toString(){
		
		
		String salida =  " compas:cant =" +
						"{ 1:"+this.getCantUnCompas() +
						" | 2:"+this.getCantDosCompases()+
						" | 4:"+this.getCantCuatroCompases()+
						" | 8:"+ this.getCantOchoCompases()+" }" +
						" notas x compas =" +
						"{ 1:"+this.getUnAcordeEnCompas() +
						" | 2:"+this.getDosAcordesEnCompas()+
						" | 3:"+this.getTresAcordesEnCompas()+
						" | 4:"+ this.getCuatroAcordesEnCompas()+" }" ;
						
				
		return salida;
		
	}
	

}
