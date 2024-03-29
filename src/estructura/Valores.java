package estructura;

/**---------------------------------------------------------------------------
  * Esta clase guarda los valores y el valor acumulado
  * 
  * @author Yamil Gomez -  Sebastian Pazos
  *
  *---------------------------------------------------------------------------*/
public class Valores {
	
	private String valor;//cadena que lo identifica
	private String estilo;
	private int cantidad;
	
	private boolean modificado; // para optimizacion de la base de dato
	
	
	/**---------------------------------------------------------------------------
	  * Constructor  
	  *---------------------------------------------------------------------------*/
	public Valores(){
		this.valor="";
		this.estilo="";
		this.cantidad=0;
		this.setModificado(false);
		
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * Constructor 
	  * 
	  * @param val
	  * @param valAc
	  *---------------------------------------------------------------------------*/
	public Valores(String valor, String estilo, int cant){
		this.valor=valor;
		this.estilo= estilo;
		this.cantidad=cant;
		this.setModificado(false);
		return;
	}
	
	
	/**---------------------------------------------------------------------------
	  * Constructor 
	  * 
	  * @param val
	  * @param valAc
	  *---------------------------------------------------------------------------*/
	public Valores(String valor, String estilo, int cant,boolean modificado){
		this.valor=valor;
		this.estilo= estilo;
		this.cantidad=cant;
		this.setModificado(modificado);
		return;
	}


	public Valores(String estilo, int cant,boolean modificado){
		this.valor="";
		this.estilo= estilo;
		this.cantidad=cant;
		this.setModificado(modificado);
		
		return;
	}
	
	
	/**---------------------------------------------------------------------------
	 *
	 *---------------------------------------------------------------------------*/
	public String getEstilo() {
		return estilo;
	}

	/**---------------------------------------------------------------------------
	 *
	 *---------------------------------------------------------------------------*/
	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}
	
	/**---------------------------------------------------------------------------
	 *
	 *---------------------------------------------------------------------------*/
	public String getValor() {
		return valor;
	}

	/**---------------------------------------------------------------------------
	 *
	 *---------------------------------------------------------------------------*/
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**---------------------------------------------------------------------------
	 *
	 *---------------------------------------------------------------------------*/
	public int getCantidad() {
		return cantidad;
	}

	/**---------------------------------------------------------------------------
	 *
	 *---------------------------------------------------------------------------*/
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	/**---------------------------------------------------------------------------
	 *
	 *---------------------------------------------------------------------------*/
	public void incrementarCantidad(boolean modificado) {
		this.setModificado(modificado);
		
		int cant = this.getCantidad();
		this.setCantidad(cant+1);
	}
		
	
	
	public boolean isModificado() {
		return modificado;
	}

	public void setModificado(boolean modificado) {
		this.modificado = modificado;
	}

	/* ---------------------------------------------------------------------------
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 *---------------------------------------------------------------------------*/
	@Override
	public String toString() {
		
		return " [ "+this.getValor()+" - "+this.getCantidad()+" - "+this.getEstilo()+" ]";
		
	}

}
