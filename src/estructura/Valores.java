package estructura;

/**---------------------------------------------------------------------------
  * Esta clase guarda los valores y el valor acumulado
  * 
  * @author Yamil Gomez -  Sebastian Pazos
  *
  *---------------------------------------------------------------------------*/
public class Valores {
	
	private String valor;
	private String estilo;
	private int cantidad;
	
	
	/**---------------------------------------------------------------------------
	  * Constructor  
	  *---------------------------------------------------------------------------*/
	public Valores(){
		this.valor="";
		this.estilo="";
		this.cantidad=0;
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
	public void incrementarCantidad() {
		
		int cant;
		
		cant = this.getCantidad();
		this.setCantidad(cant+1);
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
