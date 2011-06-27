package estructura;

import java.util.ArrayList;

/**
 * @author Admin
 *
 */
public class AcordesFila {
	
	private String nombreAcorde;
	private ArrayList<ValorAcordes> listaOcurrencias;
	private int valorAcumuladoFila; 
	
	private boolean modificado ; // para optimizacion de la base de datos
	


	/**---------------------------------------------------------------------------
	  * @param nombre
	  *---------------------------------------------------------------------------*/
	public AcordesFila(String nombre,boolean modificado){
		this.setNombreAcorde(nombre);
		this.listaOcurrencias = new ArrayList<ValorAcordes>();
		this.setValorAcumuladoFila(0);
		this.setModificado(modificado);
	}
	
	public AcordesFila(String nombre, int valor,boolean modificado){
		this.setNombreAcorde(nombre);
		this.listaOcurrencias = new ArrayList<ValorAcordes>();
		this.setValorAcumuladoFila(valor);
		this.setModificado(modificado);
	
	}
	
	/**---------------------------------------------------------------------------
	  * @param Acorde
	  *---------------------------------------------------------------------------*/
	public void agregarAcorde(String acorde,boolean modificado){
		
		ArrayList<ValorAcordes> listaAcordes = this.getListaOcurrencias();
		int cant=0;
		ValorAcordes miValAcorde;
		boolean encontrado=false;
		String sec;
	
		for (ValorAcordes va : listaAcordes) {
			sec = va.getAcordeSecundario();
			if (sec.equalsIgnoreCase(acorde)) {
				cant = va.getValor();
				cant = cant+1;
				va.setValor(cant,modificado);
				encontrado = true;
				break;
			}
		}
		
		if (!encontrado) {
			miValAcorde = new ValorAcordes(acorde, 1,modificado);
			listaAcordes.add(miValAcorde);
		}
		return;
	}
	
	
	/**---------------------------------------------------------------------------
	  * Agrego el Acorde secundario y el valor de cantidad de ocurrencias
	  * @param Acorde
	  *---------------------------------------------------------------------------*/
	public void agregarAcorde(String acorde, int valor,boolean modificado){
				
		ArrayList<ValorAcordes> listaAcordes = this.getListaOcurrencias();
		ValorAcordes miValAcorde;
		boolean encontrado=false;
		String sec;
		String est;
		
		for (ValorAcordes va : listaAcordes) {
			sec = va.getAcordeSecundario();
			if (sec.equalsIgnoreCase(acorde)) {
				va.setValor(valor,modificado);
				encontrado = true;
				break;
			}
		}
		
		if (!encontrado) {
			miValAcorde = new ValorAcordes(acorde, valor,modificado);
			listaAcordes.add(miValAcorde);
		}
		return;

	}
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void listarAcordes(){
		
		ArrayList<ValorAcordes> listaAcordes = this.getListaOcurrencias();
			
		for (ValorAcordes va : listaAcordes) {
			System.out.println(va.getAcordeSecundario() +  "  [ " + va.getValor() + " " + va.getValorAcumulado() + " ]");
		}
	}
	//---------------------------------------------------------------------------
	/**
	  * Devuelve el proximo Acorde de la matriz acumulada, correspondiente al valor pasado por parametro.
	  * Nos fijamos en los valores acumulados de cada Acorde
	  * y devolvemos el mayor mas cercano.
	  * @param valor
	  * @return
	  **/
	//---------------------------------------------------------------------------
	public String buscarAcorde(int valor){
		
		String acorde="";
		ArrayList<ValorAcordes> listaAcordes = this.getListaOcurrencias();
		
		for (ValorAcordes va : listaAcordes) {
			if (va.getValorAcumulado() >= valor) {
				acorde = va.getAcordeSecundario();
				break;
			}
		}
		return acorde;
	}
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void calcularAcumulados(){
		
		int acumulador=0;
		ArrayList<ValorAcordes> listaAcordes = this.getListaOcurrencias();
		
		for (ValorAcordes va : listaAcordes) {
			acumulador = acumulador + va.getValor();
			va.setValorAcumulado(acumulador);
		}
		this.setValorAcumuladoFila(acumulador);
		
	}
	
	/**---------------------------------------------------------------------------
	  * @param contador
	  *---------------------------------------------------------------------------*/
	public void setValorAcumuladoFila(int valor) {
		this.valorAcumuladoFila = valor;
	}
	
	/**---------------------------------------------------------------------------
	  * devuelve el acumulador total 
	  * @return
	  *---------------------------------------------------------------------------*/
	public int getValorAcumuladoFila() {
		return this.valorAcumuladoFila;
	}
	
	/**---------------------------------------------------------------------------
	  * @return
	  *---------------------------------------------------------------------------*/
	public String getNombreAcorde() {
		return nombreAcorde;
	}
	
	/**---------------------------------------------------------------------------
	  * @param nombreAcorde
	  *---------------------------------------------------------------------------*/
	public void setNombreAcorde(String nombreAcorde) {
		this.nombreAcorde = nombreAcorde;
	}
	
	public ArrayList<ValorAcordes> getListaOcurrencias() {
		return listaOcurrencias;
	}

	public void setListaOcurrencias(ArrayList<ValorAcordes> listaOcurrencias) {
		this.listaOcurrencias = listaOcurrencias;
	}

	public boolean isModificado() {
		return modificado;
	}

	public void setModificado(boolean modificado) {
		this.modificado = modificado;
	}

	
	
	
}
