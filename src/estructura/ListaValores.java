package estructura;

import java.util.ArrayList;
import java.util.Random;

import excepciones.ValoresException;


public class ListaValores {
	
	private ArrayList<Valores> lista;
	

	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public ListaValores() {
	
		this.lista = new ArrayList<Valores>();
		
	}

		
	public ArrayList<Valores> getLista() {
		return lista;
	}


	public void setLista(ArrayList<Valores> lista) {
		this.lista = lista;
	}


	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void agregarValor(String valor, String estilo) {
		
		ArrayList<Valores> listaValores = (ArrayList<Valores>) this.getLista();
		
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
	public void agregarValor(String estilo) {
		
		ArrayList<Valores> listaValores = (ArrayList<Valores>) this.getLista();
		
		for (Valores va : listaValores) {
			if (va.getEstilo().equalsIgnoreCase(estilo)) {
				va.incrementarCantidad();
				return;
			}
		}
		listaValores.add(new Valores(estilo,1));
		return;	
	}

	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void agregarValor(String valor, int cantidad, String estilo) {
		
		ArrayList<Valores> listaValores = this.getLista();
		
		listaValores.add(new Valores(valor,estilo,cantidad));
		
	}
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void listarValor() {
		
		ArrayList<Valores> listaValores = this.getLista();
		
		System.out.println("-------------------------------------------------------");
		for (Valores va : listaValores) {
			System.out.println(" ---- Valor: "+va.getValor()+" ---- Total: "+ va.getCantidad()+" ---- estilo: "+ va.getEstilo());	
		}
		System.out.println("-------------------------------------------------------");
	}
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------
	 * @throws ValoresException */
	public String obtenerMayorValorPorEstilo(String estilo) throws ValoresException{
		
		ArrayList<Valores> listaValores = this.getLista();
		ArrayList<Valores> miLista = new ArrayList<Valores>(); 
		Random rnd = new Random();
		int cant = 0;
		int miRandom;
		
		// cargo en una lista temporal todos los valores que corresponden al estilo que viene por parametro
		for (Valores va : listaValores) {
			if (va.getEstilo().equalsIgnoreCase(estilo)) {
				miLista.add(va);
				cant = cant + va.getCantidad();
			}	
		}
		
		try {
			// hago un random sobre esta lista temporal y obtengo el valor (por ej: tempo, duracion) aleatoria para
			// el estilo que ingreso por parametro
			do {
				miRandom = rnd.nextInt(cant);
			} while(miRandom > miLista.size()-1);
			Valores va = miLista.get(miRandom);
			
			return va.getValor();
		} catch (IllegalArgumentException e) {
			throw new ValoresException("El estilo no tiene datos cargados");
		}
		 
		
		
	}	
}
