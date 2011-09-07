package estructura;

/**---------------------------------------------------------------------------
  * Esta clase guarda los valores y el valor acumulado
  * 
  * @author Yamil Gomez -  Sebastian Pazos
  *
  *---------------------------------------------------------------------------*/
public class ValorEstilos {
	
	private int valor;
	private int valorAcumulado;
	
	private boolean modificado ; // para optimizacion de la base de datos
	
	
	/**
	  * Constructor 
	  * 
	  * @param val
	  */
	public ValorEstilos(int valor,boolean modificado){
		this.valor=valor;
		this.valorAcumulado=0;
		this.setModificado(modificado);
	}
	
	public ValorEstilos(boolean modificado){
		this.valor=0;
		this.valorAcumulado=0;
		this.setModificado(modificado);
	}

	
	
	public ValorEstilos(int valor , int valorAcumulado,boolean modificado){
		this.valor= valor;
		this.valorAcumulado=valorAcumulado;
		this.setModificado(modificado);
	}
	
	/**---------------------------------------------------------------------------
	  * @return
	  *---------------------------------------------------------------------------*/
	public int getValor() {
		return valor;
	}

	/**---------------------------------------------------------------------------
	  * @param valor
	  *---------------------------------------------------------------------------*/
	public void setValor(int valor,boolean modificado) {
		this.valor = valor;
		this.setModificado(modificado);
	}

	/**---------------------------------------------------------------------------
	  * @return
	  *---------------------------------------------------------------------------*/
	public int getValorAcumulado() {
		return valorAcumulado;
	}

	/**---------------------------------------------------------------------------
	  * @param valorAcumulado
	  *---------------------------------------------------------------------------*/
	public void setValorAcumulado(int valorAcumulado) {
		this.valorAcumulado = valorAcumulado;
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
		
		return " [ "+this.getValor()+" - "+this.getValorAcumulado()+" ]";
		
	}

}
