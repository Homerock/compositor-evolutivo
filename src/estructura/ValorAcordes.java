package estructura;

/**---------------------------------------------------------------------------
  * esta clase guarda los valores de las ocurrencias de las Acordes y el valor acumulado
  * @author yamil sebastian
  *
  *---------------------------------------------------------------------------*/
public class ValorAcordes {
	
	private int valor;
	private int valorAcumulado;
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public ValorAcordes(){
		this.valor=0;
		this.valorAcumulado=0;
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * @param val
	  *---------------------------------------------------------------------------*/
	public ValorAcordes(int val){
		this.valor=val;
		this.valorAcumulado=0;
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * @param val
	  * @param valAc
	  *---------------------------------------------------------------------------*/
	public ValorAcordes(int val , int valAc){
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
