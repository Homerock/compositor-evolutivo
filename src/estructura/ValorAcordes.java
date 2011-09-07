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
	
	private boolean modificado ; // para optimizacion de la base de datos
	
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public ValorAcordes(String acorde,boolean modificado){
		this.valor=0;
		this.valorAcumulado=0;
		this.acordeSecundario = acorde;
		this.setModificado(modificado);
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * @param val
	  *---------------------------------------------------------------------------*/
	public ValorAcordes(String acorde, int valor,boolean modificado){
		this.acordeSecundario = acorde;
		this.valor=valor;
		this.valorAcumulado=0;
		this.setModificado(modificado);
		return;
	}
	
	/**---------------------------------------------------------------------------
	  * @param val
	  * @param valAc
	  *---------------------------------------------------------------------------*/
	public ValorAcordes(int valor , int valorAcumulado,boolean modificado){
		this.valor= valor;
		this.valorAcumulado=valorAcumulado;
		this.setModificado(modificado);
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
	
	public String getAcordeSecundario() {
		return acordeSecundario;
	}

	public void setAcordeSecundario(String acordeSecundario) {
		this.acordeSecundario = acordeSecundario;
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
