package estructura;

import java.util.*;

/**
 * @author Yamil Gomez ,Sebastian Pazos
 *
 */
public class EstilosFila {
	
	private String nombreEstilo;
	private Map <String,Valor_Y_Acumulado> mapEstilos;
	private int contador ; 
	
	private int cantUnCompas;
	private int cantDosCompases;
	private int cantTresCompases;
	private int cantCuatroCompases;
	private int cantSeisCompases;
	private int cantOchoCompases;
	


	/**---------------------------------------------------------------------------
	  * Constructor de Estilos Fila 
	  * con el contador en cero
	  * @param nombre
	  *---------------------------------------------------------------------------*/
	public EstilosFila(String nombre){
		this.setNombreEstilo(nombre);
		this.mapEstilos=new HashMap<String, Valor_Y_Acumulado> ();
		this.setContador(1);
		this.setCantUnCompas(0);
	 	this.setCantDosCompases(0);
	 	this.setCantTresCompases(0);
	 	this.setCantCuatroCompases(0);
	 	this.setCantSeisCompases(0);
	 	this.setCantOchoCompases(0);
	}
	/**------------------------------------------------------------------------
	 * Constructor de Estilos Fila
	 * 
	 * @param nombre
	 * @param cantidad
	 ------------------------------------------------------------------------*/
	public EstilosFila(String nombre, int cantidad){
		this.setNombreEstilo(nombre);
		this.mapEstilos=new HashMap<String, Valor_Y_Acumulado> ();
		this.setContador(cantidad);
		this.setCantUnCompas(0);
	 	this.setCantDosCompases(0);
	 	this.setCantTresCompases(0);
	 	this.setCantCuatroCompases(0);
	 	this.setCantSeisCompases(0);
	 	this.setCantOchoCompases(0);
	}
	

	/**
	 *  Agrega en estilo al map con valor 1
	 *  
	 * @param Estilo
	 */
	public void agregarEstilo(String Estilo){
		
		Map<String, Valor_Y_Acumulado> mapEstilos = this.getMapEstilos();
		Integer cant=0;
		Valor_Y_Acumulado miValEstilo;
		
		if (mapEstilos.containsKey(Estilo)){
			cant= new Integer(mapEstilos.get(Estilo).getValor());
			cant=cant+1;
			mapEstilos.get(Estilo).setValor(cant);
		}else{
			miValEstilo = new Valor_Y_Acumulado(1);
			mapEstilos.put(Estilo, miValEstilo);//se sobreescribe con el valor nuevo
		}
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * Agrego un Estilo secundario y la cantidad de ocurrencias
	  * @param Estilo y cantidad
	  *---------------------------------------------------------------------------*/
	public void agregarEstilo(String Estilo, int cantidad){
		
		Map<String, Valor_Y_Acumulado> mapEstilos = this.getMapEstilos();
		Valor_Y_Acumulado miValEstilo;
		
		if (mapEstilos.containsKey(Estilo)){
			mapEstilos.get(Estilo).setValor(cantidad);
		}else{
			miValEstilo = new Valor_Y_Acumulado(cantidad);
			mapEstilos.put(Estilo, miValEstilo);//se sobreescribe con el valor nuevo
		}
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * Lista todos los estilos existentes
	  *---------------------------------------------------------------------------*/
	public void listarEstilos(){
		
		Map<String, Valor_Y_Acumulado> mapEstilos = this.getMapEstilos();
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
		
		Map<String, Valor_Y_Acumulado> mapEstilos = this.getMapEstilos();
		Iterator it = mapEstilos.entrySet().iterator();
		Valor_Y_Acumulado miValEstilo;
		String Estilo="";

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			miValEstilo = (Valor_Y_Acumulado)e.getValue(); 
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
	public Map<String, Valor_Y_Acumulado> getMapEstilos() {
		return mapEstilos;
	}

	/**---------------------------------------------------------------------------
	  * @param mapEstilos
	  *---------------------------------------------------------------------------*/
	public void setMapEstilos(Map<String, Valor_Y_Acumulado> mapEstilos) {
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
		Valor_Y_Acumulado miValEstilos;
		Map<String, Valor_Y_Acumulado> mapEstilos = this.getMapEstilos();
		Iterator it = mapEstilos.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			miValEstilos=(Valor_Y_Acumulado)e.getValue();
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
						this.getCantTresCompases()+
						this.getCantCuatroCompases()+
						this.getCantSeisCompases()+
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
	public int getCantTresCompases() {
		return cantTresCompases;
	}
	public void setCantTresCompases(int cantTresCompases) {
		this.cantTresCompases = cantTresCompases;
	}
	public int getCantCuatroCompases() {
		return cantCuatroCompases;
	}
	public void setCantCuatroCompases(int cantCuatroCompases) {
		this.cantCuatroCompases = cantCuatroCompases;
	}
	public int getCantSeisCompases() {
		return cantSeisCompases;
	}
	public void setCantSeisCompases(int cantSeisCompases) {
		this.cantSeisCompases = cantSeisCompases;
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
	
	public void incCantTresCompases(){
		int cant = this.getCantTresCompases();
		cant++;
		this.setCantTresCompases(cant);
		return;
	}
	
	public void incCantCuatroCompases(){
		int cant = this.getCantCuatroCompases();
		cant++;
		this.setCantCuatroCompases(cant);
		return;
	}

	public void incCantSeisCompases(){
		int cant = this.getCantSeisCompases();
		cant++;
		this.setCantSeisCompases(cant);
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
	
	public void incCantTresCompases(int incremento){
		int cant = this.getCantTresCompases();
		cant = cant + incremento;
		this.setCantTresCompases(cant);
		return;
	}
	
	public void incCantCuatroCompases(int incremento){
		int cant = this.getCantCuatroCompases();
		cant = cant + incremento;
		this.setCantCuatroCompases(cant);
		return;
	}

	public void incCantSeisCompases(int incremento){
		int cant = this.getCantSeisCompases();
		cant = cant + incremento;
		this.setCantSeisCompases(cant);
		return;
	}
	
	public void incCantOchoCompases(int incremento){
		int cant = this.getCantOchoCompases();
		cant = cant + incremento;
		this.setCantOchoCompases(cant);
		return;
	}
	
	
	
	public String toString(){
		
		
		String salida =  " compas:cant =" +
						"{ 1:"+this.getCantUnCompas() +
						" | 2:"+this.getCantDosCompases()+
						" | 3:"+this.getCantTresCompases()+
						" | 4:"+this.getCantCuatroCompases()+
						" | 6:"+this.getCantSeisCompases()+
						" | 8:"+ this.getCantOchoCompases()+" }";
				
		return salida;
		
	}
	

}
