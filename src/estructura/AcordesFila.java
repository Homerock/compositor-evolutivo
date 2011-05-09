package estructura;

import java.util.ArrayList;

/**
 * @author Admin
 *
 */
public class AcordesFila {
	
	private String nombreAcorde;
	private ArrayList<ValorAcordes> listaOcurrencias;
	private int contador; 


	/**---------------------------------------------------------------------------
	  * @param nombre
	  *---------------------------------------------------------------------------*/
	public AcordesFila(String nombre){
		this.setNombreAcorde(nombre);
		this.listaOcurrencias = new ArrayList<ValorAcordes>();
		this.setContador(0);
	
	}
	
	public AcordesFila(String nombre, int cantidad){
		this.setNombreAcorde(nombre);
		this.listaOcurrencias = new ArrayList<ValorAcordes>();
		this.setContador(cantidad);
	
	}
	
	/**---------------------------------------------------------------------------
	  * @param Acorde
	  *---------------------------------------------------------------------------*/
	public void agregarAcorde(String acorde, String estilo){
		
		ArrayList<ValorAcordes> listaAcordes = this.getListaOcurrencias();
		int cant=0;
		ValorAcordes miValAcorde;
		boolean encontrado=false;
		String sec;
		String est;
	
		for (ValorAcordes va : listaAcordes) {
			sec = va.getAcordeSecundario();
			est = va.getEstilo();
			if (sec.equalsIgnoreCase(acorde) && est.equalsIgnoreCase(estilo)) {
				cant = va.getValor();
				cant = cant+1;
				va.setValor(cant);
				encontrado = true;
				break;
			}
		}
		
		if (!encontrado) {
			miValAcorde = new ValorAcordes(acorde, 1, estilo);
			listaAcordes.add(miValAcorde);
		}
		return;
	}
	
	
	/**---------------------------------------------------------------------------
	  * Agrego el Acorde secundario y el valor de cantidad de ocurrencias
	  * @param Acorde
	  *---------------------------------------------------------------------------*/
	public void agregarAcorde(String acorde, int valor, String estilo){
				
		ArrayList<ValorAcordes> listaAcordes = this.getListaOcurrencias();
		ValorAcordes miValAcorde;
		boolean encontrado=false;
		String sec;
		String est;
		
		for (ValorAcordes va : listaAcordes) {
			sec = va.getAcordeSecundario();
			est = va.getEstilo();
			if (sec.equalsIgnoreCase(acorde) && est.equalsIgnoreCase(estilo)) {
				va.setValor(valor);
				encontrado = true;
				break;
			}
		}
		
		if (!encontrado) {
			miValAcorde = new ValorAcordes(acorde, valor, estilo);
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
			System.out.println(va.getAcordeSecundario() + "  " + va.getEstilo() + "  [ " + va.getValor() + " " + va.getValorAcumulado() + " ]");
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
		this.setContador(acumulador);
		
	}
	
	/**---------------------------------------------------------------------------
	  * 
	  *---------------------------------------------------------------------------*/
	public void calcularAcumulados(String estilo){
		
		int acumulador=0;
		ArrayList<ValorAcordes> listaAcordes = this.getListaOcurrencias();
		
		for (ValorAcordes va : listaAcordes) {
			if (va.getEstilo().equalsIgnoreCase(estilo)) {
				acumulador = acumulador + va.getValor();
				va.setValorAcumulado(acumulador);
			}
		}
		this.setContador(acumulador);
		
	}
	
	/**---------------------------------------------------------------------------
	  * @param contador
	  *---------------------------------------------------------------------------*/
	public void setContador(int contador) {
		this.contador = contador;
	}
	
	/**---------------------------------------------------------------------------
	  * devuelve el acumulador total 
	  * @return
	  *---------------------------------------------------------------------------*/
	public int getContador() {
		return contador;
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

}
