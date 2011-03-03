package estructura;

/**---------------------------------------------------------------------------
  * esta clase guarda los valores de las ocurrencias de las Estilos y el valor acumulado
  * @author Yamil Gomez -  Sebastian Pazos
  *
  *---------------------------------------------------------------------------*/
public class ValorEstilos {
	
	private int valor;
	private int valorAcumulado;
	
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public ValorEstilos(){
		this.valor=0;
		this.valorAcumulado=0;
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * @param val
	  *---------------------------------------------------------------------------*/
	public ValorEstilos(int val){
		this.valor=val;
		this.valorAcumulado=0;
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * @param val
	  * @param valAc
	  *---------------------------------------------------------------------------*/
	public ValorEstilos(int val , int valAc){
		this.valor= val;
		this.valorAcumulado=valAc;
		return;
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
	public void setValor(int valor) {
		this.valor = valor;
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
	
	/* ---------------------------------------------------------------------------
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 *---------------------------------------------------------------------------*/
	@Override
	public String toString() {
		
		return " [ "+this.getValor()+" - "+this.getValorAcumulado()+" ]";
		
	}

}
