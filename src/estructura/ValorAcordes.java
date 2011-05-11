package estructura;

/**---------------------------------------------------------------------------
 * esta clase guarda los valores de las ocurrencias de las Acordes y el valor acumulado
 * @author yamil sebastian
 *
 *---------------------------------------------------------------------------*/
public class ValorAcordes {
	
	private String acordeSecundario;
	private int valor;
	private int valorAcumulado;
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public ValorAcordes(String acorde){
		this.valor=0;
		this.valorAcumulado=0;
		this.acordeSecundario = acorde;
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * @param val
	  *---------------------------------------------------------------------------*/
	public ValorAcordes(String acorde, int val){
		this.acordeSecundario = acorde;
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
	
	public String getAcordeSecundario() {
		return acordeSecundario;
	}

	public void setAcordeSecundario(String acordeSecundario) {
		this.acordeSecundario = acordeSecundario;
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
