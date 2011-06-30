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
	public void agregarValor(String valor, String estilo,boolean modificado) throws ValoresException {
		
		if (valor == null) {
			throw new ValoresException("El valor a agregar es incorrecto. " + ListaValores.class);
		}
		
		ArrayList<Valores> listaValores = (ArrayList<Valores>) this.getLista();
		
		for (Valores va : listaValores) {
			if (va.getValor().equalsIgnoreCase(valor) && va.getEstilo().equalsIgnoreCase(estilo)) {
				va.incrementarCantidad(modificado);
				return;
			}
		}
		listaValores.add(new Valores(valor,estilo,1,modificado));
		return;	
	}
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void agregarValor(String estilo,boolean modificado) {
		
		ArrayList<Valores> listaValores = (ArrayList<Valores>) this.getLista();
		
		for (Valores va : listaValores) {
			if (va.getEstilo().equalsIgnoreCase(estilo)) {
				va.incrementarCantidad(modificado);
				return;
			}
		}
		listaValores.add(new Valores(estilo,1,modificado));
		return;	
	}
	/**
	 * agrega un valor
	 * 
	 * @param estilo
	 * @param modificadoNuevo
	 * @param modificadoActualizar
	 */
	public void agregarValor(String estilo,boolean modificadoNuevo,boolean modificadoActualizar) {
		
		ArrayList<Valores> listaValores = (ArrayList<Valores>) this.getLista();
		
		for (Valores va : listaValores) {
			if (va.getEstilo().equalsIgnoreCase(estilo)) {
				va.incrementarCantidad(modificadoActualizar);
				return;
			}
		}
		listaValores.add(new Valores(estilo,1,modificadoNuevo));
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
	
	public void agregarValor(String valor, int cantidad, String estilo,boolean modificado) {
		
		ArrayList<Valores> listaValores = this.getLista();
		listaValores.add(new Valores(valor,estilo,cantidad,modificado));
		
	}
	
	/**
	 * toString con mostrando si fue modificado o no, del valor leido de la base de datos
	 * 
	 * @param nombre de tabla a mostrar.
	 * @return cadena con lo contenido en memoria.
	 */
	public String toStringConModificado(String tabla){
		ArrayList<Valores> listaValores = this.getLista();
		
		String salida ="-------------------------"+tabla+"------------------------------\n";
		for (Valores va : listaValores) {
			
			if(va.isModificado()){
				salida += " < MODIFICADO >  ";
			}else{
				salida += " [NO modificado] ";
			}
			salida += "---- Valor: "+va.getValor()+" ---- Total: "+ va.getCantidad()+" ---- estilo: "+ va.getEstilo()+"\n";			
		}
		salida += "-----------------------------------------------------------------------\n";
		return salida;
	}
	
	
	/**
	 * 
	 * toString
	 * 
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
