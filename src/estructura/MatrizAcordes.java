package estructura;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**---------------------------------------------------------------------------
  * @author Sebastian Pazos , Yamil Gomez
  *
  *---------------------------------------------------------------------------*/
public class MatrizAcordes {
	
	private Map <String,AcordesFila> misAcordes;
		
	/**---------------------------------------------------------------------------
	  * Constructor
	  *---------------------------------------------------------------------------*/
	public MatrizAcordes(){
		this.misAcordes = new HashMap<String, AcordesFila>();
	}
	

	/**---------------------------------------------------------------------------
	  * calcula todos los valores acumulados de cada elemento del map
	  *---------------------------------------------------------------------------*/
	public void calcularAcumulados(){
		Map<String, AcordesFila> mapAcordes = this.getMisAcordes();
		AcordesFila mapAcordePpal;
		Iterator it = mapAcordes.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			mapAcordePpal= (AcordesFila) e.getValue();
			mapAcordePpal.calcularAcumulados();
		}
	}
	
	/**---------------------------------------------------------------------------
	  * se agrega una Acorde ppal en la matriz
	  * se debe verificar previamente si esta Acorde ya existe si se reescribe
	  * @param AcordePpal
	  *---------------------------------------------------------------------------*/
	public void agregarAcordePrincipal(String AcordePpal,boolean modificado){
		
		Map<String, AcordesFila> mapAcordes = this.getMisAcordes();
		
		mapAcordes.put(AcordePpal,new AcordesFila(AcordePpal,modificado));
		return;
	}
	
	public void agregarAcordePrincipal(String AcordePpal, int cantidad,boolean modificado){
		
		Map<String, AcordesFila> mapAcordes = this.getMisAcordes();
		
		mapAcordes.put(AcordePpal,new AcordesFila(AcordePpal,cantidad,modificado));
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * verifica si existe una Acorde ppal devolviendo verdadero
	  * en caso contrario falso 
	  * @param Acorde
	  * @return
	  *---------------------------------------------------------------------------*/
	public boolean ExisteAcordePpal(String Acorde){
		
		Map<String, AcordesFila> mapAcordes = this.getMisAcordes();
		
		return mapAcordes.containsKey(Acorde);
	}
	
	/**---------------------------------------------------------------------------
	  * se agrega una ocurrencia de una Acorde siguiente a la ppal
	  * si esta Acorde ya existe solo se incrementa el contador
	  * sino agrega la Acorde (ver mas en clase Acordes)
	  * @param AcordePpal
	  * @param AcordeSec
	  *---------------------------------------------------------------------------*/
	public void agregaOcurrenciaAcordeSecundario(String AcordePpal, String AcordeSec,boolean modificado){
		
		AcordesFila mapAcordePpal;
		mapAcordePpal= this.getMisAcordes().get(AcordePpal);
		mapAcordePpal.agregarAcorde(AcordeSec,modificado);
		return;
	}
	
	/**---------------------------------------------------------------------------*/
	public void agregaOcurrenciaAcordeSecundario(String AcordePpal, String AcordeSec, int valor,boolean modificado){
		
		AcordesFila mapAcordePpal;
		mapAcordePpal= this.getMisAcordes().get(AcordePpal);
		mapAcordePpal.agregarAcorde(AcordeSec, valor,modificado);
		
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * @return
	  *---------------------------------------------------------------------------*/
	public Map<String, AcordesFila> getMisAcordes() {
		return misAcordes;
	}

	/**---------------------------------------------------------------------------
	  * @param misAcordes
	  *---------------------------------------------------------------------------*/
	public void setMisAcordes(Map<String, AcordesFila> misAcordes) {
		this.misAcordes = misAcordes;
	}
	
	
	public void vaciarMatriz() {
		
		this.misAcordes.clear();
		
	}
	

	/**---------------------------------------------------------------------------
	  * ListarAcordes
	  *---------------------------------------------------------------------------*/
	public String toString(){
		Map<String, AcordesFila> mapAcordes = this.getMisAcordes();
		AcordesFila mapAcordePpal;
		Iterator it = mapAcordes.entrySet().iterator();
		String salida ="";
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			mapAcordePpal= (AcordesFila) e.getValue();
			salida+="Principal : "+e.getKey()+" === Acumulado :"+mapAcordePpal.getValorAcumuladoFila()+" \n";	
			salida +=mapAcordePpal.toString();
			//salida +="\n";
		}
		return salida;
	}
	
	/**---------------------------------------------------------------------------
	  * ListarAcordes
	  *---------------------------------------------------------------------------*/
	public String toStringConModificado(){
		Map<String, AcordesFila> mapAcordes = this.getMisAcordes();
		AcordesFila mapAcordePpal;
		Iterator it = mapAcordes.entrySet().iterator();
		String salida ="";
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			mapAcordePpal= (AcordesFila) e.getValue();
			
			if (mapAcordePpal.isModificado()){
				salida+=" < MODIFICADO >  ";
			}else{
				salida+=" [NO modificado] ";
			}
			
			salida+="Principal : "+e.getKey()+" === Acumulado :"+mapAcordePpal.getValorAcumuladoFila()+" \n";	
			salida +=mapAcordePpal.toStringConModificado();
			
		}
		return salida;
	}
	
	
}

