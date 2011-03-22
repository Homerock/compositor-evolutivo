package estructura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListaValores {
	
	private List lista;
	

	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public ListaValores() {
	
		this.lista = new ArrayList<Valores>();
		
	}

		
	public List getLista() {
		return lista;
	}


	public void setLista(List lista) {
		this.lista = lista;
	}


	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void agregarValor(String valor, String estilo) {
		
		ArrayList<Valores> listaValores = (ArrayList<Valores>) this.getLista();
		Valores val;
		int cant;
		
		for (Valores va : listaValores) {
			if (va.getValor().equalsIgnoreCase(valor) && va.getEstilo().equalsIgnoreCase(estilo)) {
				va.incrementarCantidad();
				return;
			}
		}
		listaValores.add(new Valores(valor,estilo,1));
		return;	
	}

	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void agregarValor(String valor, int cantidad, String estilo) {
		
		ArrayList<Valores> listaValores = (ArrayList<Valores>) this.getLista();
		
		listaValores.add(new Valores(valor,estilo,cantidad));
		
	}
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void listarValor() {
		
		ArrayList<Valores> listaValores = (ArrayList<Valores>) this.getLista();
		
		System.out.println("-------------------------------------------------------");
		for (Valores va : listaValores) {
			System.out.println(" ---- Valor: "+va.getValor()+" ---- Total: "+ va.getCantidad()+" ---- estilo: "+ va.getEstilo());	
		}
		System.out.println("-------------------------------------------------------");
	}
	
}
