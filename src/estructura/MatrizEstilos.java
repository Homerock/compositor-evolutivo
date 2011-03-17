package estructura;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import excepciones.EstilosException;

/**---------------------------------------------------------------------------
  * @author Sebastian Pazos , Yamil Gomez
  *
  *---------------------------------------------------------------------------*/
public class MatrizEstilos {
	
	private Map <String,EstilosFila> misEstilos;
	
	private static final int UN_COMPAS = 1;
	private static final int DOS_COMPASES = 2;
	private static final int TRES_COMPASES = 3;
	private static final int CUATRO_COMPASES = 4;
	private static final int SEIS_COMPASES = 6;
	private static final int OCHO_COMPASES = 8;
		
	/**---------------------------------------------------------------------------
	  * Constructor
	  *---------------------------------------------------------------------------*/
	public MatrizEstilos(){
		this.misEstilos = new HashMap<String, EstilosFila>();
	}
	
	/**---------------------------------------------------------------------------
	  * ListarEstilos
	  *---------------------------------------------------------------------------*/
	public void listarEstilos(){
		Map<String, EstilosFila> mapEstilos = this.getMisEstilos();
		EstilosFila mapEstiloPpal;
		Iterator it = mapEstilos.entrySet().iterator();
		
		
		
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			mapEstiloPpal= (EstilosFila) e.getValue();
			System.out.println(" - Estilo ppal :"+e.getKey()+"- Total :"+mapEstiloPpal.getContador()+" - "+mapEstiloPpal.toString());
			
				
			mapEstiloPpal.listarEstilos();
		}
	}
	
	/**---------------------------------------------------------------------------
	  * calcula todos los valores acumulados de cada elemento del map
	  *---------------------------------------------------------------------------*/
	public void calcularAcumulados(){
		Map<String, EstilosFila> mapEstilos = this.getMisEstilos();
		EstilosFila mapEstiloPpal;
		Iterator it = mapEstilos.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			mapEstiloPpal= (EstilosFila) e.getValue();
			mapEstiloPpal.calcularAcumulados();
		}
	}
	
	
	
	/**---------------------------------------------------------------------------
	  * se agrega una Estilo ppal en la matriz
	  * se deve verificar previamente si esta Estilo ya existe si se reescribe
	  * @param EstiloPpal
	  *---------------------------------------------------------------------------*/
	public void agregarEstiloPrincipal(String EstiloPpal){
		
		Map<String, EstilosFila> mapEstilos = this.getMisEstilos();
		
		mapEstilos.put(EstiloPpal,new EstilosFila(EstiloPpal));
		return;
	}
	
	public void agregarEstiloPrincipal(String EstiloPpal, int cantidad){
		
		Map<String, EstilosFila> mapEstilos = this.getMisEstilos();
		
		mapEstilos.put(EstiloPpal,new EstilosFila(EstiloPpal,cantidad));
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * verifica si existe una Estilo ppal devolviendo verdadero
	  * en caso contrario falso 
	  * @param Estilo
	  * @return
	  *---------------------------------------------------------------------------*/
	public boolean ExisteEstiloPpal(String Estilo){
		
		Map<String, EstilosFila> mapEstilos = this.getMisEstilos();
		
		return mapEstilos.containsKey(Estilo);
	}
	
	/**---------------------------------------------------------------------------
	  * se agrega una ocurrencia de una Estilo siguiente a la ppal
	  * si esta Estilo ya existe solo se incrementa el contador
	  * sino agrega la Estilo (ver mas en clase Estilos)
	  * @param EstiloPpal
	  * @param EstiloSec
	  *---------------------------------------------------------------------------*/
	public void agregaOcurrenciaEstiloSecundario(String EstiloPpal, String EstiloSec){
		
		EstilosFila mapEstiloPpal;
		mapEstiloPpal= this.getMisEstilos().get(EstiloPpal);
		mapEstiloPpal.agregarEstilo(EstiloSec);
		return;
	}
	
	public void agregaOcurrenciaEstiloSecundario(String EstiloPpal, String EstiloSec, int valor){
		
		EstilosFila mapEstiloPpal;
		mapEstiloPpal= this.getMisEstilos().get(EstiloPpal);
		mapEstiloPpal.agregarEstilo(EstiloSec, valor);
		
		return;
	}
	
	/**---------------------------------------------------------------------------
	 * Seteamos el compas de un estilo determinado
	 * solo consideramos los de 1,2,3,4,6,8 compases
	 * 
	 * @param estilo
	 * @param compas
	 ---------------------------------------------------------------------------*/
	public void setCompas(String estilo , int compas) throws EstilosException{
		EstilosFila miEstiloFila = this.getMisEstilos().get(estilo);
		
		switch (compas){
			case UN_COMPAS: 
							miEstiloFila.incCantUnCompas();
							break;
			case DOS_COMPASES: 
							miEstiloFila.incCantDosCompases();
							break;

			case TRES_COMPASES: 
							miEstiloFila.incCantTresCompases();
							break;				
			case CUATRO_COMPASES:	
							miEstiloFila.incCantCuatroCompases();
							break;
			case SEIS_COMPASES:	
							miEstiloFila.incCantSeisCompases();
							break;
			case OCHO_COMPASES :
							miEstiloFila.incCantOchoCompases();
							break;
			default :
					throw new EstilosException("No esta implementado para "+compas+" compases - Estilo :"+estilo);
		}

	}
	
	/**---------------------------------------------------------------------------
	  * @return
	  *---------------------------------------------------------------------------*/
	public Map<String, EstilosFila> getMisEstilos() {
		return misEstilos;
	}

	/**---------------------------------------------------------------------------
	  * @param misEstilos
	  *---------------------------------------------------------------------------*/
	public void setMisEstilos(Map<String, EstilosFila> misEstilos) {
		this.misEstilos = misEstilos;
	}
	
	
	public void vaciarMatriz() {
		
		this.misEstilos.clear();
		
	}
}
