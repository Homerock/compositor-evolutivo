package estructura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import utiles.Constantes;

import archivos.Utiles;

import excepciones.EstilosException;

/**---------------------------------------------------------------------------
  * @author Sebastian Pazos , Yamil Gomez
  *
  *---------------------------------------------------------------------------*/
public class MatrizEstilos {
	
	private Map <String,EstilosFila> misEstilos;
	
	private static boolean DEBUG = false;
	
		
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
			System.out.println(" - Estilo ppal :"+e.getKey()+"- Total :"+mapEstiloPpal.getContador()+" - Total compases "+mapEstiloPpal.getContadorCompases()+" - "+mapEstiloPpal.toString());		
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
	  * se agrega una Estilo en la matriz
	  * se deve verificar previamente si esta Estilo ya existe si se reescribe
	  * @param EstiloPpal
	  *---------------------------------------------------------------------------*/
	public void agregarEstilo(String EstiloPpal,boolean modificado){
		
		Map<String, EstilosFila> mapEstilos = this.getMisEstilos();
		
		mapEstilos.put(EstiloPpal,new EstilosFila(EstiloPpal,modificado));
		return;
	}
	
	public void agregarEstilo(String EstiloPpal, EstilosFila estilosFila){
		Map<String, EstilosFila> mapEstilos = this.getMisEstilos();
		mapEstilos.put(EstiloPpal,estilosFila);
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * verifica si existe una Estilo ppal devolviendo verdadero
	  * en caso contrario falso 
	  * @param Estilo
	  * @return
	  *---------------------------------------------------------------------------*/
	public boolean ExisteEstilo(String Estilo){
		
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
	public void agregaOcurrenciaEstilo(String EstiloPpal, String EstiloSec,boolean modificado){
		
		EstilosFila mapEstiloPpal;
		mapEstiloPpal= this.getMisEstilos().get(EstiloPpal);
		mapEstiloPpal.agregarEstilo(EstiloSec,modificado);
		return;
	}
	
	
	/**
	 *  se agrega una ocurrencia de una Estilo siguiente a la ppal
	 * si esta Estilo ya existe solo se incrementa el contador
	 * sino agrega la Estilo con la cantidad = valor 
	 * 
	 * @param EstiloPpal
	 * @param EstiloSec
	 * @param valor
	 * @param modificado
	 */
	public void agregaOcurrenciaEstilo(String EstiloPpal, String EstiloSec, int valor,boolean modificado){
		
		EstilosFila mapEstiloPpal;
		mapEstiloPpal= this.getMisEstilos().get(EstiloPpal);
		mapEstiloPpal.agregarEstilo(EstiloSec, valor,modificado);
		
		return;
	}
	
	
	/**
	 * Seteamos el compas de un estilo determinado
	 * solo consideramos los de 1,2,4,8 compases
	 * 
	 * @param estilo
	 * @param compas
	 */
	public void setCompas(String estilo , int compas,boolean modificado) throws EstilosException{
		
		EstilosFila miEstiloFila = this.getMisEstilos().get(estilo);
		
		if(compas < 0){
			throw new EstilosException("Error en compas"+compas+" - Estilo :"+estilo);
		}
		if(compas ==0){
			return;
		}
		
		if(compas % Constantes.PAR ==0){
			// es un numero par
			
			if(compas % Constantes.OCHO_COMPASES ==0){
				miEstiloFila.incCantOchoCompases(compas/Constantes.OCHO_COMPASES,modificado);
				
				if(DEBUG)
					System.out.println(" hay "+compas/Constantes.OCHO_COMPASES+" de 8 ");
			}else  
					if(compas % Constantes.CUATRO_COMPASES ==0){
						miEstiloFila.incCantCuatroCompases(compas/Constantes.CUATRO_COMPASES,modificado);
						if(DEBUG)
							System.out.println("hay "+compas/Constantes.CUATRO_COMPASES+" de 4");
					}else 
						if(compas % Constantes.DOS_COMPASES ==0){
							miEstiloFila.incCantDosCompases(compas/Constantes.DOS_COMPASES,modificado);
							if(DEBUG)
								System.out.println("hay "+compas/Constantes.DOS_COMPASES+" de 2 ");	
						}
			
		}else{
			miEstiloFila.incCantUnCompas(modificado);
			if (DEBUG)
				System.out.println(" hay 1 de un compas");
			
			compas = compas -1;
			setCompas(estilo, compas,modificado);//recursion jo jo jo
		}
		
		return;
		
	}
	
	/**
	 *  Se encarga de contabilizar la cantidad de acordes que hay en cada compas de la canción para un estilo determinado.
	 * teniendo en cuenta estos contadores: unAcordeEnCompas, dosAcordesEnCompas, tresAcordesEnCompas, cuatroAcordesEnCompas
	 *  
	 * @param estilo : nombre del estilo
	 * @param acordesDeUnEstilo : arraylist de acordes en este estilo
	 */
	 
	public void setAcordesEnCompas(String estilo, ArrayList<String> acordesDeUnEstilo,boolean modificado) {
		EstilosFila miEstiloFila = this.getMisEstilos().get(estilo);
		
		int cant;
		for (String acordes : acordesDeUnEstilo){
			cant = Utiles.calculaCantAcordesPorCompas(acordes);
		
			switch (cant) {
				case Constantes.UN_ACORDE: miEstiloFila.incrementarUnAcordeEnCompas(modificado);
										break;
				case Constantes.DOS_ACORDE: miEstiloFila.incrementarDosAcordeEnCompas(modificado);
										break;
				case Constantes.TRES_ACORDE: miEstiloFila.incrementarTresAcordeEnCompas(modificado);
										break;
				case Constantes.CUATRO_ACORDE: miEstiloFila.incrementarCuatroAcordeEnCompas(modificado);
										break;
			}
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
