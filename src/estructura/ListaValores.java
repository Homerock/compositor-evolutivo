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


	/**
	 * 
	 * @throws ValoresException 
	 */
	public void agregarValor(String valor, String estilo) throws ValoresException {
		
		if (valor == null) {
			throw new ValoresException("El valor a agregar es incorrecto. " + ListaValores.class);
		}
		
		ArrayList<Valores> listaValores = (ArrayList<Valores>) this.getLista();
		
		for (Valores va : listaValores) {
			if (va.getValor().equalsIgnoreCase(valor) && va.getEstilo().equalsIgnoreCase(estilo)) {
				va.incrementarCantidad();
				return;
			}
		}
		listaValores.add(new Valores(valor,estilo,1,true));
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
	
	/**
	 * Agrega un valor (cadena que lo identifica) a la lista, con la cantidad y el estilo pasado por parametros.
	 * 
	 * @param valor
	 * @param cantidad
	 * @param estilo
	 */
	public void agregarValor(String valor, int cantidad, String estilo) {
		
		ArrayList<Valores> listaValores = this.getLista();
		
		listaValores.add(new Valores(valor,estilo,cantidad));
		
	}
	
	/**---------------------------------------------------------------------------
	  * BORRAR  
	  *---------------------------------------------------------------------------*/
	public void listarValor() {
		
		ArrayList<Valores> listaValores = this.getLista();
		
		System.out.println("-------------------------------------------------------");
		for (Valores va : listaValores) {
			System.out.println(" ---- Valor: "+va.getValor()+" ---- Total: "+ va.getCantidad()+" ---- estilo: "+ va.getEstilo());	
		}
		System.out.println("-------------------------------------------------------");
	}
	
	
	
	
	/**
	 * toString
	 */
	public String toString() {
		
		ArrayList<Valores> listaValores = this.getLista();
		if(listaValores.size()==0){
			return "";
		}
		String salida = "------------------------------------------------------- \n";
		for (Valores va : listaValores) {
			salida += "---- Valor: "+va.getValor()+" ---- Total: "+ va.getCantidad()+" ---- estilo: "+ va.getEstilo()+ "\n";	
		}
		salida+="-------------------------------------------------------\n";
		
		return salida;
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
