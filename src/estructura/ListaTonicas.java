package estructura;

import java.util.HashMap;
import java.util.Map;

public class ListaTonicas {
	
	private Map <String,Integer> misTonicas;

	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public ListaTonicas() {
	
		this.misTonicas = new HashMap<String, Integer>();
		
	}

	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public Map<String, Integer> getMisTonicas() {
		return misTonicas;
	}

	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void setMisTonicas(Map<String, Integer> misTonicas) {
		this.misTonicas = misTonicas;
	}
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void agregarTonica(String tonica) {
		
		Map<String, Integer> mapTonicas = this.getMisTonicas();
		int cant;
		
		
		if (mapTonicas.containsKey(tonica)){
			
			cant = mapTonicas.get(tonica);
			mapTonicas.put(tonica, cant + 1);
		}	
		else {
			mapTonicas.put(tonica, 1);
		}
		return;	
	}

	public void agregarTonicas(String tonica, int cantidad) {
		
		Map<String, Integer> mapTonicas = this.getMisTonicas();
		
		mapTonicas.put(tonica, cantidad);
		
	}
	
}
